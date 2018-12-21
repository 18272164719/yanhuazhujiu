<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />

<body class='iframebody'>
		<form id="form1" class="form-horizontal" name="form1" method="post">
				<div class="form-group">
					<label class="col-xs-3 control-label">当前流程</label>
					<div  class="col-xs-7">
						<input class="form-control"  type="text" value="<c:out value='${workFlow.segmentString }'/>" readonly />
					</div>
				</div>
		
				<div class="form-group">
					<label  for="name" class="col-xs-3 control-label">步骤描述</label>
					<div  class="col-xs-7">
						<input class="form-control" id="name" name="name" type="text"  />
					</div>
				</div>
			
				<div class="form-group">
					<label  for="group" class="col-xs-3 control-label">审批组</label>
					<div  class="col-xs-7">
					<select  class="form-control" id="group" name="group.id"  ></select>
					</div>
				</div>
				
				<div class="form-group">
					<label  for="workUrl" class="col-xs-3 control-label">流程链接</label>
					<div  class="col-xs-7">
						<div class="input-group">
						  <span class="input-group-addon"><i class="fa fa-info"></i></span>
						  <input  id="workUrlId" name="workUrl.id" type="hidden" />
						  <input class="form-control" id="workUrlUrl" name="workUrl.url"  type="text" onclick=selectWorkUrl(this) />
						</div>
					</div>
					
				</div>
		
			<input type="hidden" id="id" name="id" /> 
			<input type="hidden"  name="workFlow.id"  value="<c:out value='${workFlow.id }'/>" /> 
			<input type="hidden"  name="no"  /> 
		</form>
</body>


<script>

function selectWorkUrl(obj){
	top.topCallback = function(obj){
		$("#workUrlUrl").val(obj.url);
		$("#workUrlId").val(obj.id);
	};
	top.bootbox.dialog({
		 message: "<c:out value='${pageContext.request.contextPath }'/>/sys/workUrl/choose.htmlx?workType=${workFlow.workType }",
		  title: "选择",
		  width:600,
		  height:480,
		  onEscape: function() {}
		});
}

	//初始化
	$(function() {
		$("#group").select2({
			data:"${pageContext.request.contextPath}/sys/group/listByOrg.htmlx",
			width:200,
			valueField:"id",
			textField:"name",
			value:""
		});	
		
		//表单验证
		$('#form1').initValid(["name","group.id"]);
	});

	function  submitAjax(){
		if(!h.isValid($("#form1"))) return false;
		h.ajax({
			url :"${pageContext.request.contextPath}/sys/workStep/edit.htmlx",
			data:$('#form1').serialize(),
			dataSuccess:function(data){
				h.bootboxRefresh("修改成功");
			}
		});
	}

	
</script>


