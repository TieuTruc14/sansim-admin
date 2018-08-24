package com.osp.web.dao.customer;

import com.osp.common.PagingResult;
import com.osp.modelCustomer.Customer;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 12/18/2017.
 */
public interface CustomerDao {
    Optional<PagingResult> page(PagingResult page, Long msisdn, String username, String fullName, String packageCode, Byte active);
    Optional<Customer> get(Long id);
    Optional<Boolean> edit(Customer item) throws Exception;
    Optional<List<String>> searchUsername(String name);
    //
    Optional<Long> countCusToday(Date from,Date to);
    Optional<Long> totalCus(Date to);
    Optional<Long> totalCusByStatus(Date to,Byte active);
}
