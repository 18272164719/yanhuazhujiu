<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />
<style>

</style>
<html>
<body style='padding: 0px 15px;'>
	<!-- 工具栏的按钮，可以自定义事件 -->
	<div id="toolbar" class="btn-group">
		<select id='flowType' ></select>
	</div>
	
	<table id="table"></table>
	
	<div class="panel panel-default box ohide" id="panel" >
	  <div class="panel-heading">详细信息</div>
	  <div class="panel-body" id="panelBody" style="height:300px;"></div>
	</div>
</body>
</html>


<script>
	//初始化
	$(function() {
		$("#flowType").select2({
			data : [ {
				"id" : 1,
				"text" : '待审流程'
			},{
				"id" : 2,
				"text" : '历史流程'
			}]
		});
		$('#table').bootstrapTable({
			url : "<c:out value='${pageContext.request.contextPath }'/>/sys/flow/page.htmlx",
			toolbar : '#toolbar', //工具按钮
			searchbar : [
			             {'name':'标题','code':'query[t#title_S_LK]'}
			], //工具按钮
			columns : [
			        {'radio':true,width :10},
					{field : 'workFlow.work.code',title : '流程编号',width : 120,align : 'left'},
					{field : 'workFlow.work.name',title : '流程类型',width : 120,align : 'left'},
					{field : 'status',title : '流程状态',width : 120,align : 'center'},
					{field : 'createUser',title : '创建人',width : 120,align : 'center'},
					{field : 'createDate',title : '创建时间',width : 120,align : 'center',dateFmt:'yyyy-MM-dd HH:mm'},
					{field : 'title',title : '标1题',width : 120,align : 'center'}
			],
			onDblClickRow:function(row,$el){
				chooseObj(row);
			},
			onCheck:function(row){
				$("#panel").addClass("oshow");
				$("#panelBody").html(row.content);
			}
		});

	});
	//删除
	function delFunc() {
		var selobj = $('#table').bootstrapTable('getSelections');
		if (selobj.length == 0) {
			showInfo('请选择一行');
			return;
		}
		var id = selobj[0].id;

		if(confirm('确认要删除此成员?')){
			delAjax(id);
		}
		
	}

	
	//弹窗增加
	function addOpen() {
		top.toptable2 = $("#table");
		top.bootbox.dialog({
			  message: "<c:out value='${pageContext.request.contextPath }'/>/sys/workUrl/add.htmlx",
			  title: "添加",
			  className:"workUrl_add",
			  width:400,
			  height:300,
			  buttons: {
			    "取消": {}, 
			    success: {   
			      label: "保存",
			      callback: function() {
			    	 top.$(".workUrl_add").find("iframe")[0].contentWindow.submit();
			    	 return false;
			      }
			    }
			  }
			});
		return;
		
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
		top.toptable2 = $("#table");
		top.editrow = row;
		top.bootbox.dialog({
			  message: "<c:out value='${pageContext.request.contextPath }'/>/sys/workUrl/edit.htmlx?id="+row.id,
			  title: "修改",
			  className:"workUrl_edit",
			  width:400,
			  height:300,
			  buttons: {
			    "取消": {}, 
			    success: {   
			      label: "保存",
			      callback: function() {
			    	  top.$(".workUrl_edit").find("iframe")[0].contentWindow.submit();
			    	  return false;
			      }
			    }
			  }
			});
	}
	
	function chooseObj(obj){
		var selobj = $('#table').bootstrapTable('getSelections');
		for ( var i in selobj) {
			var row = selobj[i];
			var num = $("[data-id='workFlow.work.name_"+row.id+"']").val();
		}
		return;
		top.topCallback(obj);
		top.topCallback = null;
		top.$(".workUrl").modal("hide");
	}
	//=============ajax===============
	function delAjax(id) {
		$.ajax({
			url : "<c:out value='${pageContext.request.contextPath }'/>/sys/workUrl/del.htmlx",
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

	
</script>