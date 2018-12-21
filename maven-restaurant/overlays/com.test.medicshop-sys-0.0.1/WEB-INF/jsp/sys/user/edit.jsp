<%@page import="com.shyl.sys.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />

<body class='iframebody'>
		<form id="form1" name="form1" method="post">
			<div class="row ">
				<div class="col-xs-6">
					
					<div class="form-group">
						<label for="empId">帐号</label>
						<input class="form-control"  id="empId" name="empId" type="text" disabled />
					</div>
					<div class="form-group">
						<label for="name">姓名</label>
						<input class="form-control" id="name" name="name" type="text" />
					</div>
					<div class="form-group">
						<label for="cell">手机</label>
						<input class="form-control" id="cell" name="cell" type="text" />
					</div>
					
					
				</div>
				
				<div class="col-xs-6">
					<div class="form-group">
						<label for="mail">邮箱</label>
						<input class="form-control" id="mail" name="mail" type="text" />
					</div>
					<div class="form-group">
						<label for="title">职务</label>
						<input class="form-control" id="title" name="title" type="text" />
					</div>
					<div class="form-group">
						<label for="idcard">身份证</label>
						<input class="form-control" id="idcard" name="idcard" type="text"  />
					</div>
					
				</div>
			</div>

			<div  class="row  col-xs-6" >
				<div class="form-group">
					<label >机构</label>
					<input class="form-control" id="orgName" name="organization.orgName" type="text" disabled  />
				</div>
			</div>
		
			<div class="row col-xs-12"  id="groups" ></div>
		
			<input type="hidden" id="id" name="id">
			<input type="hidden" id="orgId" name="organization.id" />
			<input type="hidden" id="group" name="group" value="">
		</form>
		
</body>


<script>
var loading = true;
	//初始化
	$(function() {
		var orgType = "${user.organization.orgType}";
		var orgPId = "${user.organization.parentOrgId}";

		//表单验证
		$('#form1').initValid(["empId","orgName"]);
	});
	 
	function submitAjax(){
		var groupArr = new Array();
		$.each($("#groups").find(":checked"),function(index){
			groupArr.push(this.id);
		});
		$("#group").val(groupArr);

		h.ajax({
			url : "${pageContext.request.contextPath}/sys/user/edit.htmlx",
			data : $('#form1').serialize(),
			beforeSend : function() {
				if(!h.isValid($("#form1"))) return false;
			},
			dataSuccess : function(data) {
				h.bootboxRefresh("修改成功");	
			}
		});
	}
	function getGroups(){
		h.ajax({//查询组
			url : "<c:out value='${pageContext.request.contextPath }'/>/sys/group/list.htmlx",
			data : {
				"query['t#organization.id_L_EQ']" : $("#orgId").val()
			},
			dataSuccess : function(data) {
				var groups = "${groups }";
				$("#groups").empty();
				$.each(data,function(index){
					var checked = "";
					if(groups.indexOf(this.id+",")>=0){
						checked = "checked";
					}
					var d =$('<div class="col-xs-4"><div class="form-group"><div class="checkbox"><label><input type="checkbox"  id="'+this.id+'" '+checked+' > '+this.name+'</label></div></div></div>');
					$("#groups").append(d);
				});
			}
		});
	}
	
	//赋值回调
	function editrowSuccess(){
		getGroups();
	}
</script>
