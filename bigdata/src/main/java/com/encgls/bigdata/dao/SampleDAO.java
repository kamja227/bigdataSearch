package com.encgls.bigdata.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SampleDAO {
	
	// 테이블 명으로 전체 컬럼 가져오기 
	public List<Map<String, String>> columnList(String tableName)throws Exception;
	// 테이블명, 칼럼명으로 칼럼 타입 가져오기
	public List<Map<String, String>> getTableInfo(Map<String, Object> paramMap);
	// 검색 시, 테이블 전체 데이터
	public List<Map<String, String>> selectTable(Map<String, Object> paramMap);
	// 검색 시, 업로드 로그 테이블 전체 데이터
	public List<Map<String, String>> selectLogTable(Map<String, Object> paramMap);
	// 검색 시, (원천 데이터) 테이블 전체 row count
	public int cntAllRecords(Map<String, String> cntParam); // 페이징 위한 칼럼 총 개수
	// 검색 시, 업로드 로그 테이블 전체 row count
	public int cntUploadLogRecords(Map<String, Object> paramMap); // 페이징 위한 칼럼 총 개수
	
	
	// 원천데이터 테이블 list, 데이터셋 테이블 list
	public List<Map<String, String>> selectEncTableList(); 
	
	//스케쳐스입고S
	public void insertSkxInwS(Map<String,Object> paramMap)throws Exception;
	//스케쳐스입고B
	public void insertSkxInwB(Map<String,Object> paramMap)throws Exception;
	//스케쳐스출고S
	public void insertSkxExwS(Map<String,Object> paramMap)throws Exception;
	//스케쳐스출고B
	public void insertSkxExwB(Map<String,Object> paramMap)throws Exception;
	//스케쳐스반품S
	public void insertSkxRtnS(Map<String,Object> paramMap)throws Exception;
	//스케쳐스반품B
	public void insertSkxRtnB(Map<String,Object> paramMap)throws Exception;
	//쏨니아출고
	public void insertSomExw(Map<String,Object> paramMap)throws Exception;
	//쏨니아입고
	public void insertSomInw(Map<String,Object> paramMap)throws Exception;
	//쏨니아반품
	public void insertSomRtn(Map<String,Object> paramMap)throws Exception;
	//공통물류출고
	public void insertLogisExw(Map<String,Object> paramMap)throws Exception;
	//공통물류입고
	public void insertLogisInw(Map<String,Object> paramMap)throws Exception;
	//공통물류반품
	public void insertLogisRtn(Map<String,Object> paramMap)throws Exception;
	//마이창고반품
	public void insertWareRtn(Map<String,Object> paramMap)throws Exception;
	//마이창고출고
	public void insertWareExw(Map<String,Object> paramMap)throws Exception;
	//마이창고입고
	public void insertWareInw(Map<String,Object> paramMap)throws Exception;
	
}
