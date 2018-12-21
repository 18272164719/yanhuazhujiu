<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />

<script src="${pageContext.request.contextPath}/resources/bootstrapTable/extensions/tree/bootstrapTreegrid.js"></script>
	
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/bootstrapTable/extensions/reorder-rows/bootstrap-table-reorder-rows.css" rel="stylesheet">
	
<html>
<body>
	<div id="searchbar">
		<div class="input-group pull-right">
			<select id="orgType" name="orgType"></select> 
		</div>
	</div>
<!-- 工具栏的按钮，可以自定义事件 -->
		<div id="toolbar" class="btn-group">
			<button type="button" class="btn btn-default"   onclick="addOpen()">
				<i class="glyphicon glyphicon-plus"></i> 新增
			</button>
<!-- 			<button type="button" class="btn btn-default" onclick="editOpen()">
				<i class="glyphicon glyphicon-pencil"></i>修改
			</button> -->
			<button type="button" class="btn btn-default" onclick="delFunc()">
				<i class="glyphicon glyphicon-trash"></i> 删除
			</button>
		</div>
	<table id="table"></table>

</body>
</html>

<script type="text/javascript" src="${pageContext.request.contextPath }/resources/bootstrapTable/extensions/reorder-rows/jquery.tablednd.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/bootstrapTable/extensions/reorder-rows/bootstrap-table-reorder-rows.js"></script>

<script>
var sysId = "admin";
var orgType = "9";
var dragId;
	//初始化
	$(function() {
		$("#orgType").select2({
			width:200,
			data: [{
				"text": "系统管理", 
			    "children" : [
			        {
			            "id": "admin-9",
			            "text": "系统管理员",
			            "selected":true,
			        },{
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
		});
		
		$('#table').bootstrapTable({
			url:"${pageContext.request.contextPath}/sys/menu/list.htmlx",
			queryParams:function(p){
				p.sysId = "admin";
				p.orgType = 9;
				return p;
			},
            toolbar: '#toolbar',
            righttoolbar: '#searchbar',
            treeView: true,
            treeId: "id",
            treeField: "name",
            treeRootLevel: 1,
            sidePagination: 'client',
            reorderableRows:true,
            treeCollapseAll:true,
            columns : [
                {'radio':true,width:15},
				{field:'name',title:'资源名称',width:180},
	        	{field:'RURL',title:'资源路径',width:180},
	        	{field:'sort',title:'顺序号',width:80},
				{field:'operation',title:'操作',width:150,align:'center',
		    		formatter: function(value,row,index){
		    			return "<a href='javascript:editResourceFunc("+row.resourceId+");' >查看资源</a>";
					}
				}],
				onReorderRowsDrag:function(table,row){
					dragId = $(row).prev().data("id");
				},
				onReorderRowsDrop:function(table,row){
					var preId = $(row).prev().data("id");
					var sourceId= $(row).data("id");
					if(dragId == preId)
						return;
					//$('#table').bootstrapTable("refresh");
					h.ajax({
						url:"${pageContext.request.contextPath}/sys/menu/drop.htmlx",
						data:{
							"sysId":sysId,
							"orgType":orgType,
							"sourceId":sourceId,
							"preId":preId
						},
						dataSuccess:function(){
							$('#table').bootstrapTable("refresh");
						}
					})
				}
        });
	});

//弹窗增加
function addOpen() {
	var boxtip = "根结点";
	var parentId = -1;
	var selobj = $('#table').bootstrapTable('getSelections');
	if (selobj.length != 0) {
		parentId = selobj[0].id;
		boxtip = selobj[0].name;
	}
	top.toptable = $("#table");
	top.bootbox.dialog({
		  message: "${pageContext.request.contextPath}/sys/menu/add.htmlx?sysId="+sysId+"&orgType="+orgType+"&parentId="+parentId,
		  title: "添加菜单 - "+boxtip,
		  width:800,
		  height:480,
		  onEscape: function() {
			  $('#table').bootstrapTable('refresh');
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

	if(confirm('确认删除?')){
		$.ajax({
			url:"${pageContext.request.contextPath }/sys/menu/delete.htmlx",
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


function editResourceFunc(id){
	top.bootbox.dialog({
		  message: "${pageContext.request.contextPath }/sys/menu/editResource.htmlx?resourceId="+id,
		  title: "修改资源",
		  width:400,
		  height:300,
		  table:$("#table"),
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
//=============ajax===============


function dragAjax(targetId,sourceId,point){
	$.ajax({
				url:"${pageContext.request.contextPath }/sys/menu/drag.htmlx",
				data:{
					targetId:targetId,
					sourceId:sourceId,
					point:point
				},
				dataType:"json",
				type:"POST",
				cache:false,
				success:function(data){
					if(data.success){
						//dgreload();
						showMsg("调整完毕！");
					} 
				},
				error:function(){
					showErr("出错，请刷新重新操作");
				}
	});	
}

</script>