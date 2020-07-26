package utilities.connectivity;

public class ConnectivityConfig {

    //SMTP Server config for Emails
    public static String server = "smtp.gmail.com";
    public static String from_addr = "sender_email@domain.com";
    public static String password = "Selenium@Password";
    public static String[] to_addr = {"receiver_email@domain.com", "receiver_email@domain.com"};
    public static String subject_body = "Extent Project Report";

    //Paths for Attachments and files
    public static String message_body = "TestMessage";
    public static String attachment_path = "src/screenshots/";
    public static String attachment_name = "test.jpg";

    //SQL DATABASE DETAILS
    public static String sqldriver = "net.sourceforge.jtds.jdbc.Driver";
    public static String dbConnectionUrl = "jdbc:jtds:sqlserver://192.101.44.22;DatabaseName=monitor_eval";
    public static String dbUserName = "sa";
    public static String dbPassword = "$ql$!!1";


    //MYSQL DATABASE DETAILS
    public static String mysqldriver = "com.mysql.jdbc.Driver";
    public static String mysqluserName = "root";
    public static String mysqlpassword = "selenium";
    public static String mysqlurl = "jdbc:mysql://localhost:3306/selenium_inc01";

}