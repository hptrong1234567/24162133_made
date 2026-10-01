package vn.iotstar.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import vn.iotstar.dao.UserDAO_24162133;
import vn.iotstar.model.User_24162133;

public class UserDAOImpl_24162133 implements UserDAO_24162133 {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bookstorePU");

    @Override
    public List<User_24162133> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<User_24162133> q = em.createQuery("SELECT u FROM User_24162133 u", User_24162133.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public User_24162133 findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(User_24162133.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public User_24162133 findByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<User_24162133> q = em.createQuery(
                "SELECT u FROM User_24162133 u WHERE u.email = :email", User_24162133.class);
            q.setParameter("email", email);
            List<User_24162133> list = q.getResultList();
            return list.isEmpty() ? null : list.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public User_24162133 findByUsernameOrEmail(String login) {
        // login có thể là email
        return findByEmail(login);
    }

    @Override
    public boolean existsByEmail(String email) {
        return findByEmail(email) != null;
    }

    @Override
    public User_24162133 insert(User_24162133 user) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(user);
            tx.commit();
            return user;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(User_24162133 user) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(user);
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
            User_24162133 u = em.find(User_24162133.class, id);
            if (u != null) em.remove(u);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public long count() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT COUNT(u) FROM User_24162133 u", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }
}