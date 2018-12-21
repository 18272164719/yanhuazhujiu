<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />

<body class='iframebody'>
		<form id="form1" name="form1" method="post">
				<div class="form-group">
					<label for="url">url</label>
					<input class="form-control" id="url" name="url" type="text" />
				</div>
				<div class="form-group">
					<label  for="urlImagePath">url图片</label>
					<input class="form-control" id="urlImagePath" name="urlImagePath" type="text" />
				</div>
				<div class="form-group">
					<label  for="winWidth">窗口宽度</label>
					<input class="form-control" id="winWidth" name="winWidth" type="text" />
				</div>
				<div class="form-group">
					<label  for="winHeight">窗口高度</label>
					<input class="form-control" id="winHeight" name="winHeight" type="text" />
				</div>
				
			<input type="hidden" id="id" name="id" />
			<input type="hidden" name="workId" />  
		</form>
</body>


<script>
	//初始化
	$(function() {
		//表单验证
		$('#form1').bootstrapValidator();
	}); 	
	function submitAjax(){
			$.ajax({
				url :"${pageContext.request.contextPath}/sys/workUrl/edit.htmlx",
				data:$('#form1').serialize(),
				type:"POST",
				beforeSend:function(){  
					if(!h.isValid($("#form1"))) return false;
				},
				success:function(data){
					if (data.success) {
						h.bootboxRefresh("修改成功");
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


