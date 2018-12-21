<%@page import="com.shyl.sys.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />
<style>
body{
	padding-top:0px;
	margin:0px;
}



</style>
<body >
		<div id="myTabs"  class="tabs" style="margin-top: 1px;">

			<!-- Nav tabs -->
			<ul class="nav nav-tabs">


			</ul>

			<!-- Tab panes -->
			<div class="tab-content"></div>
		</div>
</body>


<script type="text/javascript" src="${pageContext.request.contextPath }/resources/bootstrap/js/boottabs.js"></script>
<script>
$(function(){
	centerTabs = $('#myTabs').tabs();
	$('#myTabs').tabs('add',{
		iconCls:'fa fa-bell-o',
		title:'流程审批',
		closable : false,
		content : '<iframe  style="border:0px;width:100%;min-height:300px;" frameborder="0" scrolling="no" src="${pageContext.request.contextPath }${flowNode.url}?${flowNode.param}"></iframe>'
	});
	$('#myTabs').tabs('add',{
		iconCls:'fa fa-bell-o',
		title:'审批记录',
		closable : false,
		content : '<iframe  style="border:0px;width:100%;min-height:300px;" frameborder="0" scrolling="no" src="${pageContext.request.contextPath }/sys/flowNode/flowNode.htmlx?id=${flowNode.id}"></iframe>',
		onSelect:function(title, index){
			window.setTimeout(function(){setIframeHeight('审批记录')}, 0);
		}
	});
	$('#myTabs').tabs("select","流程审批");
	window.setTimeout(function(){setIframeHeight('流程审批')}, 300);
});

//iframe高度自适应
function setIframeHeight(id){
	var iframeid = $("#myTabs").tabs("getFrameById",id)[0];
	var h = $(window).height()-100;
	  if (document.getElementById){
	   	if (iframeid && !window.opera){ 
	   		 if (iframeid.contentDocument && iframeid.contentDocument.body.offsetHeight){ 
	    	 	h = iframeid.contentDocument.body.offsetHeight;
	    	 }else if(iframeid.Document && iframeid.Document.body.scrollHeight){ 
	     		h = iframeid.Document.body.scrollHeight;
	    	 }
	   	}
	  }
	  $(iframeid).css("height",h);
}

function flow_reject(){
	if(confirm("您确认要驳回吗？"))
		$("#myTabs").tabs("getFrameById",'流程审批')[0].contentWindow.flow_reject();
}

function flow_agree(){
	if(confirm("您确认要批准吗？"))
		$("#myTabs").tabs("getFrameById",'流程审批')[0].contentWindow.flow_agree();
}



</script>
