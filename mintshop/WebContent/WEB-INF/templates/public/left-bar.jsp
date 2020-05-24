<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<div class="sidebar content-box" style="display: block;">
    <!-- Nav-bar -->
    <ul class="nav"
        style="margin-left: 40px; margin-top: 20px; font-size: large; font-weight: bold;">
        <!-- Main menu -->
        <li id="info"><a href="${contextPath }/my-account"><i
                class="fa fa-user-o"></i> Thông tin tài khoản</a></li>
        <hr>
        <li id="order"><a href="${contextPath }/my-account/order"><i
                class="fa fa-shopping-cart"></i> Đơn hàng</a></li>
        <hr>
        <li id="contact"><a href="${contextPath }/customer-logout"><i
                class="fa fa-unlock-alt"></i> Đăng xuất</a></li>
    </ul>
    <!-- /.nav-bar -->
</div>