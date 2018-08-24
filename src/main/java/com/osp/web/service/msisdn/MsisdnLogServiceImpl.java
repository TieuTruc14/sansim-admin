package com.osp.web.service.msisdn;

import com.osp.common.DateUtils;
import com.osp.common.PagingResult;
import com.osp.modelCustomer.MsisdnLog;
import com.osp.web.dao.msisdn.MsisdnLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 1/15/2018.
 */
@Service
public class MsisdnLogServiceImpl implements MsisdnLogService {
    @Autowired
    MsisdnLogDao msisdnLogDao;

    @Override
    public Optional<MsisdnLog> get(Long id) {
        return msisdnLogDao.get(id);
    }

    @Override
    public Optional<PagingResult> pageReport(PagingResult page, Byte type, String username, String from, String to) {
        try{
            Date dateFrom= DateUtils.genDate(from,true);
            if(dateFrom==null) return Optional.ofNullable(null);
            Date dateTo=DateUtils.genDate(to,false);
            return msisdnLogDao.pageReport(page,type,username,dateFrom,dateTo);
        }catch (Exception e){
            return Optional.ofNullable(null);
        }
    }

    @Override
    public Optional<PagingResult> downloadReport(PagingResult page, Byte type, String username, String from, String to) {
        try{
            Date dateFrom= DateUtils.genDate(from,true);
            if(dateFrom==null) return Optional.ofNullable(null);
            Date dateTo=DateUtils.genDate(to,false);
            return msisdnLogDao.downloadReport(page,type,username,dateFrom,dateTo);
        }catch (Exception e){
            return Optional.ofNullable(null);
        }
    }

    @Override
    public Optional<List<MsisdnLog>> getBylistMsisdn(HashMap<String, Long> msisdns) {
        return msisdnLogDao.getBylistMsisdn(msisdns);
    }
}
