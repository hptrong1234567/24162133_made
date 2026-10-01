package vn.iotstar.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import vn.iotstar.dao.RatingDAO_24162133;
import vn.iotstar.model.Rating_24162133;

public class RatingDAOImpl_24162133 implements RatingDAO_24162133 {

    private static final EntityManagerFactory emf = 
            Persistence.createEntityManagerFactory("bookstorePU");

    @Override
    public List<Rating_24162133> findByBookId(Integer bookId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Rating_24162133> q = em.createQuery(
                "SELECT r FROM Rating_24162133 r WHERE r.book.bookId = :bid", 
                Rating_24162133.class);
            q.setParameter("bid", bookId);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void insert(Rating_24162133 rating) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            System.out.println(">>> DEBUG insert rating:");
            System.out.println(">>>   bookId = " + rating.getBook().getBookId());
            System.out.println(">>>   userId = " + rating.getUser().getId());
            System.out.println(">>>   rating = " + rating.getRating());
            System.out.println(">>>   reviewText = " + rating.getReviewText());

            // DÙNG merge THAY VÌ persist
            em.merge(rating);

            tx.commit();
            System.out.println(">>> DEBUG: merge OK");

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.out.println(">>> DEBUG LỖI: " + e.getClass().getName());
            System.out.println(">>>         " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public long countByBookId(Integer bookId) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Long> q = em.createQuery(
                "SELECT COUNT(r) FROM Rating_24162133 r WHERE r.book.bookId = :bid", 
                Long.class);
            q.setParameter("bid", bookId);
            return q.getSingleResult();
        } finally {
            em.close();
        }
    }
}