package com.osp.web.service.msisdn;

import com.osp.common.PagingResult;
import com.osp.model.User;
import com.osp.modelCustomer.ConfigPackage;
import com.osp.web.dao.msisdn.ConfigPackageDao;
import com.osp.web.service.logaccess.LogAccessService;
import com.osp.web.service.transpay.TranspayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 1/3/2018.
 */
@Service
public class ConfigPackageServiceImpl implements ConfigPackageService {
    @Autowired
    ConfigPackageDao configPackageDao;
    @Autowired
    LogAccessService logAccessService;
    @Autowired
    TranspayService transpayService;

    @Override
    public Optional<PagingResult> page(String packageCode,String packageName,Long from,Long to, PagingResult page) {
        return configPackageDao.page(packageCode,packageName,from,to,page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<Boolean> add(ConfigPackage item, String ipClient) throws Exception {
        try{
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            item.setStatus(Byte.valueOf("1"));
            item.setCreateBy(user.getUsername());
            item.setGenDate(new Date());
            item.setLastUpdated(new Date());
            item.setUpdateBy(user.getUsername());
//            logAccessService.addLog("Thêm gói phí đăng số. Mã gói:"+id.longValue(),"Quản lý đăng số",ipClient);
            configPackageDao.add(item,ipClient).orElse(Long.valueOf(0));

        }catch (Exception e){
            throw new  Exception();
        }

        return Optional.of(true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<Boolean> edit(ConfigPackage item, String ipClient) throws Exception{
        try{
            ConfigPackage itemDB=configPackageDao.get(item.getId()).orElse(null);
            if(itemDB==null) return Optional.ofNullable(false);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            itemDB.setUpdateBy(user.getUsername());
            itemDB.setLastUpdated(new Date());
            itemDB.setPackageName(item.getPackageName());
            itemDB.setFee(item.getFee());
            itemDB.setMaxQuantity(item.getMaxQuantity());
            itemDB.setPeriod(item.getPeriod());
            logAccessService.addLog("Sửa gói phí đăng số. Mã gói "+item.getId(),"Quản lý đăng số",ipClient);
            configPackageDao.edit(itemDB);

        }catch (Exception e){
            throw new Exception();
        }

        return Optional.ofNullable(true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<Integer> delete(Long id, String ipClient) throws Exception {
        try {
            ConfigPackage itemDB=configPackageDao.get(id).orElse(null);
            if(itemDB==null) return Optional.ofNullable(0);
            if((new Date()).getTime()-itemDB.getGenDate().getTime()>604800000){
                return Optional.ofNullable(2);//ko cho xoa khi da tao qua 7 ngay
            }
            boolean checkDelete=transpayService.checkTranspayByConfigPackage(itemDB.getId()).orElse(true);
            if(checkDelete) return Optional.ofNullable(3);//ko cho xoa khi da co nguoi dang ky goi cuoc

            logAccessService.addLog("Thêm gói phí đăng số","Quản lý gói cước",ipClient);
            configPackageDao.delete(id);
        }catch (Exception e){
            throw new Exception();
        }
        return Optional.ofNullable(1);
    }

    @Override
    public Optional<Boolean> disable(Long id, String ipClient) throws Exception {
        try {
            ConfigPackage itemDB=configPackageDao.get(id).orElse(null);
            if(itemDB==null) return Optional.ofNullable(false);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            itemDB.setStatus(Byte.valueOf("0"));
            itemDB.setLastUpdated(new Date());
            itemDB.setUpdateBy(user.getUsername());
            logAccessService.addLog("Khóa gói phí đăng số "+itemDB.getId()+"("+itemDB.getPackageCode()+")","Quản lý gói cước",ipClient);
            configPackageDao.edit(itemDB);
        }catch (Exception e){
            throw new Exception();
        }

        return Optional.ofNullable(true);
    }

    @Override
    public Optional<Boolean> enable(Long id, String ipClient) throws Exception {
        try {
            ConfigPackage itemDB=configPackageDao.get(id).orElse(null);
            if(itemDB==null) return Optional.ofNullable(false);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            itemDB.setStatus(Byte.valueOf("1"));
            itemDB.setLastUpdated(new Date());
            itemDB.setUpdateBy(user.getUsername());
            logAccessService.addLog("Kích hoạt gói phí đăng số "+itemDB.getId()+"("+itemDB.getPackageCode()+")","Quản lý gói cước",ipClient);
            configPackageDao.edit(itemDB);
        }catch (Exception e){
            throw new Exception();
        }

        return Optional.ofNullable(true);
    }

    @Override
    public Optional<ConfigPackage> get(Long id) {
        return configPackageDao.get(id);
    }

    @Override
    public Optional<ConfigPackage> getByPackageCode(String name) {
        return configPackageDao.getByPackageCode(name);
    }

    @Override
    public Optional<List<ConfigPackage>> list() {
        return configPackageDao.list();
    }
}
