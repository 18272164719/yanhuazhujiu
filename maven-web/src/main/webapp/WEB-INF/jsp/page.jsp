<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />
<html>
<head>
</head>
<body>
<h1>132456789</h1>
<div class="row" >
    <div class="col-xs-6">
        <!-- 工具栏的按钮，可以自定义事件 -->
        <table id="table"></table>
    </div>
    <div class="col-xs-6 lefeP0">
        <table id="table1"></table>
    </div>
</div>
</body>
</html>
<script>
    //初始化
    $(function () {
        $('#table').bootstrapTable({
            url:"<c:out value='${pageContext.request.contextPath }'/>/home/listAll.htmlx",
            sidePagination :'client',
            columns : [
                {'radio':'true',width:15},
                {field:'empId',title:'用户名',width:90},
                {field:'name',title:'姓名',width:80},
                {field:'isLocked',title:'是否锁定',width:90},
                {field:'errTimes',title:'错误次数',width:100},
                {field:'isDisabled',title:'是否禁用',width:60}
            ]/*,
            onCheck : function(row,$el) {
                searchDetail(row);
            },
            onDblClickRow : function(row,$el) {
                editOpen(row);
            }*/
        });
        $('#table1').bootstrapTable({
            url:"<c:out value='${pageContext.request.contextPath }'/>/home/listAll2.htmlx",
            sidePagination :'client',
            columns : [
                {'radio':'true',width:15},
                {field:'empId',title:'用户名',width:90},
                {field:'name',title:'姓名',width:80},
                {field:'isLocked',title:'是否锁定',width:90},
                {field:'errTimes',title:'错误次数',width:100},
                {field:'isDisabled',title:'是否禁用',width:60}
            ]/*,
            onCheck : function(row,$el) {
                searchDetail(row);
            },
            onDblClickRow : function(row,$el) {
                editOpen(row);
            }*/
        });
    })


</script>