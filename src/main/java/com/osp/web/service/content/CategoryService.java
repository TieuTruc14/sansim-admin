package com.osp.web.service.content;

import com.osp.common.PagingResult;
import com.osp.modelCustomer.Category;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 2/26/2018.
 */
public interface CategoryService {
    Optional<PagingResult> page(PagingResult page,String name);
    Optional<List<Category>> list();
    Optional<Category> get(Integer id);
    Optional<Boolean> add(Category item,String ip) throws Exception;
    Optional<Boolean> edit(Category item,String ip) throws Exception;
    Optional<Boolean> delete(Integer id,String ip) throws Exception;

}
