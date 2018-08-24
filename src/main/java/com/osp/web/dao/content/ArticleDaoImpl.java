package com.osp.web.dao.content;

import com.osp.common.PagingResult;
import com.osp.modelCustomer.Article;
import com.osp.modelCustomer.Category;
import com.osp.web.service.logaccess.LogAccessService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
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
public class ArticleDaoImpl implements ArticleDao {
    private Logger logger= LogManager.getLogger(ArticleDaoImpl.class);
    @PersistenceContext(unitName = "appCustomer")
    private EntityManager entityManager;

    @PersistenceUnit(unitName = "appCustomer")
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    LogAccessService logAccessService;

    @Override
    public Optional<PagingResult> page(PagingResult page, String name,Integer categoryId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        int offset=0;
        if(page.getPageNumber()>0) offset=(page.getPageNumber()-1)*page.getNumberPerPage();
//        CriteriaQuery<Article> q = cb.createQuery(Article.class);
        CriteriaQuery<Object[]> q = cb.createQuery(Object[].class);
        Root<Article> root = q.from(Article.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if(StringUtils.isNotBlank(name)){
            predicates.add(cb.like(root.get("title"),"%"+name+"%"));
        }
        if(categoryId!=null){
            predicates.add(cb.equal(root.get("category").get("id"),categoryId));
        }
        predicates.add(cb.equal(root.get("deleted"),false));
        q.select(cb.array(root.get("id"), root.get("title"),root.get("dateCreated"),root.get("active"), root.get("category").get("name")));
        q.where(predicates.toArray(new Predicate[]{})).orderBy(cb.desc(root.get("dateCreated")));
        List<Object[]> list = entityManager.createQuery(q).setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        if (list != null) {
            page.setItems(list);
        }
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<Article> rootCount = criteriaQuery.from(Article.class);
        criteriaQuery.select(cb.count(rootCount)).where(predicates.toArray(new Predicate[]{}));
        Long rowCount = entityManager.createQuery(criteriaQuery).getSingleResult();
        if (rowCount != null) {
            page.setRowCount(rowCount.longValue());
        }
        return Optional.ofNullable(page);
    }

    @Override
    public Optional<Article> get(Integer id) {
        Article item=entityManager.find(Article.class,id);
        return Optional.ofNullable(item);
    }

    @Override
    public Optional<Boolean> addWithWriteLog(Article item, String ipClient) throws Exception {
        EntityManager em=entityManagerFactory.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(item);
            em.flush();
            logAccessService.addLog("Thêm bài viết. Id:"+item.getId(),"Bài viết",ipClient);
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
    public Optional<Boolean> edit(Article item) throws Exception {
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
