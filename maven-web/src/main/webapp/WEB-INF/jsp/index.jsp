<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />
<meta http-equiv="Access-Control-Allow-Origin" content="http://218.18.233.228:8082">
<html>
<head>
</head>
<body>
<a href="/home/logout.htmlx">登出</a>
<!-- 工具栏的按钮，可以自定义事件 -->
<div id="toolbar" class="btn-group">
    <button type="button" class="btn btn-default"   onclick=addOpen()>
        <i class="fa fa-plus"></i> 新增
    </button>
    <button type="button" class="btn btn-default" onclick=editOpen()>
        <i class="fa fa-pencil"></i> 修改
    </button>
    <button type="button" class="btn btn-default" onclick=delFunc()>
        <i class="fa fa-trash"></i> 删除
    </button>
    <button type="button" class="btn btn-default" onclick=send()>
        <i class="fa fa-plus"></i> 调用接口
    </button>
    <button type="button" class="btn btn-default" onclick=sign()>
        <i class="fa fa-plus"></i> 加密
    </button>
    <button type="button" class="btn btn-default" onclick=open()>
        <i class="fa fa-trash"></i> 打开百度
    </button>
</div>

<div class="row" >
    <div class="col-xs-6">
        <!-- 工具栏的按钮，可以自定义事件 -->
        <table id="table"></table>
    </div>
    <div class="col-xs-6 lefeP0">
        <table id="table1"></table>
    </div>
    <input class="form-control" id="data_return" name="data_return" style="width: 600px;height: 200px;margin-left: 100px;margin-bottom: 400px;"/>
</div>

</body>
</html>
<script>

    //初始化
    $(function () {
        $('#table').bootstrapTable({
            url:"<c:out value='${pageContext.request.contextPath }'/>/home/listAll.htmlx",
            sidePagination :'client',
            toolbar:'#toolbar',//工具按钮
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

    function addOpen() {
        top.toptable = $("#table");
        top.editrow = {};
        top.bootbox.dialog({
            message: "<c:out value='${pageContext.request.contextPath }'/>/home/add.htmlx",
            title: "添加用户",
            width:420,
            height:300,
            buttons: {
                "取消": {},
                success: {
                    label: "保存",
                    callback: function() {
                        z.iframe(this).submitAjax();
                        return false;
                    }
                }
            }
        });
        return;
    }

    
    /*这里是用ajax去调用webservice接口*/
    function send() {
        var sendmsg = "<data><ptdm>SZ</ptdm><ypbm>100002</ypbm><ypmc></ypmc><pzwh></pzwh><sccj></sccj></data>";
        /*sendmsg = "<data><ptdm>SZ</ptdm><ypbm>100002</ypbm><ypmc></ypmc><pzwh></pzwh><sccj></sccj></data>";
        sendmsg = "{\"ptdm\":\"SZ\",\"ypbm\":\"100002\",\"ypmc\":\"\" }";*/
        var iocode = "74255145";
        var method = "get";
        var sign = SHA1(iocode+sendmsg);
        var soap = '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:wss="http://webservice.msc.shyl.com/">'
            +'<soapenv:Header/><soapenv:Body><wss:'+method+'>'
            +'<sign>'+sign+'</sign>'
            +'<dataType>'+1+'</dataType>'
            +'<data>'+sendmsg.replace(/\&/g,"&amp;").replace(/\>/g,"&gt;").replace(/\</g,"&lt;")+'</data>'
            +'</wss:'+method+'></soapenv:Body></soapenv:Envelope>';
        console.log(soap);
        $.ajax({
            "url":"http://218.18.233.228:8082/msc-webservice-b2b/webService/product?wsdl",
            "type" : 'POST',
            "async" : false,
            "dataType" : 'xml',
            "Content-Type" : 'text/xml;charset=UTF-8',
            "data" : soap,
            complete: function( xhr,data ){
                alert(xhr.status);
                if(xhr.status ==200) {
                    var ret = xhr.responseText;
                    $("#data_return").val(ret);
                }
                console.log("返回信息1："+JSON.stringify(xhr));
                console.log("返回信息2："+JSON.stringify(data));
            }
        })

        /*$.ajax({
            url:"http://218.18.233.228:8082/msc-webservice-b2b/webService/product?wsdl",
            type : 'POST',
            async : false,
            contentType: "application/json; charset=utf-8",
            dataType : 'jsonp',
            data : {"ptdm":"SZ","ypbm":"100002","ypmc":"","pzwh":""},
            jsonp: "callback",
            success : function(data) {
                h.log(data);
            }
        })*/
    }

    /*var xhr = new XMLHttpRequest();
    function send2() {
        var url = "http://218.18.233.228:8082/msc-webservice-b2b/webService/product?wsdl";
        var sendmsg = "<data><ptdm>SZ</ptdm><ypbm>100002</ypbm><ypmc></ypmc><pzwh></pzwh><sccj></sccj></data>";
        var iocode = "74255145";
        var method = "get";
        var sign = SHA1(iocode+sendmsg);
        var soap = '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:wss="http://webservice.msc.shyl.com/">'
            +'<soapenv:Header/><soapenv:Body><wss:'+method+'>'
            +'<sign>'+sign+'</sign>'
            +'<dataType>'+2+'</dataType>'
            +'<data>'+sendmsg.replace(/\&/g,"&amp;").replace(/\>/g,"&gt;").replace(/\</g,"&lt;")+'</data>'
            +'</wss:'+method+'></soapenv:Body></soapenv:Envelope>';
        xhr.open('POST',url,true);
        xhr.onreadystatechange = _back;
        xhr.send(soap);
        function _back(){
            if(xhr.readyState == 4){
                alert(xhr.status)
                if(xhr.status == 200){
                    alert('调用Webservice成功了');
                    var ret = xhr.responseXML;
                    alert(ret);
                }
            }
        }
    }*/
    
    function sign() {
        var iocode = "33667776";
        var data = "{\"jls\":1,\"mx\":[{\"yyypbm\":\"50670900\",\"tym\":\"帕米膦酸二钠\",\"sxh\":1,\"scqymc\":\"浙江奥托康制药集团股份有限公司\",\"gg\":\"5ml:15mg\",\"ypmc\":\"帕米膦酸二钠\",\"ypbm\":\"119335\",\"sfjy\":\"0\",\"jxmc\":\"注射液\",\"bzgg\":\"6瓶/盒\",\"bzdw\":\"盒\"}],\"yybm\":\"45726620444200011A1001\",\"ptdm\":\"ZS\"}";
        var sign = SHA1(iocode+data);
        console.log(sign);
    }
</script>
