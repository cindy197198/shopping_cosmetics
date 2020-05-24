<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!-- BREADCRUMB -->
<div id="breadcrumb">
    <div class="container">
        <ul class="breadcrumb">
            <li><a href="${contextPath }/">Trang chủ</a></li>
            <li class="active">Xác nhận mua hàng</li>
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
            <form id="checkout-form" class="clearfix"
                  action="${contextPath }/confirm/${coupon.id}" method="get">
                <div class="col-md-6">
                    <div class="billing-details">
                        <div class="section-title">
                            <h3 class="title">Xác nhận đơn hàng</h3>
                        </div>
                        <div class="form-group">
                            <label>Họ tên: ${objCustomer.fullname }</label>
                        </div>
                        <div class="form-group">
                            <label>Email: ${objCustomer.email }</label>
                        </div>
                        <div class="form-group">
                            <label>Địa chỉ: ${objCustomer.address }</label>
                        </div>
                        <div class="form-group">
                            <label>Số điện thoại: ${objCustomer.phone }</label>
                        </div>
                        <c:if test="${not empty note }">
                            <div class="form-group">
                                <label>Ghi chú: ${note }</label>
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label>Phương thức thanh toán: <c:choose>
                                <c:when test="${payment==1 }"> Thanh toán khi nhận hàng (COD)</c:when>
                                <c:otherwise> Đã thanh toán qua Paypal</c:otherwise>
                            </c:choose>
                            </label>
                        </div>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="order-summary clearfix">
                        <div class="section-title">
                            <h3 class="title">Sản phẩm đã đặt</h3>
                        </div>
                        <table class="shopping-cart-table table">
                            <thead>
                            <tr>
                                <th>Sản phẩm</th>
                                <th class="text-center">Tổng</th>
                            </tr>
                            </thead>
                            <tbody id="body">
                            <c:set value="${0 }" var="ttal"></c:set>
                            <c:forEach items="${cart }" var="item" varStatus="yourStatus">
                                <tr>
                                    <td class="details">${item.product.name }<span
                                            style="font-weight: bold; font-size: 18px;"> x
                                            ${item.quantity }</span></td>
                                    <c:choose>
                                        <c:when test="${item.product.newprice!=0 }">
                                            <c:set value="${item.product.newprice}" var="price"></c:set>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set value="${item.product.price }" var="price"></c:set>
                                        </c:otherwise>
                                    </c:choose>
                                    <td class="total text-center"><span id="sum"
                                                                        class="total">$ ${price* item.quantity} </span>
                                    </td>

                                </tr>
                                <c:set value="${price* item.quantity+ttal }" var="ttal"></c:set>
                            </c:forEach>
                            <tr>
                                <th>Tổng</th>
                                <th class="total"><p>$ ${ttal }</p></th>
                            </tr>
                            <c:if test="${coupon!=null }">
                                <tr>

                                    <th>GIẢM CÒN</th>
                                    <th class="total"><p>$ ${Math.round(ttal*(1-coupon.value/100) *100)/100}</p></th>
                                </tr>
                            </c:if>
                            </tbody>
                        </table>
                        <div class="pull-right">
                            <button class="primary-btn">Xác nhận</button>
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