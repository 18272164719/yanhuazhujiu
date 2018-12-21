<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />

<body>
	<div>
		<form id="form1" name="form1" method="post">
			<div class="form-row">
				<div class="form-group w100">
					<label class="col-sm-2 " for="attributeNo">属性代号</label>
					<div class="col-sm-8">
						<input class="form-control" class="col-sm-8" id="attributeNo" name="attributeNo" type="text" readonly />
					</div>
				</div>
			</div>
			<div class="form-row">
				<div class="form-group w100">
					<label class="col-sm-2" for="name">属性名称</label>
					<div class="col-sm-8">
						<input class="form-control" id="name" name="name" type="text" />
					</div>
				</div>
			</div>
			<div class="form-row">
				<div class="form-group w100">
					<label class="col-sm-2" for="description">属性描述</label>
					<div class="col-sm-8">
						<input class="form-control" id="description" name="description" type="text" />
					</div>
				</div>
			</div>
			<input type="hidden" id="id" name="id" /> 
			
		</form>
	</div>
</body>


<script>
	//初始化
	$(function() {
		//表单验证
		$('#form1').bootstrapValidator({
			fields : {
				attributeNo : {
					validators : {
						notEmpty : {
							message : '此栏位必输',
						}
					}
				}
			}
		});
	}); 	
	function submitAjax(){
			$.ajax({
				url :"${pageContext.request.contextPath}/sys/attribute/edit.htmlx",
				data:$('#form1').serialize(),
				type:"POST",
				beforeSend:function(){  
					var bootstrapValidator = $("#form1").data('bootstrapValidator');  
				    bootstrapValidator.validate();  
				    if(!bootstrapValidator.isValid())
				    	return false;
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
