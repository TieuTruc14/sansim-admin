package com.osp.web.dao.customer;

import com.osp.modelCustomer.CustService;
import com.osp.web.service.customer.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.Optional;

/**
 * Created by Admin on 6/21/2018.
 */
@Repository
@Transactional(value = "transactionManagerCustomer")
public class CustServiceDaoImpl implements CustServiceDao {

    private Logger logger= LogManager.getLogger(CustomerDaoImpl.class);
    @PersistenceContext(unitName = "appCustomer")
    private EntityManager entityManager;

    @Override
    public Optional<Boolean> add(CustService item) {
        entityManager.persist(item);
        entityManager.flush();
        return Optional.of(true);
    }

    @Override
    public Optional<Boolean> delete(Long customerId) {
        Query query=entityManager.createQuery("delete from com.osp.modelCustomer.CustService a WHERE a.customer.id=:customerId").setParameter("customerId",customerId);
        query.executeUpdate();
        return Optional.ofNullable(true);
    }
}
