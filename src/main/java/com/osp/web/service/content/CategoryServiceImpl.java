package com.osp.web.service.content;

import com.osp.common.PagingResult;
import com.osp.model.User;
import com.osp.modelCustomer.Category;
import com.osp.web.dao.content.CategoryDao;
import com.osp.web.service.logaccess.LogAccessService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 2/26/2018.
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    private Logger logger= LogManager.getLogger(CategoryServiceImpl.class);
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    LogAccessService logAccessService;

    @Override
    public Optional<PagingResult> page(PagingResult page, String name) {
        return categoryDao.page(page,name);
    }

    @Override
    public Optional<List<Category>> list() {
        return categoryDao.list();
    }

    @Override
    public Optional<Category> get(Integer id) {
        return categoryDao.get(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<Boolean> add(Category item, String ip) throws Exception{
        try{
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Integer value=categoryDao.getMaxValue().orElse(0);
            item.setValue(value.intValue()+1);
            item.setType(Byte.valueOf("1"));
            item.setDateCreated(new Date());
            item.setActive(true);
            item.setUserCreated(user.getId());
            item.setDateUpdated(new Date());
            item.setUserUpdated(user.getId());
            categoryDao.addWithWriteLog(item,ip);
        }catch (Exception e){
            logger.error("Have an error method addWithIp:"+e.getMessage());
            throw new Exception();
        }
        return Optional.of(true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<Boolean> edit(Category item, String ip) throws Exception {
        try{
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Category itemDB=categoryDao.get(item.getId()).orElse(null);
            if(itemDB==null) return Optional.of(false);
            item.setDateUpdated(new Date());
            item.setUserUpdated(user.getId());
            genInfo(itemDB,item);
            logAccessService.addLog("Sửa Chuyên mục, Id:"+itemDB.getId(),"Chuyên mục bài viết",ip);
            categoryDao.edit(itemDB);
        }catch (Exception e){
            logger.error("Have an error method edit:"+e.getMessage());
            throw new Exception();
        }
        return Optional.of(true);
    }

    private void genInfo(Category itemDB,Category item){
        itemDB.setName(item.getName());
        itemDB.setActive(item.isActive());
        itemDB.setDescription(item.getDescription());
    }

    @Override
    public Optional<Boolean> delete(Integer id,String ip) throws Exception{
        try{
            Category item=categoryDao.get(id).orElse(null);
            if(item==null) return Optional.ofNullable(false);
            item.setDeleted(true);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            item.setDateUpdated(new Date());
            item.setUserUpdated(user.getId());
            logAccessService.addLog("Xóa Chuyên mục, Id:"+item.getId(),"Chuyên mục bài viết",ip);
            categoryDao.edit(item);
        }catch (Exception e){
            logger.error("Have an error method delete:"+e.getMessage());
            throw new Exception();
        }
        return  Optional.of(true);
    }
}
