package service;

import dao.UserDAO;
import exception.DBException;
import model.User;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class UserService {

    private static Connection getMysqlConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance());
            String url = "jdbc:mysql://localhost:3306/web?serverTimezone=Europe/Moscow&characterEncoding=UTF-8";
            return DriverManager.getConnection(url, "root", "root");
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    private static UserDAO getUserDAO() {
        return new UserDAO(getMysqlConnection());
    }


    public UserService() {
    }

    public List<User> getAllUsers() throws DBException {
        try {
            return getUserDAO().getAllUsers();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public User getUserById(Long id) throws DBException {
        try {
            return getUserDAO().getUserById(id);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void deleteUser(Long id) throws DBException {
        try {
            getUserDAO().deleteUserById(id);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void addUser(User user) throws DBException {
        if (user.getId() != null && user.getId() > 0) {
            throw new DBException(new RuntimeException("User exists"));
        }
        try {
            getUserDAO().addUser(user);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void updateUser(User user) throws DBException {
        if (getUserById(user.getId()) == null) {
            throw new DBException(new RuntimeException("User not exists"));
        }
        try {
            getUserDAO().updateUser(user);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void cleanUp() throws DBException {
        UserDAO dao = getUserDAO();
        try {
            dao.dropTable();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void createTable() throws DBException{
        UserDAO dao = getUserDAO();
        try {
            dao.createTable();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

}
