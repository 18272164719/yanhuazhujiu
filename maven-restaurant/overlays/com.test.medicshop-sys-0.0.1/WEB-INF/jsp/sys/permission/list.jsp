<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />
<script src="${pageContext.request.contextPath}/resources/bootstrapTable/extensions/tree/bootstrapTreegrid.js"></script>
	
<html>
<style>
#table .bs-checkbox .th-inner{
	display:block !important;
} 
</style>
<body >

<div class="row" >
		<div class="col-xs-6">
			<div id="toolbar">
				<div class="col-xs-8">
					<input class="form-control" id="groupName" name="groupName" type="text"  /> 
					<input type="hidden" id="groupId" name="groupId" />
				</div>
				<button type="button" class="btn btn-default" onclick=okFunc()>
					<i class="fa fa-check"></i> 保存
				</button>
			</div>
			<table id="table"></table>
		</div>
		<div class="col-xs-6 lefeP0">
			<table id="table2"></table>
		</div>
		</div>
	</body>
</html>

<script>
 
function searchDetail(row){
	table2LoadSuccess = false;
	$('#table2').bootstrapTable("refreshOptions",{
		url:'${pageContext.request.contextPath}/sys/resource/pageO.htmlx',  
 		queryParams:function(p){
 			p.parentId = row.resourceId;
 			p.groupId = $("#groupId").val();
			return p;
		}
	});
} 
var table2LoadSuccess = false; 
//初始化
$(function() {
	$("#groupName").hSelectTable({
	  	url:'${pageContext.request.contextPath }/sys/group/page.htmlx',
		key:"query['t#name_S_LK']",
		flkey:"query['t#name_FL_LK']",
		name:"组名",
		width:310,
		pageSize:10,
		minRows:5,
		addBtn:true,
		addUrl: "${pageContext.request.contextPath }/sys/group/add.htmlx",
		addTitle: "添加组",
		addWidth:400,
		addHeight:300,
	  	columns:[
				{field : 'organization.orgName',title : '机构',width : 60,align : 'left'},
				{field : 'name',title : '组名',width : 60,align : 'left'}
		],
		
	  	onSelect:function(row){
	  		$("#groupId").val(row.id);
	  		$("#groupName").val(row.name);
			$('#table').bootstrapTable("refreshOptions",{
				url:"${pageContext.request.contextPath}/sys/permission/menulist.htmlx",
		 		queryParams:function(params){
		 			params.sysId = "admin";
		 			params.orgType = row.organization.orgType;
		 			params.groupId = row.id;
					return params;
				}
			});
	  	}
	 });
		   
	$('#table').bootstrapTable({
        toolbar: '#toolbar',
        treeView: true,
        treeId: "id",
        treeField: "name",
        treeRootLevel: 1,
        sidePagination: 'client',
        clickToSelect:false,
        columns : [
            {'checkbox':true,width:15},
			{field:'name',title:'资源名称',width:100},
        	{field:'RURL',title:'资源路径',width:100}
		],
		onClickRow:function(row){
			searchDetail(row);
		},
		onLoadSuccess:function(){
			 $.each($("#table").bootstrapTable("getData"),function(index){
				if(this.permissionId){
					//window.setTimeout(function(){
						$("#table").bootstrapTable("check", index);
					//}, 100)
				}
			}) 
		}
    });
	
	 
	$('#table2').bootstrapTable({
        columns : [ 
            {'checkbox':true,width:5},
			{field:'name',title:'资源名称',width:50},
        	{field:'url',title:'资源路径',width:50}
		],
		onCheck:function(row){
			if(table2LoadSuccess){
				addAjax(row);
			}
		},
		onUncheck:function(row){
			if(table2LoadSuccess){
				delAjax(row);
			}
		},
		onLoadSuccess:function(data){
			$.each(data.rows,function(index){
				if(this.segment==1){
					$('#table2').bootstrapTable("check",index);
				}
			});
			table2LoadSuccess = true;
		}
    });
	
});
	 
 
function okFunc(){
	if(!(rows = h.getSelectRows($("#table")))) return;
	var resourceId_arr = new Array();
	$.each(rows,function(index){
		resourceId_arr.push(this.resourceId);
	})
	h.log($("#groupId").val());
	h.ajax({
		url:"${pageContext.request.contextPath }/sys/permission/saves.htmlx",
		data:{
			"resourceIds":resourceId_arr,
			"groupId":$("#groupId").val()
		},
		traditional: true,//支持传数组参数
		dataSuccess:function(data){
			$("#table").bootstrapTable('refresh');
		}
	})
	
}
 
function addAjax(row){
	h.ajax({
		url:"${pageContext.request.contextPath }/sys/permission/add.htmlx",
		data:{
			"resourceId":row.id,
			"groupId":$("#groupId").val()
		},
		dataSuccess:function(data){
			h.showMsg("新增成功！");
		}
	});	
}

function delAjax(row){
	h.ajax({
		url:"${pageContext.request.contextPath }/sys/permission/del.htmlx",
		data:{
			"resourceId":row.id,
			"groupId":$("#groupId").val()
		},
		dataSuccess:function(data){
			h.showMsg("删除成功！");
		}
	});	
}

//=============ajax===============

</script>