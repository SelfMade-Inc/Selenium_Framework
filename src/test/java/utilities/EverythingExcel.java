package utilities;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.stream.IntStream;

public class EverythingExcel {

    public String file_path;
    public FileInputStream ip_stream = null;
    public FileOutputStream op_stream = null;
    String cellText;
    private XSSFWorkbook workbook = null;
    private XSSFSheet sheet = null;
    private XSSFRow row = null;
    private XSSFCell cell = null;

    //Initiates the connection of IOStreams to the workbook
    public void LoadExcel(String file_path) {

        //THIS keyword is used to load Class level variable declarations to Method level parameters
        this.file_path = file_path;
        try {
            ip_stream = new FileInputStream (file_path);
            workbook = new XSSFWorkbook (ip_stream);
            sheet = workbook.getSheetAt (0);
            ip_stream.close ();
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    /* Reading an Excel file */
    /*
    UpdatedReadExcel is for Repo files older than ver4.*.* and ReadExcel is for Repo files before 4.*.*
    */
    //No nonsense no parameter crude ReadExcel
    public void ReadExcel() throws IOException {
       /*
        Apache POI & JExcel POI = JAVA API used to handle .xls and .xlsx files
        HSSF API = used for .xls files
        XSSF API = used for xlxs files
       */

        try {
            //File input stream to read data
            File excel_file = new File ("WriteFile_output.xlsx");
            ip_stream = new FileInputStream (excel_file);

            //Initiate a workbook reader and read sheet names from workbook
            Workbook workbook = WorkbookFactory.create (ip_stream);
            Sheet sheet = workbook.getSheetAt (0);

            //read the all row and cell wrt CellType
            for (Row row_index : sheet) {
                for (Cell cell_index : row_index) {
                    switch (cell_index.getCellType ()) {
                        case STRING:
                            System.out.println (cell_index.getStringCellValue ());
                        case BLANK:
                            System.out.println ("Blank Cell located");
                        case BOOLEAN:
                            System.out.println (cell_index.getBooleanCellValue ());
                        case ERROR:
                            System.out.println (cell_index.getErrorCellValue ());
                        case NUMERIC:
                            System.out.println (cell_index.getNumericCellValue ());
                        case FORMULA:
                            System.out.println (cell_index.getCellFormula ());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println (e);
        }
    }

    //Reads the data from a cell with parameters as sheet name column name and row index
    public String UpdatedReadExcel(String sheet_Name, String col_Name, int row_Index) {
        try {
            if (row_Index <= 0)
                return "";

            int index = workbook.getSheetIndex (sheet_Name);
            int col_Index = -1;
            if (index == -1)
                return "";

            sheet = workbook.getSheetAt (index);
            row = sheet.getRow (0);

            for (int i = 0 ;i < row.getLastCellNum () ;i++) {
                //Print the cell data to Console
                System.out.println (row.getCell (i).getStringCellValue ().trim ());

                if (row.getCell (i).getStringCellValue ().trim ().equals (col_Name.trim ()))
                    col_Index = i;
            }

            if (col_Index == -1)
                return "";

            sheet = workbook.getSheetAt (index);
            row = sheet.getRow (row_Index - 1);

            if (row == null)
                return "";
            cell = row.getCell (col_Index);

            if (cell == null) {
                return "";
            } else {
                //Handles all Cell types
                switch (cell.getCellTypeEnum ()) {
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
                        if (HSSFDateUtil.isCellDateFormatted (cell)) {

                            double d = cell.getNumericCellValue ();

                            Calendar cal = Calendar.getInstance ();
                            cal.setTime (HSSFDateUtil.getJavaDate (d));
                            cellText =
                                    (String.valueOf (cal.get (Calendar.YEAR))).substring (2);
                            cellText = cal.get (Calendar.DAY_OF_MONTH) + "/" +
                                    cal.get (Calendar.MONTH) + 1 + "/" +
                                    cellText;
                        }
                        break;
                    case FORMULA:
                        System.out.println (cell.getCellFormula ());
                        break;
                }
                return cellText;
            }
        } catch (Exception e) {
            e.printStackTrace ();
            return "row " + row_Index + " or column " + col_Name + " does not exist in xls";
        }
    }

    //Reads the data from a cell with parameters as Sheet name, Column index and row index
    public String UpdatedReadExcel(String sheet_Name, int col_index, int row_index) {
        try {
            if (row_index <= 0)
                return "";

            //Verifies if sheet_name has any data
            int index = workbook.getSheetIndex (sheet_Name);
            if (index == -1)
                return "";

            //Returns row count from specified sheet
            sheet = workbook.getSheetAt (index);
            row = sheet.getRow (row_index - 1);
            if (row == null)
                return "";

            //Get cell count from specified row
            cell = row.getCell (col_index);
            if (cell == null)
                return "";

            //Same as the switch implementation in getCellData
            if (cell == null) {
                return "";
            } else {
                //Handles all Cell types
                switch (cell.getCellTypeEnum ()) {
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
                        if (HSSFDateUtil.isCellDateFormatted (cell)) {

                            double d = cell.getNumericCellValue ();

                            Calendar cal = Calendar.getInstance ();
                            cal.setTime (HSSFDateUtil.getJavaDate (d));
                            cellText =
                                    (String.valueOf (cal.get (Calendar.YEAR))).substring (2);
                            cellText = cal.get (Calendar.DAY_OF_MONTH) + "/" +
                                    cal.get (Calendar.MONTH) + 1 + "/" +
                                    cellText;
                        }
                        break;
                    case FORMULA:
                        System.out.println (cell.getCellFormula ());
                        break;
                }
                return cellText;
            }
        } catch (Exception e) {

            e.printStackTrace ();
            return "row " + row_index + " or column " + col_index + " does not exist  in xls";
        }
    }

    /*
    Data operations after Reading Excel
    */

    //Returns the row count in a sheet
    public int getRowCount(String sheet_Name) {
        int index = workbook.getSheetIndex (sheet_Name);
        if (index == -1)
            return 0;
        else {
            sheet = workbook.getSheetAt (index);
            int row_count = sheet.getLastRowNum () + 1;
            return row_count;
        }
    }

    /* Writing to an Excel file */

    //addCellData here uses sheet name, column name and row index to write cell data in cellText or cellURL format
    public boolean addCellData(String sheet_Name, String col_Name, int row_index, String cellText, String cellUrl) {

        try {
            ip_stream = new FileInputStream (file_path);
            workbook = new XSSFWorkbook (ip_stream);

            //Checks for blank sheets
            if (row_index <= 0)
                return false;

            //Checks for Sheets
            int index = workbook.getSheetIndex (sheet_Name);
            int col_index = -1;
            if (index == -1)
                return false;

            sheet = workbook.getSheetAt (index);

            row = sheet.getRow (0);
            for (int i = 0 ;i < row.getLastCellNum () ;i++) {

                if (row.getCell (i).getStringCellValue ().trim ().equalsIgnoreCase (col_Name))
                    col_index = i;
            }

            if (col_index == -1)
                return false;

            sheet.autoSizeColumn (col_index);
            row = sheet.getRow (row_index - 1);
            if (row == null)
                row = sheet.createRow (row_index - 1);

            cell = row.getCell (col_index);
            if (cell == null)
                cell = row.createCell (col_index);

            //Set String values into cells
            cell.setCellValue (cellText);
            XSSFCreationHelper createHelper = workbook.getCreationHelper ();

            //Set hyperlinks into cells
            CellStyle hlink_style = workbook.createCellStyle ();
            XSSFFont hlink_font = workbook.createFont ();
            hlink_font.setUnderline (XSSFFont.U_SINGLE);
            hlink_font.setColor (IndexedColors.BLUE.getIndex ());
            hlink_style.setFont (hlink_font);
            hlink_style.setWrapText (true);

            XSSFHyperlink link = createHelper.createHyperlink (HyperlinkType.FILE);
            link.setAddress (cellUrl);
            cell.setHyperlink (link);
            cell.setCellStyle (hlink_style);

            op_stream = new FileOutputStream (file_path);
            workbook.write (op_stream);

            op_stream.close ();
        } catch (Exception e) {
            e.printStackTrace ();
            return false;
        }
        return true;
    }

    //Write data in sheet_Name > testCase_Name using keyword in formats like URL or message
    public boolean addHyperLink(String sheet_name, String scrShot_col_name, String testCase_name, int index, String cell_URL, String cell_message) {

        cell_URL = cell_URL.replace ('\\', '/');

        if (!doesSheetExist (sheet_name))
            return false;

        sheet = workbook.getSheet (sheet_name);

        for (int i = 2 ;i <= getRowCount (sheet_name) ;i++) {

            if (!UpdatedReadExcel (sheet_name, 0, i).equalsIgnoreCase (testCase_name)) {
                continue;
            }
            addCellData (sheet_name, scrShot_col_name, i + index, cell_message, cell_URL);
            break;
        }
        return true;
    }

    /* Methods referencing Sheet actions */

    // Adding a sheet to existing Workbook
    public boolean addNewSheet(String sheet_name) {

        try {
            workbook.createSheet (sheet_name);
            op_stream = new FileOutputStream (file_path);
            workbook.write (op_stream);
            op_stream.close ();
        } catch (Exception e) {
            e.printStackTrace ();
            return false;
        }
        return true;
    }

    //Removing sheet from existing Workbook
    public boolean removeSheet(String sheet_name) {
        int index = workbook.getSheetIndex (sheet_name);
        if (index == -1)
            return false;

        try {
            workbook.removeSheetAt (index);
            op_stream = new FileOutputStream (file_path);
            workbook.write (op_stream);
            op_stream.close ();
        } catch (Exception e) {
            e.printStackTrace ();
            return false;
        }
        return true;
    }

    //Find whether sheets exist
    public boolean doesSheetExist(String sheet_Name) {
        int index = workbook.getSheetIndex (sheet_Name);

        if (index == -1) {
            index = workbook.getSheetIndex (sheet_Name.toUpperCase ());
            if (index == -1)
                return false;
            else
                return true;
        } else
            return true;
    }

    /* Methods referencing actions on Rows and Columns */

    //Returns true if column is created successfully
    public boolean addColumn(String sheet_Name, String col_Name) {

        try {
            ip_stream = new FileInputStream (file_path);
            workbook = new XSSFWorkbook (ip_stream);
            int index = workbook.getSheetIndex (sheet_Name);

            //checks if sheet existing
            if (index == -1)
                return false;

            XSSFCellStyle style = workbook.createCellStyle ();
            style.setFillForegroundColor (HSSFColor.HSSFColorPredefined.GREY_40_PERCENT.getIndex ());
            style.setFillPattern (FillPatternType.SOLID_FOREGROUND);

            sheet = workbook.getSheetAt (index);

            row = sheet.getRow (0);
            if (row == null)
                row = sheet.createRow (0);

            if (row.getLastCellNum () == -1)
                cell = row.createCell (0);
            else
                cell = row.createCell (row.getLastCellNum ());

            cell.setCellValue (col_Name);
            cell.setCellStyle (style);

            op_stream = new FileOutputStream (file_path);
            workbook.write (op_stream);
            op_stream.close ();

        } catch (Exception e) {
            e.printStackTrace ();
            return false;
        }
        return true;
    }

    //Removes a column and all the contents
    public boolean removeColumn(String sheet_Name, int col_index) {
        try {
            if (!doesSheetExist (sheet_Name))
                return false;

            ip_stream = new FileInputStream (file_path);
            workbook = new XSSFWorkbook (ip_stream);

            sheet = workbook.getSheet (sheet_Name);
            XSSFCellStyle style = workbook.createCellStyle ();
            style.setFillForegroundColor (HSSFColor.HSSFColorPredefined.GREY_40_PERCENT.getIndex ());
            //XSSFCreationHelper createHelper = workbook.getCreationHelper();
            style.setFillPattern (FillPatternType.NO_FILL);

            for (int i = 0 ;i < getRowCount (sheet_Name) ;i++) {
                row = sheet.getRow (i);
                if (row != null) {
                    cell = row.getCell (col_index);
                    if (cell != null) {
                        cell.setCellStyle (style);
                        row.removeCell (cell);
                    }
                }
            }
            op_stream = new FileOutputStream (file_path);
            workbook.write (op_stream);
            op_stream.close ();
        } catch (Exception e) {
            e.printStackTrace ();
            return false;
        }
        return true;
    }

    //Returns number of columns in a sheet
    public int getColumnCount(String sheet_name) {

        // check if sheet exists
        if (doesSheetExist (sheet_name)) {
            sheet = workbook.getSheet (sheet_name);
            row = sheet.getRow (0);

            if (row == null)
                return -1;
            return row.getLastCellNum ();
        } else {
            return -1;
        }
    }

    //Returns number if rows in a sheet
    public int getCellRowNum(String sheet_name, String col_name, String celltext) {
        /*
        Returns a sequential ordered IntStream(interface) produced by iteration(method) of the given next  function to an initial element, conditioned on satisfying the given hasNext predicate. The stream terminates as soon as the hasNext predicate returns false.
        It works similar to running a for each loop and returns counter = col_last_index
        */
        return IntStream.iterate (2, i -> i <= getRowCount (sheet_name), i -> i + 1).filter (i -> UpdatedReadExcel (sheet_name, col_name, i).equalsIgnoreCase (celltext)).findFirst ().orElse (-1);
    }

}
