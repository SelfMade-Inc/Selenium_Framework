import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;


public class WriteFile {

    /*
    Apache POI & JExcel POI = JAVA API used to handle .xls and .xlsx files
    HSSF API = used for .xls files only
    XSSF API = used for both .xsl and .xlxs files
   */
    public void WriteExcel() {
        //Create Workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create sheet
        XSSFSheet sheet = workbook.createSheet("First sheet");

        //Create row
        for(int rows = 0; rows <= 10; rows++){
            XSSFRow row = sheet.createRow(rows);

            //Create cell
            for(int cols = 0; cols <= rows; cols++){
                XSSFCell cell = row.createCell(cols);

                //Write to cell
                //cell.(cols+" cell in "+rows+" row");

            }
        }

        try {
            //Creating File stream and init file path
            File excel_file = new File("WriteFile_output.xlsx");
            FileOutputStream op_stream = new FileOutputStream(excel_file);

            //Post write contents to file via IO Stream
            workbook.write(op_stream);

            //Closing the IO Stream
            op_stream.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void WriteTextFile() {
        //Start IO Stream

        //Creating a File object to reference the File class

        try{
            //To create specific fileformat just change the extension
            File txt_file = new File("src/main/Class Templates/WriteFile_output.txt");


            //Object reference for FileWriter constructor class
            FileWriter file_write = new FileWriter(txt_file, true); //True enables File updating after creation
            //Object reference for BufferedWriter constructor class
            BufferedWriter buf_writer = new BufferedWriter(file_write);

            //Write into text file
                for(int i = 0; i< 5;i++) {
                    for (int j = 0; j < 5; j++) {
                        buf_writer.write("First Line into the file");
                        int num = (int) (Math.random() * 100);
                        buf_writer.write(num + "\t"); //t after each entry to insert tab
                    }
                buf_writer.newLine();
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    public void WriteCSVFile() {
        //Start IO Stream

        //Creating a File object to reference the File class

        try{
        //To create specific fileformat just change the extension
        File csv_file = new File("WriteFile_output.csv");

        //Object reference for FileWriter constructor class
        FileWriter file_write = new FileWriter(csv_file, true); //True enables File updating after creation
        //Object reference for BufferedWriter constructor class
        BufferedWriter buf_writer = new BufferedWriter(file_write);

        //Write into csv file
            for(int i = 0; i< 5;i++)
            {
                for (int j = 0; j < 5; j++) {
                    buf_writer.write("First Line into the file");
                    int num = (int) (Math.random() * 100);
                    buf_writer.write(num + ","); //, after each entry to insert comma
                }
            buf_writer.newLine();
        }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void WriteHTMLFile() {
        //Start IO Stream

        //Creating a File object to reference the File class

        try {
            //To create specific fileformat just change the extension
            File html_file = new File("WriteFile_output.html");

            //Object reference for FileWriter constructor class
            FileWriter file_write = new FileWriter(html_file, true); //True enables File updating after creation
            //Object reference for BufferedWriter constructor class
            BufferedWriter buf_writer = new BufferedWriter(file_write);

            //Write into HTML file
            for (int i = 0; i < 5; i++) {
                buf_writer.write("First Line into the file");
                int num = (int) (Math.random() * 100);
                buf_writer.write("<html><body><title>Writing into an HTML file</title><h1>This marks the first line of the HTML file</h1></body></html>");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
