<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ attribute name="title" type="java.lang.String" required="true" description="页面标题" %>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Window-target" content="_top">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <c:set var="base" value="${pageContext.request.contextPath }"/>
    <link rel="stylesheet" type="text/css" href="${base }/resources/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${base }/resources/bootstrapTable/bootstrap-table.css" />

    <script type="text/javascript" src="${base }/resources/js/jquery.min.js"></script>
    <script src="${base }/resources/js/jquery-ui.js"></script>
    <script src="${base }/resources/js/zApi.js"></script>
    <script src="${base }/resources/js/sha.js"></script>
    <script type="text/javascript" src="${base }/resources/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${base }/resources/bootstrap/js/bootbox.min.js"></script>
    <script type="text/javascript" src="${base }/resources/bootstrapTable/bootstrap-table.js"></script>

</head>

<body>

</body>
<script>
    function showMsg(text){
        top.$.bootstrapGrowl(text, { type: 'success' });
    }
    function showInfo(text){
        top.$.bootstrapGrowl(text);
    }

    function showErr(text,obj){
        top.$.bootstrapGrowl(text, { type: 'danger' });
        if(obj){
            $.each($(".editError"),function(index){
                $(this).removeClass("editError");
            });
            obj.addClass("editError");
        }
    }
</script>