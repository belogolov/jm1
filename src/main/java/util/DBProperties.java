package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBProperties {
    private static Properties mySQLProps;

    public DBProperties (String fileName) {
        String mySQLConfigPath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + fileName;
        mySQLProps = new Properties();
        try {
            mySQLProps.load(new FileInputStream(mySQLConfigPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return mySQLProps.getProperty(key);
    }

}

