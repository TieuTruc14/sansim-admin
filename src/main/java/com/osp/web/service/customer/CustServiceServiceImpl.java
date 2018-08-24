package com.osp.web.service.customer;

import com.osp.modelCustomer.CustService;
import com.osp.web.dao.customer.CustServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by Admin on 6/21/2018.
 */
@Service
@Transactional
public class CustServiceServiceImpl implements CustServiceService {
    @Autowired
    CustServiceDao custServiceDao;

    @Override
    public Optional<Boolean> add(CustService item) {
        custServiceDao.delete(item.getCustomer().getId());
        return custServiceDao.add(item);
    }
}
