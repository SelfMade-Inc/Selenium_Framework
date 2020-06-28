package utilities;

import org.apache.log4j.PropertyConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class initLogs {

<<<<<<< HEAD:src/test/java/utilities/Logs.java
    //Instantiate Logger class from Org.Apache.Log4j
    public static Logger log = LogManager.getLogger(Logs.class.getName());
=======
    public static Logger log = LogManager.getLogger(initLogs.class.getName());
>>>>>>> 659e9967719ee2979ae7520f0809ecec161c6b78:src/test/java/utilities/initLogs.java

    public static void main(String[] args) {

        //Fetch log4j.properties file for layouts
        PropertyConfigurator.configure ("src/main/resources/log4j_prop.properties");

        //Timestamping and setting Date format to be acceptable by File Explorer
        Date curr_date = new Date ();
        System.setProperty ("current.date",curr_date.toString ().replace (":","_").replace (" ","_"));

        log.debug("This is a debug log");
    }
}
