<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />
<style>

</style>
<html>

<body style='padding: 0px 15px;'>
	<!-- <div class="panel panel-default">
		<div class="panel-heading">查询条件</div>
		<div class="panel-body">
			<div class="form-row">
				<div class="form-group">
					<label class="col-sm-2" for="empId">帐号</label>
					<select id="orgType" name="orgType"></select>
					<div class="col-sm-2">
						<button class="btn btn-default" type="submit">Button</button>
					</div>
				</div>
			</div>
		</div>
	</div> -->

		<!-- <div id="searchbar" class="box-tools">
			<div id="search" class="input-group">
				<div class="input-group-btn">
					<button class="btn btn-default" type="button" data-toggle="dropdown">
						<span>帐号</span>
						<i class="caret"></i>
					</button>
					<ul class="dropdown-menu">
						<li class="active" data-search-property="query[t#empId_S_LK]">
							<a >帐号</a>
						</li>
						<li data-search-property="query[t#name_S_LK]">
							<a>名称</a>
						</li>
					</ul>
				</div>
				<input name="searchProperty" type="hidden">
				<input name="searchValue" class="form-control" type="text" value="" placeholder="搜 索">
				<div class="input-group-btn"><button class="btn btn-default"><i class="fa fa-search"></i></button></div>
			</div>
		</div> -->



	<!-- 工具栏的按钮，可以自定义事件 -->
	<div id="toolbar" class="btn-group">
		<button type="button" class="btn btn-default"   onclick=addOpen()>
			<i class="glyphicon glyphicon-plus"></i><!-- 新增 -->
		</button>
		<button type="button" class="btn btn-default" onclick=editOpen()>
			<i class="glyphicon glyphicon-pencil"></i><!-- 修改 -->
		</button>
		<button type="button" class="btn btn-default" onclick=delFunc()>
			<i class="glyphicon glyphicon-trash"></i><!-- 删除 -->
		</button>
	</div>

	<div >
		<table id="table"></table>
	</div>

</body>
</html>
<script>
	//初始化
	$(function() {
		$("#orgType").select2({
			data:"${pageContext.request.contextPath}/sys/organization/list.htmlx",
			valueField:"id",
			textField:"orgName",
			value:""
		});
		
		$('#table').bootstrapTable({
							url : "${pageContext.request.contextPath}/sys/user/page.htmlx",
							toolbar : '#toolbar', //工具按钮
							//searchbar : '#searchbar', //工具按钮
							searchbar : [{'name':'帐号','code':'query[t#empId_S_LK]'},
							             {'name':'名称','code':'query[t#name_S_LK]'}
							             ], //工具按钮
							striped : false, //间隔色
							cache : false, //缓存
							pagination : true, //是否显示分页
							sortable : true, //是否启用排序
							sortOrder : "asc", //排序方式
							sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
							pageNumber : 1, //初始化加载第一页，默认第一页
							pageSize : 10, //每页的记录行数（*）
							pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
							paginationLoop : false,
							showColumns : true, //是否显示所有的列
							showRefresh : true, //是否显示刷新按钮
							clickToSelect:true,
							//search:true,
							columns : [
							        {'radio':true,width :10},
									{
										field : 'empId',
										title : '帐号',
										width : 120,
										align : 'center',
										editable : {
											type : 'text',
											title : '账号',
											validate : function(value) {
												value = $.trim(value);
												if (!value) {
													return 'This field is required';
												}

												var data = $('#table')
														.bootstrapTable(
																'getData'), index = $(
														this).parents('tr')
														.data('index');
												console.log(data[index]);
												return '';
											}
										}
									},
									{
										field : 'name',
										title : '姓名',
										width : 120,
										align : 'center',
										sortable : true
									},
									{
										field : 'organization.orgType_I_EQ',
										title : '机构类型',
										width : 120,
										align : 'center',
										filterControl:'select',
										filterData:'json:{"5":"系统运维","2":"供应商"}',
										formatter : function(value, row, index) {
											if (row.organization.orgType == 1) {
												return "医院";
											} else if (row.organization.orgType == 2) {
												return "供应商";
											} else if (row.organization.orgType == 4) {
												return "供应商C端";
											} else if (row.organization.orgType == 3) {
												return "监管机构";
											} else if (row.organization.orgType == 5) {
												return "系统运维";
											} else if (row.organization.orgType == 6) {
												return "GPO";
											} else if (row.organization.orgType == 7) {
												return "专家组";
											}
										}
									},
									{
										field : 'organization.orgName',
										title : '机构名称',
										width : 120,
										align : 'center',
										formatter : function(value, row, index) {
											if (row.organization) {
												return row.organization.orgName;
											}
										}
									},
									{
										field : 'isAdmin',
										title : '是否管理员',
										width : 110,
										align : 'center',
										formatter : function(value, row, index) {
											if (row.isAdmin == 1) {
												return "<img src ='${pageContext.request.contextPath}/resources/js/jquery-easyui/themes/icons/ok.png' />";
											}
										}
									},
									{
										field : 'isLocked',
										title : '是否锁定',
										width : 110,
										align : 'center',
										formatter : function(value, row, index) {
											if (row.isLocked == 1) {
												return "<img src ='${pageContext.request.contextPath}/resources/js/jquery-easyui/themes/icons/disable.png' />";
											}
										}
									},
									{
										field : 'isDisabled',
										title : '是否禁用',
										width : 110,
										align : 'center',
										formatter : function(value, row, index) {
											if (row.isDisabled == 1) {
												return "<img src ='${pageContext.request.contextPath}/resources/js/jquery-easyui/themes/icons/disable.png' />";
											}
										}
									} ],
							onEditableSave : function(field, row, oldValue, $el) {
									$.ajax({
											url : "${pageContext.request.contextPath }/sys/user/editCell.htmlx",
											data : {
												"user" : JSON.stringify(row),
												"field" : field
											},
											dataType : "json",
											type : "POST",
											cache : false,
											success : function(data) {
												if (data.success) {
													showMsg("修改成功");
												} else {
													showErr("出错，请刷新重新操作");
												}
											},
											error : function() {
												showErr("出错，请刷新重新操作");
											}
										});
							},
						onDblClickRow:function(row,$el){
							editOpen(row);
						}
						});
		
	});
	//删除
	function delFunc() {
		var selobj = $('#table').bootstrapTable('getSelections');
		console.log(selobj);
		if (selobj.length == 0) {
			showInfo('请选择一行');
			return;
		}
		var id = selobj[0].id;

		if(confirm('确认要删除此成员?')){
			delAjax(id);
		}
		
	}

	//重置密码
	function resetPwd() {
		var selobj = $('#dg').datagrid('getSelected');
		if (selobj == null) {
			return;
		}
		var id = selobj.id;
		var empId = selobj.empId;
		$.messager.confirm('确认信息', '确认要重置密码吗?', function(r) {
			if (r) {
				resetPwdAjax(id, empId);
			}
		});

	}
	//手动解锁
	function unlockFunc() {
		var selobj = $('#dg').datagrid('getSelected');
		if (selobj == null) {
			return;
		}
		var id = selobj.id;
		var empId = selobj.empId;
		$.messager.confirm('确认信息', '确认要解锁帐号吗?', function(r) {
			if (r) {
				unlockAjax(id, empId);
			}
		});

	}
	//禁用
	function disabledFunc() {
		var selobj = $('#dg').datagrid('getSelected');
		if (selobj == null) {
			return;
		}
		var id = selobj.id;
		var empId = selobj.empId;
		$.messager.confirm('确认信息', '确认要禁用帐号吗?', function(r) {
			if (r) {
				disabledAjax(id, empId);
			}
		});

	}
	//解禁
	function abledFunc() {
		var selobj = $('#dg').datagrid('getSelected');
		if (selobj == null) {
			return;
		}
		var id = selobj.id;
		var empId = selobj.empId;
		$.messager.confirm('确认信息', '确认要对帐号解禁吗?', function(r) {
			if (r) {
				abledAjax(id, empId);
			}
		});

	}
	//搜索
	function search(val, name) {
		if (name == 'empId') {
			$('#dg').datagrid('load', {
				"query['t#empId_S_LK']" : val
			});
		} else if (name == 'name') {
			$('#dg').datagrid('load', {
				"query['t#name_S_LK']" : val
			});
		} else if (name == 'cell') {
			$('#dg').datagrid('load', {
				"query['t#cell_S_LK']" : val
			});
		} else if (name == 'title') {
			$('#dg').datagrid('load', {
				"query['t#title_S_LK']" : val
			});
		}
	}
	
	
	//弹窗增加
	function addOpen() {
		top.toptable = $("#table");
		top.bootbox.dialog({
			  message: "${pageContext.request.contextPath}/sys/user/add.htmlx",
			  title: "添加成员",
			  width:800,
			  height:400,
			  buttons: {
			    "取消": {}, 
			    success: {   
			      label: "保存",
			      callback: function() {
			    	 top.$("#tf")[0].contentWindow.submit();
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
		top.toptable = $("#table");
		top.editrow = row;
		top.bootbox.dialog({
			  message: "${pageContext.request.contextPath}/sys/user/edit.htmlx",
			  title: "修改成员",
			  width:800,
			  height:400,
			  buttons: {
			    "取消": {}, 
			    success: {   
			      label: "保存",
			      callback: function() {
			    	 top.$("#tf")[0].contentWindow.submit();
			    	  return false;
			      }
			    }
			  }
			});
	}
	
	//=============ajax===============
	function delAjax(id) {
		$.ajax({
			url : "${pageContext.request.contextPath }/sys/user/del.htmlx",
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

	function resetPwdAjax(id, empId) {
		$
				.ajax({
					url : "${pageContext.request.contextPath }/sys/user/resetPwd.htmlx",
					data : {
						"id" : id,
						"empId" : $.md5(empId)
					},
					dataType : "json",
					type : "POST",
					cache : false,
					success : function(data) {
						if (data.success) {
							$('#dg').datagrid('reload');
							showMsg("重置密码成功");
						} else {
							showErr("出错，请刷新重新操作");
						}
					},
					error : function() {
						showErr("出错，请刷新重新操作");
					}
				});

	}

	function unlockAjax(id, empId) {
		$.ajax({
			url : "${pageContext.request.contextPath }/sys/user/unlock.htmlx",
			data : {
				"id" : id
			},
			dataType : "json",
			type : "POST",
			cache : false,
			success : function(data) {
				if (data.success) {
					$('#dg').datagrid('reload');
					showMsg("手动解锁成功");
				} else {
					showErr("出错，请刷新重新操作");
				}
			},
			error : function() {
				showErr("出错，请刷新重新操作");
			}
		});

	}

	function disabledAjax(id, empId) {
		$
				.ajax({
					url : "${pageContext.request.contextPath }/sys/user/disabled.htmlx",
					data : {
						"id" : id
					},
					dataType : "json",
					type : "POST",
					cache : false,
					success : function(data) {
						if (data.success) {
							$('#dg').datagrid('reload');
							showMsg("帐号禁用成功");
						} else {
							showErr("出错，请刷新重新操作");
						}
					},
					error : function() {
						showErr("出错，请刷新重新操作");
					}
				});

	}

	function abledAjax(id, empId) {
		$.ajax({
			url : "${pageContext.request.contextPath }/sys/user/abled.htmlx",
			data : {
				"id" : id
			},
			dataType : "json",
			type : "POST",
			cache : false,
			success : function(data) {
				if (data.success) {
					$('#dg').datagrid('reload');
					showMsg("帐号解禁成功");
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