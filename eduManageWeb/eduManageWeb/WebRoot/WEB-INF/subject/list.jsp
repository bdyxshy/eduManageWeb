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
    
    <title>科目管理</title>
    
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
			var name="";
			if($("[name=name]").val()!=""){
				name=$("[name=name]").val();
			}
			$("#shou").click(function(){
				window.location.href="sub?type=search&ye=1&name="+name;
			})
			$("#wei").click(function(){
				window.location.href="sub?type=search&ye="+maxYe+"&name="+name;
			})
			$("#pre").click(function(){
				if(ye>1){
					window.location.href="sub?type=search&ye="+(ye-1)+"&name="+name;
				}else{
					showMes("已经是第一页了");
					$(this).attr("style","cursor:not-allowed");
				}
			})
			$("#next").click(function(){
				if(ye<maxYe){
					window.location.href="sub?type=search&ye="+(ye+1)+"&name="+name;
				}else{
					showMes("已经是最后一页了");
					$(this).attr("style","cursor:not-allowed");
				}
			})
			$("[name=namePage]").click(function(){
				window.location.href="sub?type=search&ye="+$(this).html()+"&name="+name;
			})
			$("#add").click(function(){
				window.location.href="sub?type=showAdd";
			})
			$("#modify").click(function(){
				if(selectId==0){
					showMes("请选中一条数据!");
				}else{
					window.location.href="sub?type=showModify&selectId="+selectId;
				}
			})
			$("tbody tr").click(function(){
				selectId=$(this).data("id");
				$("tbody tr").css("background","none");
				$(this).css("background","#337ab7");
			})
			$("#delete").click(function(){
				if(selectId==0){
					showMes("请选中一条数据!");
				}else{
					var type=confirm("是否删除数据?");
					if(type){
						window.location.href="sub?type=delete&selectId="+selectId;
					}
				}
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
			padding: 3px 12px;
			font-size: 14px;
			line-height: 30px;
			color: #555;
			background-color: #fff;
			background-image: none;
			border: 1px solid #ccc;
			border-radius: 4px;
		}
		#search #searchBtn{
			width:80px;
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
  	<div id="search">
	  	<form action="sub" method="post">
	  		<input type="hidden" name="type" value="search"/>
	  		<span>科目：</span>
	  		<input type="text" name="name" class="form-control" value="${sub.name}">
	  		<input id="searchBtn" type="submit" class="btn btn-primary" value="搜索">
	  	</form>
  	</div>
  	<table class="table table-bordered">
  		<thead>
  			<tr>
  				<th>科目</th>
  			</tr>
  		</thead>
  		<tbody>
  		<c:forEach var="sub" items="${subs}">
  			<tr data-id="${sub.id}">
  				<td>${sub.name}</td>
  			</tr>
  		</c:forEach>
  		</tbody>
  	</table>
  	<div class="btn-group">
		<button id="add" type="button" class="btn btn-danger">新增</button>
		<button id="modify" type="button" class="btn btn-warning">修改</button>
		<button id="delete" type="button" class="btn btn-primary">删除</button>
	</div>
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
