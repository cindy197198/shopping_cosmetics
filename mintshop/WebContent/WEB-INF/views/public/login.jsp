<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<div class="section">
    <!-- container -->
    <div class="container">
        <form id="checkout-form" class="clearfix"
              action="${contextPath }/customer-login" method="post">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <div class="billing-details" style="text-align: center;">

                    <div class="section-title">
                        <h3 class="title">Đăng nhập</h3>
                    </div>
                    <div>
                        <p style="color: red; font-style: italic;">${msg }</p>
                        <p style="color: red; font-style: italic;">${err }</p>
                    </div>
                    <div class="form-group">
                        <input class="form-control" type="text" name="username"
                               placeholder="Username">
                    </div>
                    <div class="form-group">
                        <input class="form-control" type="password" name="password"
                               placeholder="Password">
                    </div>
                </div>
                <button class="primary-btn" style="margin-left: 35%;">Đăng
                    nhập
                </button>
                <p>
                    Bạn chưa có tài khoản ? <a href="${contextPath }/register">Đăng kí</a>
                </p>
            </div>
            <div class="col-md-3"></div>
        </form>
    </div>
</div>