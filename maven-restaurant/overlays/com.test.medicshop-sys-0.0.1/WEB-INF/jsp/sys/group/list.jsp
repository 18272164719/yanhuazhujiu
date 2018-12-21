<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />

<html>
<style>
.bs-checkbox .th-inner{
	display:none;
}
</style>
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
	  <table  id="table2" ></table>
</div>
</div>	

</body>
</html>

<script>
function searchDetail(row){
	$('#table2').bootstrapTable("refreshOptions",{
 		queryParams:function(params){
 			params.groupId = row.id;
			return params;
		}
	});
}

//初始化
$(function() {
	$('#table').bootstrapTable({
		url:"<c:out value='${pageContext.request.contextPath }'/>/sys/group/page.htmlx",
		toolbar : '#toolbar', 
		/* searchbar : [
					 {'name':'组名','code':'query[t#name_S_LK]'},
		             {'name':'机构名称','code':'query[t#organization.orgName_S_LK]'}
		             
		],  */
		toolbarSize:6,
		searchbarSize:6,
		onlyPagination:true,
		filterControl: true,
		columns : [
		        {'radio':true,width:15},
		        {field:'orgType_S_EQ',title:'机构类型',filterControl:'select',filterData:'json:{"1":"连锁总部","2":"连锁直营","3":"连锁加盟","4":"非连锁店"}',width:100,
					formatter: function(value,row,index){
						if (row.orgType==1){
							return "连锁总部";
						}else if (row.orgType==2){
							return "连锁直营";
						}else if (row.orgType==3){
							return "连锁加盟";
						}else if (row.orgType==4){
							return "非连锁店";
						}
					}
	        	},
	        	{field:'organization.orgName',title:'机构名称',width:100,
					formatter: function(value,row,index){
						return row.organization.orgName;
				}},
	        	{field:'name',title:'组名',width:100}
		],
		onClickRow:function(row,$el){
			searchDetail(row);
		},
		onDblClickRow:function(row,$el){
			editOpen(row);
		}
	});
	
	
	$('#table2').bootstrapTable({
		url:'<c:out value='${pageContext.request.contextPath }'/>/sys/group/groupUserPage.htmlx',  
		onlyPagination:true,
		columns : [
		        {'checkbox':true,width:10},
		        {field:'empId',title:'帐号',width:100,align:'center'},
				{field:'name',title:'姓名',width:100,align:'center'}
		],
		onLoadSuccess:function(data){
			$.each(data.rows,function(index){
				if(this.isSelected == 1){
					$('#table2').bootstrapTable("check",index);
				}
			})
			
		},
		onClickRow:function(row,$el){
			if(row.isSelected == 0){
				row.isSelected  = 1;
   				addAjax(row.id);
   			}else{
   				row.isSelected  = 0;
   				delAjax(row.id);
   			}
		}
	});
	
});
	
//弹窗增加
function addOpen() {
	top.bootbox.dialog({
		  message: "<c:out value='${pageContext.request.contextPath }'/>/sys/group/add.htmlx",
		  title: "添加组",
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

//弹窗修改
function editOpen(row) {
	if(!row && !(row = h.getSelectRow($("#table")))) return;
	top.editrow = row;
	top.bootbox.dialog({
		  message: "<c:out value='${pageContext.request.contextPath }'/>/sys/group/edit.htmlx",
		  title: "修改组",
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

//删除
function delFunc() {
	var selobj = $('#table').bootstrapTable('getSelections');
	if (selobj.length == 0) {
		showInfo('请选择一行');
		return;
	}
	var id = selobj[0].id;

	if(confirm('确认要删除组?')){
		h.ajax({
			url : "<c:out value='${pageContext.request.contextPath }'/>/sys/group/del.htmlx",
			data : "id=" + id,
			dataSuccess : function(data) {
				$('#table').bootstrapTable('refresh');
				$('#table2').bootstrapTable('removeAll');
				h.showMsg("删除成功");
			}
		});
	}
}



//=============ajax===============
function addAjax(userId){
		var groupNode = $('#table').bootstrapTable('getSelections')[0];
		$.ajax({
			url:"<c:out value='${pageContext.request.contextPath }'/>/sys/groupuser/add.htmlx",
			data:{
				userId:userId,
				groupId:groupNode.id
			},
			dataType:"json",
			type:"POST",
			cache:false,
			success:function(data){
				if(data.success){
					showMsg("新增成功！");
				}else{
					showErr(data.msg);
				}
			},
			error:function(){
				showErr("出错，请刷新重新操作");
			}
		});	
	}

	function delAjax(userId){
		var groupNode = $('#table').bootstrapTable('getSelections')[0];
		$.ajax({
			url:"<c:out value='${pageContext.request.contextPath }'/>/sys/groupuser/del.htmlx",
			data:{
				userId:userId,
				groupId:groupNode.id
			},
			dataType:"json",
			type:"POST",
			cache:false,
			success:function(data){
				if(data.success){
					showInfo("删除成功！");
				}else{
					showErr(data.msg);
				}
			},
			error:function(){
				showErr("出错，请刷新重新操作");
			}
		});	
	}
</script>