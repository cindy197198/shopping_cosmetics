<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<div class="section">
    <!-- container -->
    <div class="container">
        <form id="checkout-form" class="clearfix"
              action="${contextPath }/edit-account" method="post">
            <div class="col-md-6">
                <div class="billing-details">
                    <div><p style="color:red;font-style:italic;">${msg }</p></div>
                    <div class="section-title">
                        <h3 class="title">Thông tin tài khoản</h3>
                    </div>
                    <div class="form-group">
                        <label>Tên đăng nhập: </label> <input class="form-control"
                                                              type="text" name="username" readonly="readonly"
                                                              value="${userLogin.username }">
                    </div>
                    <div class="form-group">
                        <label>Mật khẩu mới: </label> <input class="form-control"
                                                             type="password" name="password" placeholder="Password">
                    </div>
                    <div class="form-group">
                        <label>Họ tên : </label> <input class="form-control" type="text"
                                                        name="fullname" placeholder="Nhập họ tên"
                                                        value="${userLogin.fullname }">
                    </div>
                    <div class="form-group">
                        <label>Số điện thoại: </label> <input class="form-control"
                                                              type="text" name="phone" placeholder="Nhập số điện thoại"
                                                              value="${userLogin.phone }">
                    </div>
                    <div class="form-group">
                        <label>Email: </label> <input class="form-control" type="email"
                                                      name="email" placeholder="Nhập email" value="${userLogin.email }">
                    </div>
                    <div class="form-group">
                        <label>Địa chỉ: </label> <input class="form-control"
                                                        type="text" name="address" placeholder="Nhập địa chỉ"
                                                        value="${userLogin.address }">
                    </div>
                </div>
                <button class="primary-btn">Lưu thay đổi</button>
            </div>

        </form>
    </div>
</div>
<script>
    document.getElementById("info").classList.add("active");
</script>