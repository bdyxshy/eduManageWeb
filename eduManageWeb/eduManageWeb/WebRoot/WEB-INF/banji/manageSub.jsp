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
			function showMes(mes){
				$("#mes").html(mes);
				setTimeout(function(){
					$("#mes").html("");
				},1000);
			}
			$(document).on("click","li",function(){
				$(this).toggleClass("selected");
			})
			$("#add").click(function(){
				var subIds=new Array();
				$("#noSub li").each(function(index,element){
					if($(this).attr("class")=="selected"){
						subIds.push($(this).data("subid"));
					}
				})
				if(subIds.length>0){
					$.ajax({
						url:"bj",
						data:"type=addSub&bjId="+$("#bj").val()+"&subIds="+subIds,
						type:"get",
						dataType:"text",
						success:function(data){
							var strs=data.split("-|-");
							var subs=JSON.parse(strs[0]);
							var noSubs=JSON.parse(strs[1]);
							var substr="";
							for(var i=0;i<subs.length;i++){
								substr+="<li data-subid="+subs[i].id+">"+subs[i].name+"</li>";
							}
							$("#sub").html(substr);
							var noSubstr="";
							for(var i=0;i<noSubs.length;i++){
								noSubstr+="<li data-subid="+noSubs[i].id+">"+noSubs[i].name+"</li>";
							}
							$("#noSub").html(noSubstr);
// 							window.location.href="bj?type=manageSub&selectId="+$("#bj").data("bjid");
						}
					})
				}else{
					showMes("请选中一条数据！");
				}
			})
			$("#delete").click(function(){
				var subIds=new Array();
				$("#sub li").each(function(index,element){
					if($(this).attr("class")=="selected"){
						subIds.push($(this).data("subid"));
					}
				})
				if(subIds.length>0){
					$.ajax({
						url:"bj",
						data:"type=deleteSub&bjId="+$("#bj").val()+"&subIds="+subIds,
						type:"get",
						dataType:"text",
						success:function(data){
							var strs=data.split("-|-");
							var subs=JSON.parse(strs[0]);
							var noSubs=JSON.parse(strs[1]);
							var noSubstr="";
							for(var i=0;i<noSubs.length;i++){
								noSubstr+="<li data-subid="+noSubs[i].id+">"+noSubs[i].name+"</li>";
							}
							$("#noSub").html(noSubstr);
							var substr="";
							for(var i=0;i<subs.length;i++){
								substr+="<li data-subid="+subs[i].id+">"+subs[i].name+"</li>";
							}
							$("#sub").html(substr);
// 							window.location.href="bj?type=manageSub&selectId="+$("#bj").data("bjid");
						}
					})
				}else{
					showMes("请选中一条数据！");
				}
			})
			$("#bj").change(function(){
					$.ajax({
						url:"bj",
						data:"type=searchSub&bjId="+$("#bj").val(),
						type:"get",
						dataType:"text",
						success:function(data){
							var strs=data.split("-|-");
							var subs=JSON.parse(strs[0]);
							var noSubs=JSON.parse(strs[1]);
							var noSubstr="";
							for(var i=0;i<noSubs.length;i++){
								noSubstr+="<li data-subid="+noSubs[i].id+">"+noSubs[i].name+"</li>";
							}
							$("#noSub").html(noSubstr);
							var substr="";
							for(var i=0;i<subs.length;i++){
								substr+="<li data-subid="+subs[i].id+">"+subs[i].name+"</li>";
							}
							$("#sub").html(substr);
// 							window.location.href="bj?type=manageSub&selectId="+$("#bj").data("bjid");
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
			margin:0px auto;
		}
		ul{
			height:150px;
			border:1px solid #ccc;
			padding:15px;
			padding-right:0px;
			margin-bottom:0px;
		}
		ul li{
			float:left;
			width:100px;
			height:30px;
			background-color:#337ab7;
			list-style:none;
			margin-right:16px;
			margin-bottom:10px;
			color:#fff;
			text-align:center;
			line-height:30px;
			cursor:pointer;
		}
		#btn{
			float:left;
			margin:10px 0px;
		}
		#add,#delete{
			color:#fff;
			margin-right:10px;
		}
		#bj{
			margin-bottom:20px;
			padding-left:5px;
			font-weight:600;
			width:120px;
			height:30px;
		}
		.selected{
			background-color:#d9534f;
		}
		#mes{
			float:left;
			width:200px;
			height:54px;
			line-height:54px;
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
  	<select id="bj" >
  		<c:forEach items="${bjs}" var="banji">
			<option <c:if test="${banji.id==bj.id }">selected</c:if>data-bjid="${banji.id }" value="${banji.id }">${banji.name}</option>
		</c:forEach>
	</select>
  	<ul id="sub">
	  	<c:forEach items="${bj.subs }" var="sub">
	  		<li data-subid="${sub.id }" >${sub.name }</li>
	  	</c:forEach>
  	</ul>
  	<div id="btn">
		<button id="add" type="button" class="btn btn-danger">↑</button>
		<button id="delete" type="button" class="btn btn-danger">↓</button>
	</div>
	<div id="mes"></div>
	<div class="clearfix"></div>
  	<ul id="noSub">
	  	<c:forEach items="${noSubs }" var="sub">
	  		<li data-subid="${sub.id }">${sub.name }</li>
	  	</c:forEach>
  	</ul>
  	<div class="clearfix"></div>
  	
<!-- 	<div id="mes"></div> -->
</div>
  </body>
</html>
