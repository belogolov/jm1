package service;

import dao.UserDAO;
import dao.UserDaoFactory;
import exception.DBException;
import model.Role;
import model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class Service implements UserService {
    private static UserService userService;
    private UserDAO userDAO;

    private Service() {
        this.userDAO = new UserDaoFactory().createFactory();
    }

    public static UserService getInstance() {
        if (userService == null) {
            userService = new Service();
        }
        return userService;
    }


    @Override
    public List<User> getAllUsers() throws DBException {
        return userDAO.getAllUsers();
    }

    @Override
    public User getUserById(Long id) throws DBException {
        return userDAO.getUserById(id);
    }

    @Override
    public User getUserByLogin(String login) throws DBException {
        return userDAO.getUserByLogin(login);
    }

    @Override
    public void deleteUser(Long id) throws DBException {
        try {
            userDAO.deleteUser(getUserById(id));
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void addUser(User user) throws DBException {
        if (user.getId() != null && user.getId() > 0) {
            throw new DBException(new RuntimeException("User exists"));
        }
        try {
            userDAO.addUser(user);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void updateUser(User user) throws DBException {
        if (getUserById(user.getId()) == null) {
            throw new DBException(new RuntimeException("User not exists"));
        }
        try {
            userDAO.updateUser(user);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public User validUser(String login, String password) throws DBException {
        return userDAO.validUser(login, password);
    }

    @Override
    public Set<Role> getRoles(User user) throws DBException {
        return userDAO.getUserRoles(user);
    }

    @Override
    public Role getRoleById(Long id) throws DBException {
        return userDAO.getRoleById(id);
    }
}
