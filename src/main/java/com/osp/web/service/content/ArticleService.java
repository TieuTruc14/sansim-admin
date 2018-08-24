package com.osp.web.service.content;

import com.osp.common.PagingResult;
import com.osp.modelCustomer.Article;

import java.util.Optional;

/**
 * Created by Admin on 2/26/2018.
 */
public interface ArticleService {
    Optional<PagingResult> page(PagingResult page, String name,Integer categoryId);
    Optional<Article> get(Integer id);
    Optional<Boolean> add(Article item,String ip) throws Exception;
    Optional<Boolean> edit(Article item,String ip) throws Exception;
    Optional<Boolean> delete(Integer id,String ip) throws Exception;
}
