package com.osp.web.service.user;

import com.osp.common.PagingResult;
import com.osp.model.LogAccess;
import com.osp.model.User;
import com.osp.web.dao.logaccess.LogAccessDao;
import com.osp.web.dao.user.UserDao;
import com.osp.web.service.logaccess.LogAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

/**
 * Created by Admin on 12/26/2017.
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserDao userDao;
    @Autowired
    LogAccessService logAccessService;
    @Autowired
    BCryptPasswordEncoder encoder;
    @Override
    public Optional<PagingResult> pageUser(String username,PagingResult page) {
        return userDao.pageUser(username,page);
    }

    @Override
    @Transactional
    public Optional<Boolean> addUser(User item,String ipClient) {
        item.setStatus(1);
        item.setGenDate(new Date());
        item.setLastUpdated(new Date());
        item.setPassword(encoder.encode(item.getPassword()));
        //insert log
        logAccessService.addLog("Thêm người dùng","User",ipClient);
        return userDao.addUser(item);
    }

    @Override
    public Optional<User> get(Long id) {
        return userDao.get(id);
    }

    @Override
    @Transactional
    public Optional<Boolean> editUserFromView(User item,String ipClient) {
        User itemDB=userDao.get(item.getId()).orElse(null);
        if(itemDB!=null){
            itemDB.setFullName(item.getFullName());
            itemDB.setDescription(item.getDescription());
            //insert log
            logAccessService.addLog("Sửa người dùng:"+item.getId(),"User",ipClient);
            return userDao.editUser(itemDB);
        }else{
            return Optional.ofNullable(false);
        }

    }

    @Override
    public Optional<Boolean> deleteUser(Long id,String ipClient) {
        logAccessService.addLog("Xóa người dùng:"+id,"User",ipClient);
        return userDao.deleteUser(id);
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    @Override
    @Transactional
    public Optional<Integer> changeMyPass(String passwordCurrent,String passNew) {
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PasswordEncoder passwordEnocder = new BCryptPasswordEncoder();
        if(!passwordEnocder.matches(passwordCurrent,user.getPassword())){
            return Optional.of(2);
        }
        passNew=encoder.encode(passNew);
        user.setPassword(passNew);
        userDao.changeMyPass(passNew,user.getId());
        return Optional.of(1);
    }
}
