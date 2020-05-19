package dao;

import model.Role;
import model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface UserDAO {

    List<User> getAllUsers();

    User getUserById(long id);

    User getUserByLogin(String login);

    void addUser(User user) throws SQLException;

    void updateUser(User user) throws SQLException;

    void deleteUser(User user) throws SQLException;

    User validUser(String login, String password);

    Set<Role> getUserRoles(User user);

    Role getRoleById(long id);
}
