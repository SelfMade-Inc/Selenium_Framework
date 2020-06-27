package StepDefinations;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import utilities.initWebDriver;

import java.io.IOException;

public class navi_HomePage extends initWebDriver {

    @Before
    public void Run() throws IOException {
        initWebDriver.Go();
        System.out.println("Before");
    }

    @Given("^Should be able to land on home page$")
    public void should_be_able_to_land_on_home_page() {
        System.out.println("Given");
    }

    @When("^Okay$")
    public void okay() {
        System.out.println("When");
    }

    @Then("^Good$")
    public void good() {
        System.out.println("Then");
    }

    @After
    public void closeBrowser(){
        driver.quit();
        System.out.println("After");
    }
    @Given("^User navigates to Webpage$")
    public void user_navigates_to_Webpage() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("@Given User navigates to Webpage");
        //System.out.println(userDir);
    }

    @When("^User validates the Webpage title$")
    public void user_validates_the_Webpage_title() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("@When User validates the Webpage title");
    }

    @Then("^User navigates the webpage$")
    public void user_navigates_the_webpage() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("@Then User navigates the webpage");
    }

    @Then("^User reaches the desired screen successfully$")
    public void user_reaches_the_desired_screen_successfully() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("@Then User reaches the desired screen successfully");
    }

    @When("^User verifies the screen is correct$")
    public void user_verifies_the_screen_is_correct() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("@When User verifies the screen is correct.");
    }

    @Then("^User can perform actions$")
    public void user_can_perform_actions() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("@Then User can perform actions");
    }

}
