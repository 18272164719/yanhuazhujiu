<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />

<html>
<body  >

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
		url:"${pageContext.request.contextPath}/sys/attribute/page.htmlx",
		toolbar : '#toolbar', //工具按钮
		onlyPagination:true,
		//resizable: false,
		columns : [
		        {'radio':true,width:5},
		        {field:'attributeNo',title:'属性代码',width:20,align:'left'},
	        	{field:'name',title:'名称',width:20,align:'center'},
	        	{field:'description',title:'描述',width:20,align:'center'}
		],
		onClickRow:function(row,$el){
			searchDetail(row);
		},
		onDblClickRow:function(row,$el){
			editOpen(row);
		}
	});
	
	 $('#table2').bootstrapTable({
		url:"${pageContext.request.contextPath}/sys/attribute/queryByAttributeItem.htmlx",
		toolbar : '#toolbar2', //工具按钮
		onlyPagination:true,
		selectItemName: 'btSelectItem2',
		columns : [
		        {'radio':true,width:5},
		        {field:'field1',title:'代码',width:20,align:'left'},
				{field:'field2',title:'名称',width:50,align:'center'},
				{field:'field3',title:'值',width:20,align:'center'}
		],
		onDblClickRow:function(row,$el){
			editOpen2(row);
		}
	}); 
});
	
//弹窗增加
function addOpen2() {
	var selobj = $('#table').bootstrapTable('getSelections');
	if (selobj.length == 0) {
		showInfo('请先选择一行');
		return;
	}
	row = selobj[0];
	
	top.toptable = $("#table2");
	top.bootbox.dialog({
		  message: "${pageContext.request.contextPath}/sys/attributeItem/add.htmlx?pid="+row.id,
		  title: "添加属性值",
		  width:400,
		  height:350,
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
		  message: "${pageContext.request.contextPath}/sys/attributeItem/edit.htmlx",
		  title: "修改属性值",
		  width:400,
		  height:350,
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
		  message: "${pageContext.request.contextPath}/sys/attribute/add.htmlx",
		  title: "添加属性",
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
		  message: "${pageContext.request.contextPath}/sys/attribute/edit.htmlx",
		  title: "修改属性",
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

	if(confirm('确认要删除此属性?')){
		$.ajax({
			url : "${pageContext.request.contextPath }/sys/attribute/delete.htmlx",
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
	if(confirm('确认要删除吗?')){
		$.ajax({
			url : "${pageContext.request.contextPath }/sys/attributeItem/delete.htmlx",
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

<script>







