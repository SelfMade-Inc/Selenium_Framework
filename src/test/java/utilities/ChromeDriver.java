package utilities;

public class ChromeDriver extends DriverInstant {

    void setDriver(){

        System.setProperty("webdriver.chrome.driver", "src\\test\\java\\utilities\\Resources\\Drivers\\ChromeDriver\\chromedriver1.exe");
        driver = new org.openqa.selenium.chrome.ChromeDriver();
        log.info("Google chrome browser is being launched to Run the tests");
        driver.manage().window().maximize();
        driver.get(url);
        log.info("Requested URL is launched");

    }


}