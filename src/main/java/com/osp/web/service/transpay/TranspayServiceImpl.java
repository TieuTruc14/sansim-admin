package com.osp.web.service.transpay;

import com.osp.common.DateUtils;
import com.osp.common.PagingResult;
import com.osp.web.dao.transpay.TranspayDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * Created by Admin on 1/10/2018.
 */
@Service
public class TranspayServiceImpl implements TranspayService {
    @Autowired
    TranspayDao transpayDao;
    @Override
    public Optional<PagingResult> page(PagingResult page, String msisdn, String username, String from, String to,Long packageId, Byte type) {
        try{
            Date fromDate=DateUtils.genDate(from,true);
            Date toDate=DateUtils.genDate(to,false);
            return transpayDao.page(page,msisdn,username,fromDate,toDate,packageId,type);
        }catch (Exception e){
            return Optional.ofNullable(null);
        }

    }

    @Override
    public Optional<PagingResult> pageReportGeneralSaleAndTrade(PagingResult page, Byte type,boolean destroy, String username, String from, String to) {
        try{
            Date fromDate=DateUtils.genDate(from,true);
            Date toDate=DateUtils.genDate(to,false);
            return transpayDao.pageReportGeneralSaleAndTrade(page,type,destroy,username,fromDate,toDate);
        }catch (Exception e){
            return Optional.ofNullable(null);
        }
    }

    @Override
    public Optional<PagingResult> downloadReportGeneralSaleAndTrade(PagingResult page, Byte type,boolean destroy, String username, String from, String to) {
        try{
            Date fromDate=DateUtils.genDate(from,true);
            Date toDate=DateUtils.genDate(to,false);
            return transpayDao.downloadReportGeneralSaleAndTrade(page,type,destroy,username,fromDate,toDate);
        }catch (Exception e){
            return Optional.ofNullable(null);
        }
    }

    @Override
    public Optional<PagingResult> pageReportDetailSaleAndTrade(PagingResult page, Byte type,boolean destroy, String username, String from, String to,Long packageId) {
        try{
            Date fromDate=DateUtils.genDate(from,true);
            Date toDate=DateUtils.genDate(to,false);
            return transpayDao.pageReportDetailSaleAndTrade(page,type,destroy,username,fromDate,toDate,packageId);
        }catch (Exception e){
            return Optional.ofNullable(null);
        }
    }

    @Override
    public Optional<PagingResult> downloadReportDetailSaleAndTrade(PagingResult page, Byte type,boolean destroy, String username, String from, String to, Long packageId) {
        try{
            Date fromDate=DateUtils.genDate(from,true);
            Date toDate=DateUtils.genDate(to,false);
            return transpayDao.downloadReportDetailSaleAndTrade(page,type,destroy,username,fromDate,toDate,packageId);
        }catch (Exception e){
            return Optional.ofNullable(null);
        }

    }

    @Override
    public Optional<Boolean> checkTranspayByConfigPackage(Long id) {
        return transpayDao.checkTranspayByConfigPackage(id);
    }

    @Override
    public Optional<Long> countTranspayByType(Byte type, Date from, Date to) {
        return transpayDao.countTranspayByType(type,from,to);
    }

    @Override
    public Optional<Long> revenueByType(Byte type, Date from, Date to) {
        return transpayDao.revenueByType(type,from,to);
    }
}
