package service;

import dao.UserHibernateDAO;
import dao.UserJdbcDAO;
import exception.DBException;
import model.User;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class UserService {
    private static UserService userService;
    private SessionFactory sessionFactory;

    private UserService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService(DBHelper.getSessionFactory());
        }
        return userService;
    }

    private static Connection getMysqlConnection() {
        String mySQLConfigPath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "mySQL.properties";
        Properties mySQLProps = new Properties();
        try {
            mySQLProps.load(new FileInputStream(mySQLConfigPath));
            DriverManager.registerDriver((Driver) Class.forName(mySQLProps.getProperty("driver")).newInstance());
            return DriverManager.getConnection(mySQLProps.getProperty("host"), mySQLProps.getProperty("login"), mySQLProps.getProperty("password"));
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    private static UserJdbcDAO getUserDAO() {
        return new UserJdbcDAO(getMysqlConnection());
    }

    private UserHibernateDAO getUserHibernateDAO() {
        return new UserHibernateDAO(sessionFactory.openSession());
    }


    public List<User> getAllUsers() throws DBException {
        //return getUserDAO().getAllUsers();
        return getUserHibernateDAO().getAllUsers();
    }

    public User getUserById(Long id) throws DBException {
        //return getUserDAO().getUserById(id);
        return getUserHibernateDAO().getUserById(id);
    }

    public void deleteUser(Long id) throws DBException {
        try {
            //getUserDAO().deleteUserById(id);
            getUserHibernateDAO().deleteUser(getUserById(id));
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
            getUserHibernateDAO().addUser(user);
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
            getUserHibernateDAO().updateUser(user);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void cleanUp() throws DBException {
        UserJdbcDAO dao = getUserDAO();
        try {
            dao.dropTable();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void createTable() throws DBException{
        UserJdbcDAO dao = getUserDAO();
        try {
            dao.createTable();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

}
