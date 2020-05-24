<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<div class="section">
    <!-- container -->
    <div class="container">
        <div class="col-md-9">
            <div class="billing-details">
                <c:if test="${empty listOrder }">
                    <h3 class="title" style="text-align: center;">Hiện tại không
                        có đơn hàng nào!</h3>
                </c:if>
                <c:forEach items="${listOrder }" var="itemOrder" varStatus="status">
                    <div class="row">
                        <div class="section-title">
                            <h3 class="title">Đơn hàng ${status.index+1 }</h3>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>Họ tên: ${itemOrder.customer.fullname }</label>
                            </div>
                            <div class="form-group">
                                <label>Email: ${itemOrder.customer.email }</label>
                            </div>
                            <div class="form-group">
                                <label>Địa chỉ: ${itemOrder.customer.address }</label>
                            </div>
                            <div class="form-group">
                                <label>Số điện thoại: ${itemOrder.customer.phone }</label>
                            </div>
                            <c:if test="${not empty itemOrder.note }">
                                <div class="form-group">
                                    <label>Ghi chú: ${itemOrder.note }</label>
                                </div>
                            </c:if>
                            <div class="form-group">
                                <label>Phương thức thanh toán: <c:choose>
                                    <c:when test="${payment==1 }"> Thanh toán khi nhận hàng (COD)</c:when>
                                    <c:otherwise>Thanh toán qua Paypal</c:otherwise>
                                </c:choose>
                                </label>
                            </div>
                            <div class="form-group">
                                <label>Trạng thái: ${itemOrder.status.name }</label>
                            </div>
                            <div class="form-group">
                                <label>Ngày đặt hàng: ${itemOrder.date_create }</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <table class="shopping-cart-table table">
                                <thead>
                                <tr>
                                    <th>Sản phẩm</th>
                                    <th class="text-center">Tổng</th>
                                </tr>
                                </thead>
                                <tbody id="body">
                                <c:set value="${0 }" var="ttal"></c:set>
                                <c:forEach items="${itemOrder.listProduct }" var="item"
                                           varStatus="yourStatus">
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
                                                                            class="total">$${price* item.quantity} </span>
                                        </td>

                                    </tr>
                                    <c:set value="${price* item.quantity+ttal }" var="ttal"></c:set>
                                </c:forEach>
                                <tr>
                                    <th>Tổng</th>
                                    <th class="total"><p>$${ttal }</p></th>
                                </tr>
                                <c:if test="${itemOrder.coupon_value!=0 }">
                                    <tr>
                                        <th>GIẢM CÒN</th>
                                        <th class="total"><p>
                                            $${Math.round(ttal*(1-itemOrder.coupon_value/100) *100)/100}</p></th>
                                    </tr>
                                </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:forEach>

            </div>
        </div>

    </div>
</div>
<script>
    document.getElementById("order").classList.add("active");
</script>