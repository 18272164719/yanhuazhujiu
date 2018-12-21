<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tag:head title="" />

<body class='iframebody'>
<form id="form1" name="form1" method="post">
    <div class="row form-inline form-group-270">
        <div class="form-group">
            <label for="code">餐桌编号</label>
            <input class="form-control" id="code" name="code" type="text"/>
        </div>
        <div class="form-group">
            <label for="name">餐桌名称</label>
            <input class="form-control" id="name" name="name" type="text"/>
        </div>
        <div class="form-group">
            <label for="status">状态</label>
            <select class="form-control" id="status" name="status" type="text"></select>
        </div>
        <div class="form-group">
            <label for="sum">消费总金额</label>
            <select class="form-control" id="sum" name="sum" type="text"></select>
        </div>
        <input type="hidden" id="id" name="id">
    </div>
</form>
<div>
    <table id = "table"></table>
</div>
</body>
<script>
    //初始化
    $(function() {
        $("#status").select2({
            placeholder: "请选择",
            minimumResultsForSearch: Infinity,
            width : 160,
            data:[{
                "id":'notUse',
                "text":'未使用'
            },{
                "id":'use',
                "text":'使用中'
            }]
        });
        $('#table1').bootstrapTable({
            url:"<c:out value='${pageContext.request.contextPath }'/>/Variety/pageByList.htmlx",
            toolbar:'#toolbar1',//工具按钮
            pageNumber : 1, //初始化加载第一页，默认第一页
            pageSize : 5, //每页的记录行数（*）
            minRows: 10,
            paginationLoop : false,
            queryParams:function(params){
                params['query["t#id_L_EQ"]'] = top.editrow.id;
                return params;
            },
            columns : [
                {'radio':'true',width:15},
                {field:'code',title:'菜编号',width:90},
                {field:'name',title:'菜名称',width:80},
                {field:'price',title:'价格',width:100},
                {field:'num',title:'数量',width:100,editable:{
                    type: 'text'
                }}
            ],
            onLoadSuccess:function(data){
                var qty=0;
                var obj = new Object();
                $.each(data.rows,function(index){
                    var id = this.id;
                    var price = this.price;
                    $("[data-id='num_"+id+"']").hOninput(function(){
                        qty=Number($("[data-id='num_"+id+"']").val());
                        obj[id]=qty*price;
                        changeQty(obj);
                    });
                });
            }
        });
    });
    function changeQty(obj) {
        var sumAmt =0;
        for(var key in obj){
            sumAmt+=obj[key];
        }
        $("#sum").val(sumAmt.toFixed(2));
    }
    function submitAjax() {
        var selobj = $('#table').bootstrapTable('getSelections');
        if (selobj.length == 0) {
            showInfo('请选择一行');
            return;
        }
        var objarr = new Array();
        $.each(selobj,function (index) {
            var id = this.id;
            var obj = new Object();
            obj.id = id;
            var num = Number($("[data-id='num_"+id+"']").val());
            obj.num = num;
            objarr.push(obj);
        });
        $.ajax({
            url :"${pageContext.request.contextPath}/Variety/editBydetail.htmlx",
            data:$('#form1').serialize()+"&data = "+JSON.stringify(objarr),
            type:"POST",
            success:function(data){
                if (data.success) {
                    if (top.toptable) {
                        top.toptable.bootstrapTable('refresh');
                    }
                    top.toptable = null;
                    top.bootbox.hideAll();
                    showMsg("保存成功");
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
