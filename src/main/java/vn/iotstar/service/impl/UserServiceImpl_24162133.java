package vn.iotstar.service.impl;

import java.util.List;

import vn.iotstar.dao.UserDAO_24162133;
import vn.iotstar.dao.impl.UserDAOImpl_24162133;
import vn.iotstar.model.User_24162133;
import vn.iotstar.service.UserService_24162133;

public class UserServiceImpl_24162133 implements UserService_24162133 {

    private final UserDAO_24162133 userDAO = new UserDAOImpl_24162133();

    @Override
    public List<User_24162133> findAll() {
        return userDAO.findAll();
    }

    @Override
    public User_24162133 findById(Integer id) {
        return userDAO.findById(id);
    }

    @Override
    public User_24162133 findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public User_24162133 login(String usernameOrEmail, String password) {
        User_24162133 user = userDAO.findByUsernameOrEmail(usernameOrEmail);
        if (user != null && user.getPasswd().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public boolean register(User_24162133 user) {
        if (userDAO.existsByEmail(user.getEmail())) {
            return false;
        }
        userDAO.insert(user);
        return true;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userDAO.existsByEmail(email);
    }

    @Override
    public User_24162133 insert(User_24162133 user) {
        return userDAO.insert(user);
    }

    @Override
    public void update(User_24162133 user) {
        userDAO.update(user);
    }

    @Override
    public void delete(Integer id) {
        userDAO.delete(id);
    }

    @Override
    public long count() {
        return userDAO.count();
    }
}