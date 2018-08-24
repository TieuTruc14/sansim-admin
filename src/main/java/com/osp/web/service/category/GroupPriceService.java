package com.osp.web.service.category;

import com.osp.common.ConstantAuthor;
import com.osp.common.PagingResult;
import com.osp.modelCustomer.GroupPrice;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 2/6/2018.
 */
public interface GroupPriceService {
    Optional<PagingResult> page(PagingResult page,String name);
    Optional<GroupPrice> get(Integer id);
    Optional<Boolean> addWithIp(GroupPrice item,String ipClient) throws Exception;
    Optional<Boolean> edit(GroupPrice item,String ip) throws Exception;
    Optional<Boolean> editList(List<GroupPrice> items);
    Optional<List<GroupPrice>> list();
    Optional<Boolean> delete(Integer id,String ip) throws Exception;

}
