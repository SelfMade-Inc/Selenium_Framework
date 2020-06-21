package utilities;

public class FirefoxDriver extends DriverInstant {

    void setDriver() {

        System.setProperty("webdriver.gecko.driver", "src\\test\\java\\utilities\\Resources\\Drivers\\FirefoxDriver\\geckodriver.exe");
        driver = new org.openqa.selenium.firefox.FirefoxDriver();

        driver.manage().window().maximize();
        driver.get(url);

    }


}
