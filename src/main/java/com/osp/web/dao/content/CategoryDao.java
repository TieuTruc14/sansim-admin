package com.osp.web.dao.content;

import com.osp.common.PagingResult;
import com.osp.modelCustomer.Category;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 2/26/2018.
 */
public interface CategoryDao {
    Optional<PagingResult> page(PagingResult page,String name);
    Optional<List<Category>> list();
    Optional<Category> get(Integer id);
    Optional<Integer> getMaxValue();
    Optional<Boolean> addWithWriteLog(Category item, String ipClient) throws Exception;
    Optional<Boolean> edit(Category item) throws Exception;

}
