package utilities.reportStructure;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import static utilities.initializers.initLogs.log;
import static utilities.webDefinitions.getScreenshot;

public class ExtentReportManager {

    public static ExtentReports extentReport;

    public static ExtentReports createInstance(String filename) {

        ExtentHtmlReporter extentHtmlReporter = new ExtentHtmlReporter ("target/reports/extent/extent.html");

        //Configuration additions
        extentHtmlReporter.config ().setEncoding ("utf-8");
        extentHtmlReporter.config ().setDocumentTitle ("Automation Extent Report");
        extentHtmlReporter.config ().setReportName ("Selenium Framework 101");
        extentHtmlReporter.config ().setTheme (Theme.STANDARD);

        //Initialising Report Generator
        extentReport = new ExtentReports ();
        extentReport.attachReporter (extentHtmlReporter);

        //Assigning Key : Value to the standard text fields in Extent Reports
        extentReport.setSystemInfo ("Selenium Framework Title", "Automation Tester Name");
        extentReport.setSystemInfo ("Organisation Name", "Organisation Data");
        extentReport.setSystemInfo ("Build Index", "Framework version");

        return extentReport;
    }

    public static void addScreenshot() {

        try {

            getScreenshot ();

        } catch (Exception e) {
            log.error (e);
            e.printStackTrace ();
        }
    }
}
