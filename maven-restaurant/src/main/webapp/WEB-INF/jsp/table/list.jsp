<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />
<html>
<body>
<!-- 工具栏的按钮，可以自定义事件 -->
<div id="toolbar" class="btn-group">
    <button type="button" class="btn btn-default"   onclick=addOpen()>
        <i class="fa fa-plus"></i> 新增
    </button>
    <button type="button" class="btn btn-default" onclick=editOpen()>
        <i class="fa fa-pencil"></i> 修改
    </button>&nbsp
    <button type="button" class="btn btn-default" onclick=addVarity()>
        <i class="fa fa-pencil"></i> 点菜
    </button>&nbsp
    <button type="button" class="btn btn-default" onclick=editVarity()>
        <i class="fa fa-pencil"></i> 加菜
    </button>&nbsp
</div>
<div id="toolbar1" class="btn-group">
    <button type="button" class="btn btn-default"   onclick=addOpenVa()>
        <i class="fa fa-plus"></i> 新增
    </button>
    <button type="button" class="btn btn-default" onclick=editOpenVa()>
        <i class="fa fa-pencil"></i> 修改
    </button>&nbsp
    <button type="button" class="btn btn-default" onclick=deleteVa()>
        <i class="fa fa-pencil"></i> 删除
    </button>&nbsp
</div>
<div >
    <table id="table"></table>
    <table id="table1"></table>
</div>
</body>
</html>

<script>
    //初始化
    $(function() {
        $('#table').bootstrapTable({
            url:"<c:out value='${pageContext.request.contextPath }'/>/Variety/pageByTable.htmlx",
            toolbar:'#toolbar',//工具按钮
            pagination : true, //是否显示分页
            pageNumber : 1, //初始化加载第一页，默认第一页
            pageSize : 5, //每页的记录行数（*）
            minRows: 8,
            paginationLoop : false,
            columns : [
                {'radio':'true',width:15},
                {field:'code',title:'餐桌编号',width:90},
                {field:'name',title:'餐桌名称',width:80},
                {field:'num',title:'点菜数量',width:90},
                {field:'sum',title:'消费总金额',width:100},
                {field:'statusName',title:'状态',width:60},
                {field:'do',title:'操作',width:70,
                    formatter: function(value,row,index){
                        return "<button type='button' class='btn btn-default' onclick='settle("+JSON.stringify(row)+")'><i class='fa fa-key'></i>结算</button>";
                    }
                }
            ],
            onCheck : function(row,$el) {
                searchDetail(row);
            },
            onDblClickRow : function(row,$el) {
                editOpen(row);
            }

        });

        $('#table1').bootstrapTable({
            url:"<c:out value='${pageContext.request.contextPath }'/>/Variety/pageBy.htmlx",
            toolbar:'#toolbar1',//工具按钮
            pagination : true, //是否显示分页
            pageNumber : 1, //初始化加载第一页，默认第一页
            pageSize : 5, //每页的记录行数（*）
            minRows: 10,
            paginationLoop : false,
            columns : [
                {'radio':'true',width:15},
                {field:'code',title:'菜编号',width:90},
                {field:'name',title:'菜名称',width:80},
                {field:'price',title:'价格',width:100}
            ],
            onDblClickRow:function(row,$el){
                editOpenVa(row);
            }
        });
    });

    function searchDetail(row){
        $('#table1').bootstrapTable("refreshOptions",{
            url:"<c:out value='${pageContext.request.contextPath }'/>/Variety/pageByList.htmlx",
            queryParams:function(params){
                params['query["t#id_L_EQ"]'] = row.id;
                return params;
            },
            columns : [
                {'radio':'true',width:15},
                {field:'code',title:'菜编号',width:90},
                {field:'name',title:'菜名称',width:80},
                {field:'price',title:'价格',width:100}
            ],
        });
    }
    
    function addOpen() {
        top.toptable = $("#table");
        top.editrow = {};
        top.bootbox.dialog({
            message: "<c:out value='${pageContext.request.contextPath }'/>/Variety/add.htmlx",
            title: "新增餐桌",
            width:500,
            height:320,
            buttons: {
                "取消": {},
                success: {
                    label: "保存",
                    callback: function() {
                        h.iframe(this).submitAjax();
                        return false;
                    }
                }
            }
        });
        return;
    }

    function editOpen(row) {
        top.toptable = $("#table");
        if(!row){
            var selobj = $('#table').bootstrapTable('getSelections');
            if (selobj.length == 0) {
                showInfo('请选择一行');
                return;
            }
            row = selobj[0];
        }
        top.editrow = row;
        h.log(row);
        top.bootbox.dialog({
            message: "<c:out value='${pageContext.request.contextPath }'/>/Variety/edit.htmlx",
            title: "修改餐桌",
            width:500,
            height:320,
            buttons: {
                "取消": {},
                success: {
                    label: "保存",
                    callback: function() {
                        h.iframe(this).submitAjax();
                        return false;
                    }
                }
            }
        });
        return;
    }

    function addOpenVa() {
        top.toptable = $("#table1");
        top.editrow = {};
        top.bootbox.dialog({
            message: "<c:out value='${pageContext.request.contextPath }'/>/Variety/addVa.htmlx",
            title: "新增菜品",
            width:500,
            height:320,
            buttons: {
                "取消": {},
                success: {
                    label: "保存",
                    callback: function() {
                        h.iframe(this).submitAjax();
                        return false;
                    }
                }
            }
        });
        return;
    }

    function editOpenVa(row) {
        top.toptable = $("#table1");
        if(!row){
            var selobj = $('#table1').bootstrapTable('getSelections');
            if (selobj.length == 0) {
                showInfo('请选择一行');
                return;
            }
            row = selobj[0];
        }
        top.editrow = row;
        h.log(row);
        top.bootbox.dialog({
            message: "<c:out value='${pageContext.request.contextPath }'/>/Variety/editVa.htmlx",
            title: "修改菜品",
            width:500,
            height:320,
            buttons: {
                "取消": {},
                success: {
                    label: "保存",
                    callback: function() {
                        h.iframe(this).submitAjax();
                        return false;
                    }
                }
            }
        });
        return;
    }

    //点菜
    function addVarity(row) {
        top.toptable = $("#table");
        if(!row){
            var selobj = $('#table').bootstrapTable('getSelections');
            if (selobj.length == 0) {
                showInfo('请选择一行');
                return;
            }
            row = selobj[0];
        }
        top.editrow = row;
        top.bootbox.dialog({
            message: "<c:out value='${pageContext.request.contextPath }'/>/Variety/addVarity.htmlx",
            title: "修改餐桌",
            width:850,
            height:400,
            buttons: {
                "取消": {},
                success: {
                    label: "保存",
                    callback: function() {
                        h.iframe(this).submitAjax();
                        return false;
                    }
                }
            }
        });
        return;
    }
    //加菜
    function editVarity(row) {
        top.toptable = $("#table");
        if(!row){
            var selobj = $('#table').bootstrapTable('getSelections');
            if (selobj.length == 0) {
                showInfo('请选择一行');
                return;
            }
            row = selobj[0];
        }
        top.editrow = row;
        top.bootbox.dialog({
            message: "<c:out value='${pageContext.request.contextPath }'/>/Variety/editVarity.htmlx",
            title: "修改餐桌",
            width:850,
            height:400,
            buttons: {
                "取消": {},
                success: {
                    label: "保存",
                    callback: function() {
                        h.iframe(this).submitAjax();
                        return false;
                    }
                }
            }
        });
        return;
    }
    function settle(row) {
        if(row!= null){
            if(row.status == 'notUse'){
                showErr("该状态无法结算！");
                return;
            }
        }
        $.ajax({
            url :"${pageContext.request.contextPath}/Variety/settle.htmlx?id="+row.id,
            data:$('#form1').serialize(),
            type:"POST",
            success:function(data){
                if (data.success) {
                    showMsg("结算成功");
                    $('#table').bootstrapTable('refresh');
                    $('#table1').bootstrapTable('refresh');
                } else {
                    showErr(data.msg);
                }
            },
            error : function() {
                showErr("出错，请刷新重新操作");
            }
        });
    }
</script>
