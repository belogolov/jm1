package dao;

import util.AppProperties;

public class UserDaoFactory {
    private static String DAO_TYPE;

    private String getDaoType() {
        AppProperties prop = new AppProperties("dao.properties");
        DAO_TYPE = prop.getProperty("daotype");
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
