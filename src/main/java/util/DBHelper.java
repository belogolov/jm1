package util;

import model.Role;
import model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    private static DBHelper dbHelper;
    private static AppProperties mySQLProps;

    private DBHelper() {
    }

    public static DBHelper getInstance() {
        if (dbHelper == null) {
            dbHelper = new DBHelper();
            mySQLProps = new AppProperties("mySQL.properties");
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

    private Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Role.class);
        configuration.setProperty("hibernate.dialect", mySQLProps.getProperty("dialect"));
        configuration.setProperty("hibernate.connection.driver_class", mySQLProps.getProperty("driver"));
        configuration.setProperty("hibernate.connection.url", mySQLProps.getProperty("host"));
        configuration.setProperty("hibernate.connection.username", mySQLProps.getProperty("login"));
        configuration.setProperty("hibernate.connection.password", mySQLProps.getProperty("password"));
        configuration.setProperty("hibernate.show_sql", mySQLProps.getProperty("show_sql"));
//        configuration.setProperty("hibernate.hbm2ddl.auto", mySQLProps.getProperty("hbm2ddl"));
        return configuration;
    }

    public SessionFactory createSessionFactory() {
        Configuration configuration = getConfiguration();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
