<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tag:head title="" />

<body class='iframebody'>
<form id="form1" name="form1" method="post">
    <div class="row form-inline form-group-200">
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
            <select class="form-control" id="status" name="status"></select>
        </div>
        <div class="form-group">
            <label for="num">菜数量</label>
            <input class="form-control" id="num" name="num" type="text" readonly/>
        </div>
        <div class="form-group">
            <label for="sum">消费总金额</label>
            <input class="form-control" id="sum" name="sum" type="text" readonly/>
        </div>
        <input type="hidden" id="id" name="id">
    </div>
</form>
<div>
    <table id = "table"></table>
    <div id="toolbar" class="btn-group">
        <button type="button" class="btn btn-default"   onclick=openChoose()>
            <i class="fa fa-plus"></i> 新增
        </button>
    </div>
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
        $('#table').bootstrapTable({
            url:"<c:out value='${pageContext.request.contextPath }'/>/Variety/pageByList.htmlx",
            toolbar:'#toolbar',//工具按钮
            pagination : true, //是否显示分页
            pageNumber : 1, //初始化加载第一页，默认第一页
            pageSize : 10, //每页的记录行数（*）
            paginationLoop : false,
            queryParams:function(params){
                params['query["t#id_L_EQ"]'] = top.editrow.id;
                return params;
            },
            columns : [
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
                var obj1 = new Object();
                $.each(data.rows,function(index){
                    var id = this.id;
                    var price = this.price;
                    $("[data-id='num_"+id+"']").hOninput(function(){
                        qty=Number($("[data-id='num_"+id+"']").val());
                        obj[id] = qty;
                        obj1[id]=qty*price;
                        changeQty(obj,obj1);
                    });
                });
            }
        });
    });
    function changeQty(obj,obj1) {
        var beginNum = $("#num").val();
        var beginSum = $("#sum").val();
        var sumQty =0;
        for(var key in obj){
            sumQty+=obj[key];
        }
        $("#num").val(sumQty+beginNum);
        var sumAmt =0;
        for(var key in obj1){
            sumAmt+=obj1[key];
        }
        $("#sum").val(sumAmt+beginSum);
    }

    //选择菜品
    window.openChoose =function (obj){
        top.topCallback = function(obj){
            $('#table').bootstrapTable("refreshOptions",{
                url:"<c:out value='${pageContext.request.contextPath }'/>/Variety/pageBy.htmlx",
                queryParams:function(params){
                    params['query["t#id_L_IN"]'] = obj;
                    return params;
                }
            });
        };
        top.varitybox = top.bootbox.dialog({
            message: "<c:out value='${pageContext.request.contextPath }'/>/Variety/choose.htmlx",
            title: "选择",
            width:850,
            height:400,
            className:"workUrl",
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
    }
    function submitAjax() {
        var selobj = $('#table').bootstrapTable('getData');
        if (selobj.length == 0) {
            showInfo('请选菜');
            return;
        }
        var objarr = new Array();
        $.each(selobj,function (index) {
            var obj = new Object();
            obj.id = this.id;
            objarr.push(obj);
        });
        $.ajax({
            url:"<c:out value='${pageContext.request.contextPath }'/>/Variety/addByDetail.htmlx",
            data:$('#form1').serialize()+"&data="+JSON.stringify(objarr),
            type:"POST",
            success : function(data) {
                if (data.success) {
                    h.bootboxRefresh("新增成功");
                    h.bootboxHide();
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
