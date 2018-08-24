package com.osp.web.service.customer;

import com.osp.common.DateUtils;
import com.osp.common.PagingResult;
import com.osp.web.dao.customer.CustomerDao;
import com.osp.modelCustomer.Customer;
import com.osp.web.service.logaccess.LogAccessService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 12/18/2017.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerDao customerDao;
    @Autowired
    LogAccessService logAccessService;

    @Override
    public Optional<PagingResult> page(PagingResult page, Long msisdn, String username, String fullName, String packageCode, Byte active) {

        return customerDao.page(page,msisdn,username,fullName,packageCode,active);
    }
    @Override
    public Optional<Customer> get(Long id){
        return customerDao.get(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<Boolean> lockOrUnlock(Long id,Byte active,String ip) throws Exception{
        Customer item=customerDao.get(id).orElse(null);
        if(item==null || active==null || StringUtils.isBlank(ip)) throw new Exception();
        item.setActive(active);
        logAccessService.addLog("Khóa khách hàng:"+id.longValue()+"--"+item.getUsername(),"Khách hàng",ip);
        customerDao.edit(item);
        return customerDao.edit(item);
    }

    @Override
    public Optional<List<String>> searchUsername(String name) {
        return customerDao.searchUsername(name);
    }

    @Override
    public Optional<Long> countCusByGenDate(Date from,Date to){
        return customerDao.countCusToday(from,to);
    }

    @Override
    public Optional<Long> totalCus(Date to) {
        return customerDao.totalCus(to);
    }

    @Override
    public Optional<Long> totalCusByStatus(Date to,Byte active) {
        return customerDao.totalCusByStatus(to,active);
    }
}
