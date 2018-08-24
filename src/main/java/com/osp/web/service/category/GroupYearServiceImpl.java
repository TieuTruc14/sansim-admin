package com.osp.web.service.category;

import com.osp.common.PagingResult;
import com.osp.model.User;
import com.osp.modelCustomer.GroupYear;
import com.osp.web.dao.category.GroupYearDao;
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
 * Created by Admin on 2/6/2018.
 */
@Service
public class GroupYearServiceImpl implements GroupYearService {
    private Logger logger= LogManager.getLogger(GroupYearServiceImpl.class);
    @Autowired
    GroupYearDao groupYearDao;
    @Autowired
    LogAccessService logAccessService;

    @Override
    public Optional<PagingResult> page(PagingResult page, String name) {
        return groupYearDao.page(page,name);
    }

    @Override
    public Optional<GroupYear> get(Integer id) {
        return groupYearDao.get(id);
    }

    @Override
    public Optional<Boolean> addWithIp(GroupYear item, String ipClient) throws Exception {
        try{
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            item.setOrderNumber(Short.valueOf("1"));
            item.setGenDate(new Date());
            item.setActive(true);
            item.setUserCreated(user.getId());
            item.setLastUpdated(new Date());
            item.setUserUpdated(user.getId());
            groupYearDao.addWithWriteLog(item,ipClient);
        }catch (Exception e){
            logger.error("Have an error method addWithIp:"+e.getMessage());
            throw new Exception();
        }
        return Optional.of(true);
    }

    @Override
    public Optional<Boolean> edit(GroupYear item, String ip) throws Exception {
        try{
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            GroupYear itemDB=groupYearDao.get(item.getId()).orElse(null);
            if(itemDB==null) return Optional.of(false);
            item.setLastUpdated(new Date());
            item.setUserUpdated(user.getId());
            genInfo(itemDB,item);
            logAccessService.addLog("Sửa nhóm sim theo năm GroupYear. Id:"+itemDB.getId(),"Danh mục sim theo năm",ip);
            groupYearDao.edit(itemDB);
        }catch (Exception e){
            logger.error("Have an error method edit:"+e.getMessage());
            throw new Exception();
        }
        return Optional.of(true);
    }

    private void genInfo(GroupYear itemDB,GroupYear item){
        itemDB.setName(item.getName());
        itemDB.setActive(item.isActive());
        itemDB.setDescription(item.getDescription());
    }

    @Override
    @Transactional
    public Optional<Boolean> editList(List<GroupYear> items) {
        return groupYearDao.editList(items);
    }

    @Override
    public Optional<List<GroupYear>> list() {
        return groupYearDao.list();
    }

    @Override
    public Optional<Boolean> delete(Integer id,String ip) throws Exception{
        try{
            GroupYear itemDB=groupYearDao.get(id).orElse(null);
            if(itemDB==null) return Optional.of(false);
            groupYearDao.delete(id);
            logAccessService.addLog("Xóa nhóm sim theo năm GroupYearId:"+id,"Danh mục nhóm sim theo năm",ip);
        }catch (Exception e){
            logger.error("Have an error method delete:"+e.getMessage());
            throw new Exception();
        }
        return Optional.of(true);
    }
}
