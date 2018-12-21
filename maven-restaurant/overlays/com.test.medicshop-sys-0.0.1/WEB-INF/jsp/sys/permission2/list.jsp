<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />
<script src="${pageContext.request.contextPath}/resources/bootstrapTable/extensions/tree/bootstrapTreegrid.js"></script>
	
<html>
<style>
#table2 .bs-checkbox .th-inner{
	display:block !important;
} 
</style>
<body >

<div class="row" >
		<div class="col-xs-6">
			<div id="toolbar">
				<div class="col-xs-8">
					<select id="orgType" name="orgType"></select> 
				</div>
			</div>
			<table id="table"></table>
		</div>
		<div class="col-xs-6 lefeP0">
			<div id="toolbar2">
				<button type="button" class="btn btn-default" onclick=okFunc()>
					<i class="fa fa-check"></i> 保存
				</button>
			</div>
			<table id="table2"></table>
		</div>
		</div>
	</body>
</html>

<script>
 
function searchDetail(row){
	$('#table2').bootstrapTable("refreshOptions",{
		url:'${pageContext.request.contextPath }/sys/permission2/groupList.htmlx',
 		queryParams:function(p){
 			p.resourceId = row.resourceId;
 			p.orgType = orgType;
			return p;
		}
	});
} 
 
var table2LoadSuccess = false; 
var sysId = "admin";
var orgType = "1";
//初始化
$(function() {
	$("#orgType").select2({
		width:200,
		data: [{
			"text": "系统管理", 
		    "children" : [
		        {
		            "id": "admin-1",
		            "text": "连锁总部"
		        },
		        {
		            "id": "admin-2",
		            "text": "连锁直营"
		        },
		        {
		            "id": "admin-3",
		            "text": "连锁加盟"
		        },
		        {
		            "id": "admin-4",
		            "text": "非连锁店"
		        }
		      ]
		}]
	}); 
	$("#orgType").on("change",function(e) {
		var val = $("#orgType").val();
		sysId = val.split("-")[0];
		orgType = val.split("-")[1];
		$('#table').bootstrapTable("refreshOptions",{
	 		queryParams:function(p){
	 			p.sysId = sysId;
				p.orgType = orgType;
				return p;
			}
		});
		$('#table2').bootstrapTable("removeAll");
	});
	 	     
	$('#table').bootstrapTable({
		url:"${pageContext.request.contextPath}/sys/permission/menulist.htmlx",
		queryParams:function(p){
 			p.sysId = sysId;
			p.orgType = orgType;
			return p;
		},
        toolbar: '#toolbar',
        treeView: true,
        treeId: "id",
        treeField: "name",
        treeRootLevel: 1,
        sidePagination: 'client',
        treeCollapseAll:true,
        clickToSelect:true,
        columns : [
            {'radio':true,width:15},
			{field:'name',title:'资源名称',width:100},
        	{field:'RURL',title:'资源路径',width:100}
		],
		onClickRow:function(row){
			searchDetail(row);
		}
    });
	 
	 
	$('#table2').bootstrapTable({
		toolbar: '#toolbar2',
		sidePagination: 'client',
		pagination:false,
        columns : [ 
            {'checkbox':true,width:5},
			{field:'name',title:'组名',width:50}
		],
		onLoadSuccess:function(data){
			$.each(data,function(index){
				if(this.segment==1){
					$('#table2').bootstrapTable("check",index);
				}
			});
		}
    });
	
});
	 
 
function okFunc(){
	if(!(row = h.getSelectRow($("#table")))) return;
	var rows = $("#table2").bootstrapTable('getSelections');
	var groupId_arr = new Array();
	$.each(rows,function(index){
		groupId_arr.push(this.id);
	})
	h.ajax({
		url:"${pageContext.request.contextPath }/sys/permission2/saves.htmlx",
		data:{
			"groupIds":groupId_arr,
			"resourceId":row.resourceId,
			"orgType":orgType
		},
		traditional: true,//支持传数组参数
		dataSuccess:function(data){
			$("#table2").bootstrapTable('refresh');
		}
	})
	
}
 


//=============ajax===============

</script>