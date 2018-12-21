<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tag:head title="" />

<body class='iframebody'>
<form id="form1" name="form1" method="post">
    <div class="row">
        <div class="col-xs-12">
            <div class="form-group">
                <label for="code">餐桌编号</label>
                <input class="form-control" id="code" name="code" type="text"/>
            </div>
        </div>
        <div class="col-xs-12">
            <div class="form-group">
                <label for="name">餐桌名称</label>
                <input class="form-control" id="name" name="name" type="text"/>
            </div>
        </div>
        <div class="col-xs-12">
            <div class="form-group">
                <label for="status">状态</label>
                <select class="form-control" id="status" name="status" type="text"></select>
            </div>
        </div>
    </div>
</form>
</body>
<script>
    //初始化
    $("#status").select2({
        placeholder: "请选择",
        minimumResultsForSearch: Infinity,
        width : 450,
        data:[{
            "id":'notUse',
            "text":'未使用'
        },{
            "id":'use',
            "text":'使用中'
        }]
    });
    function submitAjax() {
        $.ajax({
            url :"${pageContext.request.contextPath}/Variety/addTable.htmlx",
            data:$('#form1').serialize(),
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
