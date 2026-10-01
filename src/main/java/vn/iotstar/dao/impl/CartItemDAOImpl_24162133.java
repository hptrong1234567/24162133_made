package vn.iotstar.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import vn.iotstar.dao.CartItemDAO_24162133;
import vn.iotstar.model.CartItem_24162133;

public class CartItemDAOImpl_24162133 implements CartItemDAO_24162133 {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("bookstorePU");

    @Override
    public List<CartItem_24162133> findByUserId(Integer userId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<CartItem_24162133> q = em.createQuery(
                    "SELECT c FROM CartItem_24162133 c WHERE c.user.id = :uid ORDER BY c.cartItemId DESC",
                    CartItem_24162133.class);
            q.setParameter("uid", userId);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public CartItem_24162133 findByUserAndBook(Integer userId, Integer bookId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<CartItem_24162133> q = em.createQuery(
                    "SELECT c FROM CartItem_24162133 c WHERE c.user.id = :uid AND c.book.bookId = :bid",
                    CartItem_24162133.class);
            q.setParameter("uid", userId);
            q.setParameter("bid", bookId);
            List<CartItem_24162133> list = q.getResultList();
            return list.isEmpty() ? null : list.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public CartItem_24162133 findById(Integer cartItemId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(CartItem_24162133.class, cartItemId);
        } finally {
            em.close();
        }
    }

    @Override
    public CartItem_24162133 insert(CartItem_24162133 item) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(item);
            tx.commit();
            return item;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(CartItem_24162133 item) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(item);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Integer cartItemId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            CartItem_24162133 item = em.find(CartItem_24162133.class, cartItemId);
            if (item != null) em.remove(item);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteByUserId(Integer userId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createQuery("DELETE FROM CartItem_24162133 c WHERE c.user.id = :uid")
              .setParameter("uid", userId)
              .executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public long countByUserId(Integer userId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Long> q = em.createQuery(
                    "SELECT COUNT(c) FROM CartItem_24162133 c WHERE c.user.id = :uid", Long.class);
            q.setParameter("uid", userId);
            return q.getSingleResult();
        } finally {
            em.close();
        }
    }
}