<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tag:head title="" />

<style>
.mailbox-attachments li{
	width:180px;
}
</style>
<body class="">
<!-- Main content -->
        <!-- /.col -->
        <div>
          <div class="box box-primary">
           
            <!-- /.box-header -->
            <div class="box-body no-padding">
              <div class="mailbox-read-info">
                <h3 style="margin:20px 0px;">${msgItem.msg.title }</h3>
                <h5>发送人: ${msgItem.msg.ownerName }
                  <span class="mailbox-read-time pull-right">${msgItem.createDate }</span></h5>
              </div>
              
              <div class="mailbox-read-message" style="min-height: 300px;">
                ${msgItem.msg.body }
              </div>
              <!-- /.mailbox-read-message -->
            </div>
          </div>
          <!-- /. box -->
        </div>


</body>