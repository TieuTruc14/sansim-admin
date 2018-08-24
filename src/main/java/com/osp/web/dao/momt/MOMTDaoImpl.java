package com.osp.web.dao.momt;

import com.osp.common.PagingResult;
import com.osp.common.QueryBuilder;
import com.osp.modelCustomer.MTLog;
import com.osp.modelCustomer.MTQueue;
import com.osp.web.dao.customer.CustomerDaoImpl;
import com.osp.web.service.logaccess.LogAccessService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 1/8/2018.
 */
@Repository
@Transactional
public class MOMTDaoImpl implements MOMTDao {
    private Logger logger= LogManager.getLogger(CustomerDaoImpl.class);
    @PersistenceContext(unitName = "appCustomer")
    private EntityManager entityManager;

    @PersistenceUnit(unitName = "appCustomer")
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    LogAccessService logAccessService;

    @Override
    public Optional<PagingResult> getMOMTofMsisdn(PagingResult page,String msisdn, String username) {
        int offset=0;
        if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();
        //de full join de lay du lieu tu ca 2 bang, neu co ko trung khop thi se load dupercate
        List<Object[]> items=entityManager.createQuery("SELECT mo.id,mo.info,mo.genDate,mt.info FROM MOLog mo left join MTLog mt ON mo.requestId=mt.requestId where mo.customer.username=:username and mo.senderNumber=:msisdn ORDER BY mo.genDate desc ",Object[].class)
                .setParameter("username",username).setParameter("msisdn",msisdn).getResultList();
        Long count=(Long)entityManager.createQuery("SELECT count(mo.id) FROM MOLog mo left join MTLog mt ON mo.requestId=mt.requestId where mo.customer.username=:username and mo.senderNumber=:msisdn")
                .setParameter("username",username).setParameter("msisdn",msisdn).setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getSingleResult();
        if(items!=null && items.size()>0 && count.longValue()>0){
            page.setItems(items);
            page.setRowCount(count.longValue());
        }
        return Optional.ofNullable(page);
    }

    @Override
    public Optional<PagingResult> page(PagingResult page, String username, Date from, Date to, String msisdn) {
        int offset=0;
        if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();
        StringBuffer sqlBuffer = new StringBuffer("SELECT mo.id,mo.genDate,mo.senderNumber,mo.serviceNumber,cus.username,mo.info,mt.id,mt.info,mt.receiverNumber "+
                " from MOLog mo FULL JOIN MTLog mt ON mo.requestId=mt.requestId LEFT JOIN Customer cus on mo.customer.id=cus.id ");
        StringBuffer sqlBufferCount = new StringBuffer("SELECT count(mo.id) from MOLog mo FULL JOIN MTLog mt ON mo.requestId=mt.requestId ");
        Query query = filterBuilderSingle(sqlBuffer,true,username,from,to,msisdn);
        List<Object[]> list=query.setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();

        if (list != null) {
            page.setItems(list);
        }
        Query queryCount = filterBuilderSingle(sqlBufferCount,false,username,from,to,msisdn);
        Long rowCount = (Long)queryCount.getSingleResult();
        if (rowCount != null) {
            page.setRowCount(rowCount.longValue());
        }

        return Optional.ofNullable(page);
    }

    private Query filterBuilderSingle(StringBuffer stringBuffer, boolean order, String username,Date from,Date to,String msisdn){

        Query result = null;
        try {
            QueryBuilder builder = new QueryBuilder(entityManager, stringBuffer);

            if (StringUtils.isNotBlank(username)) {
                builder.and(QueryBuilder.EQ, "mo.customer.username", username);
            }
            if (from!=null) {
                builder.and(QueryBuilder.GE, "mo.genDate", from);
            }
            if (to!=null) {
                builder.and(QueryBuilder.LE, "mo.genDate", to);
            }
            if (StringUtils.isNotBlank(msisdn)) {
                builder.and(QueryBuilder.EQ, "mo.senderNumber", msisdn);
            }

            builder.addOrder("mo.genDate", QueryBuilder.DESC);

            result = builder.initQuery(order);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<MTLog> getMTLog(Long id) {
        MTLog item=entityManager.find(MTLog.class,id);
        return Optional.ofNullable(item);
    }

    @Override
    public Optional<Boolean> addMTQueue(MTQueue item,String ip) throws Exception{
        EntityManager em=entityManagerFactory.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(item);
            em.flush();
            logAccessService.addLog("Gửi lại MT. ID: "+item.getId(),"Quản lý lịch sử MO/MT",ip);
            em.getTransaction().commit();
        }catch (Exception e){
            logger.error("Have an error method addMTQueue:"+e.getMessage());
            em.getTransaction().rollback();
            throw new Exception();
        }finally {
            em.close();
        }
        return Optional.of(true);
    }
}
