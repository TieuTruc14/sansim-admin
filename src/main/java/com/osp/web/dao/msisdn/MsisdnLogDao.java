package com.osp.web.dao.msisdn;

import com.osp.common.PagingResult;
import com.osp.modelCustomer.MsisdnLog;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 1/15/2018.
 */
public interface MsisdnLogDao {
    Optional<MsisdnLog> get(Long id);
    Optional<PagingResult> pageReport(PagingResult page, Byte type, String username, Date from,Date to);
    Optional<PagingResult> downloadReport(PagingResult page, Byte type, String username, Date from,Date to);
    Optional<List<MsisdnLog>> getBylistMsisdn(HashMap<String,Long> msisdns);
}
