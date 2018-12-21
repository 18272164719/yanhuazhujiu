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
		<button type="button" class="btn btn-default"   onclick=addOpen()>
			<i class="fa fa-plus"></i> 新增
		</button>
		<button type="button" class="btn btn-default" onclick=editOpen()>
			<i class="fa fa-pencil"></i> 修改
		</button>
		<button type="button" class="btn btn-default" onclick=delFunc()>
			<i class="fa fa-trash"></i> 删除
		</button>
		<button type="button" class="btn btn-default" onclick=unlockFunc()>
			<i class="fa fa-unlock-alt"></i> 解锁
		</button>
		<button type="button" class="btn btn-default" onclick=resetPwd()>
			<i class="fa fa-key"></i> 重置密码
		</button>
		<button type="button" class="btn btn-default" onclick=disabledFunc()>
			<i class="fa fa-eye-slash"></i> 禁用
		</button>
		<button type="button" class="btn btn-default" onclick=abledFunc()>
			<i class="fa fa-eye"></i> 解禁
		</button>
		<button type="button" class="btn btn-default exportXls" data-filename="会员管理">
			<i class="fa fa-file-excel-o"></i> 导出
		</button>
	</div>
	
	
	<div >
		<table id="table" ></table>
	</div>
	
	
</body>
</html>

 

<script type="text/javascript" src="<c:out value='${pageContext.request.contextPath }'/>/resources/js/jquery.md5.js"></script>

<script>
	//初始化
	$(function() {
		 
		$('#table').bootstrapTable({
			url : "<c:out value='${pageContext.request.contextPath }'/>/sys/user/page.htmlx",
			toolbar : '#toolbar',
			searchbar : [
	             {'name':'帐号','code':'query[t#empId_S_LK]'},
	             {'name':'姓名','code':'query[t#name_S_LK]'},
	             {'name':'姓名简拼','code':'query[t#name_FL_LK]'}
			],
			filterControl: true,
			columns : [
			        {'radio':true,width :15},
					{field : 'empId',title : '帐号',width : 120},
					{field : 'name',title : '姓名',width : 120},
					{field : 'organization.orgType_I_EQ',title : '机构类型',width : 120,filterControl:'select',filterData:'json:{"1":"连锁总部","2":"连锁直营","3":"连锁加盟","4":"非连锁店"}',formatter : function(value, row, index) {
							if (row.organization.orgType == 1) {
								return "连锁总部";
							} else if (row.organization.orgType == 2) {
								return "连锁直营";
							} else if (row.organization.orgType == 3) {
								return "连锁加盟";
							}else if (row.organization.orgType == 4) {
								return "非连锁店";
							}
						}
					},
					{field : 'organization.orgName',title : '机构名称',width : 120},
					{field : 'segment',title : '角色',width : 110},
					{field : 'isLocked_I_EQ',title : '是否锁定',width : 110,filterControl:'select',filterData:'json:{"0":"否","1":"是"}',formatter : function(value, row, index) {
							if (row.isLocked == 1) {
								return '<i class="fa fa-check"></i>';
							}
						}
					},
					{field : 'isDisabled_I_EQ',title : '是否禁用',width : 110,filterControl:'select',filterData:'json:{"0":"否","1":"是"}',formatter : function(value, row, index) {
							if (row.isDisabled == 1) {
								return '<i class="fa fa-check"></i>';
							}
						}
					} ],
			onDblClickRow:function(row,$el){
				editOpen(row);
			}
		});

		$('#table').bootstrapTable('hideColumn', 'segment');
	});
	//删除
	function delFunc() {
		if(!(row = h.getSelectRow($("#table")))) return;
		if(confirm('确认要删除此成员?')){
			delAjax(row.id);
		}
		
	}

	//重置密码
	function resetPwd() {
		if(!(row = h.getSelectRow($("#table")))) return;

		if(confirm('确认要重置密码吗?')){
			resetPwdAjax(row.id,row.empId);
		}
	}
	//手动解锁
	function unlockFunc() {
		if(!(row = h.getSelectRow($("#table")))) return;
		if(confirm('确认要解锁帐号吗?')){
			unlockAjax(row.id);
		}
	}
	//禁用
	function disabledFunc() {
		if(!(row = h.getSelectRow($("#table")))) return;
		if(confirm('确认要禁用帐号吗?')){
			disabledAjax(row.id);
		}
	}
	//解禁
	function abledFunc() {
		if(!(row = h.getSelectRow($("#table")))) return;

		if(confirm('确认要对帐号解禁吗?')){
			abledAjax(row.id);
		}
	}
	   
	//弹窗增加
	function addOpen() {
		top.bootbox.dialog({
			  message: "<c:out value='${pageContext.request.contextPath }'/>/sys/user/add.htmlx",
			  title: "添加成员",
			  width:600,
			  height:450,
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
		return;
		
	}

	//弹窗修改
	function editOpen(row) {
		if(!row && !(row = h.getSelectRow($("#table")))) return;
		
		top.editrow = row;
		top.bootbox.dialog({
			  message: "<c:out value='${pageContext.request.contextPath }'/>/sys/user/edit.htmlx?id="+row.id,
			  title: "修改成员",
			  width:600,
			  height:450,
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
	
	//更多查询条件
	function paramFunc(){
		top.toptable = $("#table");
		top.bootbox.dialog({
			  message: "<c:out value='${pageContext.request.contextPath }'/>/user/base/area/param.htmlx",
			  title: "更多选项",
			  width:400,
			  height:300,
			  buttons: {
			    "取消": {}, 
			    success: {   
			      label: "查询",
			      callback: function() {
			    	  h.iframe(this).paramQuery();
			    	  return false;
			      }
			    }
			  }
			});
		
	}
	
	//=============ajax===============
	function delAjax(id) {
		h.ajax({
			url : "<c:out value='${pageContext.request.contextPath }'/>/sys/user/del.htmlx",
			data : "id=" + id,
			dataSuccess : function(data) {
				$('#table').bootstrapTable("refresh");
				h.showMsg("删除成功");
			}
		});

	}

	function resetPwdAjax(id, empId) {
		h.ajax({
			url : "<c:out value='${pageContext.request.contextPath }'/>/sys/user/resetPwd.htmlx",
			data : {
				"id" : id,
				"empId" : $.md5(empId)
			},
			dataSuccess : function(data) {
				$('#table').bootstrapTable("refresh");
				h.showMsg("重置密码成功");
			}
		});

	}

	function unlockAjax(id) {
		h.ajax({
			url : "<c:out value='${pageContext.request.contextPath }'/>/sys/user/unlock.htmlx",
			data : {
				"id" : id
			},
			dataSuccess : function(data) {
				$('#table').bootstrapTable("refresh");
				h.showMsg("手动解锁成功");
			}
		});

	}

	function disabledAjax(id, empId) {
		h.ajax({
			url : "<c:out value='${pageContext.request.contextPath }'/>/sys/user/disabled.htmlx",
			data : {
				"id" : id
			},
			dataSuccess : function(data) {
				$('#table').bootstrapTable("refresh");
				h.showMsg("帐号禁用成功");
			}
		});

	}

	function abledAjax(id, empId) {
		h.ajax({
			url : "<c:out value='${pageContext.request.contextPath }'/>/sys/user/abled.htmlx",
			data : {
				"id" : id
			},
			dataSuccess : function(data) {
				$('#table').bootstrapTable("refresh");
				h.showMsg("帐号解禁成功");
			}
		});
	}
	
</script>