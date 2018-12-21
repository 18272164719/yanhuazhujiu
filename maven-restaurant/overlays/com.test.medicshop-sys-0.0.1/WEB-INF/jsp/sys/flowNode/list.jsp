<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />
<style>

</style>
<html>
<body >
	<!-- 工具栏的按钮，可以自定义事件 -->
	<div id="toolbar" class="btn-group">
		<div class="btn-group dropdown">
			<button class="btn btn-default dropdown-toggle" type="button" data-name="status" data-toggle="dropdown" >
				<i class="fa fa-edit"></i> <span>待审流程</span> <i class="caret"></i>
			</button>
			<ul class="dropdown-menu">
				<li data-code="0" class="active"><a>待审流程</a></li>
				<li data-code="1"><a>历史流程</a></li>
			</ul>
		</div>
		<button type="button" class="btn btn-default refresh">
			<i class="fa fa-refresh"></i> 刷新
		</button>
	</div>
	<table id="table"></table>
	
	<!-- <div class="panel panel-default box ohide" id="panel" >
	  <div class="panel-heading">详细信息</div>
	  <div class="panel-body" id="panelBody" style="height:300px;"></div>
	</div> -->
</body>
</html>


<script>
	//初始化
	$(function() {
		$('#table').bootstrapTable({
			url : "<c:out value='${pageContext.request.contextPath }'/>/sys/flowNode/page.htmlx",
			queryParams:function(params){
	 			params.status = $(".dropdown-menu .active").data("code");
				return params;
			},
			toolbar : '#toolbar', //工具按钮
			searchbar : [
			             {'name':'标题','code':'query[t#flow.title_S_LK]'}
			], //工具按钮
			columns : [
			        /* {'radio':true,width :10}, */
					/* {field : 'flow.workFlow.work.workType',title : '流程编号',width : 50,align : 'left'}, */
					{field : 'flow.workFlow.workType',title : '流程类型',width : 50,align : 'left'},
					{field : 'status',title : '流程状态',width : 50,align : 'center'},
					{field : 'createUser',title : '创建人',width : 50,align : 'center'},
					{field : 'createDate',title : '创建时间',width : 120,align : 'center',dateFmt:'yyyy-MM-dd HH:mm:ss'},
					{field : 'flow.title',title : '标题',width : 120,align : 'center'}/* ,
					{field : 'note',title : '审批备注',width : 120,align : 'center'} */
			],
			onDblClickRow:function(row,$el){
				detailOpen(row);
			},
			onCheck:function(row){
				$("#panel").addClass("oshow");
				$("#panelBody").html(row.flow.content);
			}
		});
 
	
	});
	
	//弹窗增加
	function detailOpen(row) {
		if(row.status == "unaudit"){
			top.bootbox.dialog({
				  message: "<c:out value='${pageContext.request.contextPath }'/>/sys/flowNode/flow.htmlx?id="+row.id,
				  title: "审批",
				  width:row.winWidth,
				  height:row.winHeight,
				  table:$("#table"),
				  buttons: {
				    "驳回": {
				      	 callback: function() {
				    		h.iframe(this).flow_reject();
				    	 	return false;
				      	 }
				    }, 
				    "批准": {
				    	className: "btn-primary",
				        callback: function() {
					    	h.iframe(this).flow_agree();
				    		return false;
				      	}
				    }
				  }
			});
		}else{
			top.bootbox.dialog({
				  message: "<c:out value='${pageContext.request.contextPath }'/>/sys/flowNode/flow.htmlx?id="+row.id,
				  title: "审批",
				  width:row.winWidth,
				  height:row.winHeight
				});
		}
		return;
	}

	//=============ajax===============
	
</script>