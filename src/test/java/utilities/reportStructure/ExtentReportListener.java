package utilities.reportStructure;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;
import java.util.Date;

import static utilities.initializers.initLogs.log;
import static utilities.webDefinitions.getScreenshot;

public class ExtentReportListener implements ITestListener {

    //User to configure reports for simultaneous executions
    public static ThreadLocal<ExtentTest> testReport = new ThreadLocal<ExtentTest> ();
    //Configuring report name
    static Date d = new Date ();
    static String fileName = "Extent_" + d.toString ().replace (":", "_").replace (" ", "_") + ".html";
    //Creating an instance of Extent report at specified location
    ExtentReports extentReport = ExtentReportManager.createInstance ("target/reports/extent/" + fileName);

    public void onTestStart(ITestResult itestResult) {

        ExtentTest extentTest = extentReport.createTest (itestResult.getTestClass ().getName () + "     @TestCase : " + itestResult.getMethod ().getMethodName ());
        testReport.set (extentTest);

    }

    public void onTestSuccess(ITestResult itestResult) {


        String methodName = itestResult.getMethod ().getMethodName ();
        String logText = "<b>" + " TEST CASE:- " + methodName.toUpperCase () + " PASSED" + "</b>";
        Markup report_decor = MarkupHelper.createLabel (logText, ExtentColor.GREEN);
        testReport.get ().pass (report_decor);

    }

    public void onTestFailure(ITestResult iTestResult) {

        //Read exception message from ITestResult value
        String exceptionMessage = Arrays.toString (iTestResult.getThrowable ().getStackTrace ());

        //Configure a error link on the Extent report
        testReport.get ().fail ("<Details>" + "<Summary>" + "<b>" + "<font color=" + "red>" + "Exception Occurred : Click to see"
                + "</font>" + "</b >" + "</Summary>" + exceptionMessage.replaceAll (",", "<br>") + "</Details>" + " \n");

        try {

            //Adding error details link on the extent report with screenshot

            ExtentReportManager.addScreenshot ();
            testReport.get ().fail ("<b>" + "<font color=" + "red>" + "Screenshot of failed step" + "</font>" + "</b>",
                    MediaEntityBuilder.createScreenCaptureFromPath (getScreenshot ())
                            .build ());

        } catch (Exception e) {
            log.error (e);
            e.printStackTrace ();
        }

        String failureLog = "TEST CASE FAILED";
        Markup report_decor = MarkupHelper.createLabel (failureLog, ExtentColor.RED);
        testReport.get ().log (Status.FAIL, report_decor);

    }
}
