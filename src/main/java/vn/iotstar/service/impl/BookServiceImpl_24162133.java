package vn.iotstar.service.impl;

import java.util.List;

import vn.iotstar.dao.BookDAO_24162133;
import vn.iotstar.dao.impl.BookDAOImpl_24162133;
import vn.iotstar.model.Book_24162133;
import vn.iotstar.service.BookService_24162133;

public class BookServiceImpl_24162133 implements BookService_24162133 {

    private final BookDAO_24162133 bookDAO = new BookDAOImpl_24162133();

    @Override
    public List<Book_24162133> findAll() {
        return bookDAO.findAll();
    }

    @Override
    public List<Book_24162133> findAll(int page, int size) {
        return bookDAO.findAll(page, size);
    }

    @Override
    public long count() {
        return bookDAO.count();
    }

    @Override
    public int getTotalPages(int size) {
        long total = bookDAO.count();
        return (int) Math.ceil((double) total / size);
    }

    @Override
    public Book_24162133 findById(Integer id) {
        return bookDAO.findById(id);
    }

    @Override
    public Book_24162133 insert(Book_24162133 book) {
        return bookDAO.insert(book);
    }

    @Override
    public void update(Book_24162133 book) {
        bookDAO.update(book);
    }

    @Override
    public void delete(Integer id) {
        bookDAO.delete(id);
    }
}