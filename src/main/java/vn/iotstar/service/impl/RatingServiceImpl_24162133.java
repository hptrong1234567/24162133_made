package vn.iotstar.service.impl;

import java.util.List;

import vn.iotstar.dao.RatingDAO_24162133;
import vn.iotstar.dao.impl.RatingDAOImpl_24162133;
import vn.iotstar.model.Rating_24162133;
import vn.iotstar.service.RatingService_24162133;

public class RatingServiceImpl_24162133 implements RatingService_24162133 {

    private final RatingDAO_24162133 ratingDAO = new RatingDAOImpl_24162133();

    @Override
    public List<Rating_24162133> findByBookId(Integer bookId) {
        return ratingDAO.findByBookId(bookId);
    }

    @Override
    public void insert(Rating_24162133 rating) {
        ratingDAO.insert(rating);
    }

    @Override
    public long countByBookId(Integer bookId) {
        return ratingDAO.countByBookId(bookId);
    }
}