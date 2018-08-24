package com.osp.web.dao.user;

import com.osp.common.PagingResult;
import com.osp.model.User;

import java.util.Optional;

/**
 * Created by Admin on 12/26/2017.
 */
public interface UserDao {
    Optional<PagingResult> pageUser(String username,PagingResult page);
    Optional<Boolean> addUser(User item);
    Optional<User> get(Long id);
    Optional<Boolean> editUser(User item);
    Optional<Boolean> deleteUser(Long id);
    Optional<User> getByUsername(String username);
    Optional<Boolean> changeMyPass(String passNew,Long id);
}
