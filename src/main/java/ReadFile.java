import java.io.*;

public class ReadFile {

    public void ReadAnyFile() {
        //Start IO Stream

        //Creating a File object to reference the File class
        //To read specific fileformat just change the extension
        File txt_file = new File("src/main/Class Templates/WriteFile_output.txt");
        File csv_file = new File("WriteFile_output.csv");
        File html_file = new File("WriteFile_output.html");

        //Object reference for FileReader constructor class
        try {
            FileReader file_reader = new FileReader(txt_file);

            //Object reference for BufferedReader constructor class
            BufferedReader buf_reader = new BufferedReader(file_reader);

            //Read entire text file
            String line = null;
            while ((line == buf_reader.readLine())) {

                //Reads single line specified as parameter value
                System.out.println(line);

            }
            //Close stream
            buf_reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ReadExcel() throws IOException {
       /*
        Apache POI & JExcel POI = JAVA API used to handle .xls and .xlsx files
        HSSF API = used for .xls files
        XSSF API = used for xlxs files
       */

        //File input stream to pass and read data
        File excel_file = new File("WriteFile_output.xlsx");
        FileInputStream ip_stream = new FileInputStream(excel_file);

        //Get the sheet from workbook
        //Workbook workbook =

        //Sheet sheet = workbook.getSheetAt(0);

        //read the specified row and cell
        //Row row = sheet.getRow(0);
        //Cell cell = sheet.getCell(0);
    }
}
