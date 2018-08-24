package com.osp.web.dao.category;

import com.osp.common.PagingResult;
import com.osp.modelCustomer.GroupMsisdn;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 2/6/2018.
 */
public interface GroupMsisdnDao {
    Optional<List<GroupMsisdn>> list();
    Optional<PagingResult> page(PagingResult page, String name);
    Optional<Boolean> editList(List<GroupMsisdn> items);
}
