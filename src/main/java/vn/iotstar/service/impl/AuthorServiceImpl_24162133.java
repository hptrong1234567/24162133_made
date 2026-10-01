package vn.iotstar.service.impl;

import java.util.List;

import vn.iotstar.dao.AuthorDAO_24162133;
import vn.iotstar.dao.impl.AuthorDAOImpl_24162133;
import vn.iotstar.model.Author_24162133;
import vn.iotstar.service.AuthorService_24162133;

public class AuthorServiceImpl_24162133 implements AuthorService_24162133 {

    private final AuthorDAO_24162133 authorDAO = new AuthorDAOImpl_24162133();

    @Override
    public List<Author_24162133> findAll() {
        return authorDAO.findAll();
    }

    @Override
    public List<Author_24162133> findAll(int page, int size) {
        return authorDAO.findAll(page, size);
    }

    @Override
    public long count() {
        return authorDAO.count();
    }

    @Override
    public int getTotalPages(int size) {
        long total = authorDAO.count();
        return (int) Math.ceil((double) total / size);
    }

    @Override
    public Author_24162133 findById(Integer id) {
        return authorDAO.findById(id);
    }

    @Override
    public Author_24162133 insert(Author_24162133 author) {
        return authorDAO.insert(author);
    }

    @Override
    public void update(Author_24162133 author) {
        authorDAO.update(author);
    }

    @Override
    public void delete(Integer id) {
        authorDAO.delete(id);
    }
}