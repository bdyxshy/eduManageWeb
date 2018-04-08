<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
			overflow:hidden;
			margin:0px auto 20px;
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
	</style>

  </head>
  
  <body>
  <div id="main">
   		<form id="form" action="bj" method="post">
   			<input type="hidden" name="type" value="add">
   			<div><span>班级：</span><input type="text" name="name" class="form-control" placeholder="请输入名称"></div>
   			<div  id="save" ><input class="btn btn-primary" type="submit" value="保存"></div>
   		</form>
   	</div>
  </body>
</html>
