<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />
<style>
</style>
<html>
<body class="singlebox">
		<table id="table" ></table>
</body>
</html>


<script>
	//初始化
	$(function() {
		$('#table').bootstrapTable({
			url : "<c:out value='${pageContext.request.contextPath }'/>/sys/workUrl/page.htmlx",
			queryParams:function(params){
	 			params.workCode = "${workType }";
				return params;
			},
			columns : [
			        {'radio':true,width :15},
					{field : 'url',title : 'url',width : 100,align : 'left'},
					{field : 'urlImagePath',title : 'url图片',width : 100,align : 'center',formatter : function(value, row, index) {
						return row.urlImagePath;
					}},
					{field : 'winWidth',title : '窗口宽度',width : 100,align : 'center'},
					{field : 'winHeight',title : '窗口高度',width : 100,align : 'center'},
			],
			onDblClickRow:function(row,$el){
				chooseObj(row);
			}
		});
	});
	
	function chooseObj(obj){
		top.topCallback(obj);
		top.topCallback = null;
		h.bootboxHide();
	}
	//=============ajax===============
</script>