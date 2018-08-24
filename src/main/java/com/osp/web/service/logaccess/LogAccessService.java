package com.osp.web.service.logaccess;

import com.osp.common.PagingResult;
import com.osp.model.LogAccess;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 1/3/2018.
 */
public interface LogAccessService {
    Optional<PagingResult> page(PagingResult page, String username);
    Optional<PagingResult> getByUserId(PagingResult page,Long userId);
    Optional<Boolean> addLog(String action,String module,String ipClient);
    Optional<Boolean> addLogWithUserId(Long userId,String action,String module,String ipClient);
}
