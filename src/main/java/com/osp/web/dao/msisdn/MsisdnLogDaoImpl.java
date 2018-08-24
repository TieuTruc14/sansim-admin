package com.osp.web.dao.msisdn;

import com.osp.common.PagingResult;
import com.osp.modelCustomer.MsisdnLog;
import com.osp.modelCustomer.StockMsisdn;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

/**
 * Created by Admin on 1/15/2018.
 */
@Repository
@Transactional(value = "transactionManagerCustomer")
public class MsisdnLogDaoImpl implements MsisdnLogDao {
    @PersistenceContext(unitName = "appCustomer")
    private EntityManager entityManager;


    @Override
    public Optional<MsisdnLog> get(Long id) {
        MsisdnLog item=entityManager.find(MsisdnLog.class,id);
        return Optional.ofNullable(item);
    }

    @Override
    public Optional<PagingResult> pageReport(PagingResult page, Byte type, String username, Date from, Date to) {

        int offset=0;
        if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = cb.createQuery(Object[].class);
        Root<MsisdnLog> root = q.from(MsisdnLog.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if(StringUtils.isNotBlank(username)){
            Predicate preUsername=cb.equal(root.get("customer").get("username"),username);
            Predicate preFullName=cb.equal(root.get("customer").get("fullName"),username);
            predicates.add(cb.or(preFullName,preUsername));
        }
        if(from!=null){
            predicates.add(cb.greaterThanOrEqualTo(root.get("genDate"), from));
        }
        if(to!=null){
            predicates.add(cb.lessThanOrEqualTo(root.get("genDate"), to));
        }
        if(type!=null){
            predicates.add(cb.equal(root.get("type"),type));
        }else{
            Predicate preDelete=cb.equal(root.get("type"),Byte.valueOf((byte)0));
            Predicate preAdd=cb.equal(root.get("type"),Byte.valueOf((byte)1));
            predicates.add(cb.or(preDelete,preAdd));
        }
        q.multiselect(root.get("type"),root.get("customer").get("username"),root.get("customer").get("fullName"),cb.count(root.get("type")));
        q.where(predicates.toArray(new Predicate[]{}));
        q.groupBy(root.get("customer").get("username"),root.get("type"),root.get("customer").get("fullName"));
        q.orderBy(cb.desc(root.get("type")),cb.asc(root.get("customer").get("username")));
//        List<Order> orderList = new ArrayList();
//        orderList.add(cb.asc(root.get("customer").get("username")));
//        orderList.add(cb.desc(root.get("type")));
//        q.orderBy(orderList);
        List<Object[]> list = entityManager.createQuery(q).setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();

        if (list != null) {
            page.setItems(list);
        }
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);

        Root<MsisdnLog> rootCount = criteriaQuery.from(MsisdnLog.class);
//        criteriaQuery.select(cb.count(criteriaQuery.from(MsisdnLog.class)));
        criteriaQuery.select(cb.count(rootCount.get("type")));
        criteriaQuery.where(predicates.toArray(new Predicate[]{}));
        criteriaQuery.groupBy(rootCount.get("customer").get("username"),rootCount.get("type"),rootCount.get("customer").get("fullName"));
//        Long rowCount =  entityManager.createQuery(criteriaQuery).setFirstResult(0).setMaxResults(1).getSingleResult();//setMaxResults tranh loi: javax.persistence.NonUniqueResultException: query did not return a unique result
//        TypedQuery<Long> typeLong =  entityManager.createQuery(criteriaQuery);
//        List<Long> list234=typeLong.getResultList();
//        Long row=entityManager.createQuery(criteriaQuery).get().longValue();
        //chỉ lay max= max of int
        Integer rowCount=entityManager.createQuery(criteriaQuery).setMaxResults(2147483646).getResultList().size();//voi cai nay so rang khi du lieu >2,1ti thi hong mat.
        if (rowCount != null) {
            page.setRowCount(rowCount.longValue());
        }

        return Optional.ofNullable(page);
    }

    @Override
    public Optional<PagingResult> downloadReport(PagingResult page, Byte type, String username, Date from, Date to) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = cb.createQuery(Object[].class);
        Root<MsisdnLog> root = q.from(MsisdnLog.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if(StringUtils.isNotBlank(username)){
            Predicate preUsername=cb.equal(root.get("customer").get("username"),username);
            Predicate preFullName=cb.equal(root.get("customer").get("fullName"),username);
            predicates.add(cb.or(preFullName,preUsername));
        }
        if(from!=null){
            predicates.add(cb.greaterThanOrEqualTo(root.get("genDate"), from));
        }
        if(to!=null){
            predicates.add(cb.lessThanOrEqualTo(root.get("genDate"), to));
        }
        if(type!=null){
            predicates.add(cb.equal(root.get("type"),type));
        }else{
            Predicate preDelete=cb.equal(root.get("type"),Byte.valueOf((byte)0));
            Predicate preAdd=cb.equal(root.get("type"),Byte.valueOf((byte)1));
            predicates.add(cb.or(preDelete,preAdd));
        }
        q.multiselect(root.get("type"),root.get("customer").get("username"),root.get("customer").get("fullName"),cb.count(root.get("type")));
        q.where(predicates.toArray(new Predicate[]{}));
        q.groupBy(root.get("customer").get("username"),root.get("type"),root.get("customer").get("fullName"));
        q.orderBy(cb.desc(root.get("type")),cb.asc(root.get("customer").get("username")));
        List<Object[]> list = entityManager.createQuery(q).getResultList();

        if (list != null) {
            page.setItems(list);
            page.setRowCount(list.size());
        }
        return Optional.ofNullable(page);
    }

    @Override
    public Optional<List<MsisdnLog>> getBylistMsisdn(HashMap<String, Long> msisdns) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MsisdnLog> q = cb.createQuery(MsisdnLog.class);
        Root<MsisdnLog> root = q.from(MsisdnLog.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        List<Predicate> predicatesSwrap = new ArrayList<Predicate>();
        if(!msisdns.isEmpty()){
            for (Map.Entry<String, Long> entry : msisdns.entrySet()) {
                String msisdn = entry.getKey().substring(entry.getKey().indexOf("-")+1);
                Long cusId = entry.getValue();
                Predicate preMsisdn=cb.equal(root.get("msisdn"),msisdn);
                Predicate preCus=cb.equal(root.get("customer").get("id"),cusId);
                predicatesSwrap.add(cb.and(preMsisdn,preCus));
            }
            predicates.add(cb.or(predicatesSwrap.toArray(new Predicate[]{})));
            q.select(root);
            q.where(predicates.toArray(new Predicate[]{}));
            List<MsisdnLog> list=entityManager.createQuery(q).getResultList();
            if(list!=null && list.size()>0) return Optional.of(list);
        }
        return Optional.ofNullable(new ArrayList<>());
    }


}
