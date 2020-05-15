package service;

import exception.DBException;
import model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers() throws DBException;

    User getUserById(Long id) throws DBException;

    void deleteUser(Long id) throws DBException;

    void addUser(User user) throws DBException;

    void updateUser(User user) throws DBException;
}
