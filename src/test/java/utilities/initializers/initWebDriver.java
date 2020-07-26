package utilities.initializers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class initWebDriver extends initLogs {

    public static WebDriver driver;
    public static String url;

    public static void main() throws IOException {
        Properties prop = new Properties ();
        FileInputStream ip_stream = null;
        try {
            ip_stream = new FileInputStream ("src\\test\\java\\utilities\\Resources\\Input.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }
        prop.load (ip_stream);
        String browserName = prop.getProperty ("BrowserName");
        url = prop.getProperty ("url");
        setDriver (browserName);
    }

    public static void setDriver(String s) throws IOException {

        switch (s) {
            case "Chrome" -> {
                initChromeDriver ();
            }
            case "IE" -> {
                initExplorerDriver ();
            }
            case "Firefox" -> {
                initFirefoxDriver ();
            }
            case "Edge" -> {
                initEdgeDriver ();
            }
            default -> throw new IllegalStateException ("Unexpected value: " + s);
        }
    }

    public static void initFirefoxDriver() {

        System.setProperty ("webdriver.gecko.driver", "src\\test\\java\\utilities\\Resources\\Drivers\\FirefoxDriver\\geckodriver.exe");
        driver = new FirefoxDriver ();
        log.info ("Firefox is being launched to Run the tests");

        driver.manage ().window ().maximize ();
        driver.get (url);
        log.info ("Requested URL is launched");

    }

    public static void initExplorerDriver() {

        System.setProperty ("webdriver.ie.driver", "src\\test\\java\\utilities\\Resources\\Drivers\\IEDriver\\IEDriverServer.exe");
        driver = new InternetExplorerDriver ();
        log.info ("Internet Explorer is being launched to Run the tests");

        driver.manage ().window ().maximize ();
        driver.get (url);
        log.info ("Requested URL is launched");

    }

    public static void initChromeDriver() {

        System.setProperty ("webdriver.chrome.driver", "src\\test\\java\\utilities\\Resources\\Drivers\\ChromeDriver\\chromedriver1.exe");
        driver = new ChromeDriver ();
        log.info ("Google Chrome is being launched to Run the tests");

        driver.manage ().window ().maximize ();
        driver.get (url);
        log.info ("Requested URL is launched");

    }

    public static void initEdgeDriver() {

        System.setProperty ("webdriver.edge.driver", "utilities\\resources\\Drivers\\EdgeDriver\\MicrosoftWebDriver.exe");
        EdgeDriver driver = new EdgeDriver ();
        log.info ("Microsoft Edge is being launched to Run the tests");

        driver.manage ().window ().maximize ();
        driver.get (url);
        log.info ("Requested URL is launched");
    }
}
