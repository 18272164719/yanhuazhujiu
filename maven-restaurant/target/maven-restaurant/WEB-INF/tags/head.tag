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
    <title>${title }</title>

    <c:set var="base" value="${pageContext.request.contextPath }"/>
    <link rel="stylesheet" type="text/css" href="${base }/resources/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${base }/resources/bootstrapTable/bootstrap-table.css" />
    <link rel="stylesheet" type="text/css" href="${base }/resources/bootstrapTable/bootstrap-editable.css" />
    <link rel="stylesheet" type="text/css" href="${base }/resources/validator/css/bootstrapValidator.css" />
    <link rel="stylesheet" type="text/css" href="${base }/resources/font-awesome/css/font-awesome.css" />
    <link rel="stylesheet" type="text/css" href="${base }/resources/css/adminLTE.css" />
    <link rel="stylesheet" type="text/css" href="${base }/resources/bootstrap/css/icon-picker.css" />
    <link rel="stylesheet" type="text/css" href="${base }/resources/select2/css/select2.css" />
    <link rel="stylesheet" type="text/css" href="${base }/resources/datePicker/css/bootstrap-datepicker.css" />
    <link rel="stylesheet" type="text/css" href="${base }/resources/datePicker/daterangepicker/daterangepicker.css" />
    <link rel="stylesheet" type="text/css" href="${base }/resources/datePicker/css/bootstrap-timepicker.css" />
    <link rel="stylesheet" href="${base }/resources/bootstrapTable/extensions/reorder-columns/dragtable.css">
    <link rel="stylesheet" href="${base }/resources/bootstrapTable/extensions/filter-control/bootstrap-table-filter-control.css">
    <link rel="stylesheet" type="text/css" href="${base }/resources/css/common.css" />
    <link rel="stylesheet" type="text/css" href="${base }/resources/css/small.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/Ionicons/css/ionicons.css" />


    <script type="text/javascript" src="${base }/resources/js/jquery.min.js"></script>
    <script src="${base }/resources/js/jquery-ui.js"></script>
    <script type="text/javascript" src="${base }/resources/js/jquery-dateFormat.min.js"></script>
    <script type="text/javascript" src="${base }/resources/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${base }/resources/select2/js/select2.full.js"></script>
    <script type="text/javascript" src="${base }/resources/select2/js/select2-tool.js"></script>
    <script type="text/javascript" src="${base }/resources/validator/js/bootstrapValidator.js"></script>
    <script type="text/javascript" src="${base }/resources/validator/js/language/zh_CN.js"></script>
    <script type="text/javascript" src="${base }/resources/bootstrapTable/bootstrap-table.js"></script>
    <script type="text/javascript" src="${base }/resources/bootstrapTable/bootstrap-editable.min.js"></script>
    <script type="text/javascript" src="${base }/resources/bootstrapTable/bootstrap-table-editable.js"></script>
    <script type="text/javascript" src="${base }/resources/bootstrapTable/locale/bootstrap-table-zh-CN.js"></script>
    <script type="text/javascript" src="${base }/resources/bootstrap/js/bootbox.min.js"></script>
    <script type="text/javascript" src="${base }/resources/bootstrap/js/iconPicker.js"></script>
    <script type="text/javascript" src="${base }/resources/datePicker/js/bootstrap-datepicker.js" ></script>
    <script type="text/javascript" src="${base }/resources/moment/moment.js"></script>
    <script type="text/javascript" src="${base }/resources/fileinput/js/fileinput.js" ></script>
    <script type="text/javascript" src="${base }/resources/fileinput/js/locales/zh.js" ></script>
    <script type="text/javascript" src="${base }/resources/datePicker/daterangepicker/daterangepicker.js" ></script>
    <script type="text/javascript" src="${base }/resources/datePicker/js/bootstrap-timepicker.js" ></script>
    <script type="text/javascript" src="${base }/resources/js/common.js"></script>
    <script type="text/javascript" src="${base }/resources/js/adminLTE.js"></script>
    <script type="text/javascript" src="${base }/resources/bootstrap/js/bootstrap-growl.js"></script>
    <script src="${base }/resources/bootstrapTable/extensions/export/tableExport.js"></script>
    <script src="${base }/resources/bootstrapTable/extensions/export/bootstrap-table-export.js"></script>
    <script src="${base }/resources/bootstrapTable/extensions/resizable/colResizable-1.6.js"></script>
    <script src="${base }/resources/bootstrapTable/extensions/resizable/bootstrap-table-resizable.js"></script>
    <script src="${base }/resources/bootstrapTable/extensions/reorder-columns/jquery.dragtable.js"></script>
    <script src="${base }/resources/bootstrapTable/extensions/reorder-columns/bootstrap-table-reorder-columns.js"></script>
    <script src="${base }/resources/bootstrapTable/extensions/filter-control/bootstrap-table-filter-control.js"></script>
    <script type="text/javascript" src="${base }/resources/js/hApi.js"></script>
    <script type="text/javascript" src="${base }/resources/js/hValid.js"></script>
    <script type="text/javascript" src="${base }/resources/js/index.js"></script>
    <!--[if lt IE 9]>
    <script src="${base}/resources/js/html5shiv.js"></script>
    <script src="${base}/resources/js/respond.js"></script>
    <![endif]-->
    <style>
        body{
            background-color:#ecf0f5;
            padding:5px;
            margin:0px;
        }
        /* .bs-checkbox .th-inner{
            display:none;
        } */
    </style>
</head>


<body>

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
    function submit(){
        $("#form1").submit();
        return false;
    }
    function setSelectVal_indexFunc(obj,val){
        if($(obj).attr("multiple")){
            //if(val.indexOf("[") != 0){
            $(obj).val(eval("["+val+"]")).trigger("change");
            //}
        }else{
            $(obj).val(val).trigger("change");
        }
    }
    function paramQuery(){
        var uid = $(window.frameElement).data("id");
        var table = top.getBootboxTable(uid) || top.toptable;
        if (table) {
            table.bootstrapTable("refreshOptions",{
                queryParams:function(params){
                    $.each($("input,select,textarea"),function(index){
                        if($(this).attr("type")=="checkbox"){
                            if($(this).is(':checked')){
                                params[$(this).attr("name")] = 1;
                            }else{
                                params[$(this).attr("name")] = 0;
                            }
                        }else{
                            params[$(this).attr("name")] = $(this).val();
                        }
                    })
                    return params;
                }
            });
        }
        //top.$("." + uid).modal("hide");
        h.bootboxHide();
    }
    function getParam(){
        var obj = new Object();
        $.each($("input,select,textarea"),function(index){
            obj[$(this).attr("name")] = $(this).val();
        });
        //var uid = $(window.frameElement).data("id");
        //top.$("." + uid).modal("hide");
        h.bootboxHide();
        return obj;
    }
    $(function() {
        $("body").focus();
        //window.setTimeout("top.SetCwinHeight()", 100);
        $(":checkbox").change(function() {
            if($(this).is(':checked')){
                this.value = 1;
            }else{
                this.value = 0;
            }
        });

        $('#form1').submit(function() {//h.log(123);
            return false;
        })
    });

</script>