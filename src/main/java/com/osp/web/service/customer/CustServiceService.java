package com.osp.web.service.customer;

import com.osp.modelCustomer.CustService;

import java.util.Optional;

/**
 * Created by Admin on 6/21/2018.
 */
public interface CustServiceService {
    Optional<Boolean> add(CustService item);
}
