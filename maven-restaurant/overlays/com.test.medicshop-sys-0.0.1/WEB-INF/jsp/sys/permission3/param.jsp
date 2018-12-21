<%@page import="com.shyl.sys.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />

<body class='form-horizontal'>
	
	<div class="form-group">
		<label for="type" class="col-xs-3 control-label">组名：</label>
		<div class="col-xs-7">
			<input class="form-control" id="type" name="groupName" type="text" value="${groupName }" />
		</div>
	</div>
	
</body>

<script>
	//初始化
	$(function() {
		
	});
	
	
</script>
