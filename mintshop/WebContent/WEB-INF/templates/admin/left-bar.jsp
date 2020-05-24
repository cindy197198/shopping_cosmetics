<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<div class="sidebar content-box" style="display: block;">
    <!-- Nav-bar -->
    <ul class="nav">
        <!-- Main menu -->
        <li id="index"><a href="${contextPath }/admincp/index"><i class="fas fa-home"></i> Trang chủ</a></li>
        <li id="proceeds"><a href="${contextPath }/admincp/proceeds"><i class="fas fa-dollar-sign"></i> Doanh thu</a>
        </li>
        <li id="category"><a href="${contextPath }/admincp/cat"><i class="far fa-list-alt"></i> Danh mục</a></li>
        <li id="product"><a href="${contextPath }/admincp/product"><i class="fas fa-shopping-cart"></i> Sản phẩm</a>
        </li>
        <li id="order"><a href="${contextPath }/admincp/order"><i class="fas fa-shopping-basket"></i> Đơn hàng</a></li>
        <li id="user"><a href="${contextPath }/admincp/user"><i class="fas fa-users"></i> Người dùng</a></li>
        <li id="contact"><a href="${contextPath }/admincp/contact"><i class="fas fa-phone-square"></i> Liên hệ</a></li>
        <li id="coupon"><a href="${contextPath }/admincp/coupon"><i class="fas fa-credit-card"></i> Mã giảm giá</a></li>
    </ul>
    <!-- /.nav-bar -->
</div>