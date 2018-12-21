<%@page import="com.shyl.sys.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />

<body class='iframebody'>
		<form id="form1" name="form1" method="post">
			<div class="row ">
				<div class="col-xs-6">
					
					<div class="form-group">
						<label for="empId">帐号</label>
						<div class="input-group">
						  <span class="input-group-addon"  id="empIdPre">机构前缀</span>
						  <input class="form-control"  id="empId" name="empId" type="text" />
						</div>
						
					</div>
					<div class="form-group">
						<label for="name">姓名</label>
						<input class="form-control" id="name" name="name" type="text" />
					</div>
					<div class="form-group">
						<label for="cell">手机</label>
						<input class="form-control" id="cell" name="cell" type="text" />
					</div>
					
					
				</div>
				
				<div class="col-xs-6">
					<div class="form-group">
						<label for="mail">邮箱</label>
						<input class="form-control" id="mail" name="mail" type="text" />
					</div>
					<div class="form-group">
						<label for="title">职务</label>
						<input class="form-control" id="title" name="title" type="text" />
					</div>
					<div class="form-group">
						<label for="idcard">身份证</label>
						<input class="form-control" id="idcard" name="idcard" type="text"  />
					</div>
					
				</div>
			</div>
			
  
			<div  class="row  col-xs-12" >
					<div class="form-group">
						<label >机构</label>
						<div class=" form-horizontal">
							<div class="col-xs-3" style="height:31px;padding-left:0px;">
								<select id="orgType" name="organization.orgType"></select> 
							</div>
							<div class="col-xs-6" style="padding-left:0px;">
								<input type="text"  class="form-control" id="orgName" name="orgName"   />
								<input type="hidden" id="orgId" name="organization.id" />
							</div>
						</div>
					</div>
				</div>
			
			<div class="row" id="groups" ></div>
			
			<input type="hidden" id="id" name="id" />
			<input type="hidden" id="empIdPrefix" name="empIdPrefix" />
			<input type="hidden" id="group" name="group" value="" />
		</form>
		
</body>

<script>


	//初始化
	$(function() {
		var orgType = "${user.organization.orgType}";
		var orgPId = "${user.organization.parentOrgId}";
		var disabled1 = disabled2 = disabled3 = disabled4 = true;
		if(orgType == 1){
			disabled1 = disabled2 = false;
		}else if(orgType == 2){
			disabled2 = false;
		}else if(orgType == 3){
			disabled3 = false;
		}else if(orgType == 4){
			disabled4 = false;
		}else if(orgType == 9){
			 disabled1 = disabled2 = disabled3 = disabled4 = false;
		}
		$("#orgType").select2({
			data : [ {
				"id" : 1,
				"text" : '连锁总部',
				"disabled": disabled1
			},{
				"id" : 2,
				"text" : '连锁直营',
				"disabled": disabled2
			},{
				"id" : 3,
				"text" : '连锁加盟',
				"disabled": disabled3
			} ,{
				"id" : 4,
				"text" : '非连锁店',
				"disabled": disabled4
			} ],
			//multiple:true,
			width:"100%"
		});    
		//setSelectVal_indexFunc($("#orgType"),[1,2]);
/* 		if($("#orgType").attr("multiple")){
			$("#orgType").val([1,2]); 
		} */
		 
		$("#orgType").on("change",function(e) {
			$("#orgName").val("");
		  	$("#orgId").val("");
		  	$("#groups").empty();
		  	
			var columns = [
							{field : 'orgName',title : '总部名称',width : 60,align : 'left'}
							];
			if($("#orgType").val()==2 || $("#orgType").val()==3){
				columns =[
					{field : 'parentOrgName',title : '总部名称',width : 60,align : 'left'},
					{field : 'orgName',title : '门店名称',width : 60,align : 'left'}
					];
			}
			$("#orgName").hSelectTable("refreshOptions",{
				columns:columns, 
				queryParams:function(params){
			  		params["query['t#orgType_I_EQ']"] = $("#orgType").val();
			  		if(orgType != 9){
			  			params["query['t#parentOrgId_L_EQ']"] = orgPId;
			  		}
			  		return params;
			  	}
			});
		});
		
		$("#orgName").hSelectTable({
		  	url:"${pageContext.request.contextPath}/sys/organization/page.htmlx",
		  	queryParams:function(params){
		  		params["query['t#orgType_I_EQ']"] = $("#orgType").val();
		  		return params;
		  	},
		  	key:"query['t#orgName_S_LK']",
		  	columns:[],
			height:200,
		  	onSelect:function(row){
		  		$("#orgName").val(row.orgName);
		  		$("#orgId").val(row.id);
		  		var pre = row.parentOrgCode?row.parentOrgCode:"SYS";
		  		$("#empIdPre").text(pre);
		  		$("#empIdPrefix").val(pre);
		  		getGroups();
		  	}
		   });
		  
		$("#orgType").trigger("change");
		
		$('#form1').initValid(["empId","orgName"]);
	});
	 
	function submitAjax(){
		var groupArr = new Array();
		$.each($("#groups").find(":checked"),function(index){
			groupArr.push(this.id);
		});
		$("#group").val(groupArr);

		h.ajax({
			url : "${pageContext.request.contextPath}/sys/user/add.htmlx",
			data : $('#form1').serialize(),
			beforeSend : function() {
				if(!h.isValid($("#form1"))) return false;
			},
			dataSuccess : function(data) {
				h.bootboxRefresh("新增成功");	
			}
		});
	}
	
	function getGroups(){
		h.ajax({//查询组
			url : "<c:out value='${pageContext.request.contextPath }'/>/sys/group/list.htmlx",
			data : {
				"query['t#organization.id_L_EQ']" : $("#orgId").val()
			},
			dataSuccess : function(data) {
				$("#groups").empty();
				$.each(data,function(index){
					var d =$('<div class="col-xs-4"><div class="form-group"><div class="checkbox"><label><input type="checkbox"  id="'+this.id+'"> '+this.name+'</label></div></div></div>');
					$("#groups").append(d);
				});
			}
		});
	}
</script>
