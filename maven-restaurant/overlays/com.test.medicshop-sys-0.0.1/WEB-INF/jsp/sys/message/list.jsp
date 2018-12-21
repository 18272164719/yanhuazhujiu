<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />

<style type="text/css">
	.file {
		position: absolute;
		top: 0px;
		left: 0px;
		height: 30px;
		width: 85px;
		filter: alpha(opacity : 0);
		opacity: 0;
		cursor: pointer;
	}
	
	.file_form {
		width: 85px;
		height: 20px;
		float:left;
		margin: 0px 5px 0px 0px;
	}
</style>

<html>
<script src="${pageContext.request.contextPath }/resources/js/echarts.min.js"></script>
<body class="easyui-layout" >
    
    
<div style="padding:10px 0px 0px 10px">

	<input id="ss" class="easyui-searchbox" style="width: 218px;display:none" 
	data-options="
		menu:'#mm',
		prompt:'支持模糊搜索',
		searcher:function(value,name){
	        search(value,name);
	    }" />
	    <div id="mm">
		<div data-options="name:'empId' ">帐号</div>
		<div data-options="name:'name'">用户名</div>
		<div data-options="name:'cell'">手机</div>
	</div> 
	 
</div>
<div style="padding:10px">
<table  id="dg" ></table>
</div>



</body>
</html>
<script>

//搜索
function search(val,name){
	if(name=='empId'){
	$('#dg').datagrid('load',{
		"query['t#empId_S_LK']": val
	});	 
	}
	else if(name=='name'){
		$('#dg').datagrid('load',{
			"query['t#name_S_LK']": val
		});	 
		}
	else if(name=='cell'){
		$('#dg').datagrid('load',{
			"query['t#cell_S_LK']": val
		});	 
		}
	else if(name=='title'){
		$('#dg').datagrid('load',{
			"query['t#title_S_LK']": val
		});	 
		}
}


//初始化
$(function(){
	
	
	//datagrid
	$('#dg').datagrid({
		fitColumns:true,
		striped:true,
		singleSelect:true,
		rownumbers:true,
		border:true,
		height :  $(this).height()-60,
		pagination:true,
		url:"${pageContext.request.contextPath}/sys/user/page.htmlx",
		pageSize:10,
		pageNumber:1,
		columns:[[
		        	{field:'empId',title:'帐号',width:10,align:'center'},
		        	{field:'name',title:'姓名',width:20,align:'center'},
		        	{field:'mail',title:'邮件地址',width:30,align:'center'},
		        	{field:'cell',title:'手机',width:20,align:'center'},
		        	{field:'ext',title:'座机',width:20,align:'center'},
		        	{field:'orgType',title:'机构类型',width:20,align:'center',
						formatter: function(value,row,index){
							if (row.orgType==1){
								return "医院";
							}else if (row.orgType==2){
								return "供应商";
							}else if (row.orgType==3){
								return "监督机构";
							}
						}
		        	}
		   		]],
		toolbar: [{
			iconCls: 'icon-add',
			text:"添加",
			handler: function(){
				addOpen();	
			}
		},'-',{
			iconCls: 'icon-no',
			text:"删除",
			handler: function(){
				delFunc();
			}
		},'-',{
			iconCls: 'icon-key',
			text:"重置密码",
			handler: function(){
				resetPwd();
			}
		}],
		onDblClickRow: function(index,field,value){
			editOpen();
		}
	});
});

//弹窗增加
function addOpen() {
	top.$.modalDialog({
		title : "添加成员",
		width : 700,
		height : 400,
		href : "${pageContext.request.contextPath}/sys/user/add.htmlx",
		onLoad:function(){
		},
		buttons : [ {
			text : '保存',
			iconCls : 'icon-ok',
			handler : function() {
				top.$.modalDialog.openner= $('#dg');//因为添加成功之后，需要刷新这个treegrid，所以先预定义好
				var f = top.$.modalDialog.handler.find("#form1");
				f.submit();
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function() {
				top.$.modalDialog.handler.dialog('destroy');
				top.$.modalDialog.handler = undefined;
			}
		}]
	});
}

//弹窗修改
function editOpen() {
	var selrow = $('#dg').datagrid('getSelected');
	top.$.modalDialog({
		title : "编辑成员",
		width : 700,
		height : 400,
		href : "${pageContext.request.contextPath}/sys/user/edit.htmlx",
		queryParams:{
			"orgType":selrow.orgType,
			"orgId":selrow.orgId
		},
		onLoad:function(){
			
			if(selrow){
				var f = parent.$.modalDialog.handler.find("#form1");
				f.form("load", selrow);
			}
		},
		buttons : [ {
			text : '保存',
			iconCls : 'icon-ok',
			handler : function() {
				top.$.modalDialog.openner= $('#dg');//因为添加成功之后，需要刷新这个treegrid，所以先预定义好
				var f = top.$.modalDialog.handler.find("#form1");
				f.submit();
			}
		}, {
			text : '取消',
			iconCls : 'icon-cancel',
			handler : function() {
				top.$.modalDialog.handler.dialog('destroy');
				top.$.modalDialog.handler = undefined;
			}
		}]
	});
}
//=============ajax===============
function delAjax(id){
	$.ajax({
				url:"${pageContext.request.contextPath }/sys/user/del.htmlx",
				data:"id="+id,
				dataType:"json",
				type:"POST",
				cache:false,
				success:function(data){
					if(data.success){
						$('#dg').datagrid('reload');
						showMsg("删除成功");
					} else{
						showErr("出错，请刷新重新操作");
					}
				},
				error:function(){
					showErr("出错，请刷新重新操作");
				}
	});	
	
}

function resetPwdAjax(id,empId){
	$.ajax({
				url:"${pageContext.request.contextPath }/sys/user/resetPwd.htmlx",
				data:{
					"id":id,
					"empId":$.md5(empId)
				},
				dataType:"json",
				type:"POST",
				cache:false,
				success:function(data){
					if(data.success){
						$('#dg').datagrid('reload');
						showMsg("重置密码成功");
					} else{
						showErr("出错，请刷新重新操作");
					}
				},
				error:function(){
					showErr("出错，请刷新重新操作");
				}
	});	
	
}
</script>