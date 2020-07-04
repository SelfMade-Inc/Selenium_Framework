package utilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCConnectivity {

    //Instantiate connection to the DB to provide information describing its table
    private static Connection sql_connect = null; //sql
    private static Connection mysql_connect = null; //mysql
    String Query = ""; //Insert query here in SQL format

    //SQL Server connection
    public static void setSQLConnection() throws SQLException, ClassNotFoundException {
        try {
            //Call the DB Connectivity driver from the config
            Class.forName (ConnectivityConfig.sqldriver);

            //Establish connection via parameters passed in config
            sql_connect = DriverManager.getConnection (ConnectivityConfig.dbConnectionUrl, ConnectivityConfig.dbUserName, ConnectivityConfig.dbPassword);

            //If True then connectivity is established
            if (!sql_connect.isClosed ())
                System.out.println ("Successfully connected to SQL server");

        } catch (Exception e) {
            System.err.println ("Exception: " + e.getMessage ());
        }
    }

    //MySQL Server connection
    public static void setMySQLConnection() throws SQLException, ClassNotFoundException {
        try {
            //Call the DB Connectivity driver from the config
            Class.forName (ConnectivityConfig.mysqldriver);

            //Establish connection via parameters passed in config
            mysql_connect = DriverManager.getConnection (ConnectivityConfig.mysqlurl, ConnectivityConfig.mysqluserName, ConnectivityConfig.mysqlpassword);

            //If True then connectivity is established
            if (!mysql_connect.isClosed ())
                System.out.println ("Successfully connected to MySQL server");
        } catch (Exception e) {
            System.err.println ("Cannot connect to database server");
        }
    }

    //Running queries in SQL DB
    public static List<String> getSQLQuery(String query) throws SQLException {

        //Creates a Statement object for sending SQL statements to the database
        Statement sql_statement = sql_connect.createStatement ();

        //ResultSet interface provides getter methods (getBoolean, getLong, and so on) for retrieving column values
        ResultSet sql_result = sql_statement.executeQuery (query);

        //Insert values in resultset into a ArrayList
        List<String> values = new ArrayList<String> ();
        while (sql_result.next ()) {
            values.add (sql_result.getString (1));
        }
        return values;
    }

    //Running queries in MySQL DB
    public static List<String> getMySQLQuery(String query) throws SQLException {

        //Creates a Statement object for sending SQL statements to the database
        Statement mysql_statement = mysql_connect.createStatement ();

        //ResultSet interface provides getter methods (getBoolean, getLong, and so on) for retrieving column values
        ResultSet sql_result = mysql_statement.executeQuery (query);

        //Insert values in resultset into a ArrayList
        List<String> values = new ArrayList<String> ();
        while (sql_result.next ()) {
            values.add (sql_result.getString (1));
        }
        return values;
    }
}
