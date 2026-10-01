package vn.iotstar.dao;

import java.util.List;
import vn.iotstar.model.Author_24162133;

public interface AuthorDAO_24162133 {
    List<Author_24162133> findAll();
    List<Author_24162133> findAll(int page, int size);
    long count();
    Author_24162133 findById(Integer id);
    Author_24162133 insert(Author_24162133 author);
    void update(Author_24162133 author);
    void delete(Integer id);
}