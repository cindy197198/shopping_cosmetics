<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!-- BREADCRUMB -->
<div id="breadcrumb">
    <div class="container">
        <ul class="breadcrumb">
            <li><a href="${contextPath }/">Trang chủ</a></li>
            <li class="active">Thanh toán</li>
        </ul>
    </div>
</div>
<!-- /BREADCRUMB -->

<!-- section -->
<div class="section">
    <!-- container -->
    <div class="container">
        <!-- row -->
        <div class="row">
            <form id="checkout-form" class="clearfix" method="post">
                <div class="col-md-6">
                    <div class="billing-details">
                        <p style="color: red; font-style: italic;">${msg }</p>
                        <c:choose>
                            <c:when test="${userLogin==null }">
                                <p>
                                    Bạn đã có tài khoản ? <a href="${contextPath }/customer-login">Đăng
                                    nhập</a>
                                </p>
                                <p>
                                    Tạo tài khoản mới ? <a href="${contextPath }/register">Đăng
                                    kí</a>
                                </p>
                                <div class="section-title">
                                    <h3 class="title">Thông tin thanh toán</h3>
                                </div>
                                <div class="form-group">
                                    <label>Họ tên: (*)</label> <input class="input" type="text"
                                                                      name="fullname" placeholder="Họ và tên"
                                                                      id="fullname"
                                                                      value="${objCustomer.fullname }">
                                </div>
                                <div class="form-group">
                                    <label>Email: (*)</label> <input class="input" type="email"
                                                                     name="email" id="email" placeholder="Email"
                                                                     value="${objCustomer.email }">
                                </div>
                                <div class="form-group">
                                    <label>Địa chỉ: (*)</label> <input class="input" type="text"
                                                                       name="address" id="address" placeholder="Địa chỉ"
                                                                       value="${objCustomer.address }">
                                </div>
                                <div class="form-group">
                                    <label>Số điện thoại: (*)</label> <input class="input"
                                                                             type="text" name="phone" id="phone"
                                                                             placeholder="Điện thoại"
                                                                             value="${objCustomer.phone }">
                                </div>
                                <div class="form-group">
                                    <label>Ghi chú: </label> <input class="input" type="text"
                                                                    name="note" placeholder="Ghi chú">
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="section-title">
                                    <h3 class="title">Thông tin thanh toán</h3>
                                </div>
                                <div class="form-group">
                                    <label>Họ tên: ${userLogin.fullname }</label>
                                </div>
                                <div class="form-group">
                                    <label>Email: ${userLogin.email }</label>
                                </div>
                                <div class="form-group">
                                    <label>Địa chỉ: ${userLogin.address }</label>
                                </div>
                                <div class="form-group">
                                    <label>Số điện thoại: ${userLogin.phone }</label>
                                </div>
                                <div class="form-group">
                                    <label>Ghi chú: </label> <input class="input" type="text"
                                                                    name="note" placeholder="Ghi chú">
                                </div>
                                <div class="form-group">
                                    <div class="input-checkbox">
                                        <input type="checkbox" id="register"> <label
                                            class="font-weak" for="register">Giao hàng đến địa
                                        chỉ khác</label>
                                        <div class="caption">
                                            <label>Địa chỉ: </label> <input class="input"
                                                                            name="otheraddress" placeholder="Địa chỉ"
                                                                            value="${objCustomer.address }"> <label>Điện
                                            thoại: </label> <input class="input" name="otherphone"
                                                                   placeholder="Số điện thoại"
                                                                   value="${objCustomer.phone }">
                                        </div>
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <div class="col-md-6">
                    <p style="color: red; font-style: italic;">${err }</p>
                    <div class="payments-methods">
                        <div class="section-title">
                            <h4 class="title">Hình thức thanh toán</h4>
                        </div>
                        <div class="input-checkbox">
                            <input type="radio" name="payments" id="payments-2" value="2"
                                   checked> <label for="payments-2">Thanh toán bằng
                            Paypal</label>
                            <div class="caption">
                                <p>
                                <div id="paypal-button-container" onload="return saveobjcustomer()"></div>
                                </p>
                            </div>
                        </div>
                        <div class="input-checkbox">
                            <input type="radio" name="payments" id="payments-1" value="1">
                            <label for="payments-1">Thanh toán sau khi nhận hàng
                                (COD)</label>
                        </div>
                    </div>
                </div>

                <div class="col-md-12">
                    <div class="order-summary clearfix">
                        <div class="section-title">
                            <h3 class="title">Sản phẩm đặt hàng</h3>
                        </div>
                        <table class="shopping-cart-table table">
                            <thead>
                            <tr>
                                <th>Sản phẩm</th>
                                <th></th>
                                <th class="text-center">Đơn giá</th>
                                <th class="text-center">Số lượng</th>
                                <th class="text-center">Tổng tiền</th>
                                <th class="text-right"></th>
                            </tr>
                            </thead>
                            <tbody id="body">
                            <c:set value="${0 }" var="ttal"></c:set>
                            <c:forEach items="${cart }" var="item" varStatus="yourStatus">
                                <tr>
                                    <td class="thumb"><img
                                            src="${contextPath}/fileUpload/${item.product.picture.substring(0, item.product.picture.indexOf(' '))}"
                                            alt=""></td>
                                    <td class="details">${item.product.name }</td>
                                    <td class="price text-center"><c:choose>
                                        <c:when test="${item.product.newprice!=0 }">
													<span id="price" class="price1"> <strong>$
															${item.product.newprice } </strong></span>
                                            <br>
                                            <del class="font-weak">
                                                <small>$ ${item.product.price } </small>
                                            </del>
                                            <c:set value="${item.product.newprice}" var="price"></c:set>
                                        </c:when>
                                        <c:otherwise>
													<span id="price" class="price1"><strong>$
															${item.product.price } </strong></span>
                                            <c:set value="${item.product.price }" var="price"></c:set>
                                        </c:otherwise>
                                    </c:choose></td>
                                    <td class="qty text-center">${item.quantity }</td>
                                    <td class="total text-center"><span id="sum"
                                                                        class="total">$ ${price* item.quantity} </span>
                                    </td>

                                </tr>
                                <c:set value="${price* item.quantity+ttal }" var="ttal"></c:set>
                            </c:forEach>
                            <tr>
                                <th class="empty" colspan="3"></th>
                                <th>TỔNG</th>
                                <th colspan="2" class="total" id="total">${ttal }</th>
                            </tr>
                            <c:choose>
                                <c:when test="${coupon!=null }">
                                    <tr>
                                        <th class="empty" colspan="3"></th>
                                        <th>GIẢM CÒN</th>
                                        <th colspan="2" class="total" id="deal"><p>
                                                ${Math.round(ttal*(1-coupon.value/100) *100)/100}</p></th>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <div id="deal"><p></p></div>
                                </c:otherwise>
                            </c:choose>
                            </tbody>
                        </table>
                        <div class="pull-right">
                            <button class="primary-btn">Tiếp tục</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <!-- /row -->
    </div>
    <!-- /container -->
</div>
<!-- /section -->

<script>
    paypal.Button
        .render(
            {

                env: 'sandbox', // sandbox | production
                client: {
                    sandbox: 'AUBrn23IMVpQ4HeVUzQTPt_BOeCHuK3Lv5uKk8GJFP3cRrtJ1VPPrYfNw1sHXM3V9IXXo0tdz-w5W8q3',
                    production: 'xxxxxxxxxx'
                },

                // Show the buyer a 'Pay Now' button in the checkout flow
                commit: true,

                // payment() is called when the button is clicked
                payment: function (data, actions) {
                    var sum = document.getElementById('total').innerText;
                    var deal = document.getElementById('deal').innerText;
                    if (deal != "")
                        sum = deal;
                    // Make a call to the REST API to set up the payment
                    return actions.payment
                        .create({
                            payment: {
                                transactions: [{
                                    amount: {
                                        total: sum,
                                        currency: 'USD'
                                    }
                                }],
                                redirect_urls: {
                                    return_url: '${contextPath}/thanh-toan/${coupon.id}?msg=success',
                                    cancel_url: '${contextPath}/thanh-toan/${coupon.id}?msg=error'
                                }
                            }
                        });
                },

                // onAuthorize() is called when the buyer approves the payment
                onAuthorize: function (data, actions) {

                    // Make a call to the REST API to execute the payment
                    return actions.payment.execute().then(function () {

                        alert("Thanh toán thành công");
                        actions.redirect();
                    });
                },

                onCancel: function (data, actions) {
                    alert("Thanh toán thất bại");
                    actions.redirect();
                },
                onClick: function () {
                    var fullname = $("#fullname").val();
                    var address = $("#address").val();
                    var email = $("#email").val();
                    var phone = $("#phone").val();
                    $.ajax({
                        type: "POST",
                        url: "${contextPath}/saveCustomer",
                        cache: false,
                        data: {afullname: fullname, aaddress: address, aemail: email, aphone: phone}
                    });
                    return false;
                }
            }, '#paypal-button-container');
</script>