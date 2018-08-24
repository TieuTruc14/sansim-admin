package com.osp.web.dao.content;

import com.osp.common.PagingResult;
import com.osp.modelCustomer.Article;

import java.util.Optional;

/**
 * Created by Admin on 2/26/2018.
 */
public interface ArticleDao {
    Optional<PagingResult> page(PagingResult page, String name,Integer categoryId);
    Optional<Article> get(Integer id);
    Optional<Boolean> addWithWriteLog(Article item, String ipClient) throws Exception;
    Optional<Boolean> edit(Article item) throws Exception;
}
