package service;

import dao.UserDAO;
import dao.UserDaoFactory;
import exception.DBException;
import model.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private static UserService userService;
    private UserDAO userDAO;

    private UserService() {
        UserDaoFactory factory = new UserDaoFactory();
        this.userDAO = factory.createFactory();
    }

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }


    public List<User> getAllUsers() throws DBException {
        //return getUserDAO().getAllUsers();
        return userDAO.getAllUsers();
    }

    public User getUserById(Long id) throws DBException {
        //return getUserDAO().getUserById(id);
        return userDAO.getUserById(id);
    }

    public void deleteUser(Long id) throws DBException {
        try {
            //getUserDAO().deleteUserById(id);
            userDAO.deleteUser(getUserById(id));
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void addUser(User user) throws DBException {
        if (user.getId() != null && user.getId() > 0) {
            throw new DBException(new RuntimeException("User exists"));
        }
        try {
            //getUserDAO().addUser(user);
            userDAO.addUser(user);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void updateUser(User user) throws DBException {
        if (getUserById(user.getId()) == null) {
            throw new DBException(new RuntimeException("User not exists"));
        }
        try {
            //getUserDAO().updateUser(user);
            userDAO.updateUser(user);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

}
