<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />
	
<html>

<body class="easyui-layout" >

  <div id='loginwin' style="padding: 10px;">
  <form name="form1" action="${pageContext.request.contextPath}/sys/user/firstPwd.htmlx" method="post">
		<table align="center">
		
		<tbody>
		<tr>
		<td colspan="2" style="color:red;" id="msg">您的密码过于简单，请重新设置
		&nbsp; &nbsp;</td>
		</tr>
		<tr>
		<td >用户名：</td>
		<td >${currentUser.name} 
		&nbsp; &nbsp;</td>
		</tr>
		<tr>
		<td>设置新密码:</td>
		<td>
			<input id="pwd1" value="" class="easyui-validatebox easyui-textbox login" type="password" name="password1" data-options="required:true" tabindex="1">
		</td>
		</tr>
		<tr>
		<td>确认新密码:</td>
		<td><input id="pwd2" value="" class="easyui-validatebox easyui-textbox login" type="password" name="password2" data-options="required:true" tabindex="2"></td>
		</tr>
		<tr>
		<td colspan="2"  align="center" height=40px>
			<input id="pwd" name="pwd" type="hidden" >
			<a id="btn" href="#" class="easyui-linkbutton" onclick="javascript:doSubmit()">设置并登陆</a>
		</td>
		</tr>
		</tbody></table>
	</form>
  
  </div>
  
  	</body>
  	
  	<script>
  	function doSubmit(){
  	  	if($("#pwd1").textbox("getValue") ==""||$("#pwd2").textbox("getValue") ==""){
			return;
  	  	}
  	  	if($("#pwd1").textbox("getValue") !=$("#pwd2").textbox("getValue")){
  	  	  	$("#msg").html("两次密码不一致")
			return;
	  	}
  	  	$("#pwd").val($.md5($("#pwd1").textbox("getValue")));

  		form1.submit();
  	}
  	$(function(){
  		$("#loginwin").window({
			title:"设置新密码",
			iconCls:'key',
			width:400,
			height:210,
			minimizable:false,
			maximizable:false,
			collapsible:false,
			closable:false
		});
  		
  		 $("input.login").keydown(function(e){
			  if(e.keyCode==13){
				doSubmit();
			  }
		  });
  	 })
  	 
  	</script>