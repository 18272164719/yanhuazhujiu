<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />
<html>
<body class="singlebox" >
<div >
    <table id="table"></table>
</div>
<script type="text/javascript">
    //初始化
    $(function(){
        $('#table').bootstrapTable({
            url:"<c:out value='${pageContext.request.contextPath }'/>/Variety/pageBy.htmlx",
            toolbar:'#toolbar1',//工具按钮
            pagination : true, //是否显示分页
            pageNumber : 1, //初始化加载第一页，默认第一页
            pageSize : 5, //每页的记录行数（*）
            minRows: 10,
            paginationLoop : false,
            columns : [
                {'checkbox':'true',width:15},
                {field:'code',title:'菜编号',width:90},
                {field:'name',title:'菜名称',width:80},
                {field:'price',title:'价格',width:100}
            ]
        });

    });

    function submitAjax() {
        var selobj = $('#table').bootstrapTable('getSelections');
        var objarr = "";
        $.each(selobj,function (index) {
            objarr += this.id +",";
        });
        top.topCallback(objarr);
        top.topCallback = null;
        h.bootboxHide();
    }
</script>
</body></html>


