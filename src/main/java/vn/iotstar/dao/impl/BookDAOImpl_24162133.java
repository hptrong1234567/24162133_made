package vn.iotstar.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import vn.iotstar.dao.BookDAO_24162133;
import vn.iotstar.model.Book_24162133;

public class BookDAOImpl_24162133 implements BookDAO_24162133 {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bookstorePU");

    @Override
    public List<Book_24162133> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT b FROM Book_24162133 b", Book_24162133.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Book_24162133> findAll(int page, int size) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Book_24162133> q = em.createQuery(
                "SELECT b FROM Book_24162133 b ORDER BY b.bookId ASC", Book_24162133.class);
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
            return em.createQuery("SELECT COUNT(b) FROM Book_24162133 b", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public Book_24162133 findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Book_24162133.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public Book_24162133 insert(Book_24162133 book) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(book);
            tx.commit();
            return book;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Book_24162133 book) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(book);
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
            Book_24162133 b = em.find(Book_24162133.class, id);
            if (b != null) em.remove(b);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}