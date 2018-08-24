package com.osp.web.service.msisdn;

import com.osp.common.PagingResult;
import com.osp.modelCustomer.StockMsisdn;

import java.util.Date;
import java.util.Optional;

/**
 * Created by Admin on 12/21/2017.
 */
public interface StockMsisdnService {

    Optional<StockMsisdn> get(Long id);
    Optional<PagingResult> page(PagingResult page, String username, String from, String to, String msisdn,Long price,Boolean confirmStatus,Integer confirmExpired,Byte telco) throws Exception;
    Optional<Long> countInTime(Date from, Date to);
    Optional<PagingResult> page(PagingResult page,Date from,Date to);
    Optional<Long> totalMsisdnLessTime(Date to);
    Optional<Long> totalMsisdnStatusLessTime(Date to,Byte status);

}
