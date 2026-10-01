package vn.iotstar.dao.impl;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import vn.iotstar.dao.OtpDAO_24162133;
import vn.iotstar.model.OtpToken_24162133;

public class OtpDAOImpl_24162133 implements OtpDAO_24162133 {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bookstorePU");

    @Override
    public OtpToken_24162133 insert(OtpToken_24162133 token) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(token);
            tx.commit();
            return token;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<OtpToken_24162133> findLatestByEmailAndType(String email, String type) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<OtpToken_24162133> q = em.createQuery(
                "SELECT o FROM OtpToken_24162133 o WHERE o.email = :email AND o.type = :type "
              + "AND o.used = false ORDER BY o.createdAt DESC", OtpToken_24162133.class);
            q.setParameter("email", email);
            q.setParameter("type", type);
            q.setMaxResults(1);
            List<OtpToken_24162133> list = q.getResultList();
            return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteByEmailAndType(String email, String type) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createQuery("DELETE FROM OtpToken_24162133 o WHERE o.email = :email AND o.type = :type")
              .setParameter("email", email)
              .setParameter("type", type)
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
    public void update(OtpToken_24162133 token) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(token);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}