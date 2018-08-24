package com.osp.web.dao.msisdn;

import com.osp.common.PagingResult;
import com.osp.modelCustomer.ConfigPackage;
import com.osp.web.service.logaccess.LogAccessService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
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
public class ConfigPackageDaoImpl implements ConfigPackageDao {
    private Logger logger= LogManager.getLogger(ConfigPackageDaoImpl.class);
    @PersistenceContext(unitName = "appCustomer")
//    @Qualifier(value = "transactionManagerCustomer")
    private EntityManager entityManager;

    @PersistenceUnit(unitName = "appCustomer")
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    LogAccessService logAccessService;

    @Override
    public Optional<PagingResult> page(String packageCode,String packageName,Long from,Long to, PagingResult page) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<ConfigPackage> q = cb.createQuery(ConfigPackage.class);
        Root<ConfigPackage> root = q.from(ConfigPackage.class);
        List<Predicate> predicates = new ArrayList<Predicate>();

        int offset=0;
        if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();
        if(StringUtils.isNotBlank(packageCode)){
            predicates.add(cb.equal(root.get("packageCode"),packageCode));
        }
        if(StringUtils.isNotBlank(packageName)){
            predicates.add(cb.like(root.get("packageName"),"%"+packageName+"%"));
        }

        if(from!=null && from.intValue()>0){
            predicates.add(cb.ge(root.get("fee"), from.intValue()));
        }
        if(to!=null && to.longValue()>0){
            predicates.add(cb.le(root.get("fee"), to.longValue()));
        }
        q.select(root).where(predicates.toArray(new Predicate[]{})).orderBy(cb.asc(root.get("maxQuantity")));
        List<ConfigPackage> list = entityManager.createQuery(q).setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();

        if (list != null) {
            page.setItems(list);
        }
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<ConfigPackage> rootCount = criteriaQuery.from(ConfigPackage.class);
        criteriaQuery.select(cb.count(rootCount)).where(predicates.toArray(new Predicate[]{}));
        Long rowCount = entityManager.createQuery(criteriaQuery).getSingleResult();
        if (rowCount != null) {
            page.setRowCount(rowCount.longValue());
        }

        return Optional.ofNullable(page);
    }

    @Override
    public Optional<Long> add(ConfigPackage item,String ipClient) throws Exception{
        EntityManager em=entityManagerFactory.createEntityManager();
        try{

            em.getTransaction().begin();
            em.persist(item);
            em.flush();
            logAccessService.addLog("Thêm gói phí đăng số. Mã gói:"+item.getId(),"Quản lý đăng số",ipClient);
            em.getTransaction().commit();

        }catch (Exception e){
            logger.error("Have an error method add:"+e.getMessage());
            em.getTransaction().rollback();
            throw new Exception();
        }finally {
            em.close();
        }
        return Optional.of(item.getId());
    }

    @Override
    public Optional<Boolean> edit(ConfigPackage item) throws Exception{
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
    public Optional<Boolean> delete(Long id) throws Exception {
        EntityManager em=entityManagerFactory.createEntityManager();
        try{
            em.getTransaction().begin();
            Query query =em.createQuery("delete from ConfigPackage u where u.id=:id").setParameter("id",id);
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

    @Override
    public Optional<ConfigPackage> get(Long id) {
        ConfigPackage item=entityManager.find(ConfigPackage.class,id);
        return Optional.ofNullable(item);
    }

    @Override
    public Optional<ConfigPackage> getByPackageCode(String name) {
        List<ConfigPackage> items=entityManager.createQuery("Select pack from ConfigPackage pack where pack.packageCode=:name",ConfigPackage.class).setParameter("name",name).getResultList();
        if(items!=null && items.size()>0) return Optional.of(items.get(0));
        return Optional.ofNullable(null);
    }

    @Override
    public Optional<List<ConfigPackage>> list() {
        List<ConfigPackage> items=entityManager.createQuery("SELECT pack from ConfigPackage pack",ConfigPackage.class).getResultList();
        return Optional.ofNullable(items);
    }
}
