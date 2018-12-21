<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />

<body class='iframebody' >
 
<%-- <h4><span class="label label-info">${parentName }</span></h4> --%>
		<table id="table"></table>
	</body>
	
 
	<script>
	var sysId = "${sysId }";
	var orgType = "${orgType }";
	var parentId = "${parentId }"; 
	//初始化
	$(function(){
		$('#table').bootstrapTable({
			url:'${pageContext.request.contextPath}/sys/menu/resourcePage.htmlx',
			queryParams:function(p){
				 p.sysId = sysId;
				 p.orgType = orgType;
				 p.parentId = parentId;
				 return p;
			},
			searchbar : [
			             {'name':'资源名称','code':'query[t#name_S_LK]'},
			             {'name':'资源路径','code':'query[t#url_S_LK]'}
					],
			searchbarSize:5,
			striped : false, //间隔色
			cache : false, //缓存
			sortable : true, //是否启用排序
			sortOrder : "asc", //排序方式
			sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
			pagination : true, //是否显示分页
			sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
			pageNumber : 1, //初始化加载第一页，默认第一页
			pageSize : 10, //每页的记录行数（*）
			pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
			paginationLoop : false,
			clickToSelect:true,
			minRows:10,
			columns : [
			        {'checkbox':true,width:5},
			        {field:'name',title:'资源名称',width:50,formatter: function(value,row,index){
		        			return "<i class='"+row.icon+"'></i> "+row.name;
					}},
		        	{field:'url',title:'资源路径',width:50}
			],
			onLoadSuccess:function(data){
				 $.each(data.rows,function(index){
					if(this.state == 1){
						$('#table').bootstrapTable("check",index);
					}
				}) 
			},
			onClickRow:function(row,$el){
				if(row.state == 0){
					row.state  = 1;
	   				addAjax(row.id);
	   			}else{
	   				row.state  = 0;
	   				delAjax(row.id);
	   			}
			},
			onCheckAll:function(rows){
				return false;
			}
		});
	});

	


	

	//=============ajax===============
	

	function addAjax(resourceId){
		
		$.ajax({
			url:"${pageContext.request.contextPath }/sys/menu/add.htmlx",
			data:{
				"resource.id":resourceId,
				"sysId":sysId,
				"orgType":orgType,
				"parentId":parentId
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
				//top.$.modalDialog.openner.datagrid('reload');
				//top.$.modalDialog.handler.dialog('close');
				showErr("出错，请刷新重新操作");
			}
		});	
	}

	function delAjax(resourceId){
		$.ajax({
			url:"${pageContext.request.contextPath }/sys/menu/del.htmlx",
			data:{
				"resource.id":resourceId,
				"orgType":orgType,
				"parentId":parentId
			},
			dataType:"json",
			type:"POST",
			cache:false,
			success:function(data){
				if(data.success){
					showMsg("删除成功！");
				}else{
					showErr(data.msg);
				}
			},
			error:function(){
				//top.$.modalDialog.openner.datagrid('reload');
				//top.$.modalDialog.handler.dialog('close');
				showErr("出错，请刷新重新操作");
			}
		});	
	}


	</script>	
	