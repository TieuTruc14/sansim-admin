package com.osp.web.service.user;

import com.osp.common.PagingResult;
import com.osp.model.User;

import java.util.Optional;

/**
 * Created by Admin on 12/26/2017.
 */

public interface UserService {
    Optional<PagingResult> pageUser(String username,PagingResult page);
    Optional<Boolean> addUser(User item,String ipClient);
    Optional<User> get(Long id);
    Optional<Boolean> editUserFromView(User item,String ipClient);
    Optional<Boolean> deleteUser(Long id,String ipClient);
    Optional<User> getByUsername(String username);
    Optional<Integer> changeMyPass(String passwordCurrent,String passNew);
}
