package com.osp.web.service.group;

import com.osp.common.PagingResult;
import com.osp.model.*;
import com.osp.model.view.GroupView;
import com.osp.validator.SanSimException;
import com.osp.web.dao.group.GroupDao;
import com.osp.web.dao.user.UserDaoImpl;
import com.osp.web.service.logaccess.LogAccessService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

/**
 * Created by Admin on 12/27/2017.
 */
@Service
public class GroupServiceImpl implements GroupService {
    private Logger logger= LogManager.getLogger(GroupServiceImpl.class);
    @Autowired
    GroupDao groupDao;
    @Autowired
    LogAccessService logAccessService;

    @Override
    public Optional<PagingResult> page(String name, PagingResult page) {
        return groupDao.page(name,page);
    }

    @Override
    public Optional<List<Group>> loadAllGroup() {
        return groupDao.loadAllGroup();
    }

    @Override
    public Optional<List<Group>> loadAllGroupOfUser(Long userId) {
        return groupDao.loadAllGroupOfUser(userId);
    }

    @Override
    public Optional<List<Authority>> loadAllAuthority() {
        return groupDao.loadAllAuthority();
    }


    @Override
    @Transactional
    public Optional<Boolean> saveGroupView(GroupView item) {
        Group group=new Group();
        group.setGroupName(item.getGroupName());
        group.setStatus(true);
        group.setDescription(item.getDescription());
        User user=(User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        group.setCreateBy(user.getUsername());
        group.setUpdateBy(user.getUsername());
        group.setGenDate(new Date());
        group.setLastUpdated(new Date());
        Integer groupId=groupDao.add(group).orElse(Integer.valueOf(0));
        if(groupId.intValue()==0) return Optional.of(false);
        genAuthority(item,groupId,user.getUsername());

        return Optional.of(true);
    }

    @Override
    public Optional<GroupView> getGroupView(Integer id) {
        Group group=groupDao.get(id).orElse(new Group());
        if(group==null || group.getId()==null){
            return null;
        }
        GroupView item=new GroupView();
        item.setId(group.getId());
        item.setGroupName(group.getGroupName());
        item.setDescription(group.getDescription());
        List<GroupAuthority> groupAuthorities=groupDao.loadByGroupId(id).orElse(null);
        if(groupAuthorities!=null && groupAuthorities.size()>0){
            StringBuilder authoritiesString=new StringBuilder("");
            groupAuthorities.stream().forEach(g -> authoritiesString.append(g.getAuthority()+","));
            item.setListAuthority(authoritiesString.toString());
        }
        return Optional.ofNullable(item);
    }

    @Override
    @Transactional
    public Optional<Boolean> editGroupView(GroupView item) {

        Group group = groupDao.get(item.getId()).orElse(null);
        if (group == null) return Optional.of(false);

        group.setGroupName(item.getGroupName());
        group.setDescription(item.getDescription());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        group.setUpdateBy(user.getUsername());
        group.setLastUpdated(new Date());
        Integer groupId = groupDao.edit(group).orElse(Integer.valueOf(0));
        if (groupId.intValue() == 0) return Optional.of(false);
        genAuthority(item, groupId, user.getUsername());
        return Optional.of(true);
    }

    public void genAuthority(GroupView item,Integer groupId,String username){
        List<String> authorities=new ArrayList<>(Arrays.asList(item.getListAuthority().split(",")));
        List<GroupAuthority> groupAuthorities=new ArrayList<>();
        if(authorities.size()>0){
            authorities.stream().forEach(au -> groupAuthorities.add(new GroupAuthority(groupId,Integer.valueOf(au),username,new Date())));
            if(item.getId()!=null){
                groupDao.deleteGroupAuthority(item.getId()).orElse(false);
            }
            groupDao.addListGroupAuthority(groupAuthorities).orElse(false);
        }
    }

    @Override
    @Transactional
    public Optional<Boolean> addListGroupUser(List<GroupUser> items,Long userId) {
        groupDao.deleteListGroupOfUser(userId);
        return groupDao.addListGroupUser(items);
    }

    @Override
    public Optional<Boolean> deleteListGroupOfUser(Long userId) {
        return groupDao.deleteListGroupOfUser(userId);
    }

    @Override
    public Optional<List<String>> loadListAuthorityOfUserByUsername(String username) {
        return groupDao.loadListAuthorityOfUserByUsername(username);
    }

    @Override
    @Transactional
    public Optional<Integer> deleteGroup(Integer id,String ip) {
        List<GroupUser> listGU=groupDao.loadAllGroupUserByGroupId(id).orElse(new ArrayList<>());
        if(listGU.size()>0){
            return Optional.of(2);
        }
        groupDao.deleteGroup(id);
        groupDao.deleteGroupAuthority(id);
        logAccessService.addLog("Xóa nhóm quyền Id:"+id,"Nhóm quyền",ip);
        return Optional.of(1);
    }
}

