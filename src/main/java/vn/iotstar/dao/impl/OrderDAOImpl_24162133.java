package vn.iotstar.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import vn.iotstar.dao.OrderDAO_24162133;
import vn.iotstar.model.Order_24162133;

public class OrderDAOImpl_24162133 implements OrderDAO_24162133 {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("bookstorePU");

    @Override
    public List<Order_24162133> findByUserId(Integer userId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Order_24162133> q = em.createQuery(
                    "SELECT o FROM Order_24162133 o WHERE o.user.id = :uid ORDER BY o.orderDate DESC",
                    Order_24162133.class);
            q.setParameter("uid", userId);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Order_24162133 findById(Integer orderId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Order_24162133.class, orderId);
        } finally {
            em.close();
        }
    }

    @Override
    public Order_24162133 insert(Order_24162133 order) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(order);
            tx.commit();
            return order;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Order_24162133 order) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(order);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Order_24162133> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Order_24162133> q = em.createQuery(
                    "SELECT o FROM Order_24162133 o ORDER BY o.orderDate DESC",
                    Order_24162133.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}