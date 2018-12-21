<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />

<body class='iframebody'>
		<form id="form1" name="form1" class="form-horizontal"  method="post">
				<div class="form-group">
					<label for="name" class="col-xs-3 control-label">资源名称</label>
					<div  class="col-xs-7">
						<input class="form-control" class="col-sm-8" id="name" name="name" type="text" value="${resource.name }" />
					</div>
				</div>
			
				<div class="form-group">
					<label for="url" class="col-xs-3 control-label">资源路径</label>
					<div  class="col-xs-7">
						<input class="form-control" id="url" name="url" type="text" value="${resource.url }"  />
					</div>
				</div>
				
				<div class="form-group">
					<label for="icon" class="col-xs-3 control-label"><i id='iconicon' class="${resource.icon }"></i> 图标</label>
					<div  class="col-xs-7">
						<input class="form-control" id="icon" name="icon" type="text" onclick="ff()" value="${resource.icon }" />
					</div>
				</div>
	
			<input type="hidden" id="id" name="id" value="${resource.id }" /> 
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
	    fields: {
	    	name: { 
	            validators: {
	                notEmpty: { }
	            }  
	        }
	    }
	}); 
}); 	
function submitAjax(){
		h.ajax({
			url :"${pageContext.request.contextPath}/sys/resource/edit.htmlx",
			data:$('#form1').serialize(),
			dataSuccess:function(data){
				h.bootboxRefresh("新增成功");	
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