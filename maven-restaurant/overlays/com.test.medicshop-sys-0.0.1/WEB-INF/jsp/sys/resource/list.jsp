<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />

<html>
<body style="" >

<div class="row" >
  <div class="col-xs-6">
	  <!-- 工具栏的按钮，可以自定义事件 -->
		<div id="toolbar" class="btn-group">
			<button type="button" class="btn btn-default"   onclick=addOpen()>
				<i class="fa fa-plus"></i> 新增
			</button>
			<button type="button" class="btn btn-default" onclick=editOpen()>
				<i class="fa fa-pencil"></i> 修改
			</button>
			<button type="button" class="btn btn-default" onclick=delFunc()>
				<i class="fa fa-trash"></i> 删除
			</button>
		</div>
	   	<table  id="table" ></table>
</div>
 <div class="col-xs-6 lefeP0">
	  <!-- 工具栏的按钮，可以自定义事件 -->
		<div id="toolbar2" class="btn-group">
			<button type="button" class="btn btn-default"   onclick=addOpen2()>
				<i class="fa fa-plus"></i> 新增
			</button>
			<button type="button" class="btn btn-default" onclick=editOpen2()>
				<i class="fa fa-pencil"></i> 修改
			</button>
			<button type="button" class="btn btn-default" onclick=delFunc2()>
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
 		queryParams:function(params){
 			params.id = row.id
			return params;
		}
	});
}

//初始化
$(function() {
	$('#table').bootstrapTable({
		url:"${pageContext.request.contextPath}/sys/resource/page.htmlx",
		toolbar : '#toolbar',
		searchbar : [
             {'name':'名称','code':'query[t#name_S_LK]'},
             {'name':'路径','code':'query[t#url_S_LK]'}
		],
		toolbarSize:6,
		searchbarSize:6,
		onlyPagination:true,
		columns : [
		        {'radio':true,width:5},
		        {field:'name',title:'资源名称',width:50,align:'left',
	        		formatter: function(value,row,index){
	        			return "<i class='"+row.icon+"'></i>"+row.name;
				}},
	        	{field:'url',title:'资源路径',width:50,align:'left'}
		],
		onClickRow:function(row,$el){
			searchDetail(row);
		},
		onDblClickRow:function(row,$el){
			editOpen(row);
		}
	});
	
	$('#table2').bootstrapTable({
		url:"${pageContext.request.contextPath}/sys/authority/list.htmlx",
		toolbar : '#toolbar2', //工具按钮
		columns : [
		        {'radio':true,width:10},
		        {field:'name',title:'权限名称',width:20},
	        	{field:'url',title:'权限路径',width:20},
	        	{field:'permCode',title:'权限编码',width:20}
		],
		responseHandler: function(data) {return {"rows": data};},
		onDblClickRow:function(row,$el){
			editOpen2(row);
		}
	});
});
	
//弹窗增加
function addOpen2() {
	var selobj = $('#table').bootstrapTable('getSelections');
	if (selobj.length == 0) {
		showInfo('请先选择资源');
		return;
	}
	row = selobj[0];
	
	top.toptable = $("#table2");
	top.bootbox.dialog({
		  message: "${pageContext.request.contextPath}/sys/authority/add.htmlx?pid="+row.id,
		  title: "添加权限",
		  width:400,
		  height:300,
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
	if(!row){
		var selobj = $('#table2').bootstrapTable('getSelections');
		if (selobj.length == 0) {
			showInfo('请选择一行');
			return;
		}
		row = selobj[0];
	}
	top.toptable = $("#table2");
	top.editrow = row;
	top.bootbox.dialog({
		  message: "${pageContext.request.contextPath}/sys/authority/edit.htmlx",
		  title: "修改权限",
		  width:400,
		  height:300,
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

//弹窗增加
function addOpen() {
	top.toptable = $("#table");
	top.bootbox.dialog({
		  message: "${pageContext.request.contextPath}/sys/resource/add.htmlx",
		  title: "添加资源",
		  width:400,
		  height:300,
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
function editOpen(row) {
	if(!row){
		var selobj = $('#table').bootstrapTable('getSelections');
		if (selobj.length == 0) {
			showInfo('请选择一行');
			return;
		}
		row = selobj[0];
	}
	top.toptable = $("#table");
	top.editrow = row;
	top.bootbox.dialog({
		  message: "${pageContext.request.contextPath}/sys/resource/edit.htmlx",
		  title: "修改资源",
		  width:400,
		  height:300,
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
function delFunc() {
	var selobj = $('#table').bootstrapTable('getSelections');
	if (selobj.length == 0) {
		showInfo('请选择一行');
		return;
	}
	var id = selobj[0].id;

	if(confirm('确认要删除此资源?')){
		$.ajax({
			url : "${pageContext.request.contextPath }/sys/resource/del.htmlx",
			data : "id=" + id,
			dataType : "json",
			type : "POST",
			cache : false,
			success : function(data) {
				if (data.success) {
					$('#table').bootstrapTable('refresh');
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
}

//删除
function delFunc2() {
	var selobj = $('#table2').bootstrapTable('getSelections');
	if (selobj.length == 0) {
		showInfo('请选择一行');
		return;
	}
	var id = selobj[0].id;

	if(confirm('确认要删除此权限?')){
		$.ajax({
			url : "${pageContext.request.contextPath }/sys/authority/del.htmlx",
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
}




//=============ajax===============

</script>