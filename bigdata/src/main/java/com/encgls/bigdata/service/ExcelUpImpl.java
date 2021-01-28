package com.encgls.bigdata.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.encgls.bigdata.dao.SampleDAO;
import com.encgls.bigdata.excelUp.ExcelRead;
import com.encgls.bigdata.excelUp.ExcelReadOption;

@Service
public class ExcelUpImpl implements ExcelUp{
	
	@Autowired
	SampleDAO sampleDAO;

	@Override
    public void excelUpload(File destFile , String originalFile) throws Exception {
        ExcelReadOption excelReadOption = new ExcelReadOption();
        excelReadOption.setFilePath(destFile.getAbsolutePath());
        if(originalFile.contains("���Ͼƹ�ǰ")) {
        	excelReadOption.setOutputColumns("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
        			"AA","AB","AC","AD","AE","AF","AG","AH","AI","AJ","AK","AL","AM","AN","AO","AP","AQ","AR","AS","AT");
        }
        else if(originalFile.contains("���Ͼ��԰�")) {
        	excelReadOption.setOutputColumns("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
        			"AA","AB","AC","AD","AE","AF","AG","AH","AI","AJ","AK","AL");
        }
        else if(originalFile.contains("���Ͼ����")) {
        	excelReadOption.setOutputColumns("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
        			"AA","AB","AC","AD","AE","AF","AG","AH","AI","AJ","AK","AL","AM","AN","AO","AP","AQ","AR","AS","AT","AU","AV","AW","AX","AY","AZ",
        			"BA","BB", "BC","BD","BE","BF","BG","BH","BI","BJ","BK","BL","BM","BN","BO","BP","BQ","BR","BS","BT","BU","BV","BW","BX","BY","BZ",
        			"CA","CB","CC");
        }
        else if(originalFile.contains("�����Ľ��԰�S")) {
        	excelReadOption.setOutputColumns("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
        			"AA","AB","AC","AD");
        }
        else if(originalFile.contains("�����Ľ��԰�B")) {
        	excelReadOption.setOutputColumns("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
        			"AA","AB","AC","AD");
        }
        else if(originalFile.contains("�����Ľ����S")) {
        	excelReadOption.setOutputColumns("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
        			"AA","AB","AC","AD","AE","AF","AG","AH","AI","AJ","AK","AL","AM","AN","AO","AP","AQ","AR","AS","AT","AU","AV","AW","AX","AY","AZ",
        			"BA","BB", "BC","BD","BE","BF","BG","BH","BI","BJ","BK","BL","BM","BN","BO","BP","BQ","BR","BS","BT","BU","BV","BW","BX","BY","BZ",
        			"CA","CB","CC","CD","CE","CF","CG","CH","CI","CJ","CK","CL","CM","CN","CO","CP");
        }
        else if(originalFile.contains("�����Ľ����B")) {
        	excelReadOption.setOutputColumns("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
        			"AA","AB","AC","AD","AE","AF","AG","AH","AI","AJ","AK","AL","AM","AN","AO","AP","AQ","AR","AS","AT","AU","AV","AW","AX","AY","AZ",
        			"BA","BB", "BC","BD","BE","BF","BG","BH","BI","BJ","BK","BL","BM","BN","BO","BP","BQ","BR","BS","BT","BU","BV","BW","BX","BY","BZ",
        			"CA","CB","CC","CD","CE","CF","CG","CH","CI","CJ","CK","CL","CM","CN","CO","CP");
        }
        else if(originalFile.contains("�����Ľ���ǰ")) {
        	excelReadOption.setOutputColumns("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
        			"AA","AB","AC","AD","AE","AF","AG","AH","AI","AJ","AK","AL","AM","AN","AO","AP","AQ","AR","AS","AT","AU","AV","AW","AX");
        }
        else if(originalFile.contains("�����������")) {
        	excelReadOption.setOutputColumns("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
        			"AA","AB","AC","AD","AE","AF","AG","AH","AI","AJ","AK","AL","AM","AN","AO","AP","AQ","AR","AS","AT","AU","AV","AW","AX","AY","AZ",
        			"BA","BB", "BC","BD","BE","BF","BG","BH","BI","BJ","BK","BL","BM","BN","BO","BP","BQ","BR","BS","BT","BU","BV","BW","BX","BY","BZ",
        			"CA","CB","CC","CD","CE","CF","CG","CH","CI","CJ","CK","CL","CM","CN","CO","CP","CQ","CR","CS","CT","CU","CV","CW","CX","CY","CZ",
        			"DA","DB","DC","DD","DE","DF","DG","DH","DI","DJ","DK","DL","DM","DN","DO","DP","DQ","DR","DS","DT","DU","DV","DW","DX","DY","DZ",
        			"EA","EB","EC",",ED","EE","EF","EG","EH","EI","EJ","EK","EL","EM","EN","EO","EP","EQ","ER","ES","ET","EU","EV");
        }
        else if(originalFile.contains("����������ǰ")) {
        	excelReadOption.setOutputColumns("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
        			"AA","AB","AC","AD","AE","AF","AG","AH","AI","AJ","AK","AL","AM","AN","AO","AP","AQ","AR","AS","AT","AU","AV","AW","AX","AY","AZ",
        			"BA","BB", "BC","BD","BE","BF","BG","BH","BI","BJ","BK","BL","BM","BN","BO","BP","BQ","BR","BS","BT","BU","BV","BW","BX","BY","BZ",
        			"CA","CB","CC","CD","CE","CF","CG","CH","CI","CJ");
        }
        else if(originalFile.contains("���������԰�")) {
        	excelReadOption.setOutputColumns("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
        			"AA","AB","AC","AD","AE","AF","AG","AH","AI","AJ","AK","AL","AM","AN","AO","AP","AQ","AR","AS","AT","AU","AV","AW","AX","AY","AZ",
        			"BA","BB", "BC","BD","BE","BF","BG","BH","BI","BJ","BK","BL","BM","BN","BO","BP","BQ","BR","BS","BT","BU","BV","BW","BX","BY","BZ",
        			"CA","CB","CC","CD","CE","CF","CG","CH","CI","CJ","CK","CL","CM","CN","CO","CP","CQ");
        }
        else if(originalFile.contains("����â��_��ǰ")) {
        	excelReadOption.setOutputColumns("A","B","C","D","E","F","G","H","I","J","K","L");
        }
        else if(originalFile.contains("����â��_�԰�")) {
        	excelReadOption.setOutputColumns("A","B","C","D","E","F","G","H");
        }
        else if(originalFile.contains("����â��_���")) {
        	excelReadOption.setOutputColumns("A","B","C","D","E","F","G");
        }
        
        excelReadOption.setStarRow(2);
        
        
        List<Map<String, String>>excelContent =ExcelRead.read(excelReadOption);
  
        
		/*
		 * for(Map<String,String> article : excelContent) { System.out.println("A: "+
		 * article.get("A")); System.out.println("B: "+ article.get("B"));
		 * System.out.println("C: "+ article.get("C")); System.out.println("D: "+
		 * article.get("D")); System.out.println("E: "+ article.get("E"));
		 * System.out.println("F: "+ article.get("F")); }
		 */
        
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("excelContent", excelContent);
        
        
       try {
    	   if(originalFile.contains("�����Ľ��԰�S")) {
    		   sampleDAO.insertSkxInwS(paramMap);
    	   }
    	   else if(originalFile.contains("�����Ľ��԰�B")) {
    		   sampleDAO.insertSkxInwB(paramMap);
    	   }
    	   else if(originalFile.contains("�����Ľ����S")) {
    		   sampleDAO.insertSkxExwS(paramMap);
    	   }
    	   else if(originalFile.contains("�����Ľ����B")) {
    		   sampleDAO.insertSkxExwB(paramMap);
    	   }
    	   else if(originalFile.contains("�����Ľ���ǰS")) {
    		   sampleDAO.insertSkxRtnS(paramMap);
    	   }
    	   else if(originalFile.contains("�����Ľ���ǰB")) {
    		   sampleDAO.insertSkxRtnB(paramMap);
    	   }
    	   else if(originalFile.contains("���Ͼ����")) {
    		   sampleDAO.insertSomExw(paramMap);
    	   }
    	   else if(originalFile.contains("���Ͼ��԰�")) {
    		   sampleDAO.insertSomInw(paramMap);
    	   }
    	   else if(originalFile.contains("���Ͼƹ�ǰ")) {
    		   sampleDAO.insertSomRtn(paramMap);
    	   }
    	   else if(originalFile.contains("��������_���")) {
    		   sampleDAO.insertLogisExw(paramMap);
    	   }
    	   else if(originalFile.contains("��������_�԰�")) {
    		   sampleDAO.insertLogisInw(paramMap);
    	   }
    	   else if(originalFile.contains("��������_��ǰ")) {
    		   sampleDAO.insertLogisRtn(paramMap);
    	   }
    	   else if(originalFile.contains("����â��_��ǰ")) {
    		   sampleDAO.insertWareRtn(paramMap);
    	   }
    	   else if(originalFile.contains("����â��_���")) {
    		   sampleDAO.insertWareExw(paramMap);
    	   }
    	   else if(originalFile.contains("����â��_�԰�")) {
    		   sampleDAO.insertWareInw(paramMap);
    	   }
    	   
       }catch(Exception e) {
    	   e.printStackTrace();
       }
	}
}
