package com.encgls.bigdata.excelUp;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;

public class ExcelCellRef {

	public static String getName(Cell cell, int cellIndex) {
		int cellNum =0;
		if(cell != null) {
			cellNum = cell.getColumnIndex();
		}
		else {
			cellNum = cellIndex;
		}
		return CellReference.convertNumToColString(cellNum);
	}
	
	@SuppressWarnings("deprecation")
	public static String getValue(Cell cell) {
		String value = "";
		
		if(cell == null) {
           return  value = null;
        }
        switch(cell.getCellType()) {
            case Cell.CELL_TYPE_FORMULA :
                value = cell.getCellFormula();
                break;
            
            case Cell.CELL_TYPE_NUMERIC :
                value = (int)cell.getNumericCellValue() + "";
                break;
                
            case Cell.CELL_TYPE_STRING :
                value = cell.getStringCellValue();
                break;
            
            case Cell.CELL_TYPE_BOOLEAN :
                value = cell.getBooleanCellValue() + "";
                break;
           
            case Cell.CELL_TYPE_BLANK :
                value = null;
                break;
            
            case Cell.CELL_TYPE_ERROR :
                value = cell.getErrorCellValue() + "";
                break;
                
            default:
                value = cell.getStringCellValue();
		}
		return value;
	}
	
}
