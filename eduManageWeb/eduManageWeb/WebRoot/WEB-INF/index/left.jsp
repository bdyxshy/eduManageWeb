<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'left.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript">
		$().ready(function(){
			$(".yi").click(function(){
				if("none"==$(this).next().css("display")){
					$(this).next().slideDown(500);
					$(this).find("span").html("[-]");
				}else{
					$(this).next().slideUp(500);
					$(this).find("span").html("[+]");
				}
			})
		})
	</script>
	<style type="text/css">
		body{
			-moz-user-select: none;
			-khtml-user-select: none;
			user-select: none;
		}
		*{
			margin:0px;
			padding:0px;
		}
		#menu{
			margin:20px 0 0 20px ;
		}
		#menu .yi{
			width:180px;
			height:35px;
			font-size:20px;
			background-color:#337ab7;
			border:1px solid #666;
			color:#fff;
			font-weight:bold;
			line-height:35px;
			text-align:center;
			margin-top:5px;
			border-radius:3px;
		}
		#menu .yi:hover{
			background-color:#286090;
			cursor:pointer;
		}
		#menu .yi span{
			font-size:14px;
			font-weight:normal;
			height:35px;
			line-height:35px;
			margin-right:5px;
		}
		#menu .er li{
			list-style:none;
			width:180px;
			height:25px;
			font-size:15px;
			background-color:#337ab7;
			border:1px solid #666;
			color:#fff;
			line-height:25px;
			text-align:center;
			margin-top:1px;
			border-radius:3px;
		}
		#menu .er li:hover{
			background-color:#286090;
			cursor:pointer;
		}
		#menu .er a{
			text-decoration:none;
			color:#fff;
		}
	</style>
  </head>
  
  <body>
  <div id="menu">
    <div class="yi"><span>[-]</span>学生管理</div>
    <ul class="er">
    	<a href="stu" target="main"><li>查看</li></a>
    	<a href="stu?type=showAdd" target="main"><li>新增</li></a>
     </ul>
    <div class="yi"><span>[-]</span>班级管理</div>
     <ul class="er">
    	<a href="bj" target="main"><li>查看</li></a>
    	<a href="bj?type=showAdd" target="main"><li>新增</li></a>
     </ul>
    <div class="yi"><span>[-]</span>科目管理</div>
     <ul class="er">
    	<a href="sub" target="main"><li>查看</li></a>
    	<a href="sub?type=showAdd" target="main"><li>新增</li></a>
    	<a href="bj?type=manageSub" target="main"><li>管理课程</li></a>
     </ul>
    <div class="yi"><span>[-]</span>成绩管理</div>
   	  <ul class="er">
    	<a href="sc" target="main"><li>查看</li></a>
    	<a href="sc?type=manage" target="main"><li>管理</li></a>
      </ul>
  </div>
  </body>
</html>
