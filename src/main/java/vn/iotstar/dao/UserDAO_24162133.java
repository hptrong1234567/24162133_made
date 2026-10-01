package vn.iotstar.dao;

import java.util.List;
import vn.iotstar.model.User_24162133;

public interface UserDAO_24162133 {
    List<User_24162133> findAll();
    User_24162133 findById(Integer id);
    User_24162133 findByEmail(String email);
    User_24162133 findByUsernameOrEmail(String login);
    boolean existsByEmail(String email);
    User_24162133 insert(User_24162133 user);
    void update(User_24162133 user);
    void delete(Integer id);
    long count();
}