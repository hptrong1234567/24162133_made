package vn.iotstar.service;

import java.util.List;
import vn.iotstar.model.Book_24162133;

public interface BookService_24162133 {
    List<Book_24162133> findAll();
    List<Book_24162133> findAll(int page, int size);
    long count();
    int getTotalPages(int size);
    Book_24162133 findById(Integer id);
    Book_24162133 insert(Book_24162133 book);
    void update(Book_24162133 book);
    void delete(Integer id);
}