package vn.iotstar.dao;

import java.util.List;
import vn.iotstar.model.Rating_24162133;

public interface RatingDAO_24162133 {
    List<Rating_24162133> findByBookId(Integer bookId);
    void insert(Rating_24162133 rating);
    long countByBookId(Integer bookId);
}