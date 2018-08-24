package com.osp.web.dao.category;

import com.osp.common.PagingResult;
import com.osp.modelCustomer.GroupMsisdn;
import com.osp.web.service.logaccess.LogAccessService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 2/6/2018.
 */
@Repository
@Transactional(value = "transactionManagerCustomer")
public class GroupMsisdnDaoImpl implements GroupMsisdnDao {
    private Logger logger= LogManager.getLogger(GroupMsisdnDaoImpl.class);
    @PersistenceContext(unitName = "appCustomer")
    private EntityManager entityManager;

    @PersistenceUnit(unitName = "appCustomer")
    private EntityManagerFactory entityManagerFactory;

    @Override
    public Optional<List<GroupMsisdn>> list() {
        List<GroupMsisdn> items=entityManager.createQuery("Select gm from GroupMsisdn gm order by gm.orderNumber",GroupMsisdn.class).getResultList();
        return Optional.ofNullable(items);
    }

    @Override
    public Optional<PagingResult> page(PagingResult page, String name) {
        int offset=0;
        if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();
        List<GroupMsisdn> list=new ArrayList<>();
        Long count=0L;
        if(StringUtils.isBlank(name)){
            list=entityManager.createQuery("Select gp from GroupMsisdn gp order by gp.orderNumber",GroupMsisdn.class)
                    .setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
            count=(Long)entityManager.createQuery("SELECT count(gp.id) from GroupMsisdn gp").getSingleResult();
        }else{
            list=entityManager.createQuery("Select gp from GroupMsisdn gp where gp.name like :name order by gp.orderNumber",GroupMsisdn.class)
                    .setParameter("name","%"+name+"%").setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
            count=(Long)entityManager.createQuery("SELECT count(gp.id) from GroupMsisdn gp where gp.name like :name").setParameter("name","%"+name+"%").getSingleResult();
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
    public Optional<Boolean> editList(List<GroupMsisdn> items) {
        if(items!=null && items.size()>0){
            items.stream().forEach(item-> entityManager.merge(item));
            entityManager.flush();
        }
        return Optional.ofNullable(true);
    }
}
