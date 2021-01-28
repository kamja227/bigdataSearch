<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" charset="UTF-8">
<title>bigdata project</title>
<style>
	.cover{
		position:absolute;
		top: 52px;
		width: 100%;
		z-index: 5;
		background-color: #EEEEEE;
		display: none;
	}
	.progress{
		position:absolute;
		top: 50%;
		left: 30%;
		width: 40%;
		display: none;
		z-index: 10;
	}
	.btn:hover{
		border: 1px solid black;
		font-weight: bold;
	}
	tr{
		font-size: 10;
		text-align: center;
		height: 15px;
		border: 1px solid white;
	}
	tr>th{
		font-size: 11;
		font-weight: bold;
		text-align: center;
		height: 15px;
		white-space: nowrap;
		border: 1px solid white;
		background-color: gray;
		
	}
	tr>td{
		font-size: 10;
		text-align: center;
		white-space: nowrap;
		height: 15px;
		border: 1px solid white;
	}
	table{
		font-size: 10;
		text-align: center;
		width: 100%;
		border: 1px solid light-gray;
	}
	#db_contents{
		text-align: center;
		overflow: scroll;
		display: none;
	}
	#linkPages {
		dislay: none;
		height: 20px;
	}
	#linkPages>a{
		margin: 10px;
	}
</style>
</head>
<body>
	<%@ include file="template/header.jspf" %>
	<div class="cover">
	</div>
	
	<div id="ex_header" class="contents" name="ex_header" style="margin:0px; padding: 0px;">
		<h2>데이터 업로드 내역 확인</h2>
	</div>

	<div id="search" class="contents" name="search">
		<form action="uploadLog" method="post" id="searchForm">
			<!-- <input type="date" class="searchForm_input" id="searchDate" name="searchDate" value="" />--><!-- default 오늘 날짜로 셋팅 -->
			<select id="table" class="searchForm_input" name="table">
				<option value="">테이블 선택</option>
				<option value="all">전체</option>
				<c:forEach items="${tables }" var="tables">
					<option value="${tables.metatableid }">${tables.metatablename }</option>
				</c:forEach>
			</select>
			
			<select id="order" class="searchForm_input" name="order">
				<option value="desc" selected>최신순</option>
				<option value="asc">오래된순</option>
			</select>
			<!-- <input type="text" class="searchForm_input" id="searchTxt" name="searchTxt" placeholder="특정 칼럼 추후에 결정." style="margin-right: 0px;"/> -->
			<input type="button" id="form_btn" class="searchForm_input btn btn-outline-secondary" value="검색" />
		</form>
	</div>
	
	<div id="db_contents" class="contents" name="db_contents" style="margin: 0px auto; width:95%; height: 520px;">
		<table class="table table-bordered db_table">
			<thead>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>

	<div class="progress">
		<div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width:100%">
		Loading
		</div>
	</div>
	
	<div id="linkPages" class="contents" name="linkPages">
	</div>
	
	<script>
	$(function(){
		$("#excel").removeClass("activeNav");
		$("#dataSearch").removeClass("activeNav");
		$("#tableSearch").removeClass("activeNav");
		$("#tableManage").removeClass("activeNav");
		$("#uploadLog").addClass("activeNav");
		
		var lastPage = 1;
		// 오늘 날짜로 설정
		var today = get_today_str();
		//$('#searchDate').val(today); 
		var f_height = $( window ).height();
		/* console.log('height:' + f_height); */

		$('.cover').css('height', f_height - 50);
			
	    $('#form_btn').on('click',function(){ 
		   
	       frmSubmit();
	       /* if(frmSubmit() == 0) console.log("submit finished"); */
	    });//click
	    
	});//ready
	
	 function get_today_str() { //get_today_str
		var date = new Date();
		var today = date.getFullYear() + "-" + ("0" + (date.getMonth()+1)).slice(-2) + "-" + ("0" + date.getDate()).slice(-2);
		console.log(today);
		return today;
	} //get_today_str end 

	function frmSubmit() { //frmSubmit

		  var nowPage = 1;
	      var thead;
	      var tbody;
	      var paging = "";
	      
	      var table = $("select[name=table]").val(); // not null
	      // var searchDate = $('#searchDate').val(); // null
	      var order = $("select[name=order]").val(); // not null
	      
	      if(table == '' || table.length <= 0) {
	         swal("검색 실패", "테이블을 선택해주세요.", "warning");
	         return false;
		  }else {
	         var url = "${pageContext.request.contextPath }/uploadLog";
	         var params = "?table=" + table + "&order=" + order;
	         // var params = "?table=" + table + "&searchDate=" + searchDate + "&order=" + order;
	         /* var params = {"company": company, "dataType": dataType, "searchDate": searchDate, "searchTxt": searchTxt}; */
	         /* ******* test ******* */
	         console.log(url);
	         console.log(params);
	         /* ******* test ******* */
	             $.ajax({
	                 type: "POST",
	                 url: url + params,
	                 success : function(data) {
	                     /* console.log('success'); */
	                     var tmp = data;
	                     var size = Object.keys(tmp).length;
	                     console.log(size);
	                     $('#db_contents').css('display', 'none'); 
	                     $('.cover').css('display', 'block')
	                     $('.progress').css('display', 'block');
	                  $('.progress-bar').addClass('active');
	                  $('#db_contents').css('border', '0'); 
	                     thead = '<tr><th>번호</th>';
	                     $.each(data, function (i, item) {
				                if(i >= 1){
				                	tbody += '<tr><td>'+ ((nowPage-1)*100+i) +'</td>';
		                         	var per = i * 100 / size;
		                          /*   progressBarCall(Math.round(per)); */
					            }
	                           $.each(item, function(key, value){
		                          if(i == 0 && key == "nowPage"){
			                          nowPage = value;
			                      }
			                      if(i == 0 && key == "lastPage") {
				                      lastPage = value;
				                  }
	                              if(i == 1) {
		                              thead += '<th>' + key + '</th>';
		                          }
	                              if(i >= 1) {
		                              tbody += '<td>' + value +'</td>';
	                              }
	                              /*  console.log('key:' + key + ' / ' + 'value:' + value); */
	                           });
	                           tbody += '</tr>';
	                         }); 
	                           thead += '</tr>';
	                           $('.table').empty().append('<thead></thad><tbody></tbody>');
	                           $('thead').append(thead);
	                           if(tbody=='undefined</tr>' || tbody=='') tbody='검색 결과가 없습니다.';
	                           $('tbody').append(tbody);
	                           $('#db_contents').css('display', 'block'); 
	                          console.log('ajax end');
	                           $('.progress-bar').removeClass('active');
	                           $('.progress').css('display', 'none');
	                           $('.cover').css('display', 'none');
	
	                           if(nowPage > 5) { 
		                           if(nowPage % 5 == 0) { paging += "<a href='javascript:void(0);' onClick='postPageMove(" + (nowPage - 5) + ");'>Prev</a>"; }
		                           else { paging += "<a href='javascript:void(0);' onClick='postPageMove(" + 5*(Math.floor(nowPage / 5)-1) + ");'>Prev</a>"; }
	                 		   }
	                           if(nowPage % 5 != 0) {
	                        	 var page1 = Math.floor(nowPage / 5)*5 + 1;
				                 var page2 = Math.floor(nowPage / 5)*5 + 2;
				                 var page3 = Math.floor(nowPage / 5)*5 + 3;
				                 var page4 = Math.floor(nowPage / 5)*5 + 4;
				                 var page5 = Math.floor(nowPage / 5)*5 + 5;
				                 var page6 = parseInt(page5, 10) + parseInt('1', 10)
		                       } else {
		                    	   var page1 = nowPage - 4;
					               var page2 = nowPage - 3;
					               var page3 = nowPage - 2;
					               var page4 = nowPage - 1;
					               var page5 = nowPage - 0;
					               var page6 = parseInt(page5, 10) + parseInt('1',10)
			                   }

								console.log("page6:" + page6);
	                           if(page1 <= lastPage) { paging += "<a href='javascript:void(0);' onClick='postPageMove(" + page1 + ");'>" + page1 + "</a>"; }
	                           if(page2 <= lastPage) { paging += "<a href='javascript:void(0);' onClick='postPageMove(" + page2 + ");'>" + page2 + "</a>"; }
	                           if(page3 <= lastPage) { paging += "<a href='javascript:void(0);' onClick='postPageMove(" + page3 + ");'>" + page3 + "</a>"; }
	                           if(page4 <= lastPage) { paging += "<a href='javascript:void(0);' onClick='postPageMove(" + page4 + ");'>" + page4 + "</a>"; }
	                           if(page5 <= lastPage) { paging += "<a href='javascript:void(0);' onClick='postPageMove(" + page5 + ");'>" + page5 + "</a>"; }

	                           if(page6 <= lastPage) { paging += "<a href='javascript:void(0);' onClick='postPageMove(" + page6 + ");'>Next</a>"; }
	                           paging += "<input type='number' name='movePage' id='movePage' style='width: 60px; margin-right:10px;' placeholder='" + lastPage + "' />";
	                           paging += "<button role='btn' type='button' style='width:50px;' onClick='chk("+ $("#movePage").val() + ");'>이동</button>";
								
		                       console.log(paging);
							   $("#linkPages").empty().append(paging);
							   $("#linkPages").css('display', 'block');
							  
	                           console.log("nowPage:" + nowPage);
	                           console.log("lastPage:" + lastPage);
	                    },//success end
	                  error:function(){
	                    swal('검색 실패', '통신 장애','warning'); 
	                  }//error end
	               });//ajax end
	      	}// else end
	   }// frmSubmit end


	function postPageMove(nowPage) {
		var nowPage = nowPage;
		if(nowPage == null || nowPage == '' || nowPage.length == 0) {
			nowPage = 1;
		} else {
			nowPage = nowPage;
		}
		
      	var thead;
      	var tbody;
      	var paging = "";
      	// var searchDate = $('#searchDate').val(); // null
      	var table = $("select[name=table]").val(); // not null
      	var order = $("select[name=order]").val(); // not null

	      if(table == '' || table.lengh <= 0) {
	         swal("검색 실패", "테이블을 선택해주세요.", "warning");
	         return false;
		  }else {
	         var url = "${pageContext.request.contextPath }/transHistory";
	         var params = "?table=" + table + "&order=" + order + "&now=" + nowPage;
	         // var params = "?table=" + table + "&searchDate=" + searchDate + "&order=" + order + "&now=" + nowPage;
	     
	         /* var params = {"company": company, "dataType": dataType, "searchDate": searchDate, "searchTxt": searchTxt}; */
	         /* ******* test ******* */
	         console.log(url);
	           console.log(params);
	         /* ******* test ******* */
	             $.ajax({
	                 type: "POST",
	                 url: url + params,
	                  success : function(data) {
	                     /* console.log('success'); */
	                     var tmp = data;
	                     var size = Object.keys(tmp).length;
	                     console.log(size);
	                     $('#db_contents').css('display', 'none'); 
	                     $('.cover').css('display', 'block')
	                     $('.progress').css('display', 'block');
	                  $('.progress-bar').addClass('active');
	                  $('#db_contents').css('border', '0'); 
	                     thead = '<tr><th>번호</th>';
	                        $.each(data, function (i, item) {
				                if(i >= 1){
				                	tbody += '<tr><td>'+ ((nowPage-1)*100+i) +'</td>';
		                         	var per = i * 100 / size;
		                            /* progressBarCall(Math.round(per)); */
					            }
	                           $.each(item, function(key, value){
		                          if(i == 0 && key == "nowPage"){
			                          nowPage = value;
			                      }
			                      if(i == 0 && key == "lastPage") {
				                      lastPage = value;
				                  }
	                              if(i == 1) {
		                              thead += '<th>' + key + '</th>';
		                          }
	                              if(i >= 1) {
		                              tbody += '<td>' + value +'</td>';
	                              }
	                              /*  console.log('key:' + key + ' / ' + 'value:' + value); */
	                           });
	                           tbody += '</tr>';
	                         }); 
	                           thead += '</tr>';
	                           $('.table').empty().append('<thead></thad><tbody></tbody>');
	                           $('thead').append(thead);
	                           if(tbody=='undefined</tr>' || tbody=='') tbody='검색 결과가 없습니다.';
	                           $('tbody').append(tbody);
	                           $('#db_contents').css('display', 'block'); 
	                           console.log('ajax end');
	                           $('.progress-bar').removeClass('active');
	                           $('.progress').css('display', 'none');
	                           $('.cover').css('display', 'none');
	                           
	                           if(nowPage > 5) { 
		                           if(nowPage % 5 == 0) { paging += "<a href='javascript:void(0);' onClick='postPageMove(" + (nowPage - 5) + ");'>Prev</a>"; }
		                           else { paging += "<a href='javascript:void(0);' onClick='postPageMove(" + 5*(Math.floor(nowPage / 5)-1) + ");'>Prev</a>"; }
	                 		   }
								
	                           if(nowPage % 5 != 0) {
		                        	 var page1 = Math.floor(nowPage / 5)*5 + 1;
					                 var page2 = Math.floor(nowPage / 5)*5 + 2;
					                 var page3 = Math.floor(nowPage / 5)*5 + 3;
					                 var page4 = Math.floor(nowPage / 5)*5 + 4;
					                 var page5 = Math.floor(nowPage / 5)*5 + 5;
					                 var page6 = parseInt(page5,10) + parseInt(1,10)
		                       } else {
		                    	   var page1 = nowPage - 4;
					               var page2 = nowPage - 3;
					               var page3 = nowPage - 2;
					               var page4 = nowPage - 1;
					               var page5 = nowPage - 0;
					               var page6 = parseInt(page5,10) + parseInt(1,10)
			                   }
	                           console.log("page1:" +page1);
	                           console.log("page2:" +page2);
	                           console.log("page3:" +page3);
	                           console.log("page4:" +page4);
	                           console.log("page5:" +page5);
	                           console.log("page6:" + page6);
	                           if(page1 <= lastPage) { paging += "<a href='javascript:void(0);' onClick='postPageMove(" + page1 + ");'>" + page1 + "</a>"; }
	                           if(page2 <= lastPage) { paging += "<a href='javascript:void(0);' onClick='postPageMove(" + page2 + ");'>" + page2 + "</a>"; }
	                           if(page3 <= lastPage) { paging += "<a href='javascript:void(0);' onClick='postPageMove(" + page3 + ");'>" + page3 + "</a>"; }
	                           if(page4 <= lastPage) { paging += "<a href='javascript:void(0);' onClick='postPageMove(" + page4 + ");'>" + page4 + "</a>"; }
	                           if(page5 <= lastPage) { paging += "<a href='javascript:void(0);' onClick='postPageMove(" + page5 + ");'>" + page5 + "</a>"; }

	                           if(page6 <= lastPage) { paging += "<a href='javascript:void(0);' onClick='postPageMove(" + page6 + ");'>Next</a>"; }
							   paging += "<input type='number' name='movePage' id='movePage' style='width:60px; margin-right:10px;' placeholder='" + lastPage + "' />";
							   paging += "<button role='btn' type='button' style='width:50px;' onClick='chk("+ $("#movePage").val() + ");'>이동</button>";
							   $("#linkPages").empty().append(paging);
							   $('#linkPages').css('display', 'block');
							  
	                           console.log("nowPage:" + nowPage);
	                           console.log("lastPage:" + lastPage);
	                    },//success end
	                  error:function(){
	                     console.log('error'); 
	                    /*  swal('검색 실패', '통신 장애','warning'); */
	                  }//error end
	               });//ajax end
      		}// else end
	}
	
	function progressBarCall(per) {
		console.log(per); 
		
		$('.progress-bar').attr('aria-value',per);
		$('.progress-bar').css('width', per + '%');
		$('.progress-bar').text(per + '%');
	} 
	
	function chk(){
		var page = $('#movePage').val();
		console.log(page);
		if(page > lastPage) {
			swal('페이지 범위 초과', '페이지가 존재하지 않습니다.', 'warning');
			return false;
		} else if(page <= 0) {
			swal('페이지 범위 초과', '페이지가 존재하지 않습니다.', 'warning');
			return false;
		} else {
			console.log('postPageMove call #' + parseInt(page, 10));
			postPageMove(page);
		}
	}
</script>
</body>
</html>