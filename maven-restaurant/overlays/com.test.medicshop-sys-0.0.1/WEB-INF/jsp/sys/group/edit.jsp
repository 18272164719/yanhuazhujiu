<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />

<body class='iframebody'>
		<form id="form1" class="form-horizontal" name="form1" method="post">
				<div class="form-group">
					<label for="orgType" class="col-xs-3 control-label">机构类型</label>
					<div  class="col-xs-7">
						<select id="orgType" name="orgType"></select> 
					</div>
				</div>
		
				<div class="form-group">
					<label  for="orgId" class="col-xs-3 control-label">机构名称</label>
					<div class="col-xs-7">
						<input type="text"  class="form-control" id="orgName" name="organization.orgName"   />
						<input type="hidden" id="orgId" name="organization.id" />
					</div>
				</div>
			
				<div class="form-group">
					<label  for="name" class="col-xs-3 control-label">组名</label>
					<div class="col-xs-7">
						<input class="form-control" id="name" name="name" type="text" />
					</div>
				</div>
			<input type="hidden" id="id" name="id" /> 
		</form>
</body>


<script>
	//初始化
	$(function() {
		var orgType = "${user.organization.orgType}";
		var orgPId = "${user.organization.parentOrgId}";
		var disabled1 = disabled2 = disabled3 = disabled4 = true;
		if(orgType == 1){
			disabled1 = disabled2 = disabled3= false;
		}else if(orgType == 2){
			disabled2 = false;
		}else if(orgType == 3){
			disabled3 = false;
		}else if(orgType == 4){
			disabled4 = false;
		}else if(orgType == 9){
			 disabled1 = disabled2 = disabled3 =disabled4 = false;
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
			width:"100%"
		}); 
		
		$("#orgType").on("change",function(e) {

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
			width:240,
		  	onSelect:function(row){
		  		$("#orgName").val(row.orgName);
		  		$("#orgId").val(row.id);
		  	}
		   });
		  
		$("#orgType").trigger("change");
		
		$('#form1').initValid(["orgName","name"]);
		
	});
	  
	function submitAjax(){
		h.ajax({
			url :"${pageContext.request.contextPath}/sys/group/edit.htmlx",
			data:$('#form1').serialize(),
			beforeSend : function() {
				if(!h.isValid($("#form1"))) return false;
			},
			dataSuccess : function(data) {
				h.hideProgressBar();
				h.bootboxRefresh("修改成功");	
			}
		});
	}

	
</script>


