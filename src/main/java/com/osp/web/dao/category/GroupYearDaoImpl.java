package com.osp.web.dao.category;

import com.osp.common.PagingResult;
import com.osp.modelCustomer.GroupYear;
import com.osp.web.service.logaccess.LogAccessService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 2/6/2018.
 */
@Repository
@Transactional(value = "transactionManagerCustomer")
public class GroupYearDaoImpl implements GroupYearDao {
    private Logger logger= LogManager.getLogger(GroupYearDaoImpl.class);
    @PersistenceContext(unitName = "appCustomer")
    private EntityManager entityManager;
    @PersistenceUnit(unitName = "appCustomer")
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    LogAccessService logAccessService;

    @Override
    public Optional<PagingResult> page(PagingResult page, String name) {
        int offset=0;
        if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();
        List<GroupYear> list=new ArrayList<>();
        Long count=0L;
        if(StringUtils.isBlank(name)){
            list=entityManager.createQuery("Select gp from GroupYear gp order by gp.orderNumber",GroupYear.class)
                    .setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
            count=(Long)entityManager.createQuery("SELECT count(gp.id) from GroupYear gp").getSingleResult();
        }else{
            list=entityManager.createQuery("Select gp from GroupYear gp where gp.name like :name order by gp.orderNumber",GroupYear.class)
                    .setParameter("name","%"+name+"%").setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
            count=(Long)entityManager.createQuery("SELECT count(gp.id) from GroupYear gp where gp.name like :name").setParameter("name","%"+name+"%").getSingleResult();
        }
        if(list!=null){
            page.setItems(list);
        }
        if(count!=null && count.longValue()>0){
            page.setRowCount(count);
        }
        return Optional.of(page);
    }

    @Override
    public Optional<GroupYear> get(Integer id) {
        GroupYear item=entityManager.find(GroupYear.class,id);
        return Optional.ofNullable(item);
    }

    @Override
    public Optional<Boolean> add(GroupYear item) {
        entityManager.persist(item);
        entityManager.flush();
        return Optional.ofNullable(true);
    }

    @Override
    public Optional<Boolean> addWithWriteLog(GroupYear item, String ipClient) throws Exception {
        EntityManager em=entityManagerFactory.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(item);
            em.flush();
            logAccessService.addLog("Thêm nhóm sim theo giá. Id:"+item.getId(),"Danh mục sim theo giá",ipClient);
            em.getTransaction().commit();
        }catch (Exception e){
            logger.error("Have an error method addWithWriteLog:"+e.getMessage());
            em.getTransaction().rollback();
            throw new Exception();
        }finally {
            em.close();
        }
        return Optional.of(true);
    }

    @Override
    public Optional<Boolean> edit(GroupYear item) throws Exception {
        EntityManager em=entityManagerFactory.createEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(item);
            em.flush();
            em.getTransaction().commit();
        }catch (Exception e){
            logger.error("Have an error method edit:"+e.getMessage());
            em.getTransaction().rollback();
            throw new Exception();
        }finally {
            em.close();
        }
        return Optional.of(true);
    }

    @Override
    public Optional<Boolean> editList(List<GroupYear> items) {
        if(items!=null && items.size()>0){
            items.stream().forEach(item-> entityManager.merge(item));
            entityManager.flush();
        }
        return Optional.ofNullable(true);
    }

    @Override
    public Optional<List<GroupYear>> list() {
        List<GroupYear> items=entityManager.createQuery("Select gm from GroupYear gm order by gm.orderNumber",GroupYear.class).getResultList();
        return Optional.ofNullable(items);
    }

    @Override
    public Optional<Boolean> delete(Integer id) throws Exception{
        EntityManager em=entityManagerFactory.createEntityManager();
        try{
            em.getTransaction().begin();
            Query query =em.createQuery("delete from GroupYear u where u.id=:id").setParameter("id",id);
            query.executeUpdate();
            em.getTransaction().commit();
        }catch (Exception e){
            logger.error("Have an error method delete:"+e.getMessage());
            em.getTransaction().rollback();
            throw new Exception();
        }finally {
            em.close();
        }
        return Optional.ofNullable(true);
    }
}
