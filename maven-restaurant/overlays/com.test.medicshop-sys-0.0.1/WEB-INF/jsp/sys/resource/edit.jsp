<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />

<body class='iframebody'>
		<form id="form1" name="form1" method="post">

				<div class="form-group">
					<label for="name">资源名称</label>
					<input class="form-control" class="col-sm-8" id="name" name="name" type="text" />
				</div>
			
				<div class="form-group">
					<label for="url">资源路径</label>
					<input class="form-control" id="url" name="url" type="text" />
				</div>
				
				<div class="form-group">
					<label for="icon">图标<i id='iconicon'></i></label>
					<input class="form-control" id="icon" name="icon" type="text" onclick="ff()" />
				</div>
	
			<input type="hidden" id="id" name="id" /> 
			<input type="hidden" name="type" value="F" />
			<input type="hidden" name="state" value="" />
		</form>
</body>



<script>
top.callback = function(c){
	$("#icon").val(c);
	$("#iconicon").attr("class",c);
}
function ff(){
	top.bootbox.dialog({
		  message: "${pageContext.request.contextPath }/sys/resource/icons.htmlx",
		  title: "图标",
		  width:900,
		  height:400,
		  buttons: {
		    "取消": {}
		  }
		});
}
//初始化
$(function(){
	//表单验证
	$('#form1').bootstrapValidator({  
	    feedbackIcons: {  
	        valid: 'glyphicon glyphicon-ok',  
	        invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
	    },  
	    fields: {
	    	name: { 
	            validators: {
	                notEmpty: {  
	                	message: '此栏位必输',  
	                }
	            }  
	        }
	    }
	}); 
}); 	
	function submitAjax(){
		$.ajax({
			url :"${pageContext.request.contextPath}/sys/resource/edit.htmlx",
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


function chooseIcon(){
	$.ajax({
		url:"${pageContext.request.contextPath }/sys/resource/iconlist.htmlx",
		dataType:"json",
		type:"POST",
		cache:false,
		success:function(data){
			$("#iconlist").empty();
			$.each(data,function(i,v){
				var d = $("<a style='margin:2px;'></a>");
				d.linkbutton({
					iconCls:v,
					onClick:function(){
						$("#icon").textbox("setValue",v);
						$("#iconBtn").linkbutton({
						    iconCls: v
						});
					}}); 
				$("#iconlist").append(d);
			})
		},
		error:function(){
			showErr("出错，请刷新重新操作");
		}
	});	
	
}
</script>