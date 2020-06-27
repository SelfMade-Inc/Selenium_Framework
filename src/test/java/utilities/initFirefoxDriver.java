package utilities;

public class initFirefoxDriver extends initWebDriver {

    void setDriver() {

        System.setProperty("webdriver.gecko.driver", "src\\test\\java\\utilities\\Resources\\Drivers\\FirefoxDriver\\geckodriver.exe");
        driver = new org.openqa.selenium.firefox.FirefoxDriver();

        driver.manage().window().maximize();
        driver.get(url);

    }


}
