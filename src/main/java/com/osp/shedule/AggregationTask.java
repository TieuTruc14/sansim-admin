package com.osp.shedule;

import com.osp.common.Constants;
import com.osp.common.DateUtils;
import com.osp.common.PagingResult;
import com.osp.modelCustomer.Aggregation;
import com.osp.modelCustomer.MsisdnLog;
import com.osp.web.dao.aggregation.AggregationDao;
import com.osp.web.service.customer.CustomerService;
import com.osp.web.service.msisdn.MsisdnLogService;
import com.osp.web.service.msisdn.StockMsisdnService;
import com.osp.web.service.transpay.TranspayService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by Admin on 1/26/2018.
 */
@Component
public class AggregationTask {
    private boolean yesterday=true;//true tuc can tinh lai cho ngay truoc do
    private Date from=null;
    private Date to=null;

    private Logger logger= LogManager.getLogger(AggregationTask.class);
    @Autowired
    CustomerService customerService;
    @Autowired
    StockMsisdnService stockMsisdnService;
    @Autowired
    MsisdnLogService msisdnLogService;
    @Autowired
    TranspayService transpayService;
    @Autowired
    AggregationDao aggregationDao;


    @Scheduled(fixedDelay = 3600000)
    @Transactional(rollbackFor = Exception.class)
    public void scheduleFixedDelayTask() throws Exception {
        try{
            Aggregation itemYesterday=new Aggregation();
            Aggregation itemToday=new Aggregation();
            Calendar cal = Calendar.getInstance();
            if(yesterday){
                genFromAndTo(yesterday);//gen ngay hom qua
                cal.setTime(from);
                itemYesterday=aggregationDao.getByDay(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH)).orElse(new Aggregation());
                itemYesterday=genAggregation(itemYesterday);
                if(itemYesterday.getId()!=null && itemYesterday.getId()>0){
                    aggregationDao.edit(itemYesterday);
                }else{
                    aggregationDao.add(itemYesterday);
                }
            }
            genFromAndTo(false);
            cal.setTime(from);
            itemToday=aggregationDao.getByDay(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH)).orElse(new Aggregation());
            itemToday=genAggregation(itemToday);
            if(itemToday.getId()!=null && itemToday.getId().longValue()>0){
                aggregationDao.edit(itemToday);
            }else{
                aggregationDao.add(itemToday);
            }
            //moi thu xong xuoi, ko tinh cho hom trc nua
            yesterday=false;
        }catch (Exception e){
            logger.error("Have an error on method scheduleFixedDelayTask: "+e.getMessage());
            throw new Exception();
        }
    }

    //thuc hien moi ngay vao 0h10 se refresh yesterday=true
    @Scheduled(cron = "0 0/10 0 * * *")
    public void scheduleTaskUsingCronExpression() {
        yesterday=true;
    }

    // "0 0 * * * *" = the top of every hour of every day.
    // "*/10 * * * * *" = every ten seconds.
    //        * "0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.
     //       * "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30 and 10 o'clock every day.
     //       * "0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays
// "0 0 0 25 12 ?" = every Christmas Day at midnight
   // Cron expression is represented by six fields:

   // second, minute, hour, day of month, month, day(s) of week

    private void genFromAndTo(boolean yesterday) throws Exception{
        Date date=new Date();
        try{
            if(yesterday){//tuc xet cho yesterday
                date= DateUtils.subtractDays(date,1);
            }
            SimpleDateFormat fm = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String dateStr= DateUtils.dateToStr(date,"dd/MM/yyyy");
            from=fm.parse(dateStr+" 00:00:00");
            to=fm.parse(dateStr+" 23:59:59");
        }catch (Exception e){
            throw new Exception();
        }
    }
    private Aggregation genAggregation(Aggregation item) throws Exception{
        //tong so khach hang
        item.setCusTotal(totalCus());
        //tong khach hang active
        item.setCusActive(totalCusActive());
        //tinh luong khach hang moi dang ky
        item.setCusNew(getCustomerNew());
        //tong so thue bao
        item.setMsisdnTotal(totalMsisdn());
        item.setMsisdnActive(totalMsisdnActive());
        //tinh luong so dang ky moi
        item.setMsisdnNew(getMsisdnNew());
        //tinh luong so dang ky moi thuc su ko ke dang trung lap trc do
        item.setMsisdnNewReal(getMsisdnNewReal(item.getMsisdnNew()));
        //so tin nhan dang ky/gia han goi cuoc/xac thuc/gia han xac thuc trong ngay/ bi huy do het han/khach tu huy
        item.setRegisterPackNumber(countTranspayByType(Constants.TranSpay.DangKy));
        item.setRenewalPackNumber(countTranspayByType(Constants.TranSpay.GiaHan));
        item.setMsisdnConfirm(countTranspayByType(Constants.TranSpay.XacThuc));
        item.setRenewalConfirm(countTranspayByType(Constants.TranSpay.GiaHanXacThuc));
        item.setDestroyPackNumber(countTranspayByType(Constants.TranSpay.HuyHeThong));
        item.setDestroyCusPackNumber(countTranspayByType(Constants.TranSpay.NguoiDungHuy));
        //doanh thu dang ky/gia han/xac thuc/gia han xac thuc
        item.setRegisterRevenue(revenueByType(Constants.TranSpay.DangKy));
        item.setRenewalRevenue(revenueByType(Constants.TranSpay.GiaHan));
        item.setAuthenticationRevenue(revenueByType(Constants.TranSpay.XacThuc));
        item.setRenewalAuthenticationRevenue(revenueByType(Constants.TranSpay.GiaHanXacThuc));
        item.setRevenueTotal(item.getRegisterRevenue()+item.getRenewalRevenue()+item.getAuthenticationRevenue()+item.getRenewalAuthenticationRevenue());
        //gen info date
        genDateInfo(item);
        return item;
    }
    //tong so khach hang
    private long totalCus(){
        Long count=customerService.totalCus(to).orElse(0L);
        return (count!=null && count.longValue()>0)? count.longValue():0;
    }
    //tong so khach hang active
    private long totalCusActive(){
        Long count=customerService.totalCusByStatus(to,Byte.valueOf((byte)1)).orElse(0L);
        return (count!=null && count.longValue()>0) ?count.longValue():0;
    }
    //so luong khach hang moi
    private int getCustomerNew(){
        Long count=customerService.countCusByGenDate(from,to).orElse(0L);
        if(count==null) count=0L;
        return count.intValue();
    }

    //tong so thue bao
    private long totalMsisdn(){
        Long count=stockMsisdnService.totalMsisdnLessTime(to).orElse(0L);
        return (count!=null && count.longValue()>0)? count.longValue():0;
    }
    //tong so thue bao active
    private long totalMsisdnActive(){
        Long count=stockMsisdnService.totalMsisdnStatusLessTime(to,Byte.valueOf((byte)1)).orElse(0L);
        return (count!=null && count.longValue()>0)? count.longValue():0;
    }

    //so luong so dang trong ngay(tinh ca trung so voi so cu)
    private int getMsisdnNew(){
        Long count=stockMsisdnService.countInTime(from,to).orElse(0L);
        if(count==null) count=0L;
        return count.intValue();
    }

    //so luong so dang moi trong ngay(ko tinh so trung voi trc do)
    private int getMsisdnNewReal(int msisdnNumber) throws Exception{
        int countReal=0;
        PagingResult page=new PagingResult();
        page.setRowCount(msisdnNumber);
        page.setNumberPerPage(50);
        HashMap<String,Long> msisdns=new HashMap<>();
        String msisdn="";
        Long cusId=0L;
        //duyet lay doi tuong theo tung page, tranh lay nhieu qua
        for(int i=0;i<page.getPageCount();i++){
            int notRealNumber=0;
            page.setPageNumber(i+1);
            page=stockMsisdnService.page(page,from,to).orElse(new PagingResult());
            if(page.getItems().size()>0){
                msisdns=new HashMap<>();
                for(int j=0;j<page.getItems().size();j++){
                    Object[] item=(Object[]) page.getItems().get(j);
                    msisdn=String.valueOf(item[2]);
                    cusId=Long.valueOf(String.valueOf(item[1]));
                    msisdns.put(cusId+"-"+msisdn,cusId);
                }
                List<MsisdnLog> logs=msisdnLogService.getBylistMsisdn(msisdns).orElse(new ArrayList<>());
                if(logs!=null && logs.size()>0){
                    for(MsisdnLog item:logs){
                        for(int k=0;k<page.getItems().size();k++){
                            Object[] itemPage=(Object[])page.getItems().get(k);
                            if(item.getMsisdn().equals(String.valueOf(itemPage[2])) && item.getCustomer().getId()==Long.valueOf(String.valueOf(itemPage[1]))){
                                long id=Long.parseLong(String.valueOf(itemPage[0]));
                                //khac id la ko phai
                                if(id!=item.getSrcId().longValue()){
                                    msisdns.put(item.getCustomer().getId()+"-"+item.getMsisdn(),-1L);
                                }
                            }
                        }
                    }
                    for (Map.Entry<String, Long> entry : msisdns.entrySet()) {
                        if(entry.getValue().intValue()==-1){
                            notRealNumber++;
                        }
                    }

                }
            }

            countReal+=(msisdns.size()-notRealNumber);
        }

        return countReal;
    }

    //so ban tin trong ngay theo type
    private int countTranspayByType(Byte type){
        Long count=transpayService.countTranspayByType(type,from,to).orElse(0L);
        if(count==null) count=0L;
        return count.intValue();
    }

    private long revenueByType(Byte type){
        Long revenue=transpayService.revenueByType(type,from,to).orElse(0L);
        return revenue==null? 0:revenue.longValue();
    }

    private void genDateInfo(Aggregation item){
        Calendar cal = Calendar.getInstance();
        cal.setTime(from);
        int day_of_month=cal.get(Calendar.DAY_OF_MONTH);
        int week_of_year=cal.get(Calendar.WEEK_OF_YEAR);
        int month=cal.get(Calendar.MONTH)+1;
        int year=cal.get(Calendar.YEAR);
        int quarter=cal.get(Calendar.MONTH)/3+1;
        java.sql.Date date=new java.sql.Date(from.getTime());
        item.setDayOfMonth(day_of_month);
        item.setWeekOfYear(week_of_year);
        item.setMonth(month);
        item.setQuarter(quarter);
        item.setYear(year);
        item.setDate(date);
    }
//
//    public static void main(String[] args){
//        try{
//            Calendar cal = Calendar.getInstance();
//            Date date1=DateUtils.strToDate("12-01-2017","dd-MM-yyyy");
//            cal.setTime(date1);
//            for(int i=0;i<200;i++){
//                System.out.println(cal.get(Calendar.DAY_OF_MONTH)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.YEAR));
//                cal.add(Calendar.DATE,-1);
//            }
//        }catch (Exception e){
//
//        }
//    }

//    @Scheduled(fixedDelay = 3600000)
//    @Transactional(rollbackFor = Exception.class)
//    public void TestTask() throws Exception {
//        try{
//            Calendar cal = Calendar.getInstance();
//            Date date1=DateUtils.strToDate("02-02-2018","dd-MM-yyyy");
//            cal.setTime(date1);
//            List<Aggregation> items=new ArrayList<>();
//
//            for(int i=0;i<800;i++){
//                Aggregation item=new Aggregation();
//                item.setCusTotal(random(30000,60000));
//                item.setCusActive(item.getCusTotal()-random(2000,5000));
//                item.setCusNew(random(30,80));
//                item.setMsisdnNew(random(80,700));
//                item.setMsisdnTotal(random(100000,300000));
//                item.setMsisdnActive(item.getMsisdnTotal()-random(10000,30000));
//                item.setMsisdnNewReal(item.getMsisdnNew()-random(0,50));
//                item.setMsisdnConfirm(random(70,430));
//                item.setRenewalConfirm(random(30,300));
//                item.setRegisterPackNumber(random(140,500));
//                item.setRenewalPackNumber(random(40,150));
//                item.setDestroyPackNumber(random(50,400));
//                item.setDestroyCusPackNumber(random(40,400));
//                item.setRegisterRevenue(random(2000000,100000000));
//                item.setRenewalRevenue(random(2000000,30000000));
//                item.setAuthenticationRevenue(random(1000000,10000000));
//                item.setRenewalAuthenticationRevenue(random(500000,10000000));
//                Date da=cal.getTime();
//                genDateInfoTest(item,da);
//                items.add(item);
////                aggregationDao.add(item);
//                cal.add(Calendar.DATE,-1);
//                System.out.println(cal.get(Calendar.DAY_OF_MONTH)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.YEAR));
//            }
//            aggregationDao.addList(items);
//        }catch (Exception e){
//            e.printStackTrace();
//            logger.error("Have an error on method scheduleFixedDelayTask: "+e.getMessage());
//            throw new Exception();
//        }
//    }
//
//    private void genDateInfoTest(Aggregation item,Date dateTest){
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(dateTest);
//        int day_of_month=cal.get(Calendar.DAY_OF_MONTH);
//        int week_of_year=cal.get(Calendar.WEEK_OF_YEAR);
//        int month=cal.get(Calendar.MONTH)+1;
//        int year=cal.get(Calendar.YEAR);
//        int quarter=cal.get(Calendar.MONTH)/3+1;
//        java.sql.Date date=new java.sql.Date(dateTest.getTime());
//        item.setDay(day_of_month);
//        item.setWeek(week_of_year);
//        item.setMonth(month);
//        item.setQuarter(quarter);
//        item.setYear(year);
//        item.setDate(date);
//    }
//
//    private int random(int min,int max){
//        Random rand=new Random();
//        return rand.nextInt(max-min)+min;
//    }
}
