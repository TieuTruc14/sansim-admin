package com.osp.web.dao.aggregation;

import com.osp.modelCustomer.Aggregation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 1/30/2018.
 */
@Repository
@Transactional(value = "transactionManagerCustomer")
public class AggregationDaoImpl implements AggregationDao {

    private Logger logger= LogManager.getLogger(AggregationDaoImpl.class);
    @PersistenceContext(unitName = "appCustomer")
    private EntityManager entityManager;

    @Override
    public Optional<Aggregation> getByDate(Date date) {
        List<Aggregation> items=entityManager.createQuery("Select ag from Aggregation ag where ag.date=:date").setParameter("date",date).getResultList();
        if(items!=null && items.size()>0) return Optional.ofNullable(items.get(0));
        return Optional.ofNullable(new Aggregation());
    }

    @Override
    public Optional<Boolean> edit(Aggregation item) {
        entityManager.merge(item);
        entityManager.flush();
        return Optional.of(true);
    }

    @Override
    public Optional<Boolean> add(Aggregation item) {
        entityManager.persist(item);
        entityManager.flush();
        return Optional.of(true);
    }

    @Override
    public Optional<Boolean> addList(List<Aggregation> items) {
        items.stream().forEach(item->entityManager.persist(item));
        entityManager.flush();
        return Optional.of(true);
    }

    @Override
    public Optional<List<Aggregation>> getAggregationWeek(int year, int week) {
        List<Aggregation> items=entityManager.createQuery("Select ag from Aggregation ag where ag.year=:year and ag.weekOfYear=:week order by ag.month,ag.dayOfMonth")
                .setParameter("year",year).setParameter("week",week).getResultList();
        return (items!=null && items.size()>0) ? Optional.of(items):Optional.ofNullable(new ArrayList<>());
    }

    @Override
    public Optional<List<Aggregation>> getAggregationMonth(int year, int month) {
        List<Aggregation> items=entityManager.createQuery("Select ag from Aggregation ag where ag.year=:year and ag.month=:month order by ag.dayOfMonth")
                .setParameter("year",year).setParameter("month",month).getResultList();
        return (items!=null && items.size()>0) ? Optional.of(items):Optional.ofNullable(new ArrayList<>());
    }

    @Override
    public Optional<List<Aggregation>> getAggregationYear(int year) {
        List<Aggregation> items=entityManager.createQuery("Select ag from Aggregation ag where ag.year=:year order by ag.month,ag.dayOfMonth")
                .setParameter("year",year).getResultList();
        return (items!=null && items.size()>0) ? Optional.of(items):Optional.ofNullable(new ArrayList<>());
    }

    @Override
    public Optional<Object[]> soLuongGiaoDichTuanType(int year, int week) {
        List<Object[]> items=entityManager.createQuery("Select sum(ag.registerPackNumber),sum(ag.renewalPackNumber),sum(ag.msisdnConfirm),sum(ag.renewalConfirm),sum(ag.destroyPackNumber),sum(ag.destroyCusPackNumber) from Aggregation ag where ag.year=:year and ag.weekOfYear=:week")
                .setParameter("year",year).setParameter("week",week).getResultList();
        return (items!=null && items.size()>0) ? Optional.of(items.get(0)):Optional.ofNullable(null);
    }

    @Override
    public Optional<Object[]> soLuongGiaoDichThangType(int year, int month) {
        List<Object[]> items=entityManager.createQuery("Select sum(ag.registerPackNumber),sum(ag.renewalPackNumber),sum(ag.msisdnConfirm),sum(ag.renewalConfirm),sum(ag.destroyPackNumber),sum(ag.destroyCusPackNumber) from Aggregation ag where ag.year=:year and ag.month=:month")
                .setParameter("year",year).setParameter("month",month).getResultList();
        return (items!=null && items.size()>0) ? Optional.of(items.get(0)):Optional.ofNullable(null);
    }

    @Override
    public Optional<Object[]> soLuongGiaoDichNamType(int year) {
        List<Object[]> items=entityManager.createQuery("Select sum(ag.registerPackNumber),sum(ag.renewalPackNumber),sum(ag.msisdnConfirm),sum(ag.renewalConfirm),sum(ag.destroyPackNumber),sum(ag.destroyCusPackNumber) from Aggregation ag where ag.year=:year")
                .setParameter("year",year).getResultList();
        return (items!=null && items.size()>0) ? Optional.of(items.get(0)):Optional.ofNullable(null);
    }

    @Override
    public Optional<Object[]> DoanhThuTuanType(int year, int week) {
        List<Object[]> items=entityManager.createQuery("Select sum(ag.registerRevenue),sum(ag.renewalRevenue),sum(ag.authenticationRevenue),sum(ag.renewalAuthenticationRevenue),sum(ag.revenueTotal) from Aggregation ag where ag.year=:year and ag.weekOfYear=:week")
                .setParameter("year",year).setParameter("week",week).getResultList();
        return (items!=null && items.size()>0) ? Optional.of(items.get(0)):Optional.ofNullable(null);
    }

    @Override
    public Optional<Object[]> DoanhThuThangType(int year, int month) {
        List<Object[]> items=entityManager.createQuery("Select sum(ag.registerRevenue),sum(ag.renewalRevenue),sum(ag.authenticationRevenue),sum(ag.renewalAuthenticationRevenue),sum(ag.revenueTotal) from Aggregation ag where ag.year=:year and ag.month=:month")
                .setParameter("year",year).setParameter("month",month).getResultList();
        return (items!=null && items.size()>0) ? Optional.of(items.get(0)):Optional.ofNullable(null);
    }

    @Override
    public Optional<Object[]> DoanhThuNamType(int year) {
        List<Object[]> items=entityManager.createQuery("Select sum(ag.registerRevenue),sum(ag.renewalRevenue),sum(ag.authenticationRevenue),sum(ag.renewalAuthenticationRevenue),sum(ag.revenueTotal) from Aggregation ag where ag.year=:year")
                .setParameter("year",year).getResultList();
        return (items!=null && items.size()>0) ? Optional.of(items.get(0)):Optional.ofNullable(null);
    }

    @Override
    public Optional<Aggregation> getByDay(int year, int month, int day) {
        List<Aggregation> items=entityManager.createQuery("Select ag from Aggregation ag where ag.year=:year and ag.month=:month and ag.dayOfMonth=:day order by ag.id desc")
                .setParameter("year",year).setParameter("month",month).setParameter("day",day).getResultList();
        if(items!=null && items.size()>0) return Optional.ofNullable(items.get(0));
        return Optional.ofNullable(new Aggregation());
    }
}
