package StepDefinitions;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import utilities.adv_initWebDriver;

public class webDefinitions extends adv_initWebDriver {

    public static Actions action;
    public static Alert alert;
    public static WebDriverWait waits;
    public static WebElement block;
    public static WebElement element;

    public static List<String> list_value;
    public static List<WebElement> list_element;
    public static List<WebElement> href_links;

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
        } else
            log.debug ("Webelements found");
            return true;
    }

    //Frames
    public static void workwithFrames(String frame_locator, String element_locator){
        driver.switchTo ().frame (frame_locator);
        list_element = driver.findElements (By.id (frame_locator));

        for (WebElement element : list_element) {
            isElementPresent ((By) driver.findElement (By.id (element_locator)));
            log.debug ("Frame embedded element "+ element_locator + " found");
        }
    }

    /* Overloading method waits to handle Implicit, Explicit and Fluent waits */

    //Call waits with number of seconds to implicitly wait
    public static void waits(Integer wait_time) {
        driver.manage ().timeouts ().implicitlyWait (wait_time, TimeUnit.SECONDS);
        log.debug ("WebDriver implicitly waits for " + wait_time + " seconds");
    }

    //Call waits with Element locator and number of seconds to explicitly wait
    public static void waits(WebElement elemLocator, Integer wait_time) {
        waits = new WebDriverWait (driver, wait_time);
        waits.until (ExpectedConditions.presenceOfElementLocated ((By) elemLocator));
        log.debug ("WebDriver explicitly waits for " + wait_time + " seconds");
    }

    //Call waits with 2 int and 2 string to fluently wait
    public static void waits(Integer wait_time, Integer poll_time, String fail_message, WebElement elemLocator) {
        Wait wait = new FluentWait (driver).withTimeout (Duration.ofSeconds (wait_time)).pollingEvery (Duration.ofSeconds (poll_time)).withMessage (fail_message).ignoring (NoSuchElementException.class);

        elemLocator = (WebElement) wait.until (new Function<WebDriver, WebElement> () {
            public WebElement apply(WebDriver driver) {
                return driver.findElement (By.xpath ("elemLocator"));
            }
        });
        log.debug ("WebDriver fluently waits for " + wait_time + " seconds");
    }

    /* Methods to read and store on WebElements */

    //Read text from dropdowns
    public static void readDropdowns() {
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
        log.debug ("Values from dropdown are stored as list of strings with size of" + list_value.size ());
    }

    //Reading from links on a webpage
    public static List<WebElement> handleHREFS(By by) {

        //Helps filter which links to work on by assigning div block as a Webelement
        block = driver.findElement (by);

        //Locates all links from the div block
        href_links = block.findElements (By.tagName ("a"));

        log.debug ("Values from total links on webpage are stored as list of webelements with size of" + href_links.size ());

        for (WebElement href : href_links) {
            //Write code to specify action on links e.g.Click or getAttribute.value
            System.out.println (href.getText () + " represents url " + href.getAttribute ("href"));
        }
        return href_links;
    }

    //DataTables
    public static void handleDatatables(String locate_rows, String locate_cols, By by){

        List<WebElement> list_datatable_rows = driver.findElements (By.xpath (locate_rows));
        List<WebElement> list_datatable_cols = driver.findElements (By.xpath (locate_cols));

        for(WebElement element_row : list_datatable_rows){
            for(WebElement element_col : list_datatable_cols){
                //Write code to specify action on Datatable
            }
        }
        log.debug (" DataTable dimensions are" +list_datatable_rows.size ()+ " by " +list_datatable_cols.size () );
    }

    /* Actions to be performed on WebElements */

    //Mouse Click on webelements
    public static void mouseClick(By by){
        waits(10);
        //driver.findElement (by).click ();
        action.moveToElement (driver.findElement (by)).perform ();
    }

    //Right click on an element
    public static void right_mouseClick(By by){
        element = driver.findElement (by);
        action.contextClick (element).perform ();
    }

    //Drag elements like sliders
    public static void dragNdrop(By by){
        element = driver.findElement (by);
        //if Element is slider
        int width = element.getSize ().width;
        int height = element.getSize ().height;

        if(height==0) {
            //To drag slider on X axis
            action.dragAndDropBy (element, width, 0);
        }
        else if(width==0){
            //To drag slider on Y axis
            action.dragAndDropBy (element, 0, height);
        }
        log.debug ("Slider drag performed");
    }

    //Drag elements and drop at destination elements
    public static void dragNdrop(By by_source, By by_destination){
        WebElement source_element = driver.findElement (by_source);
        WebElement destination_element = driver.findElement (by_destination);

        action.dragAndDrop (source_element,destination_element).perform ();
        log.debug ("Drag and drop performed");
    }

    //Reading and performing actions on dropdowns
    public static List<WebElement> handleDropdowns() {

        //Locates the dropdown
        WebElement dropdown = driver.findElement (By.tagName ("option"));

        //Select class is the handling class for any kind of dropdowns
        Select select = new Select (dropdown);
        //Locates the option values from dropdown
        list_element = driver.findElements (By.tagName ("option"));

        log.debug ("Values from dropdown are stored as list of webelements with size of" + list_element.size ());
        //list_element = select.getOptions ();

        return list_element;
    }

    //JavaScript alerts
    public static void handleAlerts(){

        alert = driver.switchTo ().alert ();
        String alert_message = alert.getText ();
        alert.accept ();
        log.debug (alert_message+ " Alert received and accepted");
    }

    public static void handleAlerts(Integer wait_time){
        waits ( wait_time);
        alert = waits.until (ExpectedConditions.alertIsPresent ());
        String alert_message = alert.getText ();
        alert.accept ();
        log.debug (alert_message+ " Alert received and accepted");
    }
}
