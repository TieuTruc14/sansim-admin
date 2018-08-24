package com.osp.web.service.content;

import com.osp.common.PagingResult;
import com.osp.model.User;
import com.osp.modelCustomer.Article;
import com.osp.web.dao.content.ArticleDao;
import com.osp.web.service.logaccess.LogAccessService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * Created by Admin on 2/26/2018.
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    private Logger logger= LogManager.getLogger(ArticleServiceImpl.class);
    @Autowired
    ArticleDao articleDao;
    @Autowired
    LogAccessService logAccessService;

    @Override
    public Optional<PagingResult> page(PagingResult page, String name,Integer categoryId) {
        return articleDao.page(page,name,categoryId);
    }

    @Override
    public Optional<Article> get(Integer id) {
        return articleDao.get(id);
    }

    @Override
    public Optional<Boolean> add(Article item, String ip) throws Exception {
        try{
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            item.setDateCreated(new Date());
            item.setActive(true);
            item.setUserCreated(user.getId());
            item.setDateUpdated(new Date());
            item.setUserUpdated(user.getId());
            articleDao.addWithWriteLog(item,ip);
        }catch (Exception e){
            logger.error("Have an error method addWithIp:"+e.getMessage());
            throw new Exception();
        }
        return Optional.of(true);
    }

    @Override
    public Optional<Boolean> edit(Article item, String ip) throws Exception {
        try{
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Article itemDB=articleDao.get(item.getId()).orElse(null);
            if(itemDB==null) return Optional.of(false);
            item.setDateUpdated(new Date());
            item.setUserUpdated(user.getId());
            genInfo(itemDB,item);
            logAccessService.addLog("Sửa bài viết, Id:"+itemDB.getId(),"Bài viết",ip);
            articleDao.edit(itemDB);
        }catch (Exception e){
            logger.error("Have an error method edit:"+e.getMessage());
            throw new Exception();
        }
        return Optional.of(true);
    }

    private void genInfo(Article itemDB,Article item){
        itemDB.setTitle(item.getTitle());
        itemDB.setActive(item.getActive());
        itemDB.setImageUrl(item.getImageUrl());
        itemDB.setAltImage(item.getAltImage());
        itemDB.setCategory(item.getCategory());
        itemDB.setContent(item.getContent());
        itemDB.setMetaDescription(item.getMetaDescription());
        itemDB.setMetaKeyword(item.getMetaKeyword());
        itemDB.setMetaTitle(item.getMetaTitle());
        itemDB.setShortContent(item.getShortContent());
        itemDB.setUserUpdated(item.getUserUpdated());
        itemDB.setDateUpdated(item.getDateUpdated());

    }
    @Override
    public Optional<Boolean> delete(Integer id, String ip) throws Exception {
        try{
            Article item=articleDao.get(id).orElse(null);
            if(item==null) return Optional.ofNullable(false);
            item.setDeleted(true);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            item.setDateUpdated(new Date());
            item.setUserUpdated(user.getId());
            logAccessService.addLog("Xóa bài viết, Id:"+item.getId(),"Bài viết",ip);
            articleDao.edit(item);
        }catch (Exception e){
            logger.error("Have an error method delete:"+e.getMessage());
            throw new Exception();
        }
        return  Optional.of(true);
    }
}
