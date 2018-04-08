<%@ page language="java" import="java.util.*,Entity.*,util.Pagination" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>成绩管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			var ye=${p.ye};
			var maxYe=${p.maxYe};
			var selectId=0;
			function showMes(mes){
				$("#mes").html(mes);
				setTimeout(function(){
					$("#mes").html("");
				},1000);
			}
			var stuName="";
			var bj=-1;
			var sub=-1;
			var bj=-1;
			if($("[name=stuName]").val()!=""){
				stuName=$("[name=stuName]").val();
			}if($("[name=bj]").val()!=""){
				bj=$("[name=bj]").val();
			}if($("[name=sub]").val()!=""){
				sub=$("[name=sub]").val();
			}
			function clickYe(selectYe){
				window.location.href="sc?type=search&ye="+selectYe+"&stuName="+stuName+"&bj="+bj+"&sub="+sub;
			}
			$("#shou").click(function(){
				clickYe(1);
			})
			$("#wei").click(function(){
				clickYe(maxYe);
			})
			$("#pre").click(function(){
				if(ye>1){
					clickYe(ye-1);
				}else{
					showMes("已经是第一页了");
					$(this).attr("style","cursor:not-allowed");
				}
			})
			$("#next").click(function(){
				if(ye<maxYe){
					clickYe(ye+1);
				}else{
					showMes("已经是最后一页了");
					$(this).attr("style","cursor:not-allowed");
				}
			})
			$("[name=namePage]").click(function(){
				clickYe($(this).html());
			})
			$("tbody tr").click(function(){
				selectId=$(this).data("id");
				$("tbody tr").css("background","none");
				$(this).css("background","#337ab7");
			})
			$("#bj").change(function(){
					$.ajax({
						url:"sub?type=searchSubByBj",
						data:{bjId:$(this).val()},
						type:"post",
						dataType:"json",
						success:function(data){
							var ops="<option  value='-1'>请选择科目</option>";
							$.each(data,function(index,element){
								ops+="<option value="+element.id+">"+element.name+"</option>";
							})
							$("#sub").html(ops);
						}
					})
			})
		})
	</script>
	<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css">
	<style>
		*{
			margin:0;
			padding:0;
		}
		body{
			-moz-user-select: none;
			-khtml-user-select: none;
			user-select: none;
		}
		#main{
			width:600px;
			margin:10px auto;
		}
		#search{
			margin-bottom:20px;
			overflow:hidden;
		}
		#search span{
			float:left;
			line-height:30px;
		}
		#search input{
			float:left;
			width:120px;
			height:30px;
			margin-right:8px;
		}
		#search select{
			float:left;
			width:120px;
			height:30px;
			margin-right:8px;
			font-size: 14px;
			line-height: 30px;
			color: #555;
			background-color: #fff;
			background-image: none;
			border: 1px solid #ccc;
			border-radius: 4px;
		}
		#search #sex{
			width:100px;
		}
		#search #age{
			width:60px;
		}
		#search #searchBtn{
			width:80px;
			float:right;
		}
		.btn-group{
			float:left;
		}
		.fenye {
			float: right;
		}
		
		.fenye>li {
			list-style: none;
			float: left;
			width: 35px;
			height: 35px;
			text-align: center;
			line-height: 35px;
			border: 1px solid #ddd;
		}
		.fenye>li:hover{
			background-color:#eee;
			cursor:pointer;
		}
		#pre,#next {
			width: 60px;
		}
		#shou {
			border-top-left-radius: 4px;
			border-bottom-left-radius: 4px;
		}
		
		#wei {
			border-top-right-radius: 4px;
			border-bottom-right-radius: 4px;
		}
		
		.fenye>.active {
			color:#fff;
			background-color: #337ab7;
			border-color:#337ab7;
		}
		.fenye>.active:hover {
			color:#fff;	
			background-color: #337ab7;
			border-color:#337ab7;
			cursor:default;
		}
		#mes{
			float:right;
			width:200px;
			height:35px;
			line-height:40px;
			color:red;
			letter-spacing:2px;
/* 			border:1px solid red; */
		}
		.clearfix{
			clear:both;
		}
	</style>
  </head>
  	
  <body>
  <div id="main">
  	<%List<Student> list=(List<Student>)request.getAttribute("stus"); %>
  	<div id="search">
	  	<form action="sc" method="post">
	  		<input type="hidden" name="type" value="search"/>
	  		<span>姓名：</span>
	  		<input type="text" name="stuName" class="form-control" value="${condition.stu.name}">
	  		<span>班级：</span>
	  		<select name="bj">
		  		<option  value="">请选择班级</option>
		  		<c:forEach var="bj" items="${bjs}">
		  			<option value="${bj.id}" <c:if test="${condition.stu.banji.id==bj.id }">selected</c:if>>${bj.name}</option>
		  		</c:forEach>
	  		</select>
	  		<span>科目：</span>
	  		<select name="sub">
		  		<option  value="">请选择科目</option>
		  		<c:forEach var="sub" items="${subs}">
		  			<option value="${sub.id}" <c:if test="${condition.sub.id==sub.id }">selected</c:if>>${sub.name}</option>
		  		</c:forEach>
	  		</select>
	  		<input id="searchBtn" type="submit" class="btn btn-primary" value="搜索">
	  		<div style="clear:right;"></div>
	  	</form>
  	</div>
  	<table class="table table-bordered">
  		<thead>
  			<tr>
  				<th>姓名</th>
  				<th>班级</th>
  				<th>科目</th>
  				<th>成绩</th>
  				<th>等级</th>
  			</tr>
  		</thead>
  		<tbody>
	  		<c:forEach var="sc" items="${scs}">
	  			<tr data-id="${sc.id}">
	  				<td>${sc.stu.name}</td>
	  				<td>${sc.stu.banji.name}</td>
	  				<td>${sc.sub.name}</td>
	  				<td>
	  					<c:choose>
	  						<c:when test="${sc.score!=null}">${sc.score}</c:when>
	  						<c:otherwise>未录入</c:otherwise>
	  					</c:choose>
	  				</td>
	  				<td>${sc.grade}</td>
	  			</tr>
	  		</c:forEach>
  		</tbody>
  	</table>
  	<ul class="fenye">
 	 	<li id="shou">首页</li>
		<li id="pre">上一页</li>
		<%
		 Pagination p=(Pagination)request.getAttribute("p");
		  
		%>
		<c:forEach begin="${p.begin}" end="${p.end}" varStatus="status">
			<li name="namePage" <c:if test="${p.ye==status.index}">class="active"</c:if>>${status.index}</li>
		</c:forEach>
		<li id="next">下一页</li>
		<li id="wei">尾页</li>
	</ul>
	<div id="mes"></div>
	<div class="clearfix"></div>
</div>
  </body>
</html>
