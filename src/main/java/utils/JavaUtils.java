package utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Random;

public class JavaUtils {
    public static String getEnvPropertyconfig(String key) throws IOException {
        Properties prop = new Properties();
        File file = new File("src/test/resources/config.properties");
        InputStream input = new FileInputStream(file);
        prop.load(input);
        return prop.getProperty(key);
    }

    public static String getCurrentTimeStemp() {
        return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Timestamp(System.currentTimeMillis())).replace(".", "_");
    }

    public static String getRandomEmail() {
        Random random = new Random();
        String email = "Testaut" + random.nextInt(100000) + "@gmail.com";
        return email;
    }

    public String getRandomString() {
        String randomalphabets = RandomStringUtils.random(5, true, false);
        return randomalphabets;
    }
}
