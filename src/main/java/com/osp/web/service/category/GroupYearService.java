package com.osp.web.service.category;

import com.osp.common.PagingResult;
import com.osp.modelCustomer.GroupYear;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 2/6/2018.
 */
public interface GroupYearService {
    Optional<PagingResult> page(PagingResult page, String name);
    Optional<GroupYear> get(Integer id);
    Optional<Boolean> addWithIp(GroupYear item,String ipClient) throws Exception;
    Optional<Boolean> edit(GroupYear item,String ip) throws Exception;
    Optional<Boolean> editList(List<GroupYear> items);
    Optional<List<GroupYear>> list();
    Optional<Boolean> delete(Integer id,String ip) throws Exception;
}
