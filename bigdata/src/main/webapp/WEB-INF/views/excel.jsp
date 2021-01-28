<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>bigdata project</title>
  <style type="text/css">
		.input-group{
			display: inline-block;
			text-align: center;
			margin: 0px auto;
			vertical-align: middle;
			width: 30%;
		}
		.btn:hover{
			border: 1px solid black;
			font-weight: bold;
		}
	
	
         .box{
          width:600px;
          margin:0 auto;
          border:1px solid #ccc;
         }
</style>

</head>
<body>
	 <%@ include file="template/header.jspf" %>
	 <div id="ex_header" class="contents" name="ex_header">
		<h2>엑셀 파일 업로드</h2>
	</div>
	
    <form id="excelUploadForm" name="excelUploadForm" action="excel/read"  method = "POST"  enctype="multipart/form-data" >
      <div class = "input-group">
         <div class="contents">
         	첨부파일은 한번에 한 개씩만 업로드 가능합니다.
         </div>
         <input type="file" class="form-control" id="excelFile" name="excelFile" aria-describedby="inputGroupFileAddon04" aria-label="Upload" style="width: 80%;">
  			<button class="btn btn-outline-secondary" type="submit" id="addExcelImpoarBtn" style="width: 20%;" onclick="check()">업로드</button>
         <!-- <dl class="vm_name">
            <dt class="down w90">첨부파일</dt>
            <dd><input id="excelFile" type="file" name="excelFile"></dd>
         </dl> -->
      </div>
    <!--   <div class = "bottom">
         <button type="button" id="addExcelImpoarBtn" class="btn" onclick="check()"><span>추가</span></button>
      </div> -->
   </form>
   
<script src="http://malsup.github.com/jquery.form.js"></script>
<script type="text/javascript">
	$(function(){
		$("#excel").addClass("activeNav");
		$("#dataSearch").removeClass("activeNav");
		$("#tableSearch").removeClass("activeNav");
		$("#tableManage").removeClass("activeNav");
		$("#uploadLog").removeClass("activeNav");
	});
	
   function checkFileType(filePath){
      var fileFormat = filePath.split(".");

      if(fileFormat.indexOf("xls") > -1 || fileFormat.indexOf("xlsx") > -1){
         return true;
         }
      else{
         return false;
         }   
      }

   function check()
   {
      var file = $("#excelFile").val();

      if(file == "" || file == null){
            alert("파일을 선택해 주세요.");
            return false;
         }else if(!checkFileType(file)){
            alert("엑셀 파일만 업로드 가능합니다.");
            return false;
         }

      if(confirm("업로드 하시겠습니까?")){
            var options = {
                  success:function(data){
                     console.log(data);
                     alert("모든 데이터가 업로드 되었습니다");
                     },
                     type: "POST"
                  };
            $("#excelUploadForm").ajaxSubmit(options);      
         }
      }
</script>
</body>
</html>