<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<html>
<shiro:authenticated>
    <%--<%
        response.sendRedirect(request.getContextPath() + "/home/success.htmlx");
    %>--%>
    <script>
        location.href = "${pageContext.request.contextPath }/home/success.htmlx";
    </script>
</shiro:authenticated>
<head>
</head>
<body>
<div align="center">
    <form id = "form" action="" method="post">
        用户名: <input type=""text id="empId" name="userName" placeholder="用户名" value="" maxlength="5"/><p>
        密码:  <input type="password" id="pwd" name="password"  placeholder="密码" value=""/><p>
        验证码：<input type="text" id="verifyCode" name="verifyCode" value=""/><p>
        <a  id="btn" href="#" class="ui-button">登 录</a>
    </form>
    <span id = "error" style="color: red;">${FAILMSG}</span><p>
    <%--<button type="button" class="btn btn-default" onclick=upload()>
        <i class="fa fa-upload"></i> 导入
    </button>--%>
</div>

</body>
</html>
<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/jquery.min.js"></script>
<script>
    $(function(){
        $("#pwd").val();
        $("#btn").click(function(){
            $("#form").submit();
            /*var param={
                empId:$("#empId").val(),
                pwd:$("#pwd").val()
            };
            $.ajax({
                type:"post",
                url:"/home/index.htmlx",
                data:{
                    empId:$("#empId").val(),
                    pwd:$("#pwd").val()
                },
                dataType:"json",
                success:function(data){
                    if(data.success){
                        //window.open("${pageContext.request.contextPath }/home/success.htmlx");
                        window.location.href="/home/success.htmlx";
                    }else{
                        $("#error").html(data.msg)
                    }
                }
            })*/
        });
    });

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

    function showErr(text,obj){
        if(obj){
            $.each($(".editError"),function(index){
                $(this).removeClass("editError");
            });
            obj.addClass("editError");
        }
    }

</script>
