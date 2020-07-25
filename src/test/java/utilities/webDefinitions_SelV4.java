package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class webDefinitions_SelV4 {

    public static WebDriver driver;

    public static void main(String[] args) {

        /* Initiate WebDriver and handle opening new tabs or windows */

        WebDriverManager.chromedriver ().setup ();

        driver = new ChromeDriver ();
        driver.get ("https:\\google.com");

        driver.manage ().window ().maximize ();
        driver.manage ().timeouts ().implicitlyWait (10, TimeUnit.SECONDS);

        System.out.println ("This is the initialisation of 1st Window");

        //To open a new browser tab with a specified url
        driver.switchTo ().newWindow (WindowType.TAB);
        driver.get ("https:\\youtube.com");

        driver.manage ().window ().maximize ();
        driver.manage ().timeouts ().implicitlyWait (10, TimeUnit.SECONDS);

        System.out.println ("This is the initialisation of 2nd Tab in the 1st Window");

        //To open a new browser window with a specified url
        driver.switchTo ().newWindow (WindowType.WINDOW);
        driver.get ("https:\\google.com.au");

        driver.manage ().window ().maximize ();
        driver.manage ().timeouts ().implicitlyWait (10, TimeUnit.SECONDS);

        System.out.println ("This is the initialisation of 2nd Window");
        getWindowHandles();

    }

    public static void getWindowHandles(){

        Set<String> window_handles = driver.getWindowHandles ();
        Iterator<String> iterator = window_handles.iterator ();

        iterator.next ();
        iterator.next ();

        String window_handle = iterator.next ();
        System.out.println ("Now we are in a different window with title " + driver.getTitle ());
    }
}
