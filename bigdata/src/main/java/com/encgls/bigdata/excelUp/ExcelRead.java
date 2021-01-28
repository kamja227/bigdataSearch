package com.encgls.bigdata.excelUp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFReader.SheetIterator;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class ExcelRead {
	
		public static List<Map<String,String>> read(ExcelReadOption excelReadOption) throws Exception{

			Workbook wb = ExcelFileType.getWorkbook(excelReadOption.getFilePath());
			
			Sheet sheet = wb.getSheetAt(0);
			
			System.out.println("Sheet 이름: " + wb.getSheetName(0));
			System.out.println("데이터가 있는 sheet의 수:" + wb.getNumberOfSheets());
			//Sheet에서 유효한(데이터가 있는) 햏의 개수를 가져온다
			int numOfRows = sheet.getPhysicalNumberOfRows();
			int numOfCells = 0;
			
			Row row = null;
			Cell cell = null;
			
			String cellName = "";
			
			Map<String, String> map = null;
			
			List<Map<String, String>> result = new ArrayList<Map<String, String>>();
			
			for(int rowIndex = excelReadOption.getStarRow() -1; rowIndex < numOfRows; rowIndex++) {
				row = sheet.getRow(rowIndex);
				
				//System.out.println("rowIndex 이름: " + rowIndex);
				
				if(row != null) {
					numOfCells = row.getPhysicalNumberOfCells();
					
					map = new HashMap<String, String>();
					
					for(int cellIndex = 0; cellIndex < numOfCells; cellIndex++) {
						
						cell = row.getCell(cellIndex);
						
						cellName = ExcelCellRef.getName(cell, cellIndex);
						
						if(!excelReadOption.getOutputColumns().contains(cellName)) {
							continue;
						}
						System.out.println("ExcelValue  : " + cell);
						map.put(cellName, ExcelCellRef.getValue(cell));
					}
					result.add(map);
				}
			}
			return result;

		}
}
