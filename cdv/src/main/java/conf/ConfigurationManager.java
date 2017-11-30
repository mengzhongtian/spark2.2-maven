package conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {
    private static Properties pro = new Properties();

    static {
        InputStream ins = ConfigurationManager.class.getClassLoader().getResourceAsStream("my.properties");
        try {
            pro.load(ins);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return pro.getProperty(key);

    }

    public static Integer getInteger(String key) {
        return Integer.valueOf(pro.getProperty(key));
    }


    public static boolean getBoolean(String key) {
        String value = pro.getProperty(key);
        try {
            return Boolean.valueOf(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;


    }
}
