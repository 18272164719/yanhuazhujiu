<%@page import="com.shyl.sys.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />

<body class='iframebody'>
		<form id="form1" name="form1" class="form-horizontal"   method="post">
			<div class="form-group">
				<label for="empId" class="col-xs-3 control-label">工号</label>
				<div  class="col-xs-7">
					<input class="form-control"  id="empId" name="empId" type="text" value="${user.empId }" readonly />
				</div>
			</div>
			<div class="form-group">
				<label for="oldPsw" class="col-xs-3 control-label">旧密码</label>
				<div  class="col-xs-7">
					<input class="form-control" id="oldPsw" name="oldPsw" type="password" />
				</div>
			</div>
			<div class="form-group">
				<label for="newPsw" class="col-xs-3 control-label">新密码</label>
				<div  class="col-xs-7">
					<input class="form-control" id="newPsw" name="newPsw" type="password" />
				</div>
			</div>
			<div class="form-group">
				<label for="newPsw1" class="col-xs-3 control-label">新密码确认</label>
				<div  class="col-xs-7">
					<input class="form-control" id="newPsw1" name="newPsw1" type="password" />
				</div>
			</div>
			<input type="hidden" id="id" name="id" value="${user.id }">
		</form>
		
</body>

<script type="text/javascript" src="<c:out value='${pageContext.request.contextPath }'/>/resources/js/jquery.md5.js"></script>

<script>

	//初始化
	$(function() {
		//表单验证
		$('#form1').bootstrapValidator({
			fields : {
				oldPsw : {
					validators : {
						notEmpty : {}
					}
				},
				newPsw : {
					validators : {
						notEmpty : {}
					}
				},
				newPsw1 : {
					validators : {
						notEmpty : {}
					}
				}
				
			}
		});
	}); 	
	function submitAjax(){
			h.ajax({
				url : "${pageContext.request.contextPath}/sys/user/updatePsw.htmlx",
				data : $('#form1').serialize(),
				beforeSend : function() {
					if(!h.isValid($("#form1"))) return false;
				},
				dataSuccess:function(data) {
					h.bootboxHide("密码修改成功");	
				}
			});
	}

</script>
