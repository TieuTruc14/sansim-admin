package com.osp.web.dao.logaccess;

import com.osp.common.PagingResult;
import com.osp.common.QueryBuilder;
import com.osp.model.LogAccess;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 1/3/2018.
 */
@Repository
@Transactional
public class LogAccessDaoImpl implements LogAccessDao {

    @PersistenceContext(unitName = "appAdmin")
    @Qualifier(value = "transactionManagerAdmin")
    private EntityManager entityManager;

    @Override
    public Optional<Boolean> add(LogAccess item) {
        entityManager.persist(item);
        entityManager.flush();
        return Optional.of(true);
    }

    @Override
    public Optional<PagingResult> page(PagingResult page,String username) {
        int offset=0;
        if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();

        StringBuffer sqlBuffer = new StringBuffer("SELECT DISTINCT log.id,log.userId,log.module,log.ip,log.actions,log.genDate,u.username "+
                "from LogAccess log LEFT JOIN User u ON log.userId=u.id ");
        StringBuffer sqlBufferCount = new StringBuffer("SELECT count(DISTINCT log.id) "+
                "from LogAccess log LEFT JOIN User u ON log.userId=u.id ");
        Query query = filterBuilderSingle(sqlBuffer,true,username);
        List<Object[]> list=query.setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        if (list != null && list.size()>0) {
            page.setItems(list);
        }
        Query queryCount = filterBuilderSingle(sqlBufferCount,false,username);
        Long rowCount = (Long)queryCount.getSingleResult();
        if (rowCount != null) {
            page.setRowCount(rowCount.longValue());
        }
        return Optional.ofNullable(page);
    }

    private Query filterBuilderSingle(StringBuffer stringBuffer, boolean order, String username){
        Query result = null;
        try {
            QueryBuilder builder = new QueryBuilder(entityManager, stringBuffer);
            if (StringUtils.isNotBlank(username)) {
                builder.and(QueryBuilder.LIKE, "u.username","%"+ username+"%");
            }
            builder.addOrder("log.genDate", QueryBuilder.DESC);
            result = builder.initQuery(order);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }


    @Override
    public Optional<PagingResult> getByUserId(PagingResult page,Long userId) {
        int offset=0;
        if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();
        List<LogAccess> items=entityManager.createQuery("Select log from LogAccess log where log.userId=:userId order by log.genDate desc ",LogAccess.class).setParameter("userId",userId)
                .setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        Long rowCount=(Long)entityManager.createQuery("Select count(log.id) from LogAccess log where log.userId=:userId").setParameter("userId",userId).getSingleResult();
        if(items!=null && items.size()>0){
            page.setItems(items);
        }
        if(rowCount!=null && rowCount.longValue()>0){
            page.setRowCount(rowCount.longValue());
        }
        return Optional.of(page);
    }
}
