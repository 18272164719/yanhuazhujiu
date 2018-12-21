<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/fileinput/css/fileinput.css" />
<style>


    .box{
    	/* margin-bottom: 0px; */
    }
</style>
<html>
<body style="padding:10px;">

		<div class="box" style="padding:10px;">

			<div class="row">
				<div class="col-xs-6">
					<textarea rows="6" cols="75%" id="mysql" name="mysql"
						style="height: 100px; padding: 5px;">select * from t_sys_user</textarea>

				</div>
				<div class="col-xs-6 lefeP0">
					<!-- 工具栏的按钮，可以自定义事件 -->
					<div id="toolbar" class="btn-group">
						<button type="button" class="btn btn-default" onclick="ff()">
							<i class="glyphicon glyphicon-plus"></i>
							查询
						</button>
						<button type="button" class="btn btn-default" onclick="delFunc()">
							<i class="glyphicon glyphicon-pencil"></i>
							执行修改/删除
						</button>
						<button type="button" class="btn btn-default" onclick="tt()">
							<i class="glyphicon glyphicon-plus"></i>
							tt
						</button>
					</div>

				</div>
			</div>
		</div>

        

	<table  id="table" ></table>
</body>
</html>



<script>

$(function(){
	
	
	//$("#purchaseOrderCode").hValid({"empty":[],"minLength":[2],length:[3,6]});
	
	/* $("#purchaseOrderCode1").hSelectTable({
		url:"${pageContext.request.contextPath}/sys/menu/list.htmlx",
		queryParams:function(p){
			p.sysId = "admin";
			p.orgType = 9;
			return p;
		},
        treeView: true,
        treeId: "id",
        treeField: "name",
        treeRootLevel: 1,
        sidePagination: 'client',
		key:"query['t#id_L_LK']",
	  	columns:[
				{field : 'id',title : '采购单号',width : 60,align : 'left'},
				{field : 'name',title : '供应商',width : 60,align : 'left'},
		],
		width:300,
		height:300,
	  	onSelect:function(row){
	  		alert();
	  	}
	 }); */
})

function delFunc(){
	if (confirm('确认要执行SQL吗?')){
		var mysql = $("#mysql").val();
		delAjax(mysql);
	}
}


function ff(){
	var mysql = $("#mysql").val();
	queryAjax(mysql);
}

function aa(rowtitle,mysql){

	var columns = "";
	var array =[];
	var columns=[];
	for ( var key in rowtitle[0]) {
		 array.push({field:'',title:'',width:''});
	}
     columns.push(array);
     var i = 0;
     for ( var key in rowtitle[0]) {
    	columns[0][i]['field']= key;
 	    columns[0][i]['title']= key;
 	    columns[0][i]['width']= '100';
 	    i++;
	}
     $('#table').bootstrapTable("destroy");
     $('#table').bootstrapTable({
     	url:"${pageContext.request.contextPath }/sys/sql/dosql.htmlx",
     	queryParams:function(p){
     		p.mysql = mysql;
 			return p;
 		},
  		striped : false, //间隔色
  		cache : false, //缓存
  		pagination : true, //是否显示分页
  		sortable : true, //是否启用排序
  		sortOrder : "asc", //排序方式
  		onlyPagination:true,
  		sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
  		pageNumber : 1, //初始化加载第一页，默认第一页
  		pageSize : 10, //每页的记录行数（*）
  		pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
  		paginationLoop : false,
  		clickToSelect:true,
  		columns:columns,
  		onClickRow:function(row,$el){
  			searchDetail(row);
  		}
  	});
      
	
}


//初始化
//=============ajax===============
function queryAjax(mysql){
	
	$.ajax({
		url:"${pageContext.request.contextPath }/sys/sql/dosql.htmlx",
				data:{
					"mysql":mysql
				},
				dataType:"json",
				type:"POST",
				cache:false,
				success:function(data){
					aa(data.rows,mysql);
				},
				error:function(){
					showErr("出错，请刷新重新操作");
				}
	});	
	
}

function delAjax(mysql){
	
	$.ajax({
		url:"${pageContext.request.contextPath }/sys/sql/del.htmlx",
				data:{
					"mysql":mysql
				},
				dataType:"json",
				type:"POST",
				cache:false,
				success:function(data){
					if(data.success){
						showMsg(data.msg);
					}else{
						showErr(data.msg);
					}
				
				},
				error:function(){
					showErr("出错，请刷新重新操作");
				}
	});	
	
}
  
  
function tt(){
	h.ajax({
		 url:"${pageContext.request.contextPath}/sys/sql/batch.htmlx",
		 data:{
			 "a":123
		 },
		dataSuccess:function(data){
			h.log(data);
		}	 
		 
	 })
}


</script>