<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<div class="row">
    <a href="${contextPath }/admincp/contact/${page}">
        <h3>
            <i class="fas fa-chevron-circle-left"></i> Trở về
        </h3>
    </a>
    <div class="col-md-12 panel-info">
        <div class="content-box-header panel-heading">
            <div class="panel-title ">Chi tiết liên hệ</div>
        </div>
        <div class="content-box-large box-with-header">
            <div>
                <div class="row mb-10"></div>
                <div class="row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="name">Họ và tên:</label>
                            <p>${objContact.fullname }</p>
                        </div>
                        <div class="form-group">
                            <label for="name">Email:</label>
                            <p>${objContact.email }</p>
                        </div>
                        <div class="form-group">
                            <label for="name">Ngày gửi:</label>
                            <p>${objContact.date_create }</p>
                        </div>
                        <div class="form-group">
                            <label for="name">Chủ đề:</label>
                            <p>${objContact.subject }</p>
                        </div>
                        <div class="form-group">
                            <label for="name">Nội dung:</label>
                            <p>${objContact.content }</p>
                        </div>
                    </div>
                </div>
                <hr>
            </div>
        </div>
    </div>
</div>
<!-- /.row col-size -->
<script>
    document.getElementById("contact").classList.add('current');
</script>