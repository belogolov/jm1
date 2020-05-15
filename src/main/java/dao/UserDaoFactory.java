package dao;

import util.DBProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class UserDaoFactory {
    private static String DAO_TYPE;

    private String getDaoType() {
//        String daoConfigPath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "dao.properties";
//        Properties daoProps = new Properties();
//        try {
//            daoProps.load(new FileInputStream(daoConfigPath));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //DAO_TYPE = daoProps.getProperty("daotype").toLowerCase();
        DBProperties prop = new DBProperties("dao.properties");
        DAO_TYPE = prop.getProperty("daotype").toLowerCase();
        return DAO_TYPE;
    }

    public UserDAO createFactory() {
        UserDAO dao = null;
        switch (DAO_TYPE == null ? getDaoType() : DAO_TYPE) {
            case "jdbc":
                dao = new UserJdbcDAO();
                break;
            case "hibernate":
                dao = new UserHibernateDAO();
                break;
            default:
                throw new IllegalArgumentException("Illegal daoType");
        }
        return dao;
    }

}
