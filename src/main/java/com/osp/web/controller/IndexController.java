package com.osp.web.controller;

import com.osp.common.DateUtils;
import com.osp.common.PagingResult;
import com.osp.model.User;
import com.osp.modelCustomer.Aggregation;
import com.osp.modelCustomer.view.BieuDo;
import com.osp.modelCustomer.view.CacGoiCuoc;
import com.osp.shedule.SunMoonMenu;
import com.osp.web.service.aggregation.AggregationService;
import com.osp.web.service.logaccess.LogAccessService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Admin on 12/14/2017.
 */
@Controller
@RequestMapping("/")
public class IndexController {
    private Logger logger= LogManager.getLogger(IndexController.class);
    @Autowired
    LogAccessService logAccessService;
    @Autowired
    AggregationService aggregationService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/")
    public String index(Model model) {
        try{
            Calendar cal=Calendar.getInstance();
            Aggregation item=aggregationService.getByDay(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH)).orElse(null);
            if(item==null || item.getId()==null){
                cal.add(Calendar.DATE,-1);
                item=aggregationService.getByDay(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH)).orElse(new Aggregation());
            }
            model.addAttribute("item",item);
            //tinh toan cho ngay cuoi tuan truoc do
            cal=Calendar.getInstance();//refresh time now
            cal.add(Calendar.DATE,-7);
            cal=DateUtils.lastDayOfWeek(cal);
            Aggregation itemLastWeek=aggregationService.getByDay(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH)).orElse(new Aggregation());

            if(item!=null && item.getId()!=null && itemLastWeek!=null && itemLastWeek.getId()!=null){
//                Float percentCusTotal= ((item.getCusTotal()*100.0f)/itemLastWeek.getCusTotal())/100;
                long percentCusTotal= item.getCusTotal()-itemLastWeek.getCusTotal();
                long percentCusActive= item.getCusActive()-itemLastWeek.getCusActive();
                long percentCusLock= (item.getCusTotal()-item.getCusActive())-(itemLastWeek.getCusTotal()-itemLastWeek.getCusActive());
                long percentMsisdnTotal= item.getMsisdnTotal()-itemLastWeek.getMsisdnTotal();
                long percentMsisdnActive= item.getMsisdnActive()-itemLastWeek.getMsisdnActive();
                long percentMsisdnLock= (item.getMsisdnTotal()-item.getMsisdnActive())-(itemLastWeek.getMsisdnTotal()-itemLastWeek.getMsisdnActive());
                model.addAttribute("percentCusTotal",percentCusTotal);
                model.addAttribute("percentCusActive",percentCusActive);
                model.addAttribute("percentCusLock",percentCusLock);
                model.addAttribute("percentMsisdnTotal",percentMsisdnTotal);
                model.addAttribute("percentMsisdnActive",percentMsisdnActive);
                model.addAttribute("percentMsisdnLock",percentMsisdnLock);
            }
        }catch (Exception e){

        }
        return "index";
    }


    /*For my history*/
    @GetMapping("/history")
    public String getOfUser(){
        return "my.history";
    }

    @GetMapping("/history/my-log")
    public ResponseEntity<PagingResult> logOfUser(@RequestParam(value = "p", required = false, defaultValue = "1")int pageNumber){
        PagingResult page=new PagingResult();
        page.setPageNumber(pageNumber);
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try{
            page=logAccessService.getByUserId(page,user.getId()).orElse(new PagingResult());
        }catch (Exception e){
        }
        return new ResponseEntity<PagingResult>(page, HttpStatus.OK);
    }

    //ham lay du lieu kiem tra leftmenu dang dong hay mo
    @GetMapping("/change-nav")
    public ResponseEntity<Boolean> changeNavXs(HttpServletRequest request){
        if(request.getSession().getAttribute("nav-xs")!=null){
            Boolean navXs=(Boolean)request.getSession().getAttribute("nav-xs");
            if(navXs!=null){
                request.getSession().setAttribute("nav-xs",!navXs);
            }
        }else{
            request.getSession().setAttribute("nav-xs",true);
        }
        return new ResponseEntity<Boolean>(true,HttpStatus.OK);
    }
//    //ham thay thoi thoi gian mau sac
//    @GetMapping("/sun-moon")
//    public ResponseEntity<Boolean> changeTime(HttpServletRequest request){
//        boolean sun= SunMoonMenu.sun;
//        request.getSession().setAttribute("sun-moon",sun);
//        return new ResponseEntity<Boolean>(sun,HttpStatus.OK);
//    }

    @GetMapping("/dashboard")
    public String dashboard(Model model){
        return "dashboard";
    }
    /*CAC HAM PHUC VU DASHBOARD BIEU DO*/

    @GetMapping("/trade-count")
    public ResponseEntity<CacGoiCuoc> getCacGoiCuoc(int year,@RequestParam(value = "month", required = false, defaultValue = "0")int month
            ,@RequestParam(value = "week", required = false, defaultValue = "0") int week){
        if(year==0) return new ResponseEntity<CacGoiCuoc>(new CacGoiCuoc(),HttpStatus.OK);
        CacGoiCuoc item=new CacGoiCuoc();
        try{
            if(week>0){
                item=aggregationService.soLuongGiaoDichTuanType(year,week).orElse(new CacGoiCuoc());
            }else if(month>0){
                item=aggregationService.soLuongGiaoDichThangType(year,month).orElse(new CacGoiCuoc());
            }else{
                item=aggregationService.soLuongGiaoDichNamType(year).orElse(new CacGoiCuoc());
            }
        }catch (Exception e){
            logger.error("Have an error in method: getCacGoiCuoc:"+e.getMessage());
        }

        return new ResponseEntity<CacGoiCuoc>(item,HttpStatus.OK);
    }

    @GetMapping("/revenue-count")
    public ResponseEntity<CacGoiCuoc> revenueCacGoiCuoc(int year,@RequestParam(value = "month", required = false, defaultValue = "0")int month
            ,@RequestParam(value = "week", required = false, defaultValue = "0") int week){
        if(year==0) return new ResponseEntity<CacGoiCuoc>(new CacGoiCuoc(),HttpStatus.OK);
        CacGoiCuoc item=new CacGoiCuoc();
        try{
            if(week>0){
                item=aggregationService.DoanhThuTuanType(year,week).orElse(new CacGoiCuoc());
            }else if(month>0){
                item=aggregationService.DoanhThuThangType(year,month).orElse(new CacGoiCuoc());
            }else{
                item=aggregationService.DoanhThuNamType(year).orElse(new CacGoiCuoc());
            }
        }catch (Exception e){
            logger.error("Have an error in method: revenueCacGoiCuoc:"+e.getMessage());
        }

        return new ResponseEntity<CacGoiCuoc>(item,HttpStatus.OK);
    }

    @GetMapping("/revenue")
    public ResponseEntity<BieuDo> revenue(int year, @RequestParam(value = "month", required = false, defaultValue = "0")int month
            , @RequestParam(value = "week", required = false, defaultValue = "0") int week){
        if(year==0) return new ResponseEntity<BieuDo>(new BieuDo(),HttpStatus.OK);
        List<Aggregation> list=new ArrayList<>();
        BieuDo item=new BieuDo();
        try{
            if(week>0){
                list=aggregationService.getAggregationWeek(year,week).orElse(new ArrayList<>());
                getBieuDoTuan(list,item,0);
            }else if(month>0){
                list=aggregationService.getAggregationMonth(year,month).orElse(new ArrayList<>());
                getBieuDoThang(list,item,0);
            }else{
                list=aggregationService.getAggregationYear(year).orElse(new ArrayList<>());
                getBieuDoNam(list,item,0);
            }

        }catch (Exception e){
            logger.error("Have an error in method: revenue:"+e.getMessage());
        }

        return new ResponseEntity<BieuDo>(item,HttpStatus.OK);
    }
    private void getBieuDoTuan(List<Aggregation> list,BieuDo bieudo,int loaigiatri){
        if(list!=null && list.size()>0){
            Calendar cal=Calendar.getInstance();
            List<String> names=new ArrayList<>();
            List<Long> values=new ArrayList<>();
            list.stream().forEach(item->{
                cal.setTime(item.getDate());
                switch (cal.get(Calendar.DAY_OF_WEEK)){
                    case Calendar.SUNDAY:
                        names.add("Sunday");
                        break;
                    case Calendar.MONDAY:
                        names.add("Monday");
                        break;
                    case Calendar.TUESDAY:
                        names.add("Tuesday");
                        break;
                    case Calendar.WEDNESDAY:
                        names.add("Wednesday");
                        break;
                    case Calendar.THURSDAY:
                        names.add("Thursday");
                        break;
                    case Calendar.FRIDAY:
                        names.add("Friday");
                        break;
                    case Calendar.SATURDAY:
                        names.add("Saturday");
                        break;
                    default:
                        names.add("00");
                        break;
                }
                switch (loaigiatri){
                    case 0:
                        values.add(item.getRevenueTotal());
                        break;
                    case 1:
                        values.add(Long.valueOf(item.getCusNew()));
                        break;
                    case 2:
                        values.add(Long.valueOf(item.getMsisdnNewReal()));
                        break;
                    default:break;
                }
            });
            bieudo.setNames(names);
            bieudo.setValues(values);
        }
    }

    private void getBieuDoThang(List<Aggregation> list,BieuDo bieudo,int loaigiatri){
        if(list!=null && list.size()>0){
            List<String> names=new ArrayList<>();
            List<Long> values=new ArrayList<>();
            switch (loaigiatri){
                case 0:
                    list.stream().forEach(item->{
                        names.add(String.valueOf(item.getDayOfMonth()));
                        values.add(item.getRevenueTotal());
                    });
                    break;
                case 1:
                    list.stream().forEach(item->{
                        names.add(String.valueOf(item.getDayOfMonth()));
                        values.add(Long.valueOf(item.getCusNew()));
                    });
                    break;
                case 2:
                    list.stream().forEach(item->{
                        names.add(String.valueOf(item.getDayOfMonth()));
                        values.add(Long.valueOf(item.getMsisdnNewReal()));
                    });
                    break;
                default:break;
            }

            bieudo.setNames(names);
            bieudo.setValues(values);
        }
    }

    private void getBieuDoNam(List<Aggregation> list,BieuDo bieudo,int loaigiatri){
        if(list!=null && list.size()>0){
            List<String> names=Arrays.asList("Jan", "Feb", "Mar","Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
            List<Long> values=new ArrayList<>();
            final long[] t={0,0,0,0,0,0,0,0,0,0,0,0};
            list.stream().forEach(item->{
                long giatri=0;
                switch (loaigiatri){
                    case 0:
                        giatri=item.getRevenueTotal();
                        break;
                    case 1:
                        giatri=item.getCusNew();
                        break;
                    case 2:
                        giatri=item.getMsisdnNewReal();
                        break;
                    default:break;
                }
                switch (item.getMonth()){
                    case 1:
                        t[0]+=giatri;
                        break;
                    case 2:
                        t[1]+=giatri;
                        break;
                    case 3:
                        t[2]+=giatri;
                        break;
                    case 4:
                        t[3]+=giatri;
                        break;
                    case 5:
                        t[4]+=giatri;
                        break;
                    case 6:
                        t[5]+=giatri;
                        break;
                    case 7:
                        t[6]+=giatri;
                        break;
                    case 8:
                        t[7]+=giatri;
                        break;
                    case 9:
                        t[8]+=giatri;
                        break;
                    case 10:
                        t[9]+=giatri;
                        break;
                    case 11:
                        t[10]+=giatri;
                        break;
                    case 12:
                        t[11]+=giatri;
                        break;
                    default:break;
                }
            });
            Arrays.stream(t).forEach(item->values.add(item));
            bieudo.setNames(names);
            bieudo.setValues(values);

        }
    }



    /*bieu do luong khach hang dang ky moi*/
    @GetMapping("/cus-new")
    public ResponseEntity<BieuDo> cusNew(int year, @RequestParam(value = "month", required = false, defaultValue = "0")int month
            , @RequestParam(value = "week", required = false, defaultValue = "0") int week){
        if(year==0) return new ResponseEntity<BieuDo>(new BieuDo(),HttpStatus.OK);
        List<Aggregation> list=new ArrayList<>();
        BieuDo result=new BieuDo();
        try{
            if(week>0){
                list=aggregationService.getAggregationWeek(year,week).orElse(new ArrayList<>());
                getBieuDoTuan(list,result,1);
            }else if(month>0){
                list=aggregationService.getAggregationMonth(year,month).orElse(new ArrayList<>());
                getBieuDoThang(list,result,1);
            }else{
                list=aggregationService.getAggregationYear(year).orElse(new ArrayList<>());
                getBieuDoNam(list,result,1);
            }

        }catch (Exception e){
            logger.error("Have an error in method: cusNew:"+e.getMessage());
        }

        return new ResponseEntity<BieuDo>(result,HttpStatus.OK);
    }

    @GetMapping("/msisdn-new")
    public ResponseEntity<BieuDo> msisdnNew(int year, @RequestParam(value = "month", required = false, defaultValue = "0")int month
            , @RequestParam(value = "week", required = false, defaultValue = "0") int week){
        if(year==0) return new ResponseEntity<BieuDo>(new BieuDo(),HttpStatus.OK);
        List<Aggregation> list=new ArrayList<>();
        BieuDo result=new BieuDo();
        try{
            if(week>0){
                list=aggregationService.getAggregationWeek(year,week).orElse(new ArrayList<>());
                getBieuDoTuan(list,result,2);
            }else if(month>0){
                list=aggregationService.getAggregationMonth(year,month).orElse(new ArrayList<>());
                getBieuDoThang(list,result,2);
            }else{
                list=aggregationService.getAggregationYear(year).orElse(new ArrayList<>());
                getBieuDoNam(list,result,2);
            }

        }catch (Exception e){
            logger.error("Have an error in method: msisdnNew:"+e.getMessage());
        }

        return new ResponseEntity<BieuDo>(result,HttpStatus.OK);
    }



}
