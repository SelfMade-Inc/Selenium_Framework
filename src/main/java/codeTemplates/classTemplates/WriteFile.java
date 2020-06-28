package classTemplates;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;


public class WriteFile {

    /*
    Apache POI & JExcel POI = JAVA API used to handle .xls and .xlsx files
    HSSF API = used for .xls files only
    XSSF API = used for both .xsl and .xlxs files
   */

    public String file_path;
    public FileInputStream ip_stream = null;
    public FileOutputStream op_stream = null;
    private XSSFWorkbook workbook = null;
    private XSSFSheet sheet = null;
    private XSSFRow row = null;
    private XSSFCell cell = null;

    //Setting Excel data using POI jars before ver4.*.*
    public void WriteExcel() {
        //Create Workbook
        XSSFWorkbook workbook = new XSSFWorkbook ();

        //Create sheet
        XSSFSheet sheet = workbook.createSheet ("First sheet");

        //Create row
        for (int row_index = 0 ;row_index <= 10 ;row_index++) {
            Row row = sheet.createRow (row_index);

            //Create and Write to cell
            for (int col_index = 0 ;col_index <= row_index ;col_index++) {
                Cell cell = row.createCell (col_index);
                cell.setCellValue ("cell index " + col_index + " of row index " + row_index);
            }
        }

        //Creating File stream and init file path
        try {

            //Post write contents to file via IO Stream
            workbook.write (op_stream);

            //Closing the IO Stream
            op_stream.flush (); //Used when the initReader will happen again
            op_stream.close (); //Used when initReader will not happen again
        } catch (Exception e) {
            System.out.println (e);
        }
    }

    public void WriteTextFile() {
        //Start IO Stream

        //Creating a File object to reference the File class
        try {
            //To create specific fileformat just change the extension
            File txt_file = new File ("src/main/Class Templates/WriteFile_output.txt");


            //Object reference for FileWriter constructor class
            FileWriter file_write = new FileWriter (txt_file, true); //True enables File updating after creation
            //Object reference for BufferedWriter constructor class
            BufferedWriter buf_writer = new BufferedWriter (file_write);

            //Write into text file
            for (int i = 0 ;i < 5 ;i++) {
                for (int j = 0 ;j < 5 ;j++) {
                    buf_writer.write ("First Line into the file");
                    int num = (int) (Math.random () * 100);
                    buf_writer.write (num + "\t"); //t after each entry to insert tab
                }
                buf_writer.newLine ();
            }

            //Closing the writer after writing
            buf_writer.flush (); //Used when the initReader will happen again
            buf_writer.close (); //Used when initReader will not happen again

        } catch (Exception e) {
            System.out.println (e);
        }
    }

    public void WriteCSVFile() {
        //Start IO Stream

        //Creating a File object to reference the File class

        try {
            //To create specific fileformat just change the extension
            File csv_file = new File ("WriteFile_output.csv");

            //Object reference for FileWriter constructor class
            FileWriter file_write = new FileWriter (csv_file, true); //True enables File updating after creation
            //Object reference for BufferedWriter constructor class
            BufferedWriter buf_writer = new BufferedWriter (file_write);

            //Write into csv file
            for (int i = 0 ;i < 5 ;i++) {
                for (int j = 0 ;j < 5 ;j++) {
                    buf_writer.write ("First Line into the file");
                    int num = (int) (Math.random () * 100);
                    buf_writer.write (num + ","); //, after each entry to insert comma
                }
                buf_writer.newLine ();
            }

            //Closing the writer after writing
            buf_writer.flush (); //Used when the initReader will happen again
            buf_writer.close (); //Used when initReader will not happen again

        } catch (Exception e) {
            System.out.println (e);
        }
    }

    public void WriteHTMLFile() {
        //Start IO Stream

        //Creating a File object to reference the File class

        try {
            //To create specific fileformat just change the extension
            File html_file = new File ("WriteFile_output.html");

            //Object reference for FileWriter constructor class
            FileWriter file_write = new FileWriter (html_file, true); //True enables File updating after creation
            //Object reference for BufferedWriter constructor class
            BufferedWriter buf_writer = new BufferedWriter (file_write);

            //Write into HTML file
            for (int i = 0 ;i < 5 ;i++) {
                buf_writer.write ("First Line into the file");
                int num = (int) (Math.random () * 100);
                buf_writer.write ("<html><body><title>Writing into an HTML file</title><h1>This marks the first line of the HTML file</h1></body></html>");
            }

            //Closing the writer after writing
            buf_writer.flush (); //Used when the initReader will happen again
            buf_writer.close (); //Used when initReader will not happen again

        } catch (Exception e) {
            System.out.println (e);
        }
    }

}
