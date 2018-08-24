package com.osp.web.service.logaccess;

import com.osp.common.PagingResult;
import com.osp.model.LogAccess;
import com.osp.model.User;
import com.osp.web.dao.logaccess.LogAccessDao;
import com.osp.web.dao.user.UserDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * Created by Admin on 1/3/2018.
 */
@Service
public class LogAccessServiceImpl implements LogAccessService {

    @Autowired
    LogAccessDao logAccessDao;
    @Autowired
    UserDao userDao;

    @Override
    public Optional<Boolean> addLog(String action, String module, String ipClient) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LogAccess log =new LogAccess();
        log.setActions(action);
        log.setModule(module);
        log.setUserId(user.getId());
        log.setIp(ipClient);
        log.setGenDate(new Date());

        return logAccessDao.add(log);
    }

    @Override
    public Optional<Boolean> addLogWithUserId(Long userId, String action, String module, String ipClient) {
        LogAccess log =new LogAccess();
        log.setActions(action);
        log.setModule(module);
        log.setUserId(userId);
        log.setIp(ipClient);
        log.setGenDate(new Date());
        return logAccessDao.add(log);
    }

    @Override
    public Optional<PagingResult> page(PagingResult page, String username) {
        return logAccessDao.page(page,username);
    }

    @Override
    public Optional<PagingResult> getByUserId(PagingResult page,Long userId) {
        return logAccessDao.getByUserId(page,userId);
    }

}
