package com.osp.web.service.transpay;

import com.osp.common.PagingResult;

import java.util.Date;
import java.util.Optional;

/**
 * Created by Admin on 1/10/2018.
 */
public interface TranspayService {
    Optional<PagingResult> page(PagingResult page, String msisdn, String username, String from, String to, Long packageId, Byte type);
    Optional<PagingResult> pageReportGeneralSaleAndTrade(PagingResult page, Byte type,boolean destroy, String username, String from, String to);
    Optional<PagingResult> downloadReportGeneralSaleAndTrade(PagingResult page, Byte type,boolean destroy, String username, String from, String to);
    Optional<PagingResult> pageReportDetailSaleAndTrade(PagingResult page,Byte type,boolean destroy,String username,String from,String to,Long packageId);
    Optional<PagingResult> downloadReportDetailSaleAndTrade(PagingResult page,Byte type,boolean destroy,String username,String from,String to,Long packageId);
    Optional<Boolean> checkTranspayByConfigPackage(Long id);
    Optional<Long> countTranspayByType(Byte type,Date from, Date to);
    Optional<Long> revenueByType(Byte type,Date from, Date to);
}
