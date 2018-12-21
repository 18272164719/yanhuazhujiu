<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />

<html>
<body class="easyui-layout"  >
            <div  data-options="region:'west',title:'菜单列表',collapsible:false" style="width:350px;background: rgb(238, 238, 238);padding:10px">
					<table  id="tg" ></table>
            </div>
            
            <div data-options="region:'center',title:'权限列表'" style="background: rgb(238, 238, 238);padding:10px">
            	<table id="dg" ></table>
            </div>
            
            
			

</body>
</html>

<script>
//初始化
$(function(){
	//datagrid
	$('#tg').treegrid({
		idField:'id',    
	    treeField:'name',
	    parentField:'parentId',
	    method:'post',
	    animate:true,
		fitColumns:true,
		striped:true,
		singleSelect:true,
		rownumbers:true,
		height :  $(this).height()-60,
		url:"${pageContext.request.contextPath}/sys/resource/list.htmlx",
		//url:"treegrid2_data.json",
		columns:[[
		        	{field:'name',title:'菜单',width:10}
		   		]],
		onClickRow:function(row){
			listFunc(row.id);
			return;
		}
	});

	$('#dg').datagrid({
		fitColumns:true,
		striped:true,
		singleSelect:true,
		rownumbers:true,
		height :  $(this).height()-60,
		url:"${pageContext.request.contextPath}/sys/authority/list.htmlx",
		columns:[[
		        	{field:'name',title:'名称',width:11},
		        	{field:'url',title:'权限路径',width:11},
		        	{field:'permCode',title:'权限编码',width:11},
		        	{field:'description',title:'描述',width:11}
		   		]],
		toolbar: [{
			iconCls: 'icon-add',
			text:"添加",
			handler: function(){
				var selobj = $('#tg').treegrid('getSelected');
				$("#parentId_win1").combotree("setValue",selobj.id);
				addOpen(); 
				return;	
			}
		},'-',{
			iconCls: 'icon-no',
			text:"删除",
			handler: function(){
				delFunc();
				return;	
			}
		}],
		onDblClickRow:function(index,row){
			editOpen(); 
			return;
		}
	});


	//弹窗增加
	function addOpen() {
		top.$.modalDialog({
			title : "添加成员",
			width : 600,
			height : 400,
			href : "${pageContext.request.contextPath}/sys/authority/add.htmlx",
			onLoad:function(){
				var selrow = $('#tg').treegrid('getSelected');
				if(selrow){
					var ct = top.$.modalDialog.handler.find("#parentId_win");
					ct.combotree("setValue",selrow.id);
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

	//弹窗修改
	function editOpen() {
		top.$.modalDialog({
			title : "编辑成员",
			width : 600,
			height : 400,
			href : "${pageContext.request.contextPath}/sys/authority/edit.htmlx",
			onLoad:function(){
				var selrow = $('#dg').datagrid('getSelected');
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
});

//查询权限列表
function listFunc(id){
	$('#dg').datagrid('load',{
		'id': id
	});	
}

//删除
function delFunc(){

	var selobj = $('#dg').datagrid('getSelected');
	if(selobj == null){
		return;
	}
	var id= $('#dg').datagrid('getSelected').id;
	$.messager.confirm('确认信息', '确认要删除此成员?', function(r){
		if (r){
			delAjax(id);
		}
	});
}

//搜索
function search(val){
	$('#dg').datagrid('load',{
		"query['t#fieldA_S_LK']": val
	});	
}

//=============ajax===============

function delAjax(id){
	$.ajax({
				url:"${pageContext.request.contextPath }/sys/authority/del.htmlx",
				data:"id="+id,
				dataType:"json",
				type:"POST",
				cache:false,
				success:function(data){
					if(data.success){
						$("#dg").datagrid('reload');
						showMsg("删除成功！");
					}else{
						showMsg("该数据已被使用，无法删除");
					}
				},
				error:function(){
					showErr("出错，请刷新重新操作");
				}
	});	
}
</script>