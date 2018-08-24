package com.osp.web.dao.logaccess;

import com.osp.common.PagingResult;
import com.osp.model.LogAccess;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 1/3/2018.
 */
public interface LogAccessDao {
    Optional<Boolean> add(LogAccess item);
//    Optional<PagingResult> page(PagingResult page, Long userId);
    Optional<PagingResult> page(PagingResult page, String username);
    Optional<PagingResult> getByUserId(PagingResult page,Long userId);
}
