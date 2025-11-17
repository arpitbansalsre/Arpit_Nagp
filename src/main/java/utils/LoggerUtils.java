package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggerUtils {
    public static Logger getLogger(Class<?> className) {
        return LogManager.getLogger(className);
    }

    public static void setLogFileName() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hhmmss");
        System.setProperty("logfilename", dateFormat.format(new Date()));
    }
}
