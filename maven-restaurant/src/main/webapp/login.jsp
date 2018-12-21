<<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />
<html>
<%--<shiro:authenticated>
    <%
        response.sendRedirect(request.getContextPath() + "/home/success.htmlx");
    %>
</shiro:authenticated>--%>
<head>
    <c:set var="base" value="${pageContext.request.contextPath }"/>
</head>
<body>
<div align="center">
    <form>
        用户名: <input type=""text id="empId" name="empId" value=""/><p>
        密码: <input type="password" id="pwd" name="pwd" value=""/><p>
        <a  id="btn" href="#" class="ui-button" >登 录</a>
    </form>

    <button type="button" class="btn btn-default" onclick=upload()>
        <i class="fa fa-upload"></i> 导入
    </button>

    <form id="form1" method="POST" enctype="multipart/form-data">
        <input type="file" name="file" style="display:none" id="file" />
    </form>
</div>

</body>
</html>
<script>
    function upload(){
        $("#file").click();
    }
    $("#file").change(function(){
        if (confirm("确认导入吗？")){
            var formData = new FormData($("#form1")[0]);
            h.ajax({
                url : "<c:out value='${pageContext.request.contextPath }'/>/upload/impExcel.htmlx",
                data : formData,
                processData:false,
                contentType: false,
                dataSuccess : function(data) {
                    $("#table").bootstrapTable('refresh');
                    showMsg("批量导入成功!");
                },
                error : function(data){
                    showErr("批量导入出错!");
                }
            });
        }else{
            location.reload(true);
        }
    });

    $(function(){
        $("#btn").click(function(){
            var param={
                empId:$("#empId").val(),
                pwd:$("#pwd").val(),
                "role.id":"123",
                id : 465
            };
            $.ajax({
                type:"post",
                url:"/home/login.htmlx",
                data:JSON.stringify(param),
                dataType:"json",
                success:function(data){
                    if(data.success){
                        window.location.href="/home/success.htmlx";
                    }else{
                        showErr(data.msg);
                    }
                }
            })
        });
    });

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
