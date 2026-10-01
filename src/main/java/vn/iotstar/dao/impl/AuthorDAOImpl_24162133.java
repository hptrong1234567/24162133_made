package vn.iotstar.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import vn.iotstar.dao.AuthorDAO_24162133;
import vn.iotstar.model.Author_24162133;

public class AuthorDAOImpl_24162133 implements AuthorDAO_24162133 {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bookstorePU");

    @Override
    public List<Author_24162133> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT a FROM Author_24162133 a", Author_24162133.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Author_24162133> findAll(int page, int size) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Author_24162133> q = em.createQuery(
                "SELECT a FROM Author_24162133 a ORDER BY a.authorId ASC", Author_24162133.class);
            q.setFirstResult((page - 1) * size);
            q.setMaxResults(size);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long count() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT COUNT(a) FROM Author_24162133 a", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public Author_24162133 findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Author_24162133.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public Author_24162133 insert(Author_24162133 author) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(author);
            tx.commit();
            return author;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Author_24162133 author) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(author);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Author_24162133 a = em.find(Author_24162133.class, id);
            if (a != null) em.remove(a);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}