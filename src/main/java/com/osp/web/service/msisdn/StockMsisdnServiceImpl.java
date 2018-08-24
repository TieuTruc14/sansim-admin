package com.osp.web.service.msisdn;

import com.osp.common.DateUtils;
import com.osp.common.PagingResult;
import com.osp.modelCustomer.StockMsisdn;
import com.osp.web.dao.msisdn.StockMsisdnDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * Created by Admin on 12/21/2017.
 */
@Service
public class StockMsisdnServiceImpl implements StockMsisdnService {

    @Autowired
    StockMsisdnDao msisdnDao;

    @Override
    public Optional<StockMsisdn> get(Long id) {
        Optional<StockMsisdn> sim=msisdnDao.get(id);
        return sim;
    }

    @Override
    public Optional<PagingResult> page(PagingResult page, String username, String from, String to, String msisdn, Long price, Boolean confirmStatus, Integer confirmExpired,Byte telco) throws Exception {
        try{
            Date dateExpired=null;
            Date fromDate=null;
            Date toDate=null;
            SimpleDateFormat fm = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            if(StringUtils.isNotBlank(from)){
                fromDate= fm.parse(from + " 00:00:00");
            }
            if(StringUtils.isNotBlank(to)){
                toDate=fm.parse(to+" 23:59:59");
            }
            if(confirmExpired!=null && confirmExpired.intValue()>0){
                dateExpired=new Date();
                dateExpired= DateUtils.addDays(dateExpired,confirmExpired.intValue());
                String date=DateUtils.dateToStr(dateExpired,"dd-MM-yyyy")+" 23:59:59";
                dateExpired=DateUtils.strToDate(date,"dd-MM-yyyy HH:mm:ss");
            }
            return msisdnDao.page(page,username,fromDate,toDate,msisdn,price,confirmStatus,dateExpired,telco);
        }catch (Exception e){
            throw new Exception();
        }

    }

    @Override
    public Optional<Long> countInTime(Date from, Date to) {
        return msisdnDao.countInTime(from,to);
    }

    @Override
    public Optional<PagingResult> page(PagingResult page, Date from, Date to) {
        return msisdnDao.page(page,from,to);
    }

    @Override
    public Optional<Long> totalMsisdnLessTime(Date to) {
        return msisdnDao.totalMsisdnLessTime(to);
    }

    @Override
    public Optional<Long> totalMsisdnStatusLessTime(Date to, Byte status) {
        return msisdnDao.totalMsisdnStatusLessTime(to,status);
    }
}
