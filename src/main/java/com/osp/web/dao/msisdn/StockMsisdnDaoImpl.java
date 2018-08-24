package com.osp.web.dao.msisdn;

import com.osp.common.PagingResult;
import com.osp.modelCustomer.StockMsisdn;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 12/21/2017.
 */
@Repository
@Transactional(value = "transactionManagerCustomer")
public class StockMsisdnDaoImpl implements StockMsisdnDao {
    @PersistenceContext(unitName = "appCustomer")
//    @Qualifier(value = "transactionManagerCustomer")
    private EntityManager entityManager;

    @Override
    public Optional<StockMsisdn> get(Long id) {
        List<StockMsisdn> list = entityManager.createQuery("select sim from StockMsisdn sim where sim.id = :id",
                StockMsisdn.class).setParameter("id", id).setFirstResult(0).setMaxResults(1).getResultList();
        StockMsisdn sim=new StockMsisdn();
        if(list!=null){
            sim=list.get(0);
            return Optional.ofNullable(sim);
        }
        return Optional.ofNullable(null);
    }

    @Override
    public Optional<PagingResult> page(PagingResult page, String username, Date from, Date to, String msisdn, Long price, Boolean confirmStatus, Date confirmExpired,Byte telco) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Object[]> q = cb.createQuery(Object[].class);
        Root<StockMsisdn> root = q.from(StockMsisdn.class);
        List<Predicate> predicates = new ArrayList<Predicate>();

        int offset=0;
        if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();

        if(StringUtils.isNotBlank(username)){
            predicates.add(cb.like(root.get("customer").get("username"),"%"+username+"%"));
//            predicates.add(cb.like(root.get("customer").get("fullName"),"%"+username+"%"));
        }
        if(telco!=null){
            predicates.add(cb.equal(root.get("telco"),telco));
        }
        if(from!=null){
            predicates.add(cb.greaterThanOrEqualTo(root.get("genDate"), from));
        }
        if(to!=null){
            predicates.add(cb.lessThanOrEqualTo(root.get("genDate"), to));
        }
        if(StringUtils.isNotBlank(msisdn)){
            predicates.add(cb.like(root.get("msisdn"),"%"+msisdn+"%"));
        }
        if(price!=null && price.longValue()>0){
            predicates.add(cb.equal(root.get("price"),price));
        }
        if(confirmStatus!=null){
            predicates.add(cb.equal(root.get("confirmStatus"),confirmStatus.booleanValue()));
            if(confirmExpired!=null){
                predicates.add(cb.lessThanOrEqualTo(root.get("confirmExpired"),confirmExpired));
            }
        }


        q.select(cb.array(root.get("id"), root.get("customer").get("username"),root.get("msisdn"), root.get("price"), root.get("genDate"), root.get("confirmStatus"), root.get("confirmExpired"),root.get("status"),root.get("telco")))
                .where(predicates.toArray(new Predicate[]{})).orderBy(cb.desc(root.get("genDate")),cb.desc(root.get("confirmExpired")));
        List<Object[]> list = entityManager.createQuery(q).setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();

        if (list != null) {
            page.setItems(list);
        }
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<StockMsisdn> rootCount = criteriaQuery.from(StockMsisdn.class);
        criteriaQuery.select(cb.count(rootCount)).where(predicates.toArray(new Predicate[]{}));
        Long rowCount = entityManager.createQuery(criteriaQuery).getSingleResult();
        if (rowCount != null) {
            page.setRowCount(rowCount.longValue());
        }

        return Optional.ofNullable(page);

    }

    @Override
    public Optional<Long> countInTime(Date from, Date to) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<StockMsisdn> rootCount = criteriaQuery.from(StockMsisdn.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if(from!=null){
            predicates.add(cb.greaterThanOrEqualTo(rootCount.get("genDate"), from));
        }
        if(to!=null){
            predicates.add(cb.lessThanOrEqualTo(rootCount.get("genDate"), to));
        }
        criteriaQuery.select(cb.count(rootCount)).where(predicates.toArray(new Predicate[]{}));
        Long rowCount = entityManager.createQuery(criteriaQuery).getSingleResult();
        if (rowCount != null && rowCount.longValue()>0) {
           return Optional.of(rowCount);
        }
        return Optional.of(0L);
    }


    @Override
    public Optional<PagingResult> page(PagingResult page, Date from, Date to) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Object[]> q = cb.createQuery(Object[].class);
        Root<StockMsisdn> root = q.from(StockMsisdn.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        int offset=0;
        if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();

        if(from!=null){
            predicates.add(cb.greaterThanOrEqualTo(root.get("genDate"), from));
        }
        if(to!=null){
            predicates.add(cb.lessThanOrEqualTo(root.get("genDate"), to));
        }

        q.select(cb.array(root.get("id"), root.get("customer").get("id"),root.get("msisdn"), root.get("genDate")))
                .where(predicates.toArray(new Predicate[]{}));
        List<Object[]> list = entityManager.createQuery(q).setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        if (list != null) {
            page.setItems(list);
        }
        return Optional.ofNullable(page);

    }

    @Override
    public Optional<Long> totalMsisdnLessTime(Date to) {
        Long count=0L;
        if(to!=null){
            count=(Long)entityManager.createQuery("Select count(m.id) from StockMsisdn m where m.genDate<=:to").setParameter("to",to).getSingleResult();
        }else{
            count=(Long)entityManager.createQuery("Select count(m.id) from StockMsisdn m").getSingleResult();
        }
        return (count!=null && count.longValue()>0)? Optional.of(count):Optional.of(0L);
    }

    @Override
    public Optional<Long> totalMsisdnStatusLessTime(Date to, Byte status) {
        Long count=0L;
        if(to!=null){
            count=(Long)entityManager.createQuery("Select count(m.id) from StockMsisdn m where m.genDate<=:to and m.status=:status")
                    .setParameter("to",to).setParameter("status",status).getSingleResult();
        }else{
            count=(Long)entityManager.createQuery("Select count(m.id) from StockMsisdn m where m.status=:status").setParameter("status",status).getSingleResult();
        }
        return (count!=null && count.longValue()>0)? Optional.of(count):Optional.of(0L);
    }
}
