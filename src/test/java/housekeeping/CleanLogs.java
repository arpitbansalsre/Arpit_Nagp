package housekeeping;

import org.apache.commons.io.FileUtils;
import utils.Constants;

import java.io.File;
import java.io.IOException;

public class CleanLogs {
    //Utility to clean logs directory
    public static void main(String args[]) throws IOException {
        String logspath = System.getProperty("user.dir") + "//"+ Constants.LOGS_DIRECTORY +"//";
        File logsDir = new File(logspath);
        FileUtils.deleteDirectory(logsDir);
    }
}
