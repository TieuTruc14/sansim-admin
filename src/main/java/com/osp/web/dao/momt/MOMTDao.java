package com.osp.web.dao.momt;

import com.osp.common.PagingResult;
import com.osp.modelCustomer.MTLog;
import com.osp.modelCustomer.MTQueue;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 1/8/2018.
 */
public interface MOMTDao {
    Optional<PagingResult> getMOMTofMsisdn(PagingResult page,String msisdn, String username);
    Optional<PagingResult> page(PagingResult page, String username, Date from, Date to, String msisdn);
    Optional<MTLog> getMTLog(Long id);
    Optional<Boolean> addMTQueue(MTQueue item,String ip) throws Exception;
}
