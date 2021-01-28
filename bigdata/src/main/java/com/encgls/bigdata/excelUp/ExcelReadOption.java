package com.encgls.bigdata.excelUp;

import java.util.ArrayList;
import java.util.List;

public class ExcelReadOption {
	
	//엑셀파일의 경로
	private String filePath;
	
	//추출할 컬럼 명
	private List<String> outputColumns;
	
	private int starRow;
	
	public String getFilePath() {
		return filePath;
	}

	public List<String> getOutputColumns() {
		
		List<String> temp = new ArrayList<String>();
		temp.addAll(outputColumns);
		return temp;
	}

	public void setOutputColumns(List<String> outputColumns) {
		List<String> temp= new ArrayList<String>();
		temp.addAll(outputColumns);
		
		this.outputColumns = temp;
	}
	
	 public void setOutputColumns(String ... outputColumns) {
	        
	        if(this.outputColumns == null) {
	            this.outputColumns = new ArrayList<String>();
	        }
	        
	        for(String ouputColumn : outputColumns) {
	            this.outputColumns.add(ouputColumn);
	        }
	    }

	public int getStarRow() {
		return starRow;
	}

	public void setStarRow(int starRow) {
		this.starRow = starRow;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
