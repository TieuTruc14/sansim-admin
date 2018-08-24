package com.osp.web.service.customer;

import com.osp.common.PagingResult;
import com.osp.modelCustomer.Customer;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 12/18/2017.
 */

public interface CustomerService {
     Optional<PagingResult> page(PagingResult page,Long msisdn,String username,String fullName,String packageCode,Byte active);
     Optional<Customer> get(Long id);
     Optional<Boolean> lockOrUnlock(Long id,Byte active,String ip) throws Exception;
     Optional<List<String>> searchUsername(String name);
     //dem so luong khach dang ky hom nay
     Optional<Long> countCusByGenDate(Date from,Date to) ;
     Optional<Long> totalCus(Date to);
     Optional<Long> totalCusByStatus(Date to,Byte active);
}
