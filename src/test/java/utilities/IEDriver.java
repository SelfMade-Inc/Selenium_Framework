package utilities;

import org.openqa.selenium.ie.InternetExplorerDriver;

public class IEDriver extends DriverInstant {

    void setDriver(){

        System.setProperty("webdriver.ie.driver", "src\\test\\java\\utilities\\Resources\\Drivers\\IEDriver\\IEDriverServer.exe");
        driver = new InternetExplorerDriver();

        driver.manage().window().maximize();
        driver.get(url);

    }

}
