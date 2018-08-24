package com.osp.web.dao.customer;

import com.osp.common.PagingResult;
import com.osp.common.QueryBuilder;
import com.osp.modelCustomer.ConfigPackage;
import com.osp.modelCustomer.CustService;
import com.osp.modelCustomer.Customer;
import com.osp.modelCustomer.StockMsisdn;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 12/18/2017.
 */
@Repository
@Transactional(value = "transactionManagerCustomer")
public class CustomerDaoImpl implements CustomerDao {
    private Logger logger= LogManager.getLogger(CustomerDaoImpl.class);
    @PersistenceContext(unitName = "appCustomer")
    private EntityManager entityManager;

    @PersistenceUnit(unitName = "appCustomer")
    private EntityManagerFactory entityManagerFactory;

    @Override
    public Optional<PagingResult> page(PagingResult page, Long msisdn, String username, String fullName, String packageCode, Byte active) {
        int offset=0;
        if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();

        StringBuffer sqlBuffer = new StringBuffer("SELECT DISTINCT cus.id,cus.genDate,cus.msisdn,cus.username,cus.fullName,pack.packageCode "+
                ",service.expiredDate,(select count(msisdn.id) from StockMsisdn msisdn where msisdn.customer.id=cus.id) as soluong " +
                ",cus.description,cus.address,cus.active "+
                "from Customer cus " +
                "LEFT join CustService service ON cus.id=service.customer.id " +
                "LEFT join ConfigPackage pack on service.configPackage.id=pack.id " +
                "LEFT join StockMsisdn msisdn ON cus.id=msisdn.customer.id  ");
        StringBuffer sqlBufferCount = new StringBuffer("SELECT count(DISTINCT cus.id)"+
                "from Customer cus " +
                "LEFT join CustService service ON cus.id=service.customer.id " +
                "LEFT join ConfigPackage pack on service.configPackage.id=pack.id " +
                "LEFT join StockMsisdn msisdn ON cus.id=msisdn.customer.id  ");
        Query query = filterBuilderSingle(sqlBuffer,true,msisdn,username,fullName,packageCode,active);
        List<Object[]> list=query.setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();

        if (list != null) {
            page.setItems(list);
        }
        Query queryCount = filterBuilderSingle(sqlBufferCount,false,msisdn,username,fullName,packageCode,active);
        Long rowCount = (Long)queryCount.getSingleResult();
        if (rowCount != null) {
            page.setRowCount(rowCount.longValue());
        }

        return Optional.ofNullable(page);

    }

    private Query filterBuilderSingle(StringBuffer stringBuffer,boolean order,Long msisdn, String username, String fullName, String packageCode, Byte active){

        Query result = null;
        try {
            QueryBuilder builder = new QueryBuilder(entityManager, stringBuffer);

            if (msisdn != null) {
                builder.and(QueryBuilder.LIKE, "cus.msisdn", "%" + msisdn + "%");
            }
            if (StringUtils.isNotBlank(username)) {
                builder.and(QueryBuilder.EQ, "cus.username", username);
            }
            if (StringUtils.isNotBlank(fullName)) {
                builder.and(QueryBuilder.LIKE, "cus.fullName", "%"+fullName+"%");
            }
            if (StringUtils.isNotBlank(packageCode)) {
                builder.and(QueryBuilder.EQ, "pack.packageCode", packageCode);
            }
            if (active!=null) {
                builder.and(QueryBuilder.EQ, "cus.active", active);
            }

            builder.addOrder("cus.genDate", QueryBuilder.DESC);

            result = builder.initQuery(order);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<Customer> get(Long id) {
        Customer customer =entityManager.find(Customer.class,id);
        return Optional.ofNullable(customer);
    }

    @Override
    public Optional<Boolean> edit(Customer item) throws Exception {
        EntityManager em=entityManagerFactory.createEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(item);
            em.flush();
            em.getTransaction().commit();
        }catch (Exception e){
            logger.error("Have an error in method customerDaoImpl.edit:"+e.getMessage());
            em.getTransaction().rollback();
            throw new Exception();
        }finally {
            em.close();
        }
        return Optional.of(true);
    }

    @Override
    public Optional<List<String>> searchUsername(String name) {
        List<String> items=entityManager.createQuery("Select cus.username from Customer cus where cus.username like :name").setParameter("name","%"+name+"%").setMaxResults(100).getResultList();
        return Optional.ofNullable(items);
    }

    @Override
    public Optional<Long> countCusToday(Date from,Date to) {
        Long count=(Long)entityManager.createQuery("select count(cus.id) from Customer cus where cus.genDate>=:fromDate and cus.genDate<=:toDate")
                .setParameter("fromDate",from).setParameter("toDate",to).getSingleResult();
        if(count!=null && count.longValue()>0) return Optional.of(count);
        return Optional.of(0L);
    }

    @Override
    public Optional<Long> totalCus(Date to) {
        Long count=0L;
        if(to!=null){
            count=(Long)entityManager.createQuery("Select count(cus.id) from Customer cus where cus.genDate<=:to").setParameter("to",to).getSingleResult();
        }else{
            count=(Long)entityManager.createQuery("Select count(cus.id) from Customer cus").getSingleResult();
        }

        return (count!=null && count.longValue()>0)? Optional.of(count):Optional.of(0L);
    }

    @Override
    public Optional<Long> totalCusByStatus(Date to,Byte active) {
        Long count=0L;
        if(to!=null){
            count=(Long)entityManager.createQuery("Select count(cus.id) from Customer cus where cus.active=:active and cus.genDate<=:to")
                    .setParameter("active",active).setParameter("to",to).getSingleResult();
        }else{
            count=(Long)entityManager.createQuery("Select count(cus.id) from Customer cus where cus.active=:active").setParameter("active",active).getSingleResult();
        }
        return (count!=null && count.longValue()>0)? Optional.of(count):Optional.of(0L);
    }
}
