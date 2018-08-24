package com.osp.web.service.momt;

import com.osp.common.DateUtils;
import com.osp.common.PagingResult;
import com.osp.model.LogAccess;
import com.osp.model.User;
import com.osp.modelCustomer.MTLog;
import com.osp.modelCustomer.MTQueue;
import com.osp.modelCustomer.StockMsisdn;
import com.osp.web.dao.momt.MOMTDao;
import com.osp.web.service.logaccess.LogAccessService;
import com.osp.web.service.msisdn.StockMsisdnService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

/**
 * Created by Admin on 1/8/2018.
 */
@Service
public class MOMTServiceImpl implements MOMTService {

    @Autowired
    MOMTDao momtDao;
    @Autowired
    StockMsisdnService stockMsisdnService;

    @Override
    public Optional<PagingResult> getMOMTofMsisdn(PagingResult page, Long msisdnId) {
        StockMsisdn stockMsisdn = stockMsisdnService.get(msisdnId).orElse(null);
        if (stockMsisdn == null) {
            return null;
        }
        return momtDao.getMOMTofMsisdn(page, stockMsisdn.getMsisdn(), stockMsisdn.getCustomer().getUsername());
    }

    @Override
    public Optional<PagingResult> page(PagingResult page, String username, String from, String to, String msisdn) {
        try {
            Date fromDate = null;
            Date toDate = null;
            if (StringUtils.isNotBlank(from)) {
                fromDate = DateUtils.strToDate(from + " 00:00:00", "dd/MM/yyyy HH:mm:ss");
            }
            if (StringUtils.isNotBlank(to)) {
                toDate = DateUtils.strToDate(to + " 23:59:59", "dd/MM/yyyy HH:mm:ss");
            }
            return momtDao.page(page, username, fromDate, toDate, msisdn);
        } catch (Exception e) {
            return Optional.ofNullable(null);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<Boolean> resendMT(Long id, String ip) throws Exception {
        MTLog mtLog = momtDao.getMTLog(id).orElse(null);
        if (mtLog != null) {
            MTQueue item = genMTLog2MTQueue(mtLog);
            return momtDao.addMTQueue(item, ip);
        }
        return Optional.of(false);
    }

    private MTQueue genMTLog2MTQueue(MTLog mtLog) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MTQueue item = new MTQueue();
        item.setSenderNumber(mtLog.getSenderNumber());
        item.setReceiverNumber(mtLog.getReceiverNumber());
        item.setServiceNumber(mtLog.getServiceNumber());
        item.setMobileOperator(mtLog.getMobileOperator());
        item.setCommandCode(mtLog.getCommandCode());
        item.setContentType(mtLog.getContentType());
        item.setMessageType(mtLog.getMessageType());
        item.setInfo(mtLog.getInfo());
        item.setRequestId(mtLog.getRequestId());
        item.setSource(mtLog.getSourceMT());
        item.setRequestInfo(mtLog.getRequestInfo());
        item.setGenDate(new Date());
        item.setCreateBy(user.getUsername());
        item.setLastUpdated(new Date());

        return item;
    }
}
