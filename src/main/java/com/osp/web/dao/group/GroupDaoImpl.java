package com.osp.web.dao.group;

import com.osp.common.PagingResult;
import com.osp.model.Authority;
import com.osp.model.Group;
import com.osp.model.GroupAuthority;
import com.osp.model.GroupUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 12/27/2017.
 */
@Repository
@Transactional
public class GroupDaoImpl implements GroupDao {

    private Logger logger= LogManager.getLogger(GroupDaoImpl.class);
    @PersistenceContext(unitName = "appAdmin")
    private EntityManager entityManager;

    @Override
    public Optional<PagingResult> page(String name, PagingResult page) {
        int offset=0;
        if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();
        Long count=(Long)entityManager.createQuery("select count(gr.id) from com.osp.model.Group gr where gr.groupName like :name").setParameter("name","%"+name+"%").getSingleResult();
        List<Group> list=entityManager.createQuery("select gr from com.osp.model.Group gr where gr.groupName like :name",Group.class).setParameter("name","%"+name+"%")
                .setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        if(list!=null && count>0){
            page.setItems(list);
            page.setRowCount(count.longValue());
        }
        return Optional.of(page);
    }

    @Override
    public Optional<Integer> add(Group item) {
        entityManager.persist(item);
        entityManager.flush();
        return Optional.of(item.getId());
    }

    @Override
    public Optional<Group> get(Integer id) {
        Group item=entityManager.find(Group.class,id);
        return Optional.ofNullable(item);
    }

    @Override
    public Optional<List<Group>> loadAllGroup() {
        List<Group> items=entityManager.createQuery("Select gr from com.osp.model.Group gr ",Group.class).getResultList();
        return Optional.ofNullable(items);
    }

    @Override
    public Optional<List<Group>> loadAllGroupOfUser(Long userId) {
        List<Group> items=entityManager.createQuery("SELECT gr FROM com.osp.model.Group gr JOIN GroupUser gu ON gr.id=gu.groupId where gu.userId=:userId and gr.status=1",Group.class)
                .setParameter("userId",userId).getResultList();
        return Optional.ofNullable(items);
    }

    @Override
    public Optional<List<GroupUser>> loadAllGroupUserByGroupId(Integer groupId) {
        List<GroupUser> items=entityManager.createQuery("Select gu from GroupUser gu where gu.groupId=:groupId",GroupUser.class).setParameter("groupId",groupId).getResultList();
        return Optional.ofNullable(items);
    }

    @Override
    public Optional<Integer> edit(Group item)  {
        entityManager.merge(item);
        entityManager.flush();
        return Optional.of(item.getId());
    }

    /*AUTHORITY*/
    @Override
    public Optional<List<Authority>> loadAllAuthority() {
        List<Authority> list=entityManager.createQuery("select a from Authority a order by a.orderId asc",Authority.class).getResultList();
        return Optional.ofNullable(list);
    }

    @Override
    public Optional<Boolean> addListGroupAuthority(List<GroupAuthority> items){
        items.stream().forEach(item->entityManager.persist(item));
        entityManager.flush();
        return Optional.of(true);
    }

    /*GROUP AUTHORITY*/

    @Override
    public Optional<List<GroupAuthority>> loadByGroupId(Integer groupId) {
        List<GroupAuthority> list=entityManager.createQuery("select a from GroupAuthority a WHERE a.groupId=:groupId",GroupAuthority.class).setParameter("groupId",groupId).getResultList();
        return Optional.ofNullable(list);
    }

    @Override
    public Optional<Boolean> deleteGroupAuthority(Integer groupId) {
        Query query=entityManager.createQuery("delete from GroupAuthority a WHERE a.groupId=:groupId").setParameter("groupId",groupId);
        query.executeUpdate();
        return Optional.ofNullable(true);
    }

    @Override
    public Optional<Boolean> deleteGroup(Integer id) {
        Query query=entityManager.createQuery("delete from com.osp.model.Group gr where gr.id=:id").setParameter("id",id);
        query.executeUpdate();
        return Optional.ofNullable(true);
    }

    @Override
    public Optional<Boolean> addListGroupUser(List<GroupUser> items) {
        if(items.size()>0){
            items.stream().forEach(item->entityManager.persist(item));
            entityManager.flush();
            return Optional.of(true);
        }
        return Optional.of(false);
    }

    @Override
    public Optional<Boolean> deleteListGroupOfUser(Long userId) {
        Query query=entityManager.createQuery("delete from GroupUser gu where gu.userId=:userId").setParameter("userId",userId);
        query.executeUpdate();
        return Optional.of(true);
    }

    @Override
    public Optional<List<String>> loadListAuthorityOfUserByUsername(String username) {
        List<String> items=entityManager.createQuery("SELECT au.authority FROM Authority au JOIN GroupAuthority ga ON au.id=ga.authority JOIN com.osp.model.Group gr ON gr.id=ga.groupId JOIN GroupUser gu ON gr.id=gu.groupId JOIN User us ON us.id=gu.userId WHERE us.username=:username")
                .setParameter("username",username).getResultList();

        return Optional.ofNullable(items);
    }
}
