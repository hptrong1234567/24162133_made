package vn.iotstar.dao;

import java.util.List;
import vn.iotstar.model.Book_24162133;

public interface BookDAO_24162133 {
    List<Book_24162133> findAll();
    List<Book_24162133> findAll(int page, int size);   // phân trang
    long count();
    Book_24162133 findById(Integer id);
    Book_24162133 insert(Book_24162133 book);
    void update(Book_24162133 book);
    void delete(Integer id);
}