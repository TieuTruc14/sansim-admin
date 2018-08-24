package com.osp.web.service.momt;

import com.osp.common.PagingResult;
import com.osp.modelCustomer.MTLog;
import com.osp.modelCustomer.MTQueue;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 1/8/2018.
 */
public interface MOMTService {
    Optional<PagingResult> getMOMTofMsisdn(PagingResult page,Long msisdnId);
    Optional<PagingResult> page(PagingResult page,String username, String from, String to, String msisdn);
    Optional<Boolean> resendMT(Long id,String ip) throws Exception;
}
