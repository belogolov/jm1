package util;

import model.User;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    private static DBHelper dbHelper;
    private static DBProperties mySQLProps;

    private DBHelper() {
    }

    public static DBHelper getInstance() {
        if (dbHelper == null) {
            dbHelper = new DBHelper();
            mySQLProps = new DBProperties("mySQL.properties");
        }
        return dbHelper;
    }

    public Connection getConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName(mySQLProps.getProperty("driver")).newInstance());
            return DriverManager.getConnection(mySQLProps.getProperty("host"), mySQLProps.getProperty("login"), mySQLProps.getProperty("password"));
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    public Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.connection.driver_class", mySQLProps.getProperty("driver"));
        configuration.setProperty("hibernate.connection.url", mySQLProps.getProperty("host"));
        configuration.setProperty("hibernate.connection.username", mySQLProps.getProperty("login"));
        configuration.setProperty("hibernate.connection.password", mySQLProps.getProperty("password"));
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        return configuration;
    }

}
