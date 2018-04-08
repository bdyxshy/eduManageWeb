<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'add.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css">
	<style>
		#main{
			width:250px;
			margin:10px auto;
		}
		#form>div{
			width:250px;
			height:34px;
			margin:0px auto 10px;
		}
		#form span{
			width:50px;
			float:left;
			line-height:34px;
		}
		#form .form-control{
			width:200px;
			float:left;
		}
		#radioBox{
			height:34px;
			line-height:34px;
		}
		#radioBox>input{
			margin-right:5px;
		}
		#save{
			text-align:center;
		}
		#main select{
			float:left;
			width:200px;
			height:34px;
			padding: 3px 12px;
			font-size: 14px;
			line-height: 30px;
			color: #555;
			background-color: #fff;
			background-image: none;
			border: 1px solid #ccc;
			border-radius: 4px;
		}
		#image{
			width:100px;
			height:100px;
		}
	</style>

  </head>
  
  <body>
  <div id="main">
   		<form id="form" action="stu?type=modify" method="post" enctype="multipart/form-data">
   			<input type="hidden" name="id" value="${stu.id}">
   			<div><span>姓名：</span><input type="text" name="name" class="form-control" placeholder="请输入姓名" value="${stu.name}"></div>
   			<div>
	   			<span>性别：</span>
	   			<div id="radioBox">
		   			<input type="radio" name="sex" value="男" <c:if test="${stu.sex eq'男'}">checked</c:if>>男
		   			<input type="radio" name="sex" value="女" style="margin-left:20px;"<c:if test="${stu.sex eq'女'}">checked</c:if>>女
	   			</div>
	   		</div>
   			<div><span>年龄：</span><input type="text" name="age" class="form-control" placeholder="请输入年龄" value="${stu.age}"></div>
   			<div>
   				<span>班级：</span>
	  			<select name="bj">
		  			<option  value="">请选择班级</option>
		  			<c:forEach var="bj" items="${bjs}">
		  				<option value="${bj.id}" <c:if test="${bj.id==stu.banji.id}">selected</c:if>>${bj.name}</option>
		  			</c:forEach>
	  			</select>
   			</div>
   			<c:if test="${empty stu.photo}">
  					<c:set target="${stu}" property="photo" value="touxiang.jpg"/>
  				</c:if>
  			<img id="image" src="photos/${stu.photo}" class="photo">
   			<div>
   				<span>照片：</span>
				<input type="file" name="photo"  id="inputfile" style="width:200px;height:34px;">
   			</div>
   			<div  id="save" ><input class="btn btn-primary" type="submit" value="保存"></div>
   		</form>
   	</div>
  </body>
</html>
