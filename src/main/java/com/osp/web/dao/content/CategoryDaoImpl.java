package com.osp.web.dao.content;

import com.osp.common.PagingResult;
import com.osp.modelCustomer.Category;
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
 * Created by Admin on 2/26/2018.
 */
@Repository
@Transactional(value = "transactionManagerCustomer")
public class CategoryDaoImpl implements CategoryDao {
    private Logger logger= LogManager.getLogger(CategoryDaoImpl.class);
    @PersistenceContext(unitName = "appCustomer")
    private EntityManager entityManager;

    @PersistenceUnit(unitName = "appCustomer")
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    LogAccessService logAccessService;

    @Override
    public Optional<PagingResult> page(PagingResult page, String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        int offset=0;
        if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();
        CriteriaQuery<Category> q = cb.createQuery(Category.class);
        Root<Category> root = q.from(Category.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if(StringUtils.isNotBlank(name)){
            predicates.add(cb.like(root.get("name"),"%"+name+"%"));
        }
        predicates.add(cb.equal(root.get("deleted"),false));
        q.select(root).where(predicates.toArray(new Predicate[]{}));
        List<Category> list = entityManager.createQuery(q).setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        if (list != null) {
            page.setItems(list);
        }
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<Category> rootCount = criteriaQuery.from(Category.class);
        criteriaQuery.select(cb.count(rootCount)).where(predicates.toArray(new Predicate[]{}));
        Long rowCount = entityManager.createQuery(criteriaQuery).getSingleResult();
        if (rowCount != null) {
            page.setRowCount(rowCount.longValue());
        }
        return Optional.ofNullable(page);
    }

    @Override
    public Optional<List<Category>> list() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> q = cb.createQuery(Category.class);
        Root<Category> root = q.from(Category.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("deleted"),false));
        predicates.add(cb.equal(root.get("active"),true));
        q.select(root).where(predicates.toArray(new Predicate[]{}));
        List<Category> list = entityManager.createQuery(q).getResultList();
        return Optional.ofNullable(list);
    }

    @Override
    public Optional<Category> get(Integer id) {
        Category item=entityManager.find(Category.class,id);
        return Optional.ofNullable(item);
    }

    @Override
    public Optional<Integer> getMaxValue() {
        Integer value=(Integer)entityManager.createQuery("Select max(c.value) from Category c").getSingleResult();
        return Optional.ofNullable(value);
    }

    @Override
    public Optional<Boolean> addWithWriteLog(Category item, String ipClient) throws Exception {
        EntityManager em=entityManagerFactory.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(item);
            em.flush();
            logAccessService.addLog("Thêm chuyên mục. Id:"+item.getId(),"Chuyên mục bài viết",ipClient);
            em.getTransaction().commit();
        }catch (Exception e){
            logger.error("Have an error method addWithWriteLog:"+e.getMessage());
            em.getTransaction().rollback();
            throw new Exception();
        }finally {
            em.close();
        }
        return Optional.of(true);
    }

    @Override
    public Optional<Boolean> edit(Category item) throws Exception {
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

}
