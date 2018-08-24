package com.osp.web.dao.group;

import com.osp.common.PagingResult;
import com.osp.model.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 12/27/2017.
 */
public interface GroupDao {

    Optional<PagingResult> page(String name, PagingResult page);
    Optional<Integer> add(Group item);
    Optional<Group> get(Integer id);
    Optional<List<Group>> loadAllGroup();
    Optional<List<Group>> loadAllGroupOfUser(Long userId);
    Optional<List<GroupUser>> loadAllGroupUserByGroupId(Integer groupId);
    Optional<Integer> edit(Group item);

    /*AUTHORITY*/
    Optional<List<Authority>> loadAllAuthority();
    Optional<Boolean> addListGroupAuthority(List<GroupAuthority> items) ;

    /*Group authority*/
    Optional<List<GroupAuthority>> loadByGroupId(Integer groupId);
    Optional<Boolean> deleteGroupAuthority(Integer groupId);
    Optional<Boolean> deleteGroup(Integer id);
    /*GroupUser*/
    Optional<Boolean> addListGroupUser(List<GroupUser> items);
    Optional<Boolean> deleteListGroupOfUser(Long userId);
    /*Authority of User*/
    Optional<List<String>> loadListAuthorityOfUserByUsername(String username);
}
