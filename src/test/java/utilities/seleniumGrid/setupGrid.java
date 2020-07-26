package utilities.seleniumGrid;

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

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class setupGrid {

    public static DesiredCapabilities capability;

    public static WebDriver driver;

    @BeforeTest
    @Parameters("browserName")
    public void gridSetup(String browserName) throws MalformedURLException {

        switch (browserName) {
            case "ie":
                capability.setBrowserName (browserName);
                capability.setPlatform (Platform.WINDOWS);

                InternetExplorerOptions iExplorerOptions = new InternetExplorerOptions ();
                iExplorerOptions.waitForUploadDialogUpTo (Duration.ofSeconds (2));
                iExplorerOptions.merge (capability);

                driver = new RemoteWebDriver (new URL ("http://localhost:4444/"), capability);

            case "chrome":
                capability.setBrowserName (browserName);

                //capability.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

                ChromeOptions chromeOptions = new ChromeOptions ();
                chromeOptions.merge (capability);

                driver = new RemoteWebDriver (new URL ("http://localhost:4444/"), capability);

            case "firefox":
                capability.setBrowserName (browserName);
                capability.setPlatform (Platform.ANY);

                FirefoxOptions firefoxOptions = new FirefoxOptions ();
                firefoxOptions.merge (capability);

                driver = new RemoteWebDriver (new URL ("http://localhost:4444/"), capability);

        }
    }

    @AfterClass
    public void afterClass() {
        driver.close ();
    }

}
