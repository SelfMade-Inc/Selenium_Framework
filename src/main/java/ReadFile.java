import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;

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

            //Read entire text file in crude code and print line one at a time
            String line = "";
            while ((line = buf_reader.readLine())!=null) {
                //Reads single line specified as parameter value
                System.out.println(line);
            }

            //Read & Write text file using one line while statement
            while ((line = buf_reader.readLine())!=null) System.out.println(line);

            //Read & Print line by line value using for each
            buf_reader.lines().forEach(System.out::println);

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

        try {
            //File input stream to read data
            File excel_file = new File("WriteFile_output.xlsx");
            FileInputStream ip_stream = new FileInputStream(excel_file);

            //Initiate a workbook reader and read sheet names from workbook
            Workbook workbook = XSSFWorkbookFactory.createWorkbook(ip_stream);
            Sheet sheet = workbook.getSheetAt(0);

            //read the all row and cell wrt CellType
            for(Row row_index : sheet){
                for(Cell cell_index : row_index){
                    switch (cell_index.getCellType()){
                        case Cell.CELL_TYPE_STRING:
                        System.out.println(cell_index.getStringCellValue());
                        case Cell.CELL_TYPE_BLANK:
                            System.out.println("Blank Cell located");
                        case Cell.CELL_TYPE_BOOLEAN:
                            System.out.println(cell_index.getBooleanCellValue());
                        case Cell.CELL_TYPE_ERROR:
                            System.out.println(cell_index.getErrorCellValue());
                        case Cell.CELL_TYPE_NUMERIC:
                            System.out.println(cell_index.getNumericCellValue());
                        case Cell.CELL_TYPE_FORMULA:
                            System.out.println(cell_index.getCellFormula());
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
