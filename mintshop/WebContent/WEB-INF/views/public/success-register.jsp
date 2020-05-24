<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<div class="section">
    <!-- container -->
    <div class="container" style="text-align: center;">
        <h2>Thành công</h2>
        <h3 class="title">Bạn đã đăng kí tài khoản thành công, vui lòng đăng nhập để tiếp tục mua hàng</h3>
        <a href="${contextPath }/customer-login">
            <button class="primary-btn"
                    style="font-size: 12px;">
                Đăng nhập <i class="fa fa-arrow-circle-right"></i>
            </button>
        </a>
    </div>
</div>
