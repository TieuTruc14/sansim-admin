package com.osp.web.dao.transpay;

import com.osp.common.Constants;
import com.osp.common.PagingResult;
import com.osp.common.QueryBuilder;
import com.osp.modelCustomer.ConfigPackage;
import com.osp.modelCustomer.TranspayLog;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 1/10/2018.
 */
@Repository
@Transactional
public class TranspayDaoImpl implements TranspayDao {
    @PersistenceContext(unitName = "appCustomer")
    private EntityManager entityManager;
    private Logger logger= LogManager.getLogger(TranspayDaoImpl.class);

    @Override
    public Optional<PagingResult> page(PagingResult page, String msisdn, String username, Date from, Date to, Long packageId, Byte type) {
        int offset=0;
        if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Object[]> q = cb.createQuery(Object[].class);
        Root<TranspayLog> root = q.from(TranspayLog.class);
        List<Predicate> predicates = new ArrayList<Predicate>();

        if(StringUtils.isNotBlank(msisdn)){
            predicates.add(cb.equal(root.get("msisdnPay"),msisdn));
        }
        if(StringUtils.isNotBlank(username)){
            predicates.add(cb.equal(root.get("customer").get("username"),username));
        }
        if(from!=null){
            predicates.add(cb.greaterThanOrEqualTo(root.get("genDate"), from));
        }
        if(to!=null){
            predicates.add(cb.lessThanOrEqualTo(root.get("genDate"), to));
        }
        if(packageId!=null && packageId.longValue()>0){
            predicates.add(cb.equal(root.get("configPackage").get("id"),packageId));
        }
        if(type!=null){
//            if(type==5){
//                Predicate predicate0=cb.equal(root.get("type"), Constants.TranSpay.HuyHeThong);//huy do he thong kiem tra
//                Predicate predicate3=cb.equal(root.get("type"),Constants.TranSpay.NguoiDungHuy);//huy do nguoi dung dang ky goi khac
//                Predicate predicate=cb.or(predicate0,predicate3);
//                predicates.add(predicate);
//            }else{
//                predicates.add(cb.equal(root.get("type"),type));
//            }
            predicates.add(cb.equal(root.get("type"),type));
        }else{
            Predicate predicate0=cb.equal(root.get("type"),Constants.TranSpay.HuyHeThong);//huy do he thong kiem tra
            Predicate predicate3=cb.equal(root.get("type"),Constants.TranSpay.NguoiDungHuy);//huy do nguoi dung dang ky goi khac
            Predicate predicate1=cb.equal(root.get("type"),Constants.TranSpay.DangKy);//dang ky goi
            Predicate predicate2=cb.equal(root.get("type"),Constants.TranSpay.GiaHan);//gia han goi
            Predicate predicate=cb.or(predicate0,predicate1,predicate2,predicate3);
            predicates.add(predicate);
        }
        q.select(cb.array(root.get("id"),root.get("genDate"),root.get("msisdnPay"),root.get("customer").get("username"),root.get("configPackage").get("packageCode"),root.get("price"),root.get("type"),root.get("payResult")));
        q.where(predicates.toArray(new Predicate[]{})).orderBy(cb.desc(root.get("genDate")));
        List<Object[]> list = entityManager.createQuery(q).setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();

        if (list != null) {
            page.setItems(list);
        }
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<TranspayLog> rootCount = criteriaQuery.from(TranspayLog.class);
        criteriaQuery.select(cb.count(rootCount)).where(predicates.toArray(new Predicate[]{}));
        Long rowCount = entityManager.createQuery(criteriaQuery).getSingleResult();
        if (rowCount != null) {
            page.setRowCount(rowCount.longValue());
        }

        return Optional.of(page);
    }


    @Override
    public Optional<PagingResult> pageReportGeneralSaleAndTrade(PagingResult page, Byte type,boolean destroy, String username, Date from, Date to) {
        int offset=0;
        if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();

        StringBuffer sqlBuffer = new StringBuffer("SELECT tran.type,cus.username,cus.fullName,sum(tran.price),count(tran.type) "+
                "from SIMA_TRANSPAY_LOG tran left join ( Select Distinct type from SIMA_TRANSPAY_LOG ) s " +
                "cross join SIMA_CUSTOMER cus ON tran.CUSTOMER_ID=cus.id AND s.type=tran.type ");
        StringBuffer sqlBufferCount = new StringBuffer("SELECT count(count(tran.type)) "+
                "from SIMA_TRANSPAY_LOG tran left join ( Select Distinct type from SIMA_TRANSPAY_LOG ) s " +
                "cross join SIMA_CUSTOMER cus ON tran.CUSTOMER_ID=cus.id AND s.type=tran.type ");

        Query query = filterBuilder(sqlBuffer,true,type,destroy,username,from,to);
        List<Object[]> list=query.setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();

        if (list != null) {
            page.setItems(list);
        }
        Query queryCount = filterBuilder(sqlBufferCount,false,type,destroy,username,from,to);
        BigDecimal rowCount = (BigDecimal)queryCount.getSingleResult();
        if (rowCount != null) {
            page.setRowCount(rowCount.longValue());
        }

        return Optional.of(page);

    }

    @Override
    public Optional<PagingResult> downloadReportGeneralSaleAndTrade(PagingResult page, Byte type,boolean destroy, String username, Date from, Date to) {
        StringBuffer sqlBuffer = new StringBuffer("SELECT tran.type,cus.username,cus.fullName,sum(tran.price),count(tran.type) "+
                "from SIMA_TRANSPAY_LOG tran left join ( Select Distinct type from SIMA_TRANSPAY_LOG ) s " +
                "cross join SIMA_CUSTOMER cus ON tran.CUSTOMER_ID=cus.id AND s.type=tran.type ");

        Query query = filterBuilder(sqlBuffer,true,type,destroy,username,from,to);
        List<Object[]> list=query.getResultList();
        if (list != null) {
            page.setItems(list);
            page.setRowCount(list.size());
        }
        return Optional.of(page);
    }

    @Override
    public Optional<PagingResult> pageReportDetailSaleAndTrade(PagingResult page, Byte type,boolean destroy, String username, Date from, Date to,Long packageId) {
        int offset=0;
        if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();

        StringBuffer sqlBuffer = new StringBuffer("SELECT tran.type,cus.username,cus.fullName,sum(tran.price),count(tran.type),pack.package_code,tran.package_id "+
                "from SIMA_TRANSPAY_LOG tran left join ( Select Distinct type from SIMA_TRANSPAY_LOG ) s " +
                "cross join SIMA_CUSTOMER cus ON tran.CUSTOMER_ID=cus.id AND s.type=tran.type left join SIMA_CONF_PACKAGE pack ON tran.package_id=pack.id ");
        StringBuffer sqlBufferCount = new StringBuffer("SELECT count(count(tran.type)) "+
                "from SIMA_TRANSPAY_LOG tran left join ( Select Distinct type from SIMA_TRANSPAY_LOG ) s " +
                "cross join SIMA_CUSTOMER cus ON tran.CUSTOMER_ID=cus.id AND s.type=tran.type left join SIMA_CONF_PACKAGE pack ON tran.package_id=pack.id ");

        Query query = filterBuilderDetail(sqlBuffer,true,type,destroy,username,from,to,packageId);
        List<Object[]> list=query.setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();

        if (list != null) {
            page.setItems(list);
        }
        Query queryCount = filterBuilderDetail(sqlBufferCount,false,type,destroy,username,from,to,packageId);
        BigDecimal rowCount = (BigDecimal)queryCount.getSingleResult();
        if (rowCount != null) {
            page.setRowCount(rowCount.longValue());
        }

        return Optional.of(page);
    }

    @Override
    public Optional<PagingResult> downloadReportDetailSaleAndTrade(PagingResult page, Byte type,boolean destroy, String username, Date from, Date to, Long packageId) {
        StringBuffer sqlBuffer = new StringBuffer("SELECT tran.type,cus.username,cus.fullName,sum(tran.price),count(tran.type),pack.package_code,tran.package_id "+
                "from SIMA_TRANSPAY_LOG tran left join ( Select Distinct type from SIMA_TRANSPAY_LOG ) s " +
                "cross join SIMA_CUSTOMER cus ON tran.CUSTOMER_ID=cus.id AND s.type=tran.type left join SIMA_CONF_PACKAGE pack ON tran.package_id=pack.id ");
        Query query = filterBuilderDetail(sqlBuffer,true,type,destroy,username,from,to,packageId);
        List<Object[]> list=query.getResultList();

        if (list != null) {
            page.setItems(list);
            page.setRowCount(list.size());
        }
        return Optional.of(page);
    }

    private Query filterBuilder(StringBuffer stringBuffer, boolean order, Byte type,boolean destroy, String username, Date from, Date to){
        Query result = null;
        try {
            QueryBuilder builder = new QueryBuilder(entityManager, stringBuffer);
            genType(type,destroy,builder);
            if (StringUtils.isNotBlank(username)) {
                builder.and(QueryBuilder.EQ, "cus.username", username);
            }
            if (from!=null) {
                builder.and(QueryBuilder.GE, "tran.GEN_DATE", from);
            }
            if (to!=null) {
                builder.and(QueryBuilder.LE, "tran.GEN_DATE", to);
            }

            builder.addOrder("cus.username", QueryBuilder.ASC);
            builder.addOrder("tran.type", QueryBuilder.ASC);
            builder.addGroupBy("tran.type");
            builder.addGroupBy("cus.username");
            builder.addGroupBy("cus.fullname");

            result = builder.initNativeQueryHaveGroupBy(order);
        } catch (Exception e) {
            logger.error("have an error when create Query:"+e.getMessage());
        }
        return result;
    }
    private void genType(Byte type,boolean destroy,QueryBuilder builder){
        if (type != null) {
//            if(type.intValue()==Constants.TranSpay.HuyHeThong.intValue()){
//                QueryBuilder.ConditionObject condition5=new QueryBuilder.ConditionObject(QueryBuilder.EQ,"tran.type",type);
//                QueryBuilder.ConditionObject condition6=new QueryBuilder.ConditionObject(QueryBuilder.EQ,"tran.type",Constants.TranSpay.NguoiDungHuy);
//                List<QueryBuilder.ConditionObject> list=new ArrayList<>();
//                list.add(condition5);
//                list.add(condition6);
//                builder.andOrListNative(list);
//            }else{
//                builder.and(QueryBuilder.EQ, "tran.type", type);
//            }
            builder.and(QueryBuilder.EQ, "tran.type", type);
        }else{
            List<QueryBuilder.ConditionObject> list=new ArrayList<>();
            if(!destroy){
                QueryBuilder.ConditionObject condition3=new QueryBuilder.ConditionObject(QueryBuilder.EQ,"tran.type",Constants.TranSpay.XacThuc);
                QueryBuilder.ConditionObject condition4=new QueryBuilder.ConditionObject(QueryBuilder.EQ,"tran.type",Constants.TranSpay.GiaHanXacThuc);
                QueryBuilder.ConditionObject condition1=new QueryBuilder.ConditionObject(QueryBuilder.EQ,"tran.type",Constants.TranSpay.DangKy);
                QueryBuilder.ConditionObject condition2=new QueryBuilder.ConditionObject(QueryBuilder.EQ,"tran.type",Constants.TranSpay.GiaHan);

                list.add(condition3);
                list.add(condition4);
                list.add(condition1);
                list.add(condition2);
                builder.andOrListNative(list);
            }
        }
    }

    private Query filterBuilderDetail(StringBuffer stringBuffer, boolean order, Byte type,boolean destroy,String username,Date from,Date to,Long packageId){
        Query result = null;
        try {
            QueryBuilder builder = new QueryBuilder(entityManager, stringBuffer);

            genType(type,destroy,builder);
            if (StringUtils.isNotBlank(username)) {
                builder.and(QueryBuilder.EQ, "cus.username", username);
            }
            if (from!=null) {
                builder.and(QueryBuilder.GE, "tran.GEN_DATE", from);
            }
            if (to!=null) {
                builder.and(QueryBuilder.LE, "tran.GEN_DATE", to);
            }
            if(packageId!=null && packageId.longValue()>0){
                builder.and(QueryBuilder.EQ,"tran.package_id",packageId);
            }

            builder.addOrder("cus.username", QueryBuilder.ASC);
            builder.addOrder("tran.type", QueryBuilder.ASC);
            builder.addOrder("tran.package_id", QueryBuilder.ASC);
            builder.addGroupBy("tran.type");
            builder.addGroupBy("cus.username");
            builder.addGroupBy("cus.fullname");
            builder.addGroupBy("pack.package_code");
            builder.addGroupBy("tran.package_id");

            result = builder.initNativeQueryHaveGroupBy(order);
        } catch (Exception e) {
            logger.error("have an error when create Query:"+e.getMessage());
        }
        return result;
    }

    @Override
    public Optional<Boolean> checkTranspayByConfigPackage(Long id) {
        Long count=(Long)entityManager.createQuery("Select count(tran.id) from TranspayLog tran where tran.configPackage.id=:id").setParameter("id",id).getSingleResult();
        if(count!=null && count>0){
            return Optional.of(true);
        }
        return Optional.of(false);
    }

    @Override
    public Optional<Long> countTranspayByType(Byte type,Date from, Date to) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<TranspayLog> root = criteriaQuery.from(TranspayLog.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if(from!=null){
            predicates.add(cb.greaterThanOrEqualTo(root.get("genDate"), from));
        }
        if(to!=null){
            predicates.add(cb.lessThanOrEqualTo(root.get("genDate"), to));
        }
        if(type!=null){
            predicates.add(cb.equal(root.get("type"),type));
        }
        criteriaQuery.select(cb.count(root)).where(predicates.toArray(new Predicate[]{}));
        Long rowCount = entityManager.createQuery(criteriaQuery).getSingleResult();
        return rowCount==null ? Optional.of(0L):Optional.of(rowCount);
    }

    @Override
    public Optional<Long> revenueByType(Byte type, Date from, Date to) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<TranspayLog> root = criteriaQuery.from(TranspayLog.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if(from!=null){
            predicates.add(cb.greaterThanOrEqualTo(root.get("genDate"), from));
        }
        if(to!=null){
            predicates.add(cb.lessThanOrEqualTo(root.get("genDate"), to));
        }
        if(type!=null){
            predicates.add(cb.equal(root.get("type"),type));
        }
        criteriaQuery.select(cb.sum(root.get("price"))).where(predicates.toArray(new Predicate[]{}));
        Long revenue=entityManager.createQuery(criteriaQuery).getSingleResult();
        return revenue==null ? Optional.of(0L):Optional.of(revenue);
    }
}
