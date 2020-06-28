package classTemplates;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Calendar;

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

    //ReadExcel is for Repo files older than 4.0.0
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
            Workbook workbook = WorkbookFactory.create(ip_stream);
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

    //UpdatedReadExcel is for Repo files ver4.*.*
    public class UpdatedReadExcel {

        public  String file_path;
        public  FileInputStream ip_stream = null;
        public  FileOutputStream op_stream =null;
        private XSSFWorkbook workbook = null;
        private XSSFSheet sheet = null;
        private XSSFRow row   =null;
        private XSSFCell cell = null;
        String cellText;

        //Initiates the connection of IOStreams to the workbook
        public void LoadExcel(String file_path){

            //THIS keyword is used to load Class level variable declarations to Method level parameters
            this.file_path = file_path;
            try {
                ip_stream = new FileInputStream(file_path);
                workbook = new XSSFWorkbook(ip_stream);
                sheet = workbook.getSheetAt(0);
                ip_stream.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Returns the row count in a sheet
        public int getRowCount(String sheet_Name){
            int index = workbook.getSheetIndex(sheet_Name);
            if(index==-1)
                return 0;
            else{
                sheet = workbook.getSheetAt(index);
                int row_count = sheet.getLastRowNum()+1;
                return row_count;
            }
        }

        //Returns the data from a cell
        //getCellData here accepts Sheet name, Column name and row index
        public String getCellData(String sheet_Name,String col_Name,int row_Index){
            try{
                if(row_Index <=0)
                    return "";

                int index = workbook.getSheetIndex(sheet_Name);
                int col_Index=-1;
                if(index==-1)
                    return "";

                sheet = workbook.getSheetAt(index);
                row=sheet.getRow(0);

                for(int i = 0; i < row.getLastCellNum(); i++){
                    //Print the cell data to Console
                    System.out.println(row.getCell(i).getStringCellValue().trim());

                    if(row.getCell(i).getStringCellValue().trim().equals(col_Name.trim()))
                        col_Index=i;
                }

                if(col_Index==-1)
                    return "";

                sheet = workbook.getSheetAt(index);
                row = sheet.getRow(row_Index-1);

                if(row == null)
                    return "";
                cell = row.getCell(col_Index);

                if(cell == null){
                    return "";
                }
                else {
                    //Handles all Cell types
                    switch (cell.getCellTypeEnum()) {
                        case STRING:
                            System.out.println (cell.getRichStringCellValue ());
                            break;
                        case BLANK:
                            System.out.println ("Blank Cell located");
                            break;
                        case BOOLEAN:
                            System.out.println (cell.getBooleanCellValue ());
                            break;
                        case NUMERIC:
                            System.out.println (cell.getNumericCellValue ());
                            //Handles date entries in Cells
                            if (HSSFDateUtil.isCellDateFormatted(cell)) {

                                double d = cell.getNumericCellValue();

                                Calendar cal =Calendar.getInstance();
                                cal.setTime(HSSFDateUtil.getJavaDate(d));
                                cellText =
                                        (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
                                cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" +
                                        cal.get(Calendar.MONTH)+1 + "/" +
                                        cellText;
                            }
                            break;
                        case FORMULA:
                            System.out.println (cell.getCellFormula ());
                            break;
                    }
                    return cellText;
                }
            }
            catch(Exception e){
                e.printStackTrace();
                return "row "+row_Index+" or column "+col_Name +" does not exist in xls";
            }
        }

        //getCellData here accepts Sheet name, Column index and row index
        public String getCellData(String sheet_Name,int col_index,int row_index){
            try{
                if(row_index <=0)
                    return "";

                //Verifies if sheet_name has any data
                int index = workbook.getSheetIndex(sheet_Name);
                if(index==-1)
                    return "";

                //Returns row count from specified sheet
                sheet = workbook.getSheetAt(index);
                row = sheet.getRow(row_index-1);
                if(row == null)
                    return "";

                //Get cell count from specified row
                cell = row.getCell(col_index);
                if(cell==null)
                    return "";

                //Same as the switch implementation in getCellData
                if(cell == null){
                    return "";
                }
                else {
                    //Handles all Cell types
                    switch (cell.getCellTypeEnum()) {
                        case STRING:
                            System.out.println (cell.getRichStringCellValue ());
                            break;
                        case BLANK:
                            System.out.println ("Blank Cell located");
                            break;
                        case BOOLEAN:
                            System.out.println (cell.getBooleanCellValue ());
                            break;
                        case NUMERIC:
                            System.out.println (cell.getNumericCellValue ());
                            //Handles date entries in Cells
                            if (HSSFDateUtil.isCellDateFormatted(cell)) {

                                double d = cell.getNumericCellValue();

                                Calendar cal =Calendar.getInstance();
                                cal.setTime(HSSFDateUtil.getJavaDate(d));
                                cellText =
                                        (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
                                cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" +
                                        cal.get(Calendar.MONTH)+1 + "/" +
                                        cellText;
                            }
                            break;
                        case FORMULA:
                            System.out.println (cell.getCellFormula ());
                            break;
                    }
                    return cellText;
                }
            }
            catch(Exception e){

                e.printStackTrace();
                return "row "+row_index+" or column "+col_index +" does not exist  in xls";
            }
        }
    }
}
