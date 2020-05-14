package dao;

import util.DBHelper;
import util.HibernateHelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class UserDaoFactory {

    public UserDAO createFactory() {
        UserDAO dao = null;

        String daoConfigPath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "dao.properties";
        Properties mySQLProps = new Properties();
        try {
            mySQLProps.load(new FileInputStream(daoConfigPath));
            String daotype = mySQLProps.getProperty("daotype").toLowerCase();
            switch (daotype) {
                case "jdbc":
                    dao = new UserJdbcDAO(DBHelper.getInstance().getConnection());
                    break;
                case "hibernate":
                    dao = new UserHibernateDAO(DBHelper.getInstance().getConfiguration());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dao;
    }
}
