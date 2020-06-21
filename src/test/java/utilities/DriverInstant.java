package utilities;

import org.openqa.selenium.WebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class DriverInstant extends Logs  {

    public static WebDriver driver;
    public static String url;

    public static void setDriver(String s) {

        if (s.equals("Chrome")) {
            DriverInstant C = new ChromeDriver();
            ChromeDriver C1 = (ChromeDriver) C;
            C1.setDriver();
        }
        if (s.equals("IE")) {
            DriverInstant IE = new IEDriver();
            IEDriver IE1 = (IEDriver) IE;
            IE1.setDriver();
        }
        if (s.equals("Firefox")) {
            DriverInstant F = new FirefoxDriver();
            FirefoxDriver F1 = (FirefoxDriver) F;
            F1.setDriver();
        }
    }

    public static void Go() throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("src\\test\\java\\utilities\\Resources\\Input.properties");
        prop.load(fis);
        String browserName = prop.getProperty("BrowserName");
        url = prop.getProperty("url");
        setDriver(browserName);


    }
}
