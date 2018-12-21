<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />

<style>
body {
    background-color: #ecf0f5;
    margin: 0px;
    overflow-y:hidden;
} 
</style>
<div class="progress progress-striped active"> <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 100%;"></div></div>

<script>

$(function() {
	var uid = $(window.frameElement).data("id")||$(parent.frameElement).data("id");
	top.progressBarId = uid;
	h.log(uid);
});
function closeFunc(){
	alert("123");
	h.hideProgressBar();
}
</script>
		

