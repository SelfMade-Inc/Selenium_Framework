package utilities;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import utilities.initializers.adv_initWebDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public class webDefinitions extends adv_initWebDriver {

    public static Actions action;
    public static Alert alert;

    public static JavascriptExecutor js = (JavascriptExecutor) driver;
    public static WebDriverWait waits;
    public static WebElement block;
    public static WebElement element;

    public static List<String> list_value;
    public static List<WebElement> list_element;
    public static List<WebElement> href_links;

    public static Date date = new Date ();
    public static String timestamp = date.toString ().replace (":", "_").replace (" ", "_");

    /* Methods to work on WebDriver */

    //Checking presence of single WebElement at a time
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

    //Checking presence of multiple WebElements
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
    public static void manageFrames(String frame_locator, String element_locator) {
        driver.switchTo ().frame (frame_locator);
        list_element = driver.findElements (By.id (frame_locator));

        for (WebElement element : list_element) {
            isElementPresent ((By) driver.findElement (By.id (element_locator)));
            log.debug ("Frame embedded element " + element_locator + " found");
        }
    }

    //Manage and work with multiple Window handles
    public static void managemultipleWindows() {
        Set<String> windowHandles = driver.getWindowHandles ();
        Iterator<String> iterator = windowHandles.iterator ();

        while (iterator.hasNext ()) {
            iterator.next ();
            //method action call
        }
        log.debug ("Total window handles available is " + windowHandles.size ());
    }


    /* Working with JAVAScript executor */

    //Highlighting elements to focus
    public static void setHighlighter(WebElement element) {
        js.executeScript ("arguments[0].style.border='3px solid red'", element);
        waits (5);
        //Call method action
    }


    /* Overloading method takeScreenshot */

    //Screenshot WebDriver screen
    public static void takeScreenshot() throws IOException {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs (OutputType.FILE);

        FileUtils.copyFile (screenshot, new File ("src\\screenshots\\scrshot_" + timestamp + ".jpg"));
    }

    //Screenshot particular element
    public static void takeScreenshot(WebElement element) throws IOException {

        setHighlighter (element);

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs (OutputType.FILE);

        //Helps manipulating images to capture elements dimensionally
        BufferedImage img = ImageIO.read (screenshot);
        Point point = element.getLocation ();
        int element_height = element.getSize ().getHeight ();
        int element_width = element.getSize ().getWidth ();

        BufferedImage element_screenshot = img.getSubimage (point.getX (), point.getY (), element_width, element_height);
        ImageIO.write (element_screenshot, "jpeg", screenshot);

        File screenshot_location = new File (".\\screenshots\\element_scrshot" + timestamp + ".jpg");
        FileUtils.copyFile (screenshot, screenshot_location);
    }

    //Screenshot entire page scrolling to the end of the available webpage
    public static void takeScreenshot(Integer scroll_timeperiod, By by, @NotNull String screenshot_level) throws IOException {

       /*
       takeScreenshot(1000, WebElement_locator, screen) to generate screenshot for entire page source

       takeScreenshot(1000, WebElement_locator, element) to generate screenshot for selective element from page source
       */

        if (screenshot_level.equals ("screen")) {
            //Generates screenshot with the specified scrolling period collecting the entire page on webdriver instance
            Screenshot adv_screenshot = new AShot ().shootingStrategy (ShootingStrategies.viewportPasting (scroll_timeperiod)).takeScreenshot (driver);

            //Writes the captured screenshot data to a specified file location
            ImageIO.write (adv_screenshot.getImage (), "jpeg", new File ("src\\screenshots\\page_scrshot_" + timestamp + ".jpg"));

        } else if (screenshot_level.equals ("element")) {

            element = driver.findElement (extractLocator (element));

            //Generates screenshot with the specified scrolling period collecting the selected element  on webdriver instance
            Screenshot adv_screenshot = new AShot ().shootingStrategy (ShootingStrategies.viewportPasting (scroll_timeperiod)).takeScreenshot (driver, element);

            ImageIO.write (adv_screenshot.getImage (), "jpg", new File ("src\\screenshots\\adv_scrshot_" + timestamp + ".jpg"));
        }
    }

    //Get screenshot location to attach into reports or emails
    public static String getScreenshot() throws Exception {

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs (OutputType.FILE);
        String screenshotName = screenshot.getName ();
        String destination = "";

        if (screenshotName.isEmpty ()) {
            log.error ("Screenshot for failed scenario not generated");
        } else {

            destination = System.getProperty ("user.dir") + "/FailedScenarios/" + screenshotName + timestamp + ".jpg";

            File finalDestination = new File (destination);
            FileUtils.copyFile (screenshot, finalDestination);

        }
        //Returns the captured file path
        return destination;
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

        waits.until (ExpectedConditions.presenceOfElementLocated (extractLocator (elemLocator)));
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

    //Type casting WebElement to a By locator string
    public static By extractLocator(WebElement element) {

        By by = null;

        String[] pathVariables = (element.toString ().split ("->")[1].replaceFirst ("(?s)(.*)\\]", "$1" + "")).split (":");

        String selector = pathVariables[0].trim ();
        String value = pathVariables[1].trim ();

        switch (selector) {
            case "id":
                by = By.id (value);
                break;
            case "className":
                by = By.className (value);
                break;
            case "tagName":
                by = By.tagName (value);
                break;
            case "xpath":
                by = By.xpath (value);
                break;
            case "cssSelector":
                by = By.cssSelector (value);
                break;
            case "linkText":
                by = By.linkText (value);
                break;
            case "name":
                by = By.name (value);
                break;
            case "partialLinkText":
                by = By.partialLinkText (value);
                break;
            default:
                throw new IllegalStateException ("locator : " + selector + " not found!!!");
        }
        return by;
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
    public static void handleDatatables(String locate_rows, String locate_cols, By by) {

        List<WebElement> list_datatable_rows = driver.findElements (By.xpath (locate_rows));
        List<WebElement> list_datatable_cols = driver.findElements (By.xpath (locate_cols));

        for (WebElement element_row : list_datatable_rows) {
            for (WebElement element_col : list_datatable_cols) {
                //Write code to specify action on Datatable
            }
        }
        log.debug (" DataTable dimensions are" + list_datatable_rows.size () + " by " + list_datatable_cols.size ());
    }

    /* Actions to be performed on WebElements */

    //Mouse Click on webelements
    public static void mouseClick(By by) {
        waits (10);
        //driver.findElement (by).click ();
        action.moveToElement (driver.findElement (by)).perform ();
    }

    //Right click on an element
    public static void right_mouseClick(By by) {
        element = driver.findElement (by);
        action.contextClick (element).perform ();
    }

    //Drag elements like sliders
    public static void dragNdrop(By by) {
        element = driver.findElement (by);
        //if Element is slider
        int width = element.getSize ().width;
        int height = element.getSize ().height;

        if (height == 0) {
            //To drag slider on X axis
            action.dragAndDropBy (element, width, 0);
        } else if (width == 0) {
            //To drag slider on Y axis
            action.dragAndDropBy (element, 0, height);
        }
        log.debug ("Slider drag performed");
    }

    //Drag elements and drop at destination elements
    public static void dragNdrop(By by_source, By by_destination) {
        WebElement source_element = driver.findElement (by_source);
        WebElement destination_element = driver.findElement (by_destination);

        action.dragAndDrop (source_element, destination_element).perform ();
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
    public static void handleAlerts() {

        alert = driver.switchTo ().alert ();
        String alert_message = alert.getText ();
        alert.accept ();
        log.debug (alert_message + " Alert received and accepted");
    }

    public static void handleAlerts(Integer wait_time) {
        waits (wait_time);
        alert = waits.until (ExpectedConditions.alertIsPresent ());
        String alert_message = alert.getText ();
        alert.accept ();
        log.debug (alert_message + " Alert received and accepted");
    }

    /* Jquery Calendars (tgt_date format is always DD/MM/YYYY)
     * by_calendar = locator for the calendar
     * by_move_back = locator for the backwards navigation of calendar
     * by_move_fwd = locator for the forwards navigation of calendar
     * */

    public static void handleCalendars(String tgt_date, By by_calendar, By by_move_back, By by_move_fwd) {

        int tgt_day = 0, tgt_month = 0, tgt_year = 0;
        int curr_day = 0, curr_month = 0, curr_year = 0;
        int jump_months_by = 0;
        boolean increment = true;

        //Get Current System date
        Calendar cal = Calendar.getInstance ();
        curr_day = cal.get (Calendar.DAY_OF_MONTH);
        curr_month = cal.get (Calendar.MONTH);
        curr_year = cal.get (Calendar.YEAR);

        log.debug ("Date configured as Current date is " + curr_day + "/" + curr_month + "/" + curr_year);

        //Get Target date from dateString
        int firstIndex = tgt_date.indexOf ("/");
        int lastIndex = tgt_date.lastIndexOf ("/");

        //Typecasting provided DateString into dates
        tgt_day = Integer.parseInt (tgt_date.substring (0, firstIndex));
        tgt_month = Integer.parseInt (tgt_date.substring (firstIndex + 1, lastIndex));
        tgt_year = Integer.parseInt (tgt_date.substring (lastIndex + 1, tgt_date.length ()));

        log.debug ("Date configured as Target date is " + tgt_day + "/" + tgt_month + "/" + tgt_year);

        //Calculating the Date jump from Current date to Target date
        if (tgt_month - curr_month > 0) {
            jump_months_by = tgt_month - curr_month;
            log.info ("Target date is in the future");
        } else {
            jump_months_by = curr_month - tgt_month;
            increment = false;
            log.info ("Target date is in the past");
        }

        //WebDriver integration
        waits (10);
        driver.findElement (by_calendar).click ();

        //Traverse from Current date to Target date
        driver.findElement (By.linkText (Integer.toString (tgt_day)));

        //Traverse from Current month to Target month
        for (int i = 0 ;i < jump_months_by ;i++) {
            if (increment) {
                driver.findElement (by_move_fwd).click ();
            } else {
                driver.findElement (by_move_back).click ();
            }
            waits (10);
        }
    }
}
