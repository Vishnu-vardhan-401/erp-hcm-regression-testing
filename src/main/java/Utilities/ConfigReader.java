package Utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    public static Properties prop;
    public static String getProperty(String key) {
        try {
            if (prop == null) {
                prop = new Properties();
                String path = "src/test/resources/config.properties";
                FileInputStream fis = new FileInputStream(path);
                prop.load(fis);
            }
            return prop.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

