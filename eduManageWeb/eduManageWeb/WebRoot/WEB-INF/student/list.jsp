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
    
    <title>学生管理</title>
    
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
			var sex="";
			var age=-1;
			var bj=-1;
			if($("[name=name]").val()!=""){
				name=$("[name=name]").val();
			}if($("[name=sex]").val()!=""){
				sex=$("[name=sex]").val();
			}if($("[name=age]").val()!=""){
				age=$("[name=age]").val();
			}if($("[name=bj]").val()!=""){
				bj=$("[name=bj]").val();
			}
			function clickYe(selectYe){
				window.location.href="stu?type=search&ye="+selectYe+"&name="+name+"&sex="+sex+"&age="+age+"&bj="+bj;
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
			$("#add").click(function(){
				window.location.href="stu?type=showAdd";
			})
			$("#modify").click(function(){
				if(selectId==0){
					showMes("请选中一条数据!");
				}else{
					window.location.href="stu?type=showModify&selectId="+selectId;
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
						window.location.href="stu?type=delete&selectId="+selectId;
					}
				}
			})
			$(".photo").hover(function(event){
				var str=$(this).attr("src");
				$("#bigPhoto").show();
				$("#bigPhoto").css({
					left:event.pageX,
					top:event.pageY
				})
				$("#bigPhoto img").attr("src",str);
			},function(){
				$("#bigPhoto").hide();
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
			margin-top:10px;
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
		.photo{
			width:25px;
			height:25px;
		}
		#bigPhoto{
			display:none;
			position:absolute;
		}
		#bigPhoto img{
		border:1px solid #ccc;
			width:100px;
			height:100px;
		}
	</style>
  </head>
  	
  <body>
  <div id="main">
  	<%List<Student> list=(List<Student>)request.getAttribute("stus"); %>
  	<div id="search">
	  	<form action="stu" method="post">
	  		<input type="hidden" name="type" value="search"/>
	  		<span>姓名：</span>
	  		<input type="text" name="name" class="form-control" value="${stu.name}">
	  		<span>性别：</span>
	  		<select name="sex" id="sex">
	  			<option  value="">请选择性别</option>
	  			<option  value="男" <c:if test="${stu.sex eq '男' }">selected</c:if>>男</option>
	  			<option  value="女" <c:if test="${stu.sex eq '女' }">selected</c:if>>女</option>
	  		</select>
	  		<span>年龄：</span>
	  		<input id="age" type="text" name="age" class="form-control" <c:if test="${stu.age!=-1}">value="${stu.age}"</c:if>>
	  		<span>班级：</span>
	  		<select name="bj">
		  		<option  value="">请选择班级</option>
		  		<c:forEach var="bj" items="${bjs}">
		  			<option value="${bj.id}" <c:if test="${stu.banji.id==bj.id }">selected</c:if>>${bj.name}</option>
		  		</c:forEach>
	  		</select>
	  		<input id="searchBtn" type="submit" class="btn btn-primary" value="搜索">
	  		<div style="clear:right;"></div>
	  	</form>
  	</div>
  	<table class="table table-bordered">
  		<thead>
  			<tr>
  				<th width="30%">姓名</th>
  				<th width="15%">性别</th>
  				<th width="15%">年龄</th>
  				<th width="20%">班级</th>
  				<th width="20%">照片</th>
  			</tr>
  		</thead>
  		<tbody>
  		<c:forEach var="stu" items="${stus}">
  			<tr data-id="${stu.id}">
  				<td>${stu.name}</td>
  				<td>${stu.sex}</td>
  				<td>${stu.age}</td>
  				<td>${stu.banji.name}</td>
  				<c:if test="${empty stu.photo}">
  					<c:set target="${stu}" property="photo" value="touxiang.jpg"/>
  				</c:if>
  				<td><img src="photos/${stu.photo}" class="photo"></td>
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
	<div id="bigPhoto"><img src=""></div>
</div>
  </body>
</html>
