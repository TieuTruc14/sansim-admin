package com.osp.web.service.category;

import com.osp.common.PagingResult;
import com.osp.modelCustomer.GroupMsisdn;
import com.osp.web.dao.category.GroupMsisdnDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 2/6/2018.
 */
@Service
public class GroupMsisdnServiceImpl implements GroupMsisdnService {
    @Autowired
    GroupMsisdnDao groupMsisdnDao;

    @Override
    public Optional<PagingResult> page(PagingResult page, String name) {
        return groupMsisdnDao.page(page,name);
    }

    @Override
    public Optional<List<GroupMsisdn>> list() {
        return groupMsisdnDao.list();
    }

    @Override
    @Transactional
    public Optional<Boolean> editList(List<GroupMsisdn> items) {
        return groupMsisdnDao.editList(items);
    }
}
