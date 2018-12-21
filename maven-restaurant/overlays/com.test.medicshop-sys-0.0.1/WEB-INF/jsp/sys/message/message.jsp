<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />

<html>

<body>

        <div id="el" class="easyui-layout"  >
            <div id='elwest' data-options="region:'west',collapsible:false" style="width:136px;">
            	<!-- 左侧导航按钮 -->
            	<a id="sjx_btn" href="#"></a>
            	<a id="fjx_btn" href="#"></a>
            	<a id="cgx_btn" href="#"></a>
            </div>
            
            <div data-options="region:'center'" style="background: rgb(238, 238, 238);">
            	 <div  class="easyui-layout" style="width:100%;height:100%" >
		            <div  data-options="region:'north',title:'邮件',collapsible:false,border:false,split:true" style="height:300px;">
						<!-- 收件箱 -->
						<div id="recv_tb" style="display:none;padding:5px">
							<a id="create_recv" href="javascript:newMsg()" class="easyui-linkbutton" data-options="" >新增</a>
							<a id="del_recv" href="#" class="easyui-linkbutton" data-options="disabled:true" onclick=delMsg_recv()>删除</a>
							
							<input id="ss_recv" />
						</div>
						<table  id="dg_recv" ></table>
						   
						<!-- 发件箱 -->
						<div id="send_tb" style="display:none;padding:5px">
							<a id="del_send" href="#" class="easyui-linkbutton" data-options="disabled:true" onclick=delMsg_send()>删除</a>
							
							<input id="ss_send" />
						</div>
						<table  id="dg_send" ></table>  
						<!-- 草稿箱 -->
						<div id="draft_tb" style="display:none;padding:5px">
							<a id="del_draft" href="#" class="easyui-linkbutton" data-options="disabled:true" onclick=delMsg_draft()>删除</a>
							
							<input id="ss_draft" />
						</div>
						<table  id="dg_draft" ></table> 
		            </div>
		            
		            <div   data-options="region:'center',title:'详细信息',border:false" style="background: rgb(238, 238, 238);">
		            	<textarea id="xxxx" style="overflow:auto;width:99.5%;height:95%;outline-style:none;border:0;resize:none;"></textarea> 
		        	</div>
		       	</div>
            </div>
       </div>


<!-- 新建邮件 -->
<div id='newmsg' style="display:none">
	<form id="form1" method="post">
					<table class="table-bordered">
					   <tr>
					   		<th>标题</th>
					   		<th>
					   			<input name="title" class="easyui-textbox easyui-validatebox" data-options="required:true" style="width:300px">
							</th>
							
					   </tr>
					   <tr>
					   		<th>收件人</th>
					   		<th>
								<input id="users" class="easyui-textbox easyui-validatebox" data-options="required:true" style="width:260px">
								<input id="userIds" name="userIds" type="hidden" class="easyui-textbox" />
								<a  href="javascript:selectuser('users','userIds')" class="easyui-linkbutton" data-options="">名录</a>
							</th>
					   </tr>
					   <tr>
					   		<th colspan=2 height=240>
								邮件内容<br>
								<span class="textbox" style="width:100%;height:90%">
									<textarea name="body" class="textbox-text validatebox-text" style="width:95%;height:90%">123</textarea>
								</span>
								
							</th>
					   </tr>
					   <tr  align=center>
					   		<th colspan=2 >
								<a id="btn" href="javascript:send()" class="easyui-linkbutton" data-options="">发送</a>
								<a id="btn" href="javascript:addDraft()" class="easyui-linkbutton" data-options="">存为草稿</a>
								<a id="btn" href="javascript:closeWin('newmsg')" class="easyui-linkbutton" data-options="">取消</a>
							</th>
					   </tr>
					     
					</table>
					</form>
</div>
  <!-- 人员挑选 -->
<div id='userlist' style="display:none">
	<iframe src="${pageContext.request.contextPath }/userlist.c" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>
</div>

<!-- 编辑草稿 -->
<div id='draftmsg' style="display:none">
	<form id="form2" method="post">
					<table class="table-bordered">
					   <tr>
					   		<th>标题</th>
					   		<th>
					   			<input  name="id" type=hidden />
					   			<input  id="form2title" name="title" class="easyui-textbox easyui-validatebox" data-options="required:true" style="width:300px" value="aa">
							</th>
							
					   </tr>
					   <tr>
					   		<th>收件人</th>
					   		<th>
								<input id="edit_users" class="easyui-textbox easyui-validatebox" data-options="required:true" style="width:260px">
								<input id="edit_userIds" name="userIds" type="hidden" class="easyui-textbox" />
								<a  href="javascript:selectuser('edit_users','edit_userIds')" class="easyui-linkbutton" data-options="">名录</a>
							</th>
					   </tr>
					   <tr>
					   		<th colspan=2 height=240>
								邮件内容<br>
								<span class="textbox" style="width:100%;height:90%">
									<textarea id="ttbody" name="body" class="textbox-text validatebox-text" style="width:95%;height:90%"></textarea>
								</span>
								
							</th>
					   </tr>
					   <tr  align=center>
					   		<th colspan=2 >
								<a id="btn" href="javascript:edit_send()" class="easyui-linkbutton" data-options="">发送</a>
								<a id="btn" href="javascript:edit_addDraft()" class="easyui-linkbutton" data-options="">保存</a>
								<a id="btn" href="javascript:closeWin('draftmsg')" class="easyui-linkbutton" data-options="">取消</a>
							</th>
					   </tr>
					     
					</table>
	</form>
</div>
  
 
            
            
</body>

</html>
<script>


//关闭窗口
function closeWin(id){
	$("#"+id).window('close');
}

//刷新
function dgreload(obj){
	obj.datagrid('reload');    
}

//清空
function clearWin(id){
	$("#"+id).find("input[type=text]").each(function(){
			$(this).val("");
		 });
}

//新增
function newMsg(){
	$("#newmsg").window("open");
}
//发送
function send(){
	sendMsgAjax();
}
//保存为草稿
function addDraft(){
	draftMsgAjax();
}

//编辑 发送
function edit_send(){
	edit_sendMsgAjax();
}
//编辑 保存为草稿
function edit_addDraft(){
	edit_draftMsgAjax();
}

//人员挑选
function selectuser(a,b){
	usersText = a;
	usersIdsText = b;
	$("#userlist").window("open");
}
//修改
function updategroup(){
	if(!$(form2).form('validate')){
		return false;
	}
	updateAjax();
}
//删除 收件箱
function delMsg_recv(){
	var selobj = $('#dg_recv').datagrid('getSelected');
	if(selobj == null){
		return;
	}
	var id= selobj.id;
	
	$.messager.confirm('确认', '确定删除邮件?', function(r){
		if (r){
			delMsgAjax_recv(id);
		}
	});
}
//删除 发件箱
function delMsg_send(){
	var selobj = $('#dg_send').datagrid('getSelected');
	if(selobj == null){
		return;
	}
	var id= selobj.id;
	
	$.messager.confirm('确认', '确定删除邮件?', function(r){
		if (r){
			delMsgAjax_send(id);
		}
	});
}
//删除 草稿箱
function delMsg_draft(){
	var selobj = $('#dg_draft').datagrid('getSelected');
	if(selobj == null){
		return;
	}
	var id= selobj.id;
	
	$.messager.confirm('确认', '确定删除草稿?', function(r){
		if (r){
			delMsgAjax_draft(id);
		}
	});
}

function selectedCHG(obj){
	$("#elwest").find("a").each(function(index){
		$(this).linkbutton({selected: false});				
	});

	$(obj).linkbutton({selected: true});

	$("#dg_recv").datagrid({closed:true});
	$("#dg_send").datagrid({closed:true});
	$("#dg_draft").datagrid({closed:true});
}
//收件箱
function sjx(obj){
	selectedCHG(obj);
	$("#dg_recv").datagrid({closed:false});
	$("#del_recv").linkbutton({disabled: true});
	search_recv("");
}
//发件箱
function fjx(obj){
	selectedCHG(obj);
	$("#dg_send").datagrid({closed:false});
	$("#del_send").linkbutton({disabled: true});
	search_send("");
}
//草稿箱
function cgx(obj){
	selectedCHG(obj);
	$("#dg_draft").datagrid({closed:false});
	$("#del_draft").linkbutton({disabled: true});
	search_draft("");
}

//搜索
function search_recv(value){
	$('#dg_recv').datagrid({  
		url:"${pageContext.request.contextPath}/message/recvpage.c", 
	    queryParams:{  
			"msgHub.title":value
	    }  
	});
}
function search_send(value){
	$('#dg_send').datagrid({  
		url:"${pageContext.request.contextPath}/message/sendpage.c", 
	    queryParams:{  
	    	"msgHub.title":value
	    }  
	});
}
function search_draft(value){
	$('#dg_draft').datagrid({  
		url:"${pageContext.request.contextPath}/message/draftpage.c", 
	    queryParams:{  
	    	"title":value
	    }  
	});
}

//关联
function guanlian(obj,index,type){
	var row = $('#dg2').datagrid('getData').rows[index];
	
	if(obj.checked){
		createGroupAjax(row.id,type);
	}else{
		delGroupAjax(row.id,type);
	}
}

$(function(){

	$("#tt").layout({
	    height:'100%'
	});	
	
	$('#el').layout({
	    width:$(this).width(),
	    height:$(this).height()
	});	

	$("#sjx_btn").linkbutton({
		plain:true,
		width:134,
		text:"收件箱",
		selected:true,
		onClick:function(){
			sjx(this);
		}
	});
	$("#fjx_btn").linkbutton({
		plain:true,
		width:134,
		text:"发件箱",
		onClick:function(){
			fjx(this);
		}
	});
	$("#cgx_btn").linkbutton({
		plain:true,
		width:134,
		text:"草稿箱",
		onClick:function(){
			cgx(this);
		}
	});

	//搜索框
	$("#ss_recv").searchbox({
		searcher:function(value,name){
			search_recv(value);
		},
		prompt:'搜索标题...',
		width:150
	});
	$("#ss_send").searchbox({
		searcher:function(value,name){
			search_send(value);
		},
		prompt:'搜索标题...',
		width:150
	});
	$("#ss_draft").searchbox({
		searcher:function(value,name){
			search_draft(value);
		},
		prompt:'搜索标题...',
		width:150
	});
	
	//表格 收件箱
	$('#dg_recv').datagrid({
		fitColumns:true,
		striped:true,
		singleSelect:true,
		rownumbers:true,
		border:false,
		height :  '100%',
		url:"${pageContext.request.contextPath}/message/recvpage.c",
		queryParams:{  
			"sender":0,
			"user.id":1,
			"msgHub.title":""
	    },
		pagination:true,
		pageSize:10,
		pageNumber:1,
		columns:[[
		        	{field:'1',title:'',width:1,align:'center',
		 	 	       formatter: function(value,row,index){
		 	 	    	 return '通知';
					}},
		        	{field:'2',title:'工号 ',width:5,align:'center',
		 	           formatter: function(value,row,index){
		 	        	  return row.msgHub.title;
					}},
		        	{field:'3',title:'姓名',width:2,align:'center',
			 	       formatter: function(value,row,index){
			 	    	  return row.user.name+'('+row.user.empId+')';
					}},
					{field:'4',title:'日期',width:2,align:'center',
				 	       formatter: function(value,row,index){
				 	    	  return row.createDate;
					}}
		   		]],
		toolbar: '#recv_tb',
		onClickRow: function(index,row){
			$("#xxxx").html(row.msgHub.body);
			$("#del_recv").linkbutton({disabled: false});
		}
	});

	
	//表格 发件箱
	$('#dg_send').datagrid({
		closed:true,
		fitColumns:true,
		striped:true,
		singleSelect:true,
		rownumbers:true,
		border:false,
		height :  '100%',
		pagination:true,
		pageSize:10,
		pageNumber:1,
		columns:[[
		        	{field:'1',title:'',width:1,align:'center',
		 	 	       formatter: function(value,row,index){
		 	 	    	 return '通知';
					}},
		        	{field:'2',title:'标题 ',width:7,align:'center',
		 	           formatter: function(value,row,index){
		 	        	  return row.msgHub.title;
					}},
					{field:'4',title:'日期',width:2,align:'center',
				 	       formatter: function(value,row,index){
				 	    	  return row.createDate;
					}}
		   		]],
		toolbar: '#send_tb',
		onClickRow: function(index,row){
			$("#xxxx").html(row.msgHub.body);
			$("#del_send").linkbutton({disabled: false});
		}
	});

	//表格 草稿箱
	$('#dg_draft').datagrid({
		closed:true,
		fitColumns:true,
		striped:true,
		singleSelect:true,
		rownumbers:true,
		border:false,
		height :  '100%',
		pagination:true,
		pageSize:10,
		pageNumber:1,
		columns:[[
		        	{field:'1',title:'',width:1,align:'center',
		 	 	       formatter: function(value,row,index){
		 	 	    	 return '通知';
					}},
		        	{field:'2',title:'标题 ',width:7,align:'center',
		 	           formatter: function(value,row,index){
		 	        	  return row.title;
					}},
					{field:'4',title:'日期',width:2,align:'center',
			 	       formatter: function(value,row,index){
				 	       if(row.modifyDate)
				 	    	  return row.modifyDate;
				 	       else if(row.createDate)
			 	    	  	  return row.createDate;
					}}
		   		]],
		toolbar: '#draft_tb',
		onClickRow: function(index,row){
			$("#xxxx").html(row.body);
			$("#del_draft").linkbutton({disabled: false});
		},
		onDblClickRow: function(index,row){
			var selobj = $('#dg_draft').datagrid('getSelected');
			$("#form2title").textbox("setValue",selobj.title);
			$("#ttbody").html(selobj.body);
			form2.id.value = selobj.id;
			$("#draftmsg").window('open');	
		}
	});
	
	 $("#newmsg").window({
			title:"新建邮件",
			width:450,
			height:403,
			top:0,
			minimizable:false,
			maximizable:false,
			collapsible:false,
			closed:true,
			modal:true
	});	

	 $("#userlist").window({
			title:"选择收件人",
			width:500,
			height:450,
			top:0,
			minimizable:false,
			maximizable:false,
			collapsible:false,
			closed:true,
			modal:true
	});	

	 $("#draftmsg").window({
			title:"编辑草稿",
			width:450,
			height:403,
			top:0,
			minimizable:false,
			maximizable:false,
			collapsible:false,
			closed:true,
			modal:true
	});	

	 $("#newmsg").show();
	 $("#userlist").show();
	 $("#draftmsg").show();
})

var user_arr;
var userid_arr;
var usersText;
var usersIdsText;
//人员挑选回调方法
function userlist_ok(userarr,useridarr){
	if(userarr.length != useridarr.length)
		return;
	
	$("#"+usersText).textbox("setValue",userarr);
	$("#"+usersIdsText).textbox("setValue",useridarr);
	
	$("#userlist").window("close");
}

function userlist_cancel(){
	 $("#userlist").window("close");
}

//=============ajax===============

function sendMsgAjax(){
	$.ajax({
		url:"${pageContext.request.contextPath }/message/send.c",
		data:$('#form1').serialize(),
		dataType:"json",
		type:"POST",
		cache:false,
		success:function(data){
			if(data.success){
				closeWin("newmsg");
				clearWin('newmsg');
				dgreload($("#dg_recv"));
				showMsg("邮件已发送！");
				
			} 
		},
		error:function(){
			showErr("出错，请刷新重新操作");
		}
	});	
}

function draftMsgAjax(){
	$.ajax({
		url:"${pageContext.request.contextPath }/message/addDraft.c",
		data:$('#form1').serialize(),
		dataType:"json",
		type:"POST",
		cache:false,
		success:function(data){
			if(data.success){
				closeWin("newmsg");
				clearWin('newmsg');
				showMsg("草稿已保存");
			} 
		},
		error:function(){
			showErr("出错，请刷新重新操作");
		}
	});	
}

function edit_sendMsgAjax(){
	$.ajax({
		url:"${pageContext.request.contextPath }/message/editsend.c",
		data:$('#form2').serialize(),
		dataType:"json",
		type:"POST",
		cache:false,
		success:function(data){
			if(data.success){
				closeWin("draftmsg");
				clearWin('draftmsg');
				dgreload($("#dg_draft"));
				showMsg("邮件已发送！");
				
			} 
		},
		error:function(){
			showErr("出错，请刷新重新操作");
		}
	});	
}

function edit_draftMsgAjax(){
	$.ajax({
		url:"${pageContext.request.contextPath }/message/editaddDraft.c",
		data:$('#form2').serialize(),
		dataType:"json",
		type:"POST",
		cache:false,
		success:function(data){
			if(data.success){
				dgreload($("#dg_draft"));
				showMsg("草稿已保存");
			} 
		},
		error:function(){
			showErr("出错，请刷新重新操作");
		}
	});	
}

function delMsgAjax_recv(id){
	$.ajax({
				url:"${pageContext.request.contextPath }/message/delete.c",
				data:"id="+id,
				dataType:"json",
				type:"POST",
				cache:false,
				success:function(data){
					if(data.success){
						dgreload($("#dg_recv"));
						showMsg("删除邮件成功！");
					} 
				},
				error:function(){
					showErr("出错，请刷新重新操作");
				}
	});	
}

function delMsgAjax_send(id){
	$.ajax({
				url:"${pageContext.request.contextPath }/message/delete.c",
				data:"id="+id,
				dataType:"json",
				type:"POST",
				cache:false,
				success:function(data){
					if(data.success){
						dgreload($("#dg_send"));
						showMsg("删除邮件成功！");
					} 
				},
				error:function(){
					showErr("出错，请刷新重新操作");
				}
	});	
}

function delMsgAjax_draft(id){
	$.ajax({
				url:"${pageContext.request.contextPath }/message/deletedraft.c",
				data:"id="+id,
				dataType:"json",
				type:"POST",
				cache:false,
				success:function(data){
					if(data.success){
						dgreload($("#dg_draft"));
						showMsg("删除草稿成功！");
					} 
				},
				error:function(){
					showErr("出错，请刷新重新操作");
				}
	});	
}

</script>