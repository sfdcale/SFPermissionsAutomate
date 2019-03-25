package utility;

import java.io.FileInputStream;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;

public class ExcelUtils {
	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static org.apache.poi.ss.usermodel.Cell Cell;
	private static XSSFRow Row;
	// private static XSSFRow Row;

	public static void setConfigFilePath(String Path) throws Exception {
		FileInputStream ExcelFile = new FileInputStream(Path);
		ExcelWBook = new XSSFWorkbook(ExcelFile);
	}

	public static String getCellData(int RowNum, int ColNum, String SheetName){
		System.out.println("Entered getCellData method");
		System.out.println("RowNum="+RowNum + " ColNum=" +ColNum + " SheetName="+SheetName );
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		// We are specifying CREATE_NULL_AS_BLANK so that if the cell doesn't contain value then it returns blank.
		Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum,org.apache.poi.xssf.usermodel.XSSFRow.CREATE_NULL_AS_BLANK);
		String CellData = Cell.getStringCellValue();
		System.out.println("CellData: " + CellData);
		return CellData;
	}

	public static int getRowCount(String SheetName) {
		int iNumber = 0;
		try {
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			iNumber = ExcelWSheet.getLastRowNum() + 1;
		} catch (Exception e) {
			System.out.println("Class Utils | Method getRowCount | Exception desc : " + e.getMessage());
		}
		return iNumber;
	}

}