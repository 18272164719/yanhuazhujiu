<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />
<style>
.bs-checkbox .th-inner{
	display:none;
}
</style>
<body class='iframebody' >
	<table id="table"></table>
</body>

<script>
	var loadSuccess = false;
	//初始化
	$(function(){
		$('#table').bootstrapTable({
			url:'${pageContext.request.contextPath}/sys/workFlow/listSuper.htmlx',
			onlyPagination:true,
			sidePagination: 'client',
			columns : [
			        {'checkbox':true,width:5},
			        {field:'code',title:'流程代码',width:30},
		        	{field:'name',title:'流程名称',width:30}
			],
			onCheck:function(row){
				if(loadSuccess){
					addAjax(row.code);
				}
			},
			onUncheck:function(row){
				if(loadSuccess){
					delAjax(row.code);
				}
			},
			onLoadSuccess:function(data){
				$.each(data,function(index){
					if(this.segmentString==1){
						$('#table').bootstrapTable("check",index);
					}
				});
				loadSuccess = true;
			}
		});
	});
	//=============ajax===============
	function addAjax(workCode){
		h.ajax({
			url:"${pageContext.request.contextPath }/sys/workFlow/add.htmlx",
			data:{
				"workCode":workCode
			},
			dataSuccess:function(data){
				h.showMsg("新增成功！");
			}
		});	
	}

	function delAjax(workCode){
		h.ajax({
			url:"${pageContext.request.contextPath }/sys/workFlow/delete.htmlx",
			data:{
				"workCode":workCode
			},
			dataSuccess:function(data){
				h.showMsg("删除成功！");
			}
		});	
	}
</script>	
	