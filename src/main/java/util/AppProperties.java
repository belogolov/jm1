package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppProperties {
    private static Properties props;

    public AppProperties(String fileName) {
        String configPath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + fileName;
        props = new Properties();
        try {
            props.load(new FileInputStream(configPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return props.getProperty(key);
    }

}

