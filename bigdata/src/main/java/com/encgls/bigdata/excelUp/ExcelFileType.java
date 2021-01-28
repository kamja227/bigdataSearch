package com.encgls.bigdata.excelUp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileType {
	
	public static Workbook getWorkbook(String filePath) throws Exception {
		
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
		}catch(FileSystemNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
		Workbook wb = null;
		
		if(filePath.toUpperCase().endsWith(".XLS")) {
			try {
				wb = new HSSFWorkbook(fis);
			}catch ( IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		else if(filePath.toUpperCase().endsWith(".XLSX")) {
			try {
				wb = new XSSFWorkbook(fis);
			}catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		
		return wb;
		
	}

}
