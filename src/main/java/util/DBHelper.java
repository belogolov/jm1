package util;

import model.User;
import org.hibernate.cfg.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBHelper {
    private static DBHelper dbHelper;

    private DBHelper() {
    }

    public static DBHelper getInstance() {
        if (dbHelper == null) {
            dbHelper = new DBHelper();
        }
        return dbHelper;
    }

    public Connection getConnection() {
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

    public Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        String mySQLConfigPath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "mySQL.properties";
        Properties mySQLProps = new Properties();
        try {
            mySQLProps.load(new FileInputStream(mySQLConfigPath));
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
            configuration.setProperty("hibernate.connection.driver_class", mySQLProps.getProperty("driver"));
            configuration.setProperty("hibernate.connection.url", mySQLProps.getProperty("host"));
            configuration.setProperty("hibernate.connection.username", mySQLProps.getProperty("login"));
            configuration.setProperty("hibernate.connection.password", mySQLProps.getProperty("password"));
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return configuration;
    }

}
