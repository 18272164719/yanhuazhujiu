<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tag:head title="" />

<body class='iframebody'>
<form id="form1" name="form1" method="post">
    <div class="row">
        <div class="col-xs-12">
            <div class="form-group">
                <label for="empId">用户名</label>
                <input class="form-control" id="empId" name="empId" type="text"/>
            </div>
            <div class="form-group">
                <label for="name">姓名</label>
                <input class="form-control" id="name" name="name" type="text"/>
            </div>
            <div class="form-group">
                <label for="pwd">密码</label>
                <input class="form-control" id="pwd" name="pwd" type="text"/>
            </div>
        </div>
    </div>
</form>
</body>

<script>
    function submitAjax() {
        $.ajax({
            url :"${pageContext.request.contextPath}/home/add.htmlx",
            data:$('#form1').serialize(),
            type:"POST",
            success:function(data){
                if (data.success) {
                    top.toptable.bootstrapTable('refresh');
                    top.toptable = null;
                    top.bootbox.hideAll();
                    showMsg("新增成功");
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
