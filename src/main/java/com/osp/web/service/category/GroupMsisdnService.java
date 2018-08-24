package com.osp.web.service.category;

import com.osp.common.PagingResult;
import com.osp.model.Group;
import com.osp.modelCustomer.GroupMsisdn;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 2/6/2018.
 */
public interface GroupMsisdnService {
    Optional<PagingResult> page(PagingResult page, String name);
    Optional<List<GroupMsisdn>> list();
    Optional<Boolean> editList(List<GroupMsisdn> items);
}
