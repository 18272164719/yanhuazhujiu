<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <c:set var="base" value="${pageContext.request.contextPath }"/>
    <link rel="stylesheet" type="text/css" href="${base }/resources/font-awesome/css/font-awesome.css" />
    <style type="text/css">
        html{height:100%}
        body{height:100%;margin:0px;padding:0px}
        #container{width:100%;height:100%;}
        #address_search{z-index:99;position:absolute;top:10px;left:60px;margin:5px 5px 5px 5px;width:300px;padding-left:5px;padding-right:5px;height:24px;border-radius:5px;line-height:24px;font-size:12px;border:1px solid #ccc;}
        #address_panel{position:absolute;top:75px;left:100px;height:150px;overflow:auto;}
        #address_btn{cursor:pointer;box-sizing: border-box;z-index:99;position:absolute;top:16px;left:380px;height:24px;line-height:24px;padding-left:5px;padding-right:5px;font-size:12px;border: 1px solid #ccc;background: #fff;border-radius:2px;}
        #address_btn:before{margin-right:3px;color:darkred;}
    </style>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=KQ6KxUcdAGfjsjcf8WnCRqaWn1H7KuBq">
    </script>
    <script type="text/javascript" src="${base }/resources/js/jquery.min.js"></script>
</head>

<body>
<div style="position:relative;width:100%;z-index: 9999;">
    <input id="address_search" type="text" style="" placeholder="请输入关键字">
    <div id="address_btn" class="fa fa-map-marker" onclick="selectMarket()">选择</div>
    <div id="address_panel" style="display:none;">

    </div>
</div>
<div id="container"></div>
<script type="text/javascript">


    function addMarket(lng, lat) {
        var marker = new BMap.Marker(new BMap.Point(lng, lat));
        marker.enableDragging();
        marker.addEventListener("dragend", function(e){ //拖动事件
            mkInf.lng = e.point.lng;
            mkInf.lat = e.point.lat;
            //根据坐标得到地址描述
            new BMap.Geocoder().getLocation(new BMap.Point(mkInf.lng, mkInf.lat), function(result){
                if (result){
                    $("#address_search").val(result.address);
                }
            });
        });
        //添加标注
        map.addOverlay(marker);
    }
    function selectMarket(){
        try {
            console.log(mkInf.lng+"+"+top.mapInf.lng);
            if (mkInf.lng==top.mapInf.lng && mkInf.lat == top.mapInf.lat) {
                top.$.bootstrapGrowl("未更新地图坐标", {type: 'error' });
                return;
            }
            if (mkInf.lng!=undefined&&mkInf.lat!=undefined
                &&mkInf.city!=undefined&&mkInf.district!=undefined&&mkInf.address!=undefined
                &&mkInf.lng!=null&&mkInf.lat!=null!=null
                &&mkInf.city!=null&&mkInf.district!=null&&mkInf.address!=null) {
                top.topCallback(mkInf);
            } else {
                if (mkInf.address == null) {
                    top.$.bootstrapGrowl("未选择数据", {type: 'error' });
                } else {
                    top.$.bootstrapGrowl("当前选择："+mkInf.province+"-"+mkInf.city+"-"+mkInf.district+"-"+mkInf.address, {type: 'error' });
                }
            }
        } catch(e){
            alert("当前选择："+mkInf.province+"-"+mkInf.city+"-"+mkInf.district+"-"+mkInf.address);
        }
    }
    var mkInf = $.extend(true,{}, top.mapInf);
    var map = new BMap.Map("container");
    // 创建地图实例，如果有设置默认值则使用默认值
    if (mkInf.lng!=undefined&&mkInf.lat!=undefined&&mkInf.lng!=""&&mkInf.lat!="") {
        // 创建点坐标
        map.centerAndZoom(new BMap.Point(Number(mkInf.lng), Number(mkInf.lat)), 15);
        addMarket(mkInf.lng,mkInf.lat);
    } else {
        // 创建点坐标
        map.centerAndZoom(new BMap.Point(116.404, 39.915), 15);
        //跳转本地地图并居中
        new BMap.LocalCity().get(function(result) {
            var cityName = result.name;
            map.setCenter(cityName);
        });

    }
    map.enableScrollWheelZoom(true);
    map.addControl(new BMap.NavigationControl());
    map.addControl(new BMap.ScaleControl());
    map.addControl(new BMap.OverviewMapControl());
    map.addControl(new BMap.MapTypeControl());
    var local = new BMap.LocalSearch(map, {
        //智能搜索
        onSearchComplete: function() {
            var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
            mkInf.lng = pp.lng;
            mkInf.lat = pp.lat;
            addMarket(pp.lng,pp.lat);
            map.centerAndZoom(pp, 18);

        }
    });

    var ac = new BMap.Autocomplete(  {"input" : "address_search" ,"location" : map});
    ac.addEventListener("onhighlight", function(e) {  //鼠标放在下拉列表上的事件
        var html = "";
        var data = e.fromitem.value;
        var address = "";
        if (e.fromitem.index > -1) {
            address = data.province +  data.city +  data.district +  data.street +  data.business;
        }
        html = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + address;

        address = "";
        if (e.toitem.index > -1) {
            data = e.toitem.value;
            address = data.province +  data.city +  data.district +  data.street +  data.business;
        }
        html += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + address;
        $("#address_panel").html(html);
    });

    ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
        var data = e.item.value;
        var address = data.province +  data.city +  data.district +  data.street +  data.business;
        mkInf.province = data.province;
        mkInf.city = data.city;
        mkInf.district = data.district;
        mkInf.address = data.street+data.business;
        $("#address_panel").html("onconfirm<br />index = " + e.item.index + "<br />myValue = " + address);
        //清除地图上所有覆盖物
        map.clearOverlays();
        local.search(address);
        $("#address_search").val(address);
    });
    if (top.topCallback == undefined) {
        $("#address_btn,#address_search").hide();
    }
</script>
</body>
</html>