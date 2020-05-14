package util;

import model.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DBHelper {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = createSessionFactory();
        }
        return sessionFactory;
    }

    private static Configuration getMySqlConfiguration() {
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

    private static SessionFactory createSessionFactory() {
        Configuration configuration = getMySqlConfiguration();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

}
