package service;

import dao.UserDAO;
import dao.UserDaoFactory;
import exception.DBException;
import model.User;

import java.sql.SQLException;
import java.util.List;

public class Service implements UserService {
    private static UserService userService;
    private UserDAO userDAO;

    private Service() {
        UserDaoFactory factory = new UserDaoFactory();
        this.userDAO = factory.createFactory();
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

}
