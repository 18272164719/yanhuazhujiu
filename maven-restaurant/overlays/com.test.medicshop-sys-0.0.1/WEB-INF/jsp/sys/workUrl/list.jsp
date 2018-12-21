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
  	<table  id="table" ></table>
</div>
 <div class="col-xs-7 lefeP0">
	<!-- 工具栏的按钮，可以自定义事件 -->
	<div id="toolbar2" class="btn-group">
		<button type="button" class="btn btn-default"   onclick="addOpen2()">
			<i class="fa fa-plus"></i> 新增
		</button>
		<button type="button" class="btn btn-default" onclick="editOpen2()">
			<i class="fa fa-pencil"></i> 修改
		</button>
		<button type="button" class="btn btn-default" onclick="delFunc2()">
			<i class="fa fa-trash"></i> 删除
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
		url : "<c:out value='${pageContext.request.contextPath }'/>/sys/workUrl/page.htmlx",
 		queryParams:function(params){
 			params["workCode"] = row.code;
			return params;
		}
	});
}

//初始化
$(function() {
	$('#table').bootstrapTable({
		url:"<c:out value='${pageContext.request.contextPath }'/>/sys/workType/list.htmlx",
		onlyPagination:true,
		sidePagination: 'client',
		columns : [
		        {'radio':true,width:10},
		        {field:'code',title:'流程代码',width:50},
	        	{field:'name',title:'流程名称',width:50}
		],
		onClickRow:function(row,$el){
			searchDetail(row);
		}
	});
	
	
	$('#table2').bootstrapTable({
		toolbar : '#toolbar2', //工具按钮
		selectItemName: 'btSelectItem2',
		columns : [
		        {'radio':true,width :15},
				{field : 'url',title : 'url',width : 120,align : 'left'},
				{field : 'urlImagePath',title : 'url图片',width : 50,align : 'center',formatter : function(value, row, index) {
					return row.urlImagePath;
				}},
				{field : 'winWidth',title : '窗口宽度',width : 50,align : 'center'},
				{field : 'winHeight',title : '窗口高度',width : 50,align : 'center'},
		],
		onDblClickRow:function(row,$el){
			editOpen2(row);
		}
	});
	
});
//删除 
function delFunc2() {
	if(!(row = h.getSelectRow($("#table2")))) return;
	if(confirm('确认要删除吗?')){
		delAjax2(row.id);
	}
	
}
 

//弹窗增加
function addOpen2() {
	if(!(row = h.getSelectRow($("#table")))) return;
	top.bootbox.dialog({
		  message: "<c:out value='${pageContext.request.contextPath }'/>/sys/workUrl/add.htmlx?workType="+row.code,
		  title: "添1加",
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
	return;
	
}

//弹窗修改
function editOpen2(row) {
	if(!(row = h.getSelectRow($("#table2")))) return;
	
	top.editrow = row;
	top.bootbox.dialog({
		  message: "<c:out value='${pageContext.request.contextPath }'/>/sys/workUrl/edit.htmlx?id="+row.id,
		  title: "修改",
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

function chooseObj(obj){
	top.topCallback(obj);
	top.topCallback = null;
	top.$(".workUrl").modal("hide");
}
//=============ajax===============
function delAjax2(id) {
	$.ajax({
		url : "<c:out value='${pageContext.request.contextPath }'/>/sys/workUrl/del.htmlx",
		data : "id=" + id,
		dataType : "json",
		type : "POST",
		cache : false,
		success : function(data) {
			if (data.success) {
				$('#table2').bootstrapTable('refresh');
				showMsg("删除成功");
			} else {
				showErr("出错，请刷新重新操作");
			}
		},
		error : function() {
			showErr("出错，请刷新重新操作");
		}
	});

}


</script>