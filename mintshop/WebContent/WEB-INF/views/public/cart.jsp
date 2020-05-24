<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<div class="section">
    <!-- container -->
    <div class="container">
        <div class="col-md-12">
            <div class="order-summary clearfix">
                <div class="section-title">
                    <h3 class="title">Giỏ hàng</h3>
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
                            <td class="details"><a href="#">${item.product.name }</a></td>
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
                            <td class="qty text-center"><input class="quantity"
                                                               id="quantity" name="quantity" type="number"
                                                               value="${item.quantity }" min="1"
                                                               max="${item.product.amount }"></td>
                            <td class="total text-center"><span id="sum" class="total">$
										${price* item.quantity} </span></td>
                            <td class="text-right">
                                <button class="main-btn icon-btn"
                                        onclick="return delcart(${yourStatus.index},${coupon.id})">
                                    <i class="fa fa-close"></i>
                                </button>
                            </td>
                        </tr>
                        <c:set value="${price* item.quantity+ttal }" var="ttal"></c:set>
                    </c:forEach>
                    <tr>
                        <th class="empty" colspan="3"></th>
                        <th>TỔNG</th>
                        <th colspan="2" class="total"><p>$ ${ttal }</p></th>
                    </tr>
                    <c:if test="${coupon!=null }">
                        <tr>
                            <th class="empty" colspan="3"></th>
                            <th>GIẢM CÒN</th>
                            <th colspan="2" class="total"><p>$ ${Math.round(ttal*(1-coupon.value/100) *100)/100}</p>
                            </th>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
                <div class="pull-left">
                    <p style="color:red;font-style:italic;">${msg }</p>
                    <h4>Mã giảm giá</h4>
                    <form action="${contextPath }/gio-hang/giam-gia" method="post">
                        <input class="input search-input" type="text" placeholder="Nhập mã" name="coupon">
                        <button class="primary-btn">Áp dụng</button>
                    </form>
                </div>
                <div class="pull-right">
                    <button class="main-btn" onclick="return updatecart(${coupon.id})">Cập
                        nhật giỏ hàng
                    </button>
                    <a href="${contextPath }/thanh-toan/${coupon.id}">
                        <button
                                class="primary-btn">Thanh toán
                        </button>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function updatecart(coupon_id) {
        var elem = document.getElementsByClassName('quantity');
        if (coupon_id == null) coupon_id = 0;
        var names = [];
        for (var i = 0; i < elem.length; ++i) {
            names.push(elem[i].value);
            //    sum.push(elem[i].value*parseInt(x[i].innerText));
        }
        var webcamval = names;
        //	var total=0;
        $.ajax({
            url: "${contextPath}/changeqty/" + webcamval + "/" + coupon_id,
            cache: false,
            data: {},
            error: function () {
                alert('Có lỗi xảy ra');
            },
            success: function (data) {
                $("#body").html(data);
            }
        });
        return false;
    }

    function delcart(pos, coupon_id) {
        if (coupon_id == null) coupon_id = 0;
        $.ajax({
            url: "${contextPath}/delcart/" + pos + "/" + coupon_id,
            cache: false,
            data: {},
            error: function () {
                alert('Có lỗi xảy ra');
            },
            success: function (data) {
                $("#body").html(data);
                //alert("Bạn đã xoá một mặt hàng");
            }
        });
        return false;
    }

    document.getElementById('aaaaa').innerText = '';
</script>