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
			</div>
			<table id="table"></table>
		</div>
		<div class="col-xs-6 lefeP0">
			<div id="toolbar2">
				<button type="button" class="btn btn-default" onclick=okFunc2()>
					<i class="fa fa-check"></i> 新增组并复制授权
				</button>
			</div>
			<table id="table2"></table>
		</div>
		</div>
	</body>
</html>

<script>
//初始化
$(function() {
	$("#groupName").hSelectTable({
	  	url:'${pageContext.request.contextPath }/sys/group/page.htmlx',
	  	queryParams:function(params){
 			params["query['organization.orgId_NULL_IS']"] = "null";
			return params;
		},
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
			$('#table2').bootstrapTable("refreshOptions",{
				url:"${pageContext.request.contextPath}/sys/organization/page.htmlx",
		 		queryParams:function(params){
		 			params["query['t#orgType_I_EQ']"] = row.organization.orgType;
		 			params["query['orgId_NULL_NOT']"] = "not null";
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
        clickToSelect:true,
        columns : [
            {'checkbox':true,width:15},
			{field:'name',title:'资源名称',width:100},
        	{field:'RURL',title:'资源路径',width:100}
		],
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
		toolbar: '#toolbar2',
        columns : [ 
            {'checkbox':true,width:5},
            {field : 'orgName',title : '机构',width : 60,align : 'left'}
		],
		onCheck:function(row){
			
		},
		onUncheck:function(row){
			
		}
    });
	
});
	 
 
function okFunc2(){
	if(!(rows = h.getSelectRows($("#table")))) return;
	var resourceId_arr = new Array();
	$.each(rows,function(index){
		resourceId_arr.push(this.resourceId);
	});
	if(!(rows2 = h.getSelectRows($("#table2")))) return;
	var orgId_arr = new Array();
	var orgType_arr = new Array();
	$.each(rows2,function(index){
		orgId_arr.push(this.id);
	});
	$.each(rows2,function(index){
		orgType_arr.push(this.orgType);
	});

	top.bootbox.dialog({
		  message: "${pageContext.request.contextPath }/sys/permission3/param.htmlx?groupName="+$("#groupName").val(),
		  title: "参数",
		  width:400,
		  height:300,
		  table:$("#table"),
		  buttons: {
		    "取消": {}, 
		    success: {   
		      label: "保存",
		      callback: function() {
		    	 var param = h.iframe(this).getParam();
		    	 h.ajax({
		    			url:"${pageContext.request.contextPath }/sys/permission3/saves.htmlx",
		    			data:{
		    				"resourceIds":resourceId_arr,
		    				"orgIds":orgId_arr,
		    				"orgTypes":orgType_arr,
		    				"groupName":param.groupName
		    			},
		    			traditional: true,//支持传数组参数
		    			dataSuccess:function(data){
		    				$("#table").bootstrapTable('refresh');
		    				h.showMsg("授权成功！");
		    			}
		    		})
		    	 return false;
		      }
		    }
		  }
		});
	return;
}
 

//=============ajax===============

</script>