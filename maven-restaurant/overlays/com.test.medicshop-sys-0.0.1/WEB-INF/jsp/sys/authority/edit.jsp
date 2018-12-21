<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />

<body>
	<div>
		<form id="form1" name="form1" method="post">
			<div class="form-row">
				<div class="form-group w100">
					<label class="col-sm-2 " for="name">权限名称</label>
					<div class="col-sm-8">
						<input class="form-control" class="col-sm-8" id="name" name="name" type="text" />
					</div>
				</div>
			</div>
			<div class="form-row">
				<div class="form-group w100">
					<label class="col-sm-2" for="url">权限路径</label>
					<div class="col-sm-8">
						<input class="form-control" id="url" name="url" type="text" />
					</div>
				</div>
			</div>
			<div class="form-row">
				<div class="form-group w100">
					<label class="col-sm-2" for="permCode">权限编码</label>
					<div class="col-sm-8">
						<input class="form-control" id="permCode" name="permCode" type="text" />
					</div>
				</div>
			</div>
			<input type="hidden" id="id" name="id" /> 
			<input type="hidden" name="type" value="O"  >
			<input type="hidden" name="state" value=""  >
			<input type="hidden" id="parentId" name="parentId" value="${parent.id }"  >
		</form>
	</div>
</body>



<script>
//初始化
$(function(){
	//表单验证
	$('#form1').bootstrapValidator();
}); 	
function submitAjax(){
		$.ajax({
			url :"${pageContext.request.contextPath}/sys/authority/edit.htmlx",
			data:$('#form1').serialize(),
			type:"POST",
			beforeSend:function(){  
				
			},
			success:function(data){
				if (data.success) {
					top.toptable.bootstrapTable('refresh');
					top.toptable = null;
					top.bootbox.hideAll();
					showMsg("修改成功");
				} else {
					showErr(data.msg);
				}
			},
			error : function() {
				showErr("出错，请刷新重新操作");
			}
		});
	
}
</script>