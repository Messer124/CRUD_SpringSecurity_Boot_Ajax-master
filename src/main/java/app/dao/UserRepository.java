package app.dao;

import app.model.User;

import java.util.List;

public interface UserRepository {
    boolean insertUser(User user);
    List<User> selectAllUsers();
    boolean deleteUser(Long id);
    void updateUser(User user);
    User selectUserById(Long id);
    User selectUserByUsername(String login);
}
