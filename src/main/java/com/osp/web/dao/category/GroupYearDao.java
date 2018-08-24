package com.osp.web.dao.category;

import com.osp.common.PagingResult;
import com.osp.modelCustomer.GroupYear;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 2/6/2018.
 */
public interface GroupYearDao {

    Optional<PagingResult> page(PagingResult page, String name);
    Optional<GroupYear> get(Integer id);
    Optional<Boolean> add(GroupYear item);
    Optional<Boolean> addWithWriteLog(GroupYear item,String ipClient) throws Exception;
    Optional<Boolean> edit(GroupYear item) throws Exception;
    Optional<Boolean> editList(List<GroupYear> items);
    Optional<List<GroupYear>> list();
    Optional<Boolean> delete(Integer id) throws Exception;
}
