<%@page import="com.shyl.sys.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />

<body class='iframebody'>
		<form id="form1" name="form1" method="post">
		com.test com.test com.test 业务数据
			<div class="form-group">
				<label for="empId">审批备注</label>
				<input class="form-control"  id="empId" name="empId" type="text" />
			</div>
		</form>
</body>


<script>

function flow_reject(){
	//驳回业务逻辑
	top.topCallback("this is note","this is param");
}
function flow_agree(){
	//批准业务逻辑
	top.topCallback("this is note","this is param");
}
	//初始化
	$(function() {
		
	});
</script>
