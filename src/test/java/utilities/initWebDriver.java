package utilities;

import org.openqa.selenium.WebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class initWebDriver extends Logs {

    public static WebDriver driver;
    public static String url;

    public static void setDriver(String s) {

        if (s.equals("Chrome")) {
            initWebDriver C = new initChromeDriver();
            initChromeDriver C1 = (initChromeDriver) C;
            C1.setDriver();
        }
        if (s.equals("IE")) {
            initWebDriver IE = new initExplorerDriver();
            initExplorerDriver IE1 = (initExplorerDriver) IE;
            IE1.setDriver();
        }
        if (s.equals("Firefox")) {
            initWebDriver F = new initFirefoxDriver();
            initFirefoxDriver F1 = (initFirefoxDriver) F;
            F1.setDriver();
        }
    }

    public static void Go() throws IOException {
        Properties props = new Properties();
        FileInputStream ip_stream = new FileInputStream("src\\test\\java\\utilities\\Resources\\Input.properties");
        props.load(ip_stream);
        String browserName = props.getProperty("BrowserName");
        url = props.getProperty("url");
        setDriver(browserName);
    }
}
