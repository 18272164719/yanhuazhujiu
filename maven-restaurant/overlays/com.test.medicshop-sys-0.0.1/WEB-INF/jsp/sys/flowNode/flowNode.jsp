<%@page import="com.shyl.sys.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<tag:head title="" />
<style>
.iframebody {
	padding: 5px;
	background: #ecf0f5;
}
</style>
<body class='iframebody'>
	<ul class="timeline">
		<li class="time-label"><span class="bg-aqua"> ${createDate }
		</span></li>
		<c:forEach items="${flowNodeList}" var="item">
			<c:choose>
				<c:when com.test="${item.status eq 'agree'}">
					<li><i class="fa fa-user bg-green"></i>
						<div class="timeline-item">
							<span class="time"><i class="fa fa-clock-o"></i>${item.flowDate }</span>
							<h3 class="timeline-header">审核人：${item.auditor }</h3>
							<div class="timeline-body">${item.note }</div>
						</div>
					</li>
				</c:when>
				<c:when com.test="${item.status eq 'unaudit'}">
					<li><i class="fa fa-hourglass-half bg-aqua"></i>
						<div class="timeline-item">
							<h3 class="timeline-header no-border">审核中...</h3>
						</div>
					</li>
				</c:when>
				<c:when com.test="${item.status eq 'disagree'}">
					<li><i class="fa fa-user bg-red"></i>
						<div class="timeline-item">
							<span class="time"><i class="fa fa-clock-o"></i>${item.flowDate }</span>
							<h3 class="timeline-header">审核人：${item.auditor }</h3>
							<div class="timeline-body">${item.note }</div>
						</div>
					</li>
				</c:when>
			</c:choose>
		</c:forEach>
		<c:choose>
			<c:when com.test="${status eq 'unaudit'}">
				<li><i class="fa fa-clock-o bg-gray"></i></li>
			</c:when>
			<c:otherwise>
				<li><i class="fa fa-stop bg-aqua"></i></li>
			</c:otherwise>
		</c:choose>
	</ul>
</body>

<script>
	//初始化
	$(function() {
		
	});
</script>
