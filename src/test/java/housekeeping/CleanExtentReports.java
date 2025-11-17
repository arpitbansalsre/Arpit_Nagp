package housekeeping;

import org.apache.commons.io.FileUtils;
import utils.Constants;

import java.io.File;
import java.io.IOException;

public class CleanExtentReports {
    //Utility to clean extent reports directory
    public static void main(String args[]) throws IOException {
        String reports_path = System.getProperty("user.dir") + "//"+ Constants.REPORTS_DIRECTORY +"//";
        File reportsDir = new File(reports_path);
        FileUtils.deleteDirectory(reportsDir);
    }
}
