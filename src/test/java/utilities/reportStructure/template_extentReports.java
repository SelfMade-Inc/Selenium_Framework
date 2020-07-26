package utilities.reportStructure;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Arrays;

public class template_extentReports {

    //HTML Report formatter and reader
    public ExtentHtmlReporter extentHtmlReporter;
    public ExtentReports extentReport;
    public ExtentTest extentTest;

    @BeforeTest
    public void setup_Reports() {

        extentHtmlReporter = new ExtentHtmlReporter ("target/reports/extent/extent.html");

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

    }

    @AfterTest
    public void endReporting() {

        extentReport.flush ();

    }

    @Test
    public void initPassTest() {

        extentTest = extentReport.createTest ("Initial Test Generation");
        System.out.println ("Initial Test Generation for Passed Test");

    }


    @Test
    public void initFailTest() {

        extentTest = extentReport.createTest ("Initial Test Generation for Failed Test");
        Assert.fail ("Initial Test Generation for Failed Test");

    }

    @Test
    public void initSkippedTest() {

        extentTest = extentReport.createTest ("Initial Test Generation for Skipped Test");
        throw new SkipException ("Initial Test Generation for Skipped Test");

    }

    @AfterMethod
    public void tearDown(ITestResult test_result) {

        switch (test_result.getStatus ()) {

            case ITestResult.FAILURE: {

                String methodName = test_result.getMethod ().getMethodName ();
                String logger = "<b>" + "Test CASE :: " + methodName.toUpperCase () + " FAILED " + "</b>";

                String exceptionMessage = Arrays.toString (test_result.getThrowable ().getStackTrace ());
                extentTest.fail ("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occured:Click to see"
                        + "</font>" + "</b >" + "</summary>" + exceptionMessage.replaceAll (",", "<br>") + "</details>"
                        + " \n");

                //To capture screenshots and embed into the failure reports
            /*
            public static ThreadLocal<ExtentTest> testReport = new ThreadLocal<ExtentTest>();
            try{

				ExtentManager.captureScreenshot();
				testReport.get().fail("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
						MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.screenshotName)
								.build());
			} catch (IOException e) {
			}
			*/

                Markup report_decor = MarkupHelper.createLabel (logger, ExtentColor.RED);
                extentTest.log (Status.FAIL, report_decor);

            }
            case ITestResult.SKIP: {

                String methodName = test_result.getMethod ().getMethodName ();
                String logger = "<b>" + "Test CASE :: " + methodName.toUpperCase () + " SKIPPED " + "</b>";
                Markup report_decor = MarkupHelper.createLabel (logger, ExtentColor.BLUE);
                extentTest.skip (report_decor);
            }
            case ITestResult.SUCCESS: {

                String methodName = test_result.getMethod ().getMethodName ();
                String logger = "<b>" + "Test CASE :: " + methodName.toUpperCase () + " PASSED " + "</b>";
                Markup report_decor = MarkupHelper.createLabel (logger, ExtentColor.GREEN);
                extentTest.pass (report_decor);
            }
        }
    }
}
