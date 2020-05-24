<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<div class="section">
    <!-- container -->
    <div class="container">
        <form id="checkout-form" class="clearfix"
              action="${contextPath }/register" method="post">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <div class="billing-details">

                    <div class="section-title" style="text-align: center;">
                        <h3 class="title">Đăng kí</h3>
                    </div>
                    <div>
                        <p style="color: red; font-style: italic;">${msg }</p>
                        <form:errors cssStyle="color:red;font-style:italic;"
                                     path="objUser.*"></form:errors>
                    </div>
                    <div class="form-group">
                        <label>Tên đăng nhập: (*)</label>
                        <input class="form-control" type="text" name="username"
                               placeholder="Nhập tên đăng nhập" value="${objUser.username }">
                    </div>
                    <div class="form-group">
                        <label>Mật khẩu: (*)</label>
                        <input class="form-control" type="password" name="password"
                               placeholder="Password">
                    </div>
                    <div class="form-group">
                        <label>Họ tên: (*)</label>
                        <input class="form-control" type="text" name="fullname"
                               placeholder="Nhập họ tên" value="${objUser.fullname }">
                    </div>
                    <div class="form-group">
                        <label>Số điện thoại: (*)</label>
                        <input class="form-control" type="text" name="phone"
                               placeholder="Nhập số điện thoại" value="${objUser.phone }">
                    </div>
                    <div class="form-group">
                        <label>Email: (*)</label>
                        <input class="form-control" type="email" name="email"
                               placeholder="Nhập email" value="${objUser.email }">
                    </div>
                    <div class="form-group">
                        <label>Địa chỉ: (*)</label>
                        <input class="form-control" type="text" name="address"
                               placeholder="Nhập địa chỉ" value="${objUser.address }">
                    </div>
                </div>
                <button class="primary-btn" style="margin-left: 35%;">Đăng kí</button>
            </div>
            <div class="col-md-3"></div>
        </form>
    </div>
</div>