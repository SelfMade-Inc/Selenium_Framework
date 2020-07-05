package StepDefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;
import utilities.adv_initWebDriver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public class webDefinitions extends adv_initWebDriver {

    public static WebDriverWait waits;
    public static WebElement block;
    List<String> list_value;
    List<WebElement> list_element;
    List<WebElement> href_links;
    String str = "";

    /* Methods to work on WebDriver */

    //Checking presence of single webelement at a time
    public static boolean isElementPresent(By by) {
        try {
            driver.findElement (by);
            return true;
        } catch (Exception e) {
            System.out.println (e.getMessage ());
            log.error (e.getMessage ());
            return false;
        }
    }

    //Checking presence of multiple webelements
    public static boolean areElementsPresent(By by) {

        //Declare block to read elements from
        block = driver.findElement (by);
        int size = block.findElements (by).size ();

        if (size == 0) {
            return false;
        } else return true;
    }

    /* Overloading method waits to handle Implicit, Explicit and Fluent waits */

    //Call waits with number of seconds to implicitly wait
    public void waits(Integer wait_time) {
        driver.manage ().timeouts ().implicitlyWait (wait_time, TimeUnit.SECONDS);
        log.info ("WebDriver implicitly waits for " + wait_time + " seconds");
    }

    //Call waits with Element locator and number of seconds to explicitly wait
    public void waits(WebElement elemLocator, Integer wait_time) {

        waits = new WebDriverWait (driver, wait_time);
        waits.until (ExpectedConditions.presenceOfElementLocated ((By) elemLocator));
        log.info ("WebDriver explicitly waits for " + wait_time + " seconds");
    }

    //Call waits with 2 int and 2 string to fluently wait
    public void waits(Integer wait_time, Integer poll_time, String fail_message, WebElement elemLocator) {
        Wait wait = new FluentWait (driver).withTimeout (Duration.ofSeconds (wait_time)).pollingEvery (Duration.ofSeconds (poll_time)).withMessage (fail_message).ignoring (NoSuchElementException.class);

        elemLocator = (WebElement) wait.until (new Function<WebDriver, WebElement> () {
            public WebElement apply(WebDriver driver) {
                return driver.findElement (By.xpath ("elemLocator"));
            }
        });
        log.info ("WebDriver fluently waits for " + wait_time + " seconds");
    }

    /* Methods to work on WebElements */

    //Reading and performing actions on dropdowns
    public List<WebElement> handleDropdowns() {

        //Locates the dropdown
        WebElement dropdown = driver.findElement (By.tagName ("option"));

        //Select class is the handling class for any kind of dropdowns
        Select select = new Select (dropdown);
        //Locates the option values from dropdown
        list_element = driver.findElements (By.tagName ("option"));

        log.info ("Values from dropdown are stored as list of webelements with size of" + list_element.size ());
        //list_element = select.getOptions ();

        return list_element;
    }

    //Read text from dropdowns
    public void readDropdowns() {
        list_element = handleDropdowns ();
        list_value = new ArrayList<String> ();

        //Converting list<WebElements> to list<String>
        /*
        for (int i = 0 ;i < list_element.size () ; i++) {
            list_value.add (list_element.get (i).getAttribute (str));
        }
        */

        //Compact code for conversion of list<WebElement> to list<String>
        for (WebElement element : list_element) {
            list_value = list_element.stream ().map (WebElement::getText).collect (Collectors.toList ());
        }
        log.info ("Values from dropdown are stored as list of strings with size of" + list_value.size ());
    }

    //Reading from links on a webpage
    public List<WebElement> handleHREFS(By by) {

        //Helps filter which links to work on by assigning div block as a Webelement
        block = driver.findElement (by);

        //Locates all links from the div block
        href_links = block.findElements (By.tagName ("a"));

        log.info ("Values from total links on webpage are stored as list of webelements with size of" + href_links.size ());

        for (WebElement href : href_links) {
            //Write code to specify work on links e.g.Click or getAttribute.value
            System.out.println (href.getText () + " represents url " + href.getAttribute ("href"));
        }
        return href_links;
    }

    //javascripts


}
