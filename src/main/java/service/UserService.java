package service;

import exception.DBException;
import model.Role;
import model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> getAllUsers() throws DBException;

    User getUserById(Long id) throws DBException;

    User getUserByLogin(String login) throws DBException;

    void deleteUser(Long id) throws DBException;

    void addUser(User user) throws DBException;

    void updateUser(User user) throws DBException;

    boolean isValidUser(String login, String password) throws DBException;

    Set<Role> getRoles(User user) throws DBException;

    Role getRoleById(Long id) throws DBException;
}
