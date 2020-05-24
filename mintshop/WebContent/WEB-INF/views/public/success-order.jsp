<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<div class="section">
    <!-- container -->
    <div class="container" style="text-align: center;">
        <h2>THANK YOU</h2>
        <h3 class="title">Đặt hàng hoàn tất, cám ơn bạn đã mua sản phẩm
            của chúng tôi!</h3>
        <a href="${contextPath }/">
            <button class="primary-btn"
                    style="font-size: 12px;">
                Quay về trang chủ <i class="fa fa-arrow-circle-right"></i>
            </button>
        </a>
    </div>
</div>
