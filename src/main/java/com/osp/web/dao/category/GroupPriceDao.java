package com.osp.web.dao.category;

import com.osp.common.PagingResult;
import com.osp.modelCustomer.GroupPrice;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 2/6/2018.
 */
public interface GroupPriceDao {
    Optional<PagingResult> page(PagingResult page,String name);
    Optional<GroupPrice> get(Integer id);
    Optional<Boolean> add(GroupPrice item);
    Optional<Boolean> addWithWriteLog(GroupPrice item,String ipClient) throws Exception;
    Optional<Boolean> edit(GroupPrice item) throws Exception;
    Optional<Boolean> editList(List<GroupPrice> items);
    Optional<List<GroupPrice>> list();
    Optional<Boolean> delete(Integer id) throws Exception;
}
