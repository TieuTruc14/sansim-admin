package com.osp.web.dao.customer;

import com.osp.modelCustomer.CustService;

import java.util.Optional;

/**
 * Created by Admin on 6/21/2018.
 */
public interface CustServiceDao {
    Optional<Boolean> add(CustService item);
    Optional<Boolean> delete(Long customerId);
}
