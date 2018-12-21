<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />

<body>
	<div>
		<form id="form1" name="form1" method="post">
			<div class="form-row">
				<div class="form-group w100">
					<label class="col-sm-2 " for="field1">代号</label>
					<div class="col-sm-8">
						<input class="form-control" class="col-sm-8" id="field1" name="field1" type="text" />
					</div>
				</div>
			</div>
			<div class="form-row">
				<div class="form-group w100">
					<label class="col-sm-2" for="field2">显示名称</label>
					<div class="col-sm-8">
						<input class="form-control" id="field2" name="field2" type="text" />
					</div>
				</div>
			</div>
			<div class="form-row">
				<div class="form-group w100">
					<label class="col-sm-2" for="field3">特殊标记</label>
					<div class="col-sm-8">
						<input class="form-control" id="field3" name="field3" type="text" />
					</div>
				</div>
			</div>
			<div class="form-row">
				<div class="form-group w100">
					<label class="col-sm-2" for="field4">备注</label>
					<div class="col-sm-8">
						<input class="form-control" id="field4" name="field4" type="text" />
					</div>
				</div>
			</div>
			<input type="hidden" id="id" name="id" /> 
			<input type="hidden"  name="pid" id = "pid" value="${attribute.id }">
		</form>
	</div>
</body>


<script>
	//初始化
	$(function() {
		//表单验证
		$('#form1').bootstrapValidator({
			fields : {
				field1 : {
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
				url :"${pageContext.request.contextPath}/sys/attributeItem/add.htmlx",
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
						showMsg("新增成功");
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

