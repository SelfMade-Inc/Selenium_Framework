package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class adv_initWebDriver extends initLogs {

    public static WebDriver driver;
    public static String url;

    public static void main(String[] args) throws IOException {

        Properties prop = new Properties ();
        FileInputStream ip_stream = null;

        try {
            ip_stream = new FileInputStream ("src\\test\\java\\utilities\\Resources\\Input.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }
        prop.load (ip_stream);

        //Read config settings
        String browserName = prop.getProperty ("BrowserName");
        url = prop.getProperty ("url");

        //Call browser method with the config setting for browser name
        setDriver (browserName);

        //Load URL
        driver.manage ().window ().maximize ();
        driver.get (url);
        log.info ("Requested URL is launched");

    }

    public static void setDriver(String s) throws IOException {

        switch (s) {
            case "Chrome" -> {
                WebDriverManager.chromedriver ().setup ();
                driver = new ChromeDriver ();
                log.info ("Google Chrome is being launched to Run the tests");
            }
            case "IE" -> {
                WebDriverManager.iedriver ().setup ();
                driver = new InternetExplorerDriver ();
                log.info ("Internet Explorer is being launched to Run the tests");
            }
            case "Firefox" -> {
                WebDriverManager.firefoxdriver ().setup ();
                driver = new FirefoxDriver ();
                log.info ("Firefox is being launched to Run the tests");
            }
            case "Edge" -> {
                WebDriverManager.edgedriver ().setup ();
                driver = new EdgeDriver ();
                log.info ("Microsoft Edge is being launched to Run the tests");
            }
            default -> throw new IllegalStateException ("Unexpected value: " + s);
        }

    }

}
