package com.encgls.bigdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.encgls.bigdata.dao.SampleDAO;

@Controller
public class BigdataController {
	@Autowired
	SampleDAO sampleDAO;
	
	@GetMapping("/upload")
	public String upload() {
		
		return "upload";
	}
	
	@GetMapping("/dataSearch")
	public String dataSearch() {

		System.out.println("dataSearch page is opened.");
		return "dataSearch";
	}
	
	@PostMapping("/dataSearch")
	@ResponseBody
	public List<Map<String, String>> dataSearch(@RequestParam String company, @RequestParam String dataType, @RequestParam(value="fromDate", required=false) String fromDate, 
			@RequestParam(value="toDate", required=false) String toDate, @RequestParam String order, @RequestParam(value="searchTxt", required=false) String searchTxt, @RequestParam(value="now", required=false) String now) throws Exception {
		
		System.out.println("post Controller");
		
		// 선택 된 테이블명 지정.
		String tableName = null; 
		tableName = "tb_en_" + company + "_" + dataType + "_stats_s";
		System.out.println(tableName);
		
		// 테이블 별 날짜 검색 기준 컬럼
		String standardDate = null;
		
		// 테이블 별 날짜 검색 기준 컬럼 설정.
		if(!fromDate.equals(null) && !toDate.equals(null) && fromDate.length() > 0 && toDate.length() > 0) {
			switch(tableName) {
				case "tb_en_skx_wrhsn_stats_s": 
					standardDate = "wrhsn_cmple_dtm";
					break;
					
				case "tb_en_skx_dlivr_stats_s": 
					standardDate = "dlivr_cmple_dtm";
					break;
					
				case "tb_en_skx_rtgds_stats_s": 
					standardDate = "rtgds_wrhsn_acrsl_dt";
					break;
				
				case "tb_en_som_wrhsn_stats_s": 
					standardDate = "wrhsn_prrrg_dt";
					break;
					
				case "tb_en_som_dlivr_stats_s": 
					standardDate = "dlivr_prrrg_dtm";
					break;
					
				case "tb_en_som_rtgds_stats_s": 
					standardDate = "rtgds_wrhsn_acrsl_dtm";
					break;
					
				case "tb_en_com_wrhsn_stats_s": 
					standardDate = null;
					fromDate = null;
					toDate = null;
//					standardDate = "wrhsn_prrrg_dt";
					break;
					
				case "tb_en_com_dlivr_stats_s": 
					standardDate = null;
					fromDate = null;
					toDate = null;
//					standardDate = "dlivr_prrrg_dt";
					break;
					
				case "tb_en_com_rtgds_stats_s": 
					standardDate = null;
					fromDate = null;
					toDate = null;
//					standardDate = "rtgds_wrhsn_dt";
					break;
					
				case "tb_en_myw_wrhsn_stats_s": 
					standardDate = "wrhsn_dt";
					break;
					
				case "tb_en_myw_dlivr_stats_s": 
					standardDate = "dlivr_dt";
					break;
					
				case "tb_en_myw_rtgds_stats_s": 
					standardDate = "pcipt_dt";
					break;
					
				default: standardDate = null;
					break;
			}
		}
		
		// --------- paging start --------- 
		int nowPage = 1;
		int startNum = 1;
		if(now != null && now.length() > 0) { 
			 nowPage = Integer.parseInt(now); 
		}
		 
		// page 당 row 개수 int startNum = cntByPage*(nowPage-1)+1;
		int cntByPage = 100; 
		if(nowPage > 1) {
			startNum = cntByPage * (nowPage - 1) + 1;
		}
		
		Map<String, String> cntParam = new HashMap<String, String>();
		cntParam.put("table", tableName);
		cntParam.put("standardDate", standardDate);
		cntParam.put("fromDate", fromDate);
		cntParam.put("toDate", toDate);

		int cntRecords = sampleDAO.cntAllRecords(cntParam);
		
		 System.out.println(cntRecords);
		int lastPage = 1; 
		if(cntRecords > 0) {
			if(cntRecords % cntByPage == 0) {
				 lastPage = cntRecords / cntByPage;
			} else { 
				lastPage = cntRecords / cntByPage + 1; 
			} 
		}
		// --------- paging end. --------- 
		 List<Map<String, String>> tableInfo = sampleDAO.columnList(tableName);
		 List<String> callColumns = new ArrayList<String>();
		 
		 // select 쿼리 날리기 전, 데이터 타입별 컬럼 처리. (쿼리문 수정)
		 for(int i=0; i<tableInfo.size(); i++) {
			 Map<String, String> tmp = tableInfo.get(i);
			 System.out.println(tmp);
			 if(tmp.get("DATA_TYPE").trim().equals("TIMESTAMP") || tmp.get("DATA_TYPE").trim().equals("DATE") || tmp.get("DATA_TYPE").trim().equals("DATETIME")){
				 callColumns.add("if(" + tmp.get("COLUMN_NAME") + " is null, '', if(substr(date_format(" + tmp.get("COLUMN_NAME") + ", \'%Y-%m-%d %H:%i:%s\'),12,8) =\'00:00:00\', "
					+ "date_format(" + tmp.get("COLUMN_NAME") + ", \'%Y-%m-%d\'), date_format(" + tmp.get("COLUMN_NAME") + ", \'%Y-%m-%d %H:%i:%s\')))"
					+ " as " + tmp.get("COLUMN_NAME"));
			 }else if(tmp.get("DATA_TYPE").trim().equals("INT") || tmp.get("DATA_TYPE")=="INT"){
				 callColumns.add("if(" + tmp.get("COLUMN_NAME") + " is null, '', convert(" + tmp.get("COLUMN_NAME") + ", char)) as " + tmp.get("COLUMN_NAME"));
			 }else{
				 callColumns.add("if(" + tmp.get("COLUMN_NAME") + " is null, '', " + tmp.get("COLUMN_NAME") + ") as " + tmp.get("COLUMN_NAME"));
			 }
			 
		 }
		 System.out.println(callColumns);
	
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("column", callColumns);
		paramMap.put("tableName", tableName);
		paramMap.put("startNum", startNum);
		paramMap.put("cntByPage", cntByPage);
		paramMap.put("order", order);
		paramMap.put("standardDate", standardDate);
		paramMap.put("fromDate", fromDate);
		paramMap.put("toDate", toDate);
	
	
		List<Map<String, String>> table = sampleDAO.selectTable(paramMap);

		System.out.println(table.size());
		
		/*
		 * for(int i=0; i<table.size(); i++) { HashMap<String, String> tmp =
		 * (HashMap<String, String>) table.get(i); System.out.println(tmp); }
		 */
		 
		 
		/*
		 * Map<String, String> hashColumns = new HashMap<String, String>();
		 *  for(int i=0;i<columns.size(); i++) { 
		 *  	hashColumns.put(columns.get(i), columns.get(i));
		 *  } 
		 *  System.out.println(hashColumns);
		 *  table.add(0, hashColumns);
		 */
		
		Map<String, String> pagingInfo = new HashMap<String, String>();
		pagingInfo.put("nowPage", nowPage+"");
		pagingInfo.put("lastPage", lastPage+"");
		System.out.println("lastPage:" + lastPage);
		table.add(0, pagingInfo);
		
		return table;
	}
	
	@GetMapping("/uploadLog")
	public ModelAndView uploadLog() {
		ModelAndView mav = new ModelAndView();
		
		List<Map<String, String>> tables = sampleDAO.selectEncTableList();
		
//		System.out.println(tables);
		
		mav.addObject("tables", tables);
		mav.setViewName("/uploadLog");
		
		return mav;
	}
	
	@PostMapping("/uploadLog")
	@ResponseBody
	public List<Map<String, String>> uploadLog(@RequestParam(value="table",required=false) String table, @RequestParam(value="searchDate", required=false) String searchDate, 
			@RequestParam String order, @RequestParam(value="now", required=false) String now) throws Exception {
	
		System.out.println("post Controller");
		
		if(table == "null" || table.equals("all")) {
			table = null;
		}
		System.out.println("table:" + table);
		
		// for paging
		int nowPage = 1;
		int startNum = 1;
		if(now != null && now.length() > 0) { 
			 nowPage = Integer.parseInt(now); 
		}
		 
		// page 당 row 개수 int startNum = cntByPage*(nowPage-1)+1;
		int cntByPage = 100; 
		if(nowPage > 1) {
			startNum = cntByPage * (nowPage - 1) + 1;
		}
		 
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("table", table);
		paramMap.put("order", order);

		paramMap.put("startNum", startNum);
		paramMap.put("cntByPage", cntByPage);
		
		int cntRecords = sampleDAO.cntUploadLogRecords(paramMap);
		 System.out.println(cntRecords);
		int lastPage = 1; 
		if(cntRecords > 0) {
			if(cntRecords % cntByPage == 0) {
				 lastPage = cntRecords / cntByPage;
			} else { 
				lastPage = cntRecords / cntByPage + 1; 
			} 
		}
		
		List<Map<String, String>> contents = sampleDAO.selectLogTable(paramMap);

		System.out.println(contents.size());
		
		/*
		 * for(int i=0; i<table.size(); i++) { HashMap<String, String> tmp =
		 * (HashMap<String, String>) table.get(i); System.out.println(tmp); }
		 */
		 
		 
		/*
		 * Map<String, String> hashColumns = new HashMap<String, String>();
		 *  for(int i=0;i<columns.size(); i++) { 
		 *  	hashColumns.put(columns.get(i), columns.get(i));
		 *  } 
		 *  System.out.println(hashColumns);
		 *  table.add(0, hashColumns);
		 */
		
		Map<String, String> pagingInfo = new HashMap<String, String>();
		pagingInfo.put("nowPage", nowPage+"");
		pagingInfo.put("lastPage", lastPage+"");
		System.out.println("lastPage:" + lastPage);
		contents.add(0, pagingInfo);
		
		return contents;
	}
	
	@GetMapping("/tableSearch")
	public ModelAndView tableSearch() {
		ModelAndView mav = new ModelAndView();
		
		List<Map<String, String>> tables = sampleDAO.selectEncTableList();
		
//		for(int i=0; i<tables.size(); i++) { 
//		  HashMap<String, String> tmp = (HashMap<String, String>) tables.get(i); 
//		  System.out.println(tmp); 
//		}
		 
		mav.addObject("tables", tables);
		mav.setViewName("/tableSearch");
		
		return mav;
	}
	
	@PostMapping("/tableSearch")
	@ResponseBody
	public List<Map<String, String>> tableSearch(@RequestParam String table, @RequestParam(value="fromDate", required=false) String fromDate, 
			@RequestParam(value="toDate", required=false) String toDate, @RequestParam String order, @RequestParam(value="now", required=false) String now) throws Exception {
	
//		System.out.println("post Controller");
		
		// 테이블 별 날짜 검색 기준 컬럼
		String standardDate = null;
		
		// 테이블 별 날짜 검색 기준 컬럼 설정.
		if(!fromDate.equals(null) && !toDate.equals(null) && fromDate.length() > 0 && toDate.length() > 0) {
			switch(table) {
				case "enc_bigdata.tb_en_skx_wrhsn_stats_s": // 스케쳐스 입고
				case "open.tb_en_chrcr_wrhsn_conn_s": // EN_상품특성별입고량_S
					standardDate = "wrhsn_cmple_dtm";
					break;
					
				case "enc_bigdata.tb_en_skx_dlivr_stats_s": // 스케쳐스 출고
				case "open.tb_en_tpe_dlivr_s": // EN_출고유형별_B2B_B2C_출고수량_S
				case "open.tb_en_prcd_order_qntt_s": // EN_상품별주문량_S
				case "open.tb_en_prcd_order_conn_s": // EN_상품별주문건수_S
				case "open.tb_en_ara_order_qntt_s": // EN_지역별주문량_S
				case "open.tb_en_ara_order_conn_s": // EN_지역별주문건수_S
				case "open.tb_en_prcd_chrcr_order_s": // EN_상품특성별주문량_S
				case "open.tb_en_chrcr_order_conn_s": // EN_상품특성별주문건수_S
				case "open.tb_en_invnr_stats_s": // EN_일별재고현황_S
				case "open.tb_en_prcd_grad_i": // EN_상품ABC등급_I
					standardDate = "dlivr_cmple_dtm";
					break;
					
				case "enc_bigdata.tb_en_skx_rtgds_stats_s":  // 스케쳐스 반품
					standardDate = "rtgds_wrhsn_acrsl_dt";
					break;
				
				case "enc_bigdata.tb_en_som_wrhsn_stats_s":  // 쏨니아 입고
					standardDate = "wrhsn_prrrg_dt";
					break;
					
				case "enc_bigdata.tb_en_som_dlivr_stats_s": // 쏨니아 출고
				case "open.tb_en_ara_dlivr_s": // EN_지역별매장출고수량_S
					standardDate = "dlivr_prrrg_dtm";
					break;
					
				case "enc_bigdata.tb_en_som_rtgds_stats_s": // 쏨니아 반품
				case "open.tb_en_ara_rtgds_conn_s": // EN_지역별일별반품수량_S
				case "open.tb_en_rtgds_rsn_i": // EN_반품사유_I
				case "open.tb_en_prcd_rtgds_conn_s": // EN_상품군별반품수량_S
				case "open.tb_en_prcd_chrcr_rtgds_conn_s": // EN_상품특성별반품수량_S
					standardDate = "rtgds_wrhsn_acrsl_dtm";
					break;
					
				case "enc_bigdata.tb_en_com_wrhsn_stats_s": // 공동물류 입고
					standardDate = null;
					fromDate = null;
					toDate = null;
//					standardDate = "wrhsn_prrrg_dt";
					break;
					
				case "enc_bigdata.tb_en_com_dlivr_stats_s": // 공동물류 출고
					standardDate = null;
					fromDate = null;
					toDate = null;
//					standardDate = "dlivr_prrrg_dt";
					break;
					
				case "enc_bigdata.tb_en_com_rtgds_stats_s": // 공동물류 반품
					standardDate = null;
					fromDate = null;
					toDate = null;
//					standardDate = "rtgds_wrhsn_dt";
					break;
					
				case "enc_bigdata.tb_en_myw_wrhsn_stats_s": // 마이창고 입고
					standardDate = "wrhsn_dt";
					break;
					
				case "enc_bigdata.tb_en_myw_dlivr_stats_s": // 마이창고 출고
					standardDate = "dlivr_dt";
					break;
					
				case "enc_bigdata.tb_en_myw_rtgds_stats_s": // 마이창고 반품
					standardDate = "pcipt_dt";
					break;
				
				case "open.tb_en_mnb_otsrc_hmfrc_s": // 월별 도급 인력 사용량
					standardDate = "otsrc_dtm";
					break;
					
				default: standardDate = null;
					break;
			}
		}
		System.out.println("standardDate:" + standardDate);
		
		// for paging
		int nowPage = 1;
		int startNum = 1;
		if(now != null && now.length() > 0) { 
			 nowPage = Integer.parseInt(now); 
		}
		 
		// page 당 row 개수 int startNum = cntByPage*(nowPage-1)+1;
		int cntByPage = 100; 
		if(nowPage > 1) {
			startNum = cntByPage * (nowPage - 1) + 1;
		}
		 
		Map<String, String> cntParam = new HashMap<String, String>();
		cntParam.put("table", table);
		cntParam.put("standardDate", standardDate);
		cntParam.put("fromDate", fromDate);
		cntParam.put("toDate", toDate);
		
		int cntRecords = sampleDAO.cntAllRecords(cntParam);
		
		System.out.println(cntRecords);
		
		int lastPage = 1; 
		if(cntRecords > 0) {
			if(cntRecords % cntByPage == 0) {
				 lastPage = cntRecords / cntByPage;
			} else { 
				lastPage = cntRecords / cntByPage + 1; 
			} 
		}
		
		System.out.println("table:" + table);
		String originTable = "";
		if(table.contains("open")) {
			originTable=table.substring(5);
		} else if(table.contains("enc_bigdata")){
			originTable=table.substring(12);
		}
		
		List<Map<String, String>> tableInfo = sampleDAO.columnList(originTable);
		System.out.println(tableInfo);
		 
		List<String> callColumns = new ArrayList<String>();
		 
		for(int i=0; i<tableInfo.size(); i++) {
			 Map<String, String> tmp = tableInfo.get(i);
			 System.out.println("tmp.DATA_TYPE: " + tmp.get("DATA_TYPE"));
			 if(tmp.get("DATA_TYPE").trim().equals("TIMESTAMP") || tmp.get("DATA_TYPE").trim().equals("DATE") || tmp.get("DATA_TYPE").trim().equals("DATETIME")){
				 callColumns.add("if(" + tmp.get("COLUMN_NAME") + " is null, '', if(substr(date_format(" + tmp.get("COLUMN_NAME") + ", \'%Y-%m-%d %H:%i:%s\'),12,8) =\'00:00:00\', "
					+ "date_format(" + tmp.get("COLUMN_NAME") + ", \'%Y-%m-%d\'), date_format(" + tmp.get("COLUMN_NAME") + ", \'%Y-%m-%d %H:%i:%s\')))"
					+ " as " + tmp.get("COLUMN_NAME"));
			 }else if(tmp.get("DATA_TYPE").trim().equals("INT") || tmp.get("DATA_TYPE")=="INT"){
				 callColumns.add("if(" + tmp.get("COLUMN_NAME") + " is null, '', convert(" + tmp.get("COLUMN_NAME") + ", char)) as " + tmp.get("COLUMN_NAME"));
			 }else{
				 callColumns.add("if(" + tmp.get("COLUMN_NAME") + " is null, '', " + tmp.get("COLUMN_NAME") + ") as " + tmp.get("COLUMN_NAME"));
			 }
		}
		System.out.println(callColumns);
	
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("column", callColumns);
		paramMap.put("tableName", table);
		paramMap.put("startNum", startNum);
		paramMap.put("cntByPage", cntByPage);
		paramMap.put("standardDate", standardDate);
		paramMap.put("fromDate", fromDate);
		paramMap.put("toDate", toDate);
	
		List<Map<String, String>> contents = sampleDAO.selectTable(paramMap);

		System.out.println(contents.size());
		
		/*
		 * for(int i=0; i<table.size(); i++) { HashMap<String, String> tmp =
		 * (HashMap<String, String>) table.get(i); System.out.println(tmp); }
		 */
		 
		 
		/*
		 * Map<String, String> hashColumns = new HashMap<String, String>();
		 *  for(int i=0;i<columns.size(); i++) { 
		 *  	hashColumns.put(columns.get(i), columns.get(i));
		 *  } 
		 *  System.out.println(hashColumns);
		 *  table.add(0, hashColumns);
		 */
		
		Map<String, String> pagingInfo = new HashMap<String, String>();
		pagingInfo.put("nowPage", nowPage+"");
		pagingInfo.put("lastPage", lastPage+"");
		System.out.println("lastPage:" + lastPage);
		contents.add(0, pagingInfo);
		
		return contents;
	}
		
}
