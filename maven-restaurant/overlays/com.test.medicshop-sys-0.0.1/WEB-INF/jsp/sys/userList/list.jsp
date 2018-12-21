<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />

<html> 
<body class="easyui-layout" >
<div class="single-dg">
	<table  id="dg"   ></table>
</div>



</body>
</html>
    
<script>

//删除
function delFunc(){
	var selobj = $('#dg').datagrid('getSelected');
	if(selobj == null){
		$.messager.alert('错误','没有选中行!','info');
		return;
	}
	var id= $('#dg').datagrid('getSelected').id;
	
	$.messager.confirm('确认信息', '确认要删除此成员?', function(r){
		if (r){
			delAjax(id);
		}
	});
}

//重置密码
function resetPwd(){
	var selobj = $('#dg').datagrid('getSelected');
	if(selobj == null){
		return;
	}
	var id= selobj.id;
	var empId = selobj.empId;
	$.messager.confirm('确认信息', '确认要重置密码吗?', function(r){
		if (r){
			resetPwdAjax(id,empId);
		}
	});
	
}
//手动解锁
function unlockFunc(){
	var selobj = $('#dg').datagrid('getSelected');
	if(selobj == null){
		return;
	}
	var id= selobj.id;
	var empId = selobj.empId;
	$.messager.confirm('确认信息', '确认要解锁帐号吗?', function(r){
		if (r){
			unlockAjax(id,empId);
		}
	});
	
}
//禁用
function disabledFunc(){
	var selobj = $('#dg').datagrid('getSelected');
	if(selobj == null){
		return;
	}
	var id= selobj.id;
	var empId = selobj.empId;
	$.messager.confirm('确认信息', '确认要禁用帐号吗?', function(r){
		if (r){
			disabledAjax(id,empId);
		}
	});
	
}
//解禁
function abledFunc(){
	var selobj = $('#dg').datagrid('getSelected');
	if(selobj == null){
		return;
	}
	var id= selobj.id;
	var empId = selobj.empId;
	$.messager.confirm('确认信息', '确认要对帐号解禁吗?', function(r){
		if (r){
			abledAjax(id,empId);
		}
	});
	
}
//搜索
function search(val,name){
	if(name=='empId'){
	$('#dg').datagrid('load',{
		"query['t#empId_S_LK']": val
	});	 
	}
	else if(name=='name'){
		$('#dg').datagrid('load',{
			"query['t#name_S_LK']": val
		});	 
		}
	else if(name=='cell'){
		$('#dg').datagrid('load',{
			"query['t#cell_S_LK']": val
		});	 
		}
	else if(name=='title'){
		$('#dg').datagrid('load',{
			"query['t#title_S_LK']": val
		});	 
		}
}


//初始化
$(function(){
	
	//datagrid
	$('#dg').datagrid({
		fitColumns:true,
		striped:true,
		singleSelect:true,
		rownumbers:true,
		border:true,
		height : $(this).height()-3,
		pagination:true,
		url:"${pageContext.request.contextPath}/sys/userList/page.htmlx",
		pageSize:20,
		pageNumber:1,
		remoteFilter: true,
		columns:[[
		        	{field:'empId',title:'帐号',width:10,align:'center'},
		        	{field:'name',title:'姓名',width:20,align:'center'},
		        	/* {field:'mail',title:'邮件地址',width:30,align:'center'},
		        	{field:'cell',title:'手机',width:20,align:'center'}, */
/* 		        	{field:'ext',title:'座机',width:20,align:'center'}, */
		        	{field:'organization.orgType',title:'机构类型',width:20,align:'center',
						formatter: function(value,row,index){
							if (row.organization.orgType==1){
								return "医院";
							}else if (row.organization.orgType==2){
								return "供应商";
							}else if (row.organization.orgType==4){
								return "供应商C端";
							}else if (row.organization.orgType==3){
								return "监管机构";
							}else if (row.organization.orgType==5){
								return "系统运维";
							}else if (row.organization.orgType==6){
								return "GPO";
							}else if (row.organization.orgType==7){
								return "专家组";
							}
						}
		        	},
		        	{field:'organization.orgName',title:'机构名称',width:20,align:'center',
						formatter: function(value,row,index){
							if (row.organization){
								return row.organization.orgName;
							}
						}},
					{field:'isAdmin',title:'是否管理员',width:10,align:'center',
		        		formatter: function(value,row,index){
							if (row.isAdmin == 1){
								return "<img src ='${pageContext.request.contextPath}/resources/js/jquery-easyui/themes/icons/ok.png' />";
							}
						}
		    		},
		    		{field:'isLocked',title:'是否锁定',width:10,align:'center',
		        		formatter: function(value,row,index){
							if (row.isLocked == 1){
								return "<img src ='${pageContext.request.contextPath}/resources/js/jquery-easyui/themes/icons/disable.png' />";
							}
						}
		    		},
					{field:'isDisabled',title:'是否禁用',width:10,align:'center',
		        		formatter: function(value,row,index){
							if (row.isDisabled == 1){
								return "<img src ='${pageContext.request.contextPath}/resources/js/jquery-easyui/themes/icons/disable.png' />";
							}
						}
		    		}
		   		]],
		toolbar: [{
			iconCls: 'icon-key',
			text:"重置密码",
			handler: function(){
				resetPwd();
			}
		}]
	});
	
	$('#dg').datagrid('enableFilter', [{
        field:'organization.orgType',
        type:'combobox',
        options:{
            panelHeight:'auto',
            editable:false,
            data:[{value:'',text:'-请选择-'},
                  {value:'1',text:'医院'},
                  {value:'2',text:'供应商'},
                  {value:'4',text:'供应商C端'},
                  {value:'3',text:'监管机构'},
            	  {value:'5',text:'系统运维'},
            	  {value:'6',text:'GPO'},
            	  {value:'7',text:'专家组'}],
            onChange:function(value){
                if (value == ''){
                	$('#dg').datagrid('removeFilterRule', 'organization.orgType');
                } else {
                	$('#dg').datagrid('addFilterRule', {
                        field: 'organization.orgType',
                        op: 'EQ',
                        fieldType:'I',
                        value: value
                    });
                }
                $('#dg').datagrid('doFilter');
            }
        }
    },{
        field:'isAdmin',
        type:'combobox',
        options:{
            panelHeight:'auto',
            editable:false,
            data:[{value:'',text:'-请选择-'},
                  {value:'1',text:'管理员'},
                  {value:'0',text:'子账号'}],
            onChange:function(value){
                if (value == ''){
                	$('#dg').datagrid('removeFilterRule', 'isAdmin');
                } else {
                	$('#dg').datagrid('addFilterRule', {
                        field: 'isAdmin',
                        op: 'EQ',
                        fieldType:'S',
                        value: value
                    });
                }
                $('#dg').datagrid('doFilter');
            }
        }
    },{
        field:'isLocked',
        type:'combobox',
        options:{
            panelHeight:'auto',
            editable:false,
            data:[{value:'',text:'-请选择-'},
                  {value:'0',text:'未锁定'},
                  {value:'1',text:'锁定'}
                  ],
            onChange:function(value){
                if (value == ''){
                	$('#dg').datagrid('removeFilterRule', 'isLocked');
                } else {
                	$('#dg').datagrid('addFilterRule', {
                        field: 'isLocked',
                        op: 'EQ',
                        fieldType:'I',
                        value: value
                    });
                }
                $('#dg').datagrid('doFilter');
            }
        }
    },{
        field:'isDisabled',
        type:'combobox',
        options:{
            panelHeight:'auto',
            editable:false,
            data:[{value:'',text:'-请选择-'},
                  {value:'0',text:'未禁用'},
                  {value:'1',text:'禁用'}
                  ],
            onChange:function(value){
                if (value == ''){
                	$('#dg').datagrid('removeFilterRule', 'isDisabled');
                } else {
                	$('#dg').datagrid('addFilterRule', {
                        field: 'isDisabled',
                        op: 'EQ',
                        fieldType:'I',
                        value: value
                    });
                }
                $('#dg').datagrid('doFilter');
            }
        }
    }]);


});

//=============ajax===============

function resetPwdAjax(id,empId){
	$.ajax({
				url:"${pageContext.request.contextPath }/sys/user/resetPwd.htmlx",
				data:{
					"id":id,
					"empId":$.md5(empId)
				},
				dataType:"json",
				type:"POST",
				cache:false,
				success:function(data){
					if(data.success){
						$('#dg').datagrid('reload');
						showMsg("重置密码成功");
					} else{
						showErr("出错，请刷新重新操作");
					}
				},
				error:function(){
					showErr("出错，请刷新重新操作");
				}
	});	
	
}

</script>