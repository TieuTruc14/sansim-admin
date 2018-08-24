package com.osp.web.service.category;

import com.osp.common.PagingResult;
import com.osp.model.LogAccess;
import com.osp.model.User;
import com.osp.modelCustomer.GroupPrice;
import com.osp.web.dao.category.GroupPriceDao;
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
public class GroupPriceServiceImpl implements GroupPriceService {
    private Logger logger= LogManager.getLogger(GroupPriceServiceImpl.class);
    @Autowired
    GroupPriceDao groupPriceDao;
    @Autowired
    LogAccessService logAccessService;

    @Override
    public Optional<PagingResult> page(PagingResult page, String name) {
        return groupPriceDao.page(page,name);
    }

    @Override
    public Optional<GroupPrice> get(Integer id) {
        return groupPriceDao.get(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<Boolean> addWithIp(GroupPrice item,String ipClient) throws Exception {
        try{
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            item.setGenDate(new Date());
            item.setActive(true);
            item.setOrderNumber(Short.valueOf("1"));
            item.setUserCreated(user.getId());
            item.setLastUpdated(new Date());
            item.setUserUpdated(user.getId());
            groupPriceDao.addWithWriteLog(item,ipClient);
        }catch (Exception e){
            logger.error("Have an error method addWithIp:"+e.getMessage());
            throw new Exception();
        }
        return Optional.of(true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<Boolean> edit(GroupPrice item,String ip) throws Exception{
        try{
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            GroupPrice itemDB=groupPriceDao.get(item.getId()).orElse(null);
            if(itemDB==null) return Optional.of(false);
            item.setLastUpdated(new Date());
            item.setUserUpdated(user.getId());
            genInfo(itemDB,item);
            logAccessService.addLog("Sửa nhóm sim theo giá GroupPrice. Id:"+itemDB.getId(),"Danh mục sim theo giá",ip);
            groupPriceDao.edit(itemDB);
        }catch (Exception e){
            logger.error("Have an error method edit:"+e.getMessage());
            throw new Exception();
        }
        return Optional.of(true);
    }

    private void genInfo(GroupPrice itemDB,GroupPrice item){
        itemDB.setName(item.getName());
        itemDB.setActive(item.isActive());
        itemDB.setDescription(item.getDescription());
        itemDB.setMin(item.getMin());
        itemDB.setMax(item.getMax());
    }

    @Override
    @Transactional
    public Optional<Boolean> editList(List<GroupPrice> items) {
        return groupPriceDao.editList(items);
    }

    @Override
    public Optional<List<GroupPrice>> list() {
        return groupPriceDao.list();
    }

    @Override
    public Optional<Boolean> delete(Integer id,String ip) throws Exception {
        try{
            GroupPrice itemDB=groupPriceDao.get(id).orElse(null);
            if(itemDB==null) return Optional.of(false);
            groupPriceDao.delete(id);
            logAccessService.addLog("Xóa nhóm sim theo giá GroupPriceId:"+id,"Danh mục nhóm sim theo giá",ip);
        }catch (Exception e){
            logger.error("Have an error method delete:"+e.getMessage());
            throw new Exception();
        }
        return Optional.of(true);
    }
}
