package StepDefinitions;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utilities.initializers.adv_initWebDriver;

import java.io.IOException;

import static utilities.webDefinitions.*;

public class actions_HomePage {

    @Before()
    public void RunSetup() throws IOException {
        adv_initWebDriver.setupWebDriver ();
        System.out.println ("Initiating WebDriver");
    }

    @Given("Should be able to land on home page")
    public void should_be_able_to_land_on_home_page() throws IOException {

        By page_title = By.xpath ("//*[@id=\'site-name\']/a/h1");

        if (isElementPresent (page_title)) {

            String Mainpage_title = (driver.findElement (By.xpath ("//*[@id=\'site-name\']/a/h1")).getText ());
            System.out.println (("Navigated successfully to " + Mainpage_title));
            takeScreenshot ();
        }
    }

    @When("User is able to locate the desired Webpage to test")
    public void user_is_able_to_locate_the_desired_Webpage_to_test() throws IOException {

        //Locate Navigational webpage
        WebElement wait_element = driver.findElement (By.xpath ("//*[@id=\'node-20\']//div[1]//div[2]/p[3]/a[2]"));
        waits (wait_element, 100);

        WebElement test_page1 = driver.findElement (By.xpath ("//*[@id=\'node-20\']//div[1]//div[2]/p[3]/a[2]"));

        System.out.println ("Beginning Selenium Test on " + test_page1.getText ());

        setHighlighter (test_page1);
        takeScreenshot ();

        driver.findElement (By.xpath ("//*[@id=\'node-20\']//div[1]//div[2]/p[3]/a[2]")).click ();

    }

    @And("Begin Validating desired Webpage for Testable elements")
    public void begin_Validating_desired_Webpage_for_Testable_elements() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException ();
    }
}
