package vn.iotstar.service;

import java.util.List;
import vn.iotstar.model.Rating_24162133;

public interface RatingService_24162133 {
    List<Rating_24162133> findByBookId(Integer bookId);
    void insert(Rating_24162133 rating);
    long countByBookId(Integer bookId);
}