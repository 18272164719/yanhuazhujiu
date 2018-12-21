<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<shiro:authenticated>
<%
response.sendRedirect(request.getContextPath() + "/index.htmlx");
%>
</shiro:authenticated>
<html>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/bootstrap/css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/css/login.css" />
     
	<!--[if IE]><link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/css/loginIE.css" /> <![endif]-->
    <!--[if lt IE 9]>
		<script src="${pageContext.request.contextPath }/resources/js/html5shiv.js"></script>
		<script src="${pageContext.request.contextPath }/resources/js/respond.js"></script>
	<![endif]-->
    <script  type="text/javascript" src="${pageContext.request.contextPath }/resources/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/jquery.md5.js"></script> 

<style>
p{
padding:5px;
}
</style>

<body>
<div id="head" class="head" >
 	<img src="${pageContext.request.contextPath }/resources/images/logo.png"  style="margin-left:30px;margin-top:5px;width:64px;height:64px;float:left"></img>
	<span style="margin-left:30px;font-size:14px; line-height: 70px;"></span>
</span>
</div>
 
<div id="content" class="content" >
	<div id='loginwin' class="loginwin"> 
	  <form name="form1" id="form1" action="" method="post">
			  <div class="input-group">
				  <span class="input-group-addon" id="basic-addon1"><span class="glyphicon glyphicon-user" aria-hidden="true"></span></span>
				  <input type="text" class="form-control" name="userName" id="userName" placeholder="用户名" aria-describedby="basic-addon1">
				</div>
				<p>
				 <div class="input-group">
				  <span class="input-group-addon" id="basic-addon1"><span class="glyphicon glyphicon-lock" aria-hidden="true"></span></span>
				  <input type="password" class="form-control"  name="password" id="password" placeholder="密码" aria-describedby="basic-addon1">
				</div>
				<p>
				 <div class="input-group">
				  <input type="text" class="form-control" id="verifyCode" name="verifyCode" placeholder="验证码" >
				  <span class="input-group-addon" id="basic-addon1">
			        <img id="verifyCodeImage" style="height:16px;" title="点我换一张" src="${pageContext.request.contextPath}/guest/vcimage.htmlx" onclick="reloadVerifyCode()" />
			
			      </span>
				 </div>

			<input type="hidden" value="59"  name="loginType" />
			<p style="color:#d43f3a;margin-top:5px;font-size:12px;">&nbsp; ${FAILMSG}</p>
			
			<button type="button" class="btn btn-info btn-lg btn-block" onclick="doSubmit()" >登1 录</button>
		</form>
	  </div>	
  </div>

<div id="backgrounddiv" class="backgrounddiv">
	<img id="bgimg" width="100%"  height="100%" src="${pageContext.request.contextPath}/resources/images/memberbg.png" ></img>
<div id="footer" class="footer" style="color:#999;text-align:center;font-size:10px;line-height: 30px;">COPYRIGHT ©2017-2018 </div></div>

  	
  	<script>
  	
  	function clearAll(){
  	  
  	}
  	function doSubmit(){
  	  	if($("#userName").val() ==""||$("#password").val() ==""||$("#verifyCode").val() ==""){
			return;
  	  	}	
  		$("#password").val($.md5($("#password").val()));
  		
  		form1.submit();
  	}

  	
  	function reloadVerifyCode(){  
  	    $('#verifyCodeImage').attr('src', '${pageContext.request.contextPath}/guest/vcimage.htmlx?f='+new Date());  
  	}
  	$(function(){
  		var bgH = document.body.clientHeight;
  		$("#backgrounddiv").css("min-height",620);
  		$("#backgrounddiv").css("height",bgH);
  		$("#bgimg").css("height",bgH-30);
  		
  	//当浏览器窗口大小改变时，设置显示内容的高度  
        $(document).keydown(function(event){  
        	  if(event.keyCode == 13){  
        		  doSubmit();   
        	  }  
        }); 
  	 })
  	</script>
  	</body>