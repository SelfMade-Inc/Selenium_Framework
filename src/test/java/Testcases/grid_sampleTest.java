package Testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class grid_sampleTest {

    public static DesiredCapabilities capability;
    public static WebDriver driver;

    @Test
    public void sampleTest() throws InterruptedException {

        driver.get ("https://www.google.com/");
        driver.manage ().window ().maximize ();

        Thread.sleep (2000);

        driver.findElement (By.xpath ("//*[@id='tsf']/div[2]/div[1]//div[2]/input")).sendKeys ("Selenium Grid Test");

        driver.findElement (By.xpath ("//*[@id='tsf']//div[3]//input[1]")).click ();
        Thread.sleep (5000);
    }

    @BeforeTest
    @Parameters("browserName")
    public void gridSetup(String browserName) throws MalformedURLException {

        switch (browserName) {
            case "ie":
                capability = new DesiredCapabilities ();
                capability.setBrowserName (browserName);
                capability.setPlatform (Platform.WINDOWS);

                InternetExplorerOptions iExplorerOptions = new InternetExplorerOptions ();
                iExplorerOptions.waitForUploadDialogUpTo (Duration.ofSeconds (2));
                iExplorerOptions.merge (capability);

                driver = new RemoteWebDriver (new URL ("http://localhost:4444/wd/hub"), capability);

            case "chrome":
                capability = new DesiredCapabilities ();
                capability.setBrowserName (browserName);

                //capability.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

                ChromeOptions chromeOptions = new ChromeOptions ();
                chromeOptions.merge (capability);

                driver = new RemoteWebDriver (new URL ("http://localhost:4444/wd/hub"), capability);

            case "firefox":
                capability = new DesiredCapabilities ();
                capability.setBrowserName (browserName);
                capability.setPlatform (Platform.ANY);

                FirefoxOptions firefoxOptions = new FirefoxOptions ();
                firefoxOptions.merge (capability);

                driver = new RemoteWebDriver (new URL ("http://localhost:4444/wd/hub"), capability);

        }
    }

    @AfterClass
    public void afterClass() {
        driver.close ();
    }

}

