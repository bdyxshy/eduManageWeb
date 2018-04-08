<%@ page language="java" import="java.util.*,Entity.*,util.Pagination" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
	<script type="text/javascript">
		$(document).ready(function(){
			function showMes(mes){
				$("#manageSub2 #mes").html(mes);
				setTimeout(function(){
					$("#manageSub2 #mes").html("");
				},1000);
			}
			$(document).on("click","#manageSub2 li",function(){
				$(this).toggleClass("selected");
			})
			$("#manageSub2 #add").click(function(){
				var subIds=new Array();
				$("#manageSub2 #noSub li").each(function(index,element){
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
							$("#manageSub2 #sub").html(substr);
							var noSubstr="";
							for(var i=0;i<noSubs.length;i++){
								noSubstr+="<li data-subid="+noSubs[i].id+">"+noSubs[i].name+"</li>";
							}
							$("#manageSub2 #noSub").html(noSubstr);
// 							window.location.href="bj?type=manageSub&selectId="+$("#bj").data("bjid");
						}
					})
				}else{
					showMes("请选中一条数据！");
				}
			})
			$("#manageSub2 #delete").click(function(){
				var subIds=new Array();
				$("#manageSub2 #sub li").each(function(index,element){
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
							$("#manageSub2 #noSub").html(noSubstr);
							var substr="";
							for(var i=0;i<subs.length;i++){
								substr+="<li data-subid="+subs[i].id+">"+subs[i].name+"</li>";
							}
							$("#manageSub2 #sub").html(substr);
// 							window.location.href="bj?type=manageSub&selectId="+$("#bj").data("bjid");
						}
					})
				}else{
					showMes("请选中一条数据！");
				}
			})
			$("#manageSub2 #bj").change(function(){
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
							$("#manageSub2 #noSub").html(noSubstr);
							var substr="";
							for(var i=0;i<subs.length;i++){
								substr+="<li data-subid="+subs[i].id+">"+subs[i].name+"</li>";
							}
							$("#manageSub2 #sub").html(substr);
// 							window.location.href="bj?type=manageSub&selectId="+$("#bj").data("bjid");
						}
					})
			})
		})
	</script>

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
		#manageSub2{
			width:500px;
			margin:0px auto;
		}
		#manageSub2 ul{
			height:100px;
			border:1px solid #ccc;
			padding:15px;
			padding-right:0px;
			margin-bottom:0px;
		}
		#manageSub2 ul li{
			float:left;
			width:100px;
			height:20px;
			background-color:#337ab7;
			list-style:none;
			margin-right:20px;
			margin-bottom:5px;
			color:#fff;
			text-align:center;
			line-height:20px;
			cursor:pointer;
		}
		#manageSub2 #btn{
			float:left;
			margin:10px 0px;
		}
		#manageSub2 #add,#delete{
			color:#fff;
			margin-right:10px;
		}
		#manageSub2 #bj{
			margin-bottom:10px;
			padding-left:5px;
			font-weight:600;
			width:120px;
			height:30px;
		}
		#manageSub2 .selected{
			background-color:#d9534f;
		}
		#manageSub2 #mes{
			float:left;
			width:200px;
			height:54px;
			line-height:54px;
			color:red;
			letter-spacing:2px;
/* 			border:1px solid red; */
		}
		#manageSub2 .clearfix{
			clear:both;
		}
	</style>

  <div id="manageSub2">
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

