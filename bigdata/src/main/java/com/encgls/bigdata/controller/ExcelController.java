package com.encgls.bigdata.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.encgls.bigdata.excelUp.ExcelRead;
import com.encgls.bigdata.excelUp.ExcelReadOption;
import com.encgls.bigdata.service.ExcelUp;

@Controller
public class ExcelController {
	
	@Autowired
    private ExcelUp sampleService;
	
	@GetMapping("/excel")
	public String main() {
		return "excel";
	}
	
	@ResponseBody
	@RequestMapping(value="/excel/read", method=RequestMethod.POST)
	public ModelAndView excelUploadAjax(MultipartHttpServletRequest request)throws Exception
	{
		
		MultipartFile excelFile = request.getFile("excelFile");
		
		if(excelFile==null || excelFile.isEmpty()) {
			throw new RuntimeException("엑셀파일을 선택해 주세요.");
		}
		
		File destFile = new File("C:\\Users\\Public\\Documents\\excelUp" + excelFile.getOriginalFilename());
		try {
			excelFile.transferTo(destFile);
		}catch(IllegalStateException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
		 //Service 단에서 가져온 코드 
			/*
			 * ExcelReadOption excelReadOption = new ExcelReadOption();
			 * excelReadOption.setFilePath(destFile.getAbsolutePath());
			 * excelReadOption.setOutputColumns("A","B","C","D","E","F");
			 * excelReadOption.setStarRow(2);
			 * 
			 * 
			 * List<Map<String, String>>excelContent =ExcelRead.read(excelReadOption);
			 * 
			 * for(Map<String, String> article: excelContent){
			 * System.out.println("A: "+article.get("A"));
			 * System.out.println("B: "+article.get("B"));
			 * System.out.println("C: "+article.get("C"));
			 * System.out.println("D :"+article.get("D"));
			 * System.out.println("E: "+article.get("E"));
			 * System.out.println("F: "+article.get("F")); }
			 */
        
        //userService.excelUpload(destFile); //서비스 부분을 삭제한다.
        
        //FileUtils.forceDelete(destFile.getAbsolutePath());
		
		sampleService.excelUpload(destFile,  excelFile.getOriginalFilename());
        
        destFile.delete();
        
        ModelAndView view = new ModelAndView();
        view.setViewName("/egoSampleList.do");
        return view;	
	}

}
