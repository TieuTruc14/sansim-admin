package com.osp.web.service.group;

import com.osp.common.PagingResult;
import com.osp.model.Authority;
import com.osp.model.Group;
import com.osp.model.GroupUser;
import com.osp.model.view.GroupView;
import com.osp.validator.SanSimException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 12/27/2017.
 */
@Transactional
public interface GroupService {
    Optional<PagingResult> page(String name, PagingResult page);
    Optional<List<Group>> loadAllGroup();
    Optional<List<Group>> loadAllGroupOfUser(Long userId);
    Optional<GroupView> getGroupView(Integer id);
    Optional<List<Authority>> loadAllAuthority();
    Optional<Boolean> saveGroupView(GroupView item);
    Optional<Boolean> editGroupView(GroupView item) throws SanSimException;

    Optional<Boolean> addListGroupUser(List<GroupUser> items,Long userId);
    Optional<Boolean> deleteListGroupOfUser(Long userId);

    Optional<List<String>> loadListAuthorityOfUserByUsername(String username);
    Optional<Integer> deleteGroup(Integer id,String ip);
}
