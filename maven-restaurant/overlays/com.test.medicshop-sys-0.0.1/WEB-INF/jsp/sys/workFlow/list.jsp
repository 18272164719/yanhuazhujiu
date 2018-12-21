<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />

<html>
<style>
.bs-checkbox .th-inner{
	display:none;
}
</style>
<body  >

<div class="row" >
  <div class="col-xs-5">
 	<!-- 工具栏的按钮，可以自定义事件 -->
	<div id="toolbar" class="btn-group">
		<button type="button" class="btn btn-default"   onclick="addOpen()">
			<i class="fa fa-plus"></i> 新增流程
		</button>
	</div>
  	<table  id="table" ></table>
</div>
 <div class="col-xs-7 lefeP0"> 
	<!-- 工具栏的按钮，可以自定义事件 -->
	<div id="toolbar2" class="btn-group">
		<button type="button" class="btn btn-default"   onclick="addOpen2()">
			<i class="fa fa-plus"></i> <!-- 新增 -->
		</button>
		<button type="button" class="btn btn-default" onclick="editOpen2()">
			<i class="fa fa-pencil"></i> <!-- 修改 -->
		</button>
		<button type="button" class="btn btn-default" onclick="delFunc2()">
			<i class="fa fa-trash"></i> <!-- 删除 -->
		</button>
	</div>
	<table  id="table2" ></table>
</div>
</div>	

</body>
</html>

<script>
function searchDetail(row){
	$('#table2').bootstrapTable("refreshOptions",{
		url:'<c:out value='${pageContext.request.contextPath }'/>/sys/workStep/page.htmlx',  
 		queryParams:function(params){
 			params["query[t#workFlow.id_L_EQ]"] = row.id;
			return params;
		}
	});
}

//初始化
$(function() {
	$('#table').bootstrapTable({
		url:"<c:out value='${pageContext.request.contextPath }'/>/sys/workFlow/page.htmlx",
		toolbar : '#toolbar', 
		onlyPagination:true,
		columns : [
		        {'radio':true,width:10},
		        {field:'workType',title:'流程代码',width:50},
	        	{field:'workTypeName',title:'流程名称',width:50}
		],
		onClickRow:function(row,$el){
			searchDetail(row);
		}
	});
	
	
	$('#table2').bootstrapTable({
		toolbar : '#toolbar2', 
		onlyPagination:true,
		selectItemName: 'btSelectItem2',
		columns : [
		        {'radio':true,width:10},
		        {field:'no',title:'流程步骤',width:100,align:'center'},
				{field:'name',title:'步骤描述',width:100,align:'center'},
				{field:'group.name',title:'审批组',width:100,align:'center'}
		],
		onDblClickRow:function(row,$el){
			editOpen2(row);
		}
	});
	
});
	 
//弹窗增加
function addOpen() {
	top.bootbox.dialog({
		  message: "<c:out value='${pageContext.request.contextPath }'/>/sys/workFlow/add.htmlx",
		  title: "添加流程",
		  width:400,
		  height:470,
		  table:$("#table"),
		  onEscape: function() {
			  $('#table').bootstrapTable('refresh');
		  }
		});
}

//弹窗增加
function addOpen2() {
	if(!(row = h.getSelectRow($("#table")))) return;
	top.bootbox.dialog({
		  message: "${pageContext.request.contextPath}/sys/workStep/add.htmlx?workFlowId="+row.id,
		  title: "添加步骤",
		  width:400,
		  height:350,
		  table:$("#table2"),
		  buttons: {
		    "取消": {}, 
		    success: {   
		      label: "保存",
		      callback: function() {
		    	  h.iframe(this).submitAjax();
		    	 return false;
		      }
		    }
		  }
		});
}

//弹窗修改
function editOpen2(row) {
	if(!(row = h.getSelectRow($("#table2")))) return;

	top.editrow = row;
	top.bootbox.dialog({
		  message: "${pageContext.request.contextPath}/sys/workStep/edit.htmlx?workFlowId="+row.workFlow.id,
		  title: "修改步骤",
		  width:400,
		  height:350,
		  table:$("#table2"),
		  buttons: {
		    "取消": {}, 
		    success: {   
		      label: "保存",
		      callback: function() {
		    	  h.iframe(this).submitAjax();
		    	 return false;
		      }
		    }
		  }
		});
	}

//删除
function delFunc2() {
	if(!(row = h.getSelectRow($("#table2")))) return;
	if(confirm('确认要删除此步骤?')){
		h.ajax({
			url : "${pageContext.request.contextPath }/sys/workStep/del.htmlx",
			data : "id=" + row.id,
			dataSuccess : function(data) {
				$('#table2').bootstrapTable('refresh');
				h.showMsg("删除成功");
			}
		});
	}
}

//=============ajax===============

</script>