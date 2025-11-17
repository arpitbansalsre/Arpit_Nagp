package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExtentManager {

    private static final String REPORT_BASE_PATH = System.getProperty("user.dir") + "//"+Constants.REPORTS_DIRECTORY+"//";
    private static final String OUTPUT_FOLDER = "Current_Test_Results_" + JavaUtils.getCurrentTimeStemp();
    private static final String FILE_NAME = "ExtendReport_" + JavaUtils.getCurrentTimeStemp() + ".html";
    private static final String REPORT_PATH = REPORT_BASE_PATH + OUTPUT_FOLDER + "//" + FILE_NAME;

    // Define the archived results path
    private static final String archiveBasePath = REPORT_BASE_PATH + "Archived_Test_Results";

    public static ExtentReports getReportObject() {
        ExtentSparkReporter reporter = new ExtentSparkReporter(REPORT_PATH);
        reporter.config().setReportName("Magneto Test Automation Results");
        reporter.config().setDocumentTitle("Test Results");
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(reporter);
        return extent;


    }

    public static String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String actualpath = REPORT_BASE_PATH + OUTPUT_FOLDER + "//screenshots//" + testCaseName + ".png";
        File file = new File(actualpath);
        FileUtils.copyFile(source, file);
        return actualpath;


    }

    public static void manageArchivedResults() {
        // Create the archived results directory if it does not exist
        File archiveBaseDirectory = new File(archiveBasePath);
        if (!archiveBaseDirectory.exists()) {
            archiveBaseDirectory.mkdirs();
        }

        File parentDir = new File(REPORT_BASE_PATH);

        // Check if the parent directory exists and is indeed a directory
        if (parentDir.exists() && parentDir.isDirectory()) {
            // List all files and folders in the parent directory
            File[] directories = parentDir.listFiles(File::isDirectory);
            if (directories != null) {
                // Iterate over the directories and check for the partial name
                for (File dir : directories) {
                    if (dir.getName().contains("Current_Test_Results")) {
                        try {
                            FileUtils.moveToDirectory(dir, archiveBaseDirectory, false);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
//        cleanCurrentResults();
    }

    private static void cleanCurrentResults() {
        try {
            Files.deleteIfExists(Paths.get("Current_Test_Results"));
        } catch (IOException e) {
            System.out.println("Unable to clean Current_Test_Results: " + e.getMessage());
        }
    }
}
