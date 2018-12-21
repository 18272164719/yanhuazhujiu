<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<meta name="author" content="SHYL Team">
	<meta name="copyright" content="SHYL">
	<c:set var="base" value="${pageContext.request.contextPath }"/>
	<title>药联云药店</title>
	<link href="${base}/favicon.ico" rel="icon">
	

	
	<link rel="stylesheet" type="text/css" href="${base }/resources/bootstrap/css/bootstrap.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="${base }/resources/font-awesome/css/font-awesome.css" />
	<link rel="stylesheet" type="text/css" href="${base }/resources/adminLTE/css/AdminLTE.css" />     
	<link rel="stylesheet" type="text/css" href="${base }/resources/adminLTE/css/skins/_all-skins.min.css" />   
    <link rel="stylesheet" type="text/css" href="${base }/resources/css/common.css" /> 
    <link rel="stylesheet" type="text/css" href="${base }/resources/css/small.css" />
    	
   
    
  	
	<style>
	body {
	    padding-right:0!important;
	}

	
	</style>
	
	<script type="text/javascript" src="${base }/resources/js/jquery.min.js"></script>
    <script src="${base }/resources/js/jquery-ui.js"></script>
    <script type="text/javascript" src="${base }/resources/js/jquery-dateFormat.min.js"></script>
  	<script type="text/javascript" src="${base }/resources/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript" src="${base }/resources/js/adminLTE.js"></script>
	<script type="text/javascript" src="${base}/resources/bootstrap/js/bootstrap-contextmenu.js"></script>
	<script type="text/javascript" src="${base }/resources/bootstrap/js/bootbox.min.js"></script>
	<script type="text/javascript" src="${base }/resources/bootstrap/js/boottabs.js"></script>
	<script type="text/javascript" src="${base }/resources/bootstrap/js/bootstrap-growl.js"></script>
   	

	 <!--[if lt IE 9]>
		<script src="${base}/resources/js/html5shiv.js"></script>
		<script src="${base}/resources/js/respond.js"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/css/adminLTE-ie8.css" />
	<![endif]-->
<script type="text/javascript" src="${base }/resources/js/hApi.js"></script>
<script type="text/javascript" src="${base }/resources/js/index.js"></script>

</head> 
<!-- <body class="hold-transition sidebar-mini sidebar-collapse" style="overflow-y:scroll;"> -->
<body class="skin-blue sidebar-mini" style="overflow-y:scroll;">
	<div class="wrapper">
		<header class="main-header">
	<a class="logo" href="${base}/index.htmlx" data-toggle="tooltip" data-placement="right" title="首页">
		<span class="logo-mini">
			<img style="width:40px;height:40px;" src="${base }/resources/images/minilogo.png" alt="药联云药店">
		</span>
		<span>
			<img style="width:75%;height:25px;" src="${base }/resources/images/logofb.png" alt="药联云药店">
		</span>
	</a>
	  
	<nav class="navbar navbar-static-top">
		<div class="container-fluid">
			<a class="sidebar-toggle" href="javascript:;" data-toggle="offcanvas"></a>
			<span style="float:left;margin:auto;padding:0px 16px;color:#fff;line-height: 46px;font-size:20px;">${currentUser.organization.orgName }</span>
	
			<div class="navbar-custom-menu">
				<ul class="nav navbar-nav">
					
					 <li class="dropdown user user-menu">
			          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="fa fa-user"></i> ${currentUser.name }<span class="caret"></span></a>
			          <ul class="dropdown-menu">
			              <!-- User image -->
			              <li class="user-header" >
			                <i class="fa fr fa-fw fa-user "></i>
			                <p>
			                 	${currentUser.name }
			                  <small>${currentUser.organization.orgName }</small>
			                </p>
			              </li>
			              <!-- Menu Footer-->
			              <li class="user-footer">
			                <div class="pull-left">
			                  <a href="javascript:updatePsw()" class="btn btn-default btn-flat"><i class="fa fa-key"></i> 修改密码</a>
			                </div>
			                <div class="pull-right">
			                  <a href="javascript:logout()" class="btn btn-default btn-flat"><i class="fa fa-sign-out"></i> 注销</a>
			                </div>
			              </li>
			          </ul>
			        </li>
			        <li>
						<a id="atest" href="javascript:launchFullscreen()" data-toggle="tooltip" data-placement="left" title="全屏">
							<i class="fa fa-fw fa-arrows-alt"></i>
						</a>
					</li>
				</ul>
			</div>
		</div>
			</nav>
</header>
<section class="content-header">
	<ol class="breadcrumb" id="urlOL"></ol>
</section>
<aside class="main-sidebar">
	<section class="sidebar">
		<ul class="sidebar-menu" id="tree"></ul>
	</section>
</aside>
		<div class="content-wrapper">
			<section>
				<div id="context-menu">
					<ul class="dropdown-menu" role="menu">
						<li><a tabindex="-1"><span class="fa fa-refresh"></span>刷新</a></li>
						<li><a tabindex="-1"><span class="fa fa-close"></span>关闭</a></li>
						<li><a tabindex="-1"><span class="fa fa-star-o"></span>收藏</a></li>
						<li class="divider"></li>
						<li><a tabindex="-1"><span class="fa fa-bell-o"></span>关闭其他标签</a></li>
						<li><a tabindex="-1"><span class="fa fa-bell-o"></span>关闭右侧标签</a></li>
					</ul>
				</div>
				<div id="myTabs" class="tabs">
					<!-- Nav tabs -->
					<ul class="nav nav-tabs" style="padding-right: 200px;"></ul>
					<!-- Tab panes -->
					<div class="tab-content"></div>
				</div>
				
		</div>
	</div>
	</body>

<script type="text/javascript">
var centerTabs;
$(function(){
	centerTabs = $('#myTabs').tabs();
	makeTree("${parent0.id }","${parent0.name }","${parent0.icon }");
	$('#myTabs').tabs('add',{
		iconCls:'fa fa-bell-o',
		title:'首页',
		ol:"fa fa-tachometer,首页",
		closable : false,
		content : '<iframe  style="background-color:#ecf0f5;border:0px;width:100%;height:2000px;min-height:500px;" frameborder="0" scrolling="no" src="${base }${homeUrl }"></iframe>'
	});
	
	
});


//注销
function logout(){
	if(confirm("确定要注销吗？")){
		location.href = "${base }/shiro/logout.htmlx";
	} 
}
function collection(url){
	url = url.replace("${pageContext.request.contextPath }","");
	$.ajax({
		url:"${pageContext.request.contextPath }/sys/privateMenu/add.htmlx",
		data : {
			"url" : url
		},
		dataType : "json",
		type : "POST",
		cache : false,
		success : function(data) {
			showMsg("收藏成功");
		},
		error : function() {}
	});
}
//修改密码
function updatePsw(){
	top.bootbox.dialog({
		  message: "<c:out value='${pageContext.request.contextPath }'/>/sys/user/updatePsw.htmlx",
		  title: "修改密码",
		  width:400,
		  height:300,
		  buttons: {
		    "取消": {}, 
		    success: {   
		      label: "保存", 
		      callback: function() {
		    	 this.find("iframe")[0].contentWindow.submitAjax();
		    	 return false;
		      }
		    }
		  }
		});
}

	
//tabs
function addTab(title,url,icon,refreshFlag,ol){
	if (!centerTabs.tabs('exists', title)) {
		centerTabs.tabs('add',{
			iconCls:'fa fa-bell-o',
			title:title,
			ol:ol,
			content : '<iframe   style="border:0px;background-color:#ecf0f5;width:100%;min-height:500px;" frameborder="0" scrolling="no" src="${base}'+url+'"></iframe>'
		});
	}else{
		centerTabs.tabs('select', title);
	}
}


//tree
var preParentId = -1;
var oneOL;
function makeTree(parentId,name,icon) { 
	oneOL = new Object();
	oneOL.name = name;
	oneOL.icon = icon;
	
	if(preParentId == parentId) return;
	preParentId = parentId;
	$.ajax({
			url:"${pageContext.request.contextPath }/sys/menu/treeByOrgType.htmlx",
			dataType : "json",
			async: false,
			type : "POST",
			cache : false,
			success : function(data) {
				$("#tree").empty();
				 $.each(transTreeData(data,-1), function (i, item) {
					 if(item.children.length>0){
						 var li;
						 if(item.children[0].children.length==0){//二级菜单
							 li = '<li class="treeview" id="treeLi'+item.id+'" ><a href="javascript:;"><i class="'+item.icon+'"></i><span>'+item.name+'</span><span class="pull-right-container"></span></a>';
			            	 li += '<div class="second-menu second-menu-left-full"><div class="second-menu-wrap">';
		            		 li += '<ul class="second-menu-item">';
		            		 $.each(item.children, function (k, item2) {
		            		 	li += '<li><a href="javascript:treeNodeFunc(1,\''+item2.id+'\',\''+item2.name+'\',\''+item2.url+'\',\''+item2.icon+'\',\''+item2.parentId+'\');"><i class="'+item2.icon+'"></i><span id="treeMenu_'+item2.id+'">'+item2.name+'</span><span class="pull-right-container"></span></a></li>';
		            		 });
		            		 li += '</ul>';
			            	 li += '</div>';
			            	 li += ' </li>';
						 }else{//三级菜单
							 li = '<li class="treeview" id="treeLi'+item.id+'" ><a href="javascript:;"><i class="'+item.icon+'"></i><span>'+item.name+'</span><span class="pull-right-container"></span></a>';
			            	 li += '<div class="second-menu second-menu-left-full"><div class="second-menu-wrap">';
			            	 $.each(item.children, function (j, item2) {
			            		 li += '<ul class="second-menu-item"><span class="second-menu-title">'+item2.name+'</span>';
			            		 $.each(item2.children, function (k, item3) {
			            		 	li += '<li><a href="javascript:treeNodeFunc(0,\''+item3.id+'\',\''+item3.name+'\',\''+item3.url+'\',\''+item3.icon+'\',\''+item2.parentId+'\');"><i class="'+item3.icon+'"></i><span id="treeMenu_'+item3.id+'">'+item3.name+'</span><span class="pull-right-container"></span></a></li>';
			            		 });
			            		 li += '</ul>';
			            	 });
			            	 li += '</div>';
			            	 li += ' </li>';
						 }
						 $("#tree").append($(li));
					 }
					
				 });
			},
			error : function() {
				location.href = "${base }/shiro/logout.htmlx";
			}
		});
	
	
}


</script>
	</html>
   
