package com.osp.web.service.msisdn;

import com.osp.common.PagingResult;
import com.osp.modelCustomer.MsisdnLog;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 1/15/2018.
 */
public interface MsisdnLogService {
    Optional<MsisdnLog> get(Long id);
    Optional<PagingResult> pageReport(PagingResult page, Byte type, String username, String from, String to);
    Optional<PagingResult> downloadReport(PagingResult page, Byte type, String username, String from, String to);
    Optional<List<MsisdnLog>> getBylistMsisdn(HashMap<String,Long> msisdns);
}
