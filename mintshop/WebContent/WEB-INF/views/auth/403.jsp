<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<div class="section">
    <!-- container -->
    <div class="container" style="text-align: center;">
        <h2>OOPS!!!</h2>
        <h3 class="title">Error 404!!! Page not found</h3>
        <a href="${contextPath }/">
            <button class="primary-btn"
                    style="font-size: 12px;">
                Quay về trang chủ <i class="fa fa-arrow-circle-right"></i>
            </button>
        </a>
    </div>
</div>
