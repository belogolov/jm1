package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DAOProperties {
    private Properties daoProps;

    static {
        String daoConfigPath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "dao.properties";
        Properties daoProps = new Properties();
        try {
            daoProps.load(new FileInputStream(daoConfigPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return daoProps.getProperty(key);
    }


}

