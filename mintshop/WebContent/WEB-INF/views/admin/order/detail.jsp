<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<div class="row">
    <form
            action="${contextPath }/admincp/order/view/page-${page }/${objOrder.id }"
            method="post" enctype="multipart/form-data">
        <a href="${contextPath }/admincp/order/${page}">
            <h3><i class="fas fa-chevron-circle-left"></i> Trở về
            </h3></a>
        <div class="col-md-12 panel-info">
            <div class="content-box-header panel-heading">
                <div class="panel-title ">Thông tin đơn hàng</div>
            </div>
            <div class="content-box-large box-with-header">
                <div>
                    <div class="row mb-10"></div>
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label for="name">Họ và tên:</label>
                                <p>${objOrder.customer.fullname }</p>
                            </div>
                            <div class="form-group">
                                <label for="name">Email:</label>
                                <p>${objOrder.customer.email }</p>
                            </div>
                            <div class="form-group">
                                <label for="name">Số điện thoại:</label>
                                <p>${objOrder.customer.phone }</p>
                            </div>
                            <div class="form-group">
                                <label for="name">Địa chỉ:</label>
                                <p>${objOrder.customer.address }</p>
                            </div>
                            <div class="form-group">
                                <label for="name">Phương thức thanh toán:</label>
                                <p>${objOrder.pay_method.name }</p>
                            </div>
                            <div class="form-group">
                                <label for="name">Ngày đặt hàng:</label>
                                <p>${objOrder.date_create }</p>
                            </div>
                            <div class="form-group">
                                <label>Trạng thái thanh toán</label> <select
                                    class="form-control" name="payment_status">
                                <c:choose>
                                    <c:when test="${objOrder.payment_status==0 }">
                                        <option value="0" selected="selected">Chưa thanh
                                            toán
                                        </option>
                                        <option value="1">Đã thanh toán</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="1" selected="selected">Đã thanh toán</option>
                                        <option value="0">Chưa thanh toán</option>
                                    </c:otherwise>
                                </c:choose>
                            </select>
                            </div>
                            <div class="form-group">
                                <label>Trạng thái đơn hàng</label> <select class="form-control"
                                                                           name="status.id">
                                <c:forEach items="${listStatus }" var="item">
                                    <c:choose>
                                        <c:when test="${item.id==objOrder.status.id }">
                                            <option selected="selected" value="${item.id }">${item.name }</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${item.id }">${item.name }</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                            </div>
                        </div>
                    </div>
                    <hr>

                    <div class="row">
                        <div class="col-sm-12">
                            <label>Sản phẩm đã đặt</label>
                            <table cellpadding="0" cellspacing="0" border="0"
                                   class="table table-striped table-bordered center text-center"
                                   id="example">
                                <thead>
                                <tr>
                                    <th>Tên sản phẩm</th>
                                    <th>Số lượng</th>
                                    <th>Giá gốc</th>
                                    <th>Giá khuyến mãi</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:if test="${not empty objOrder.listProduct }">
                                    <c:set var="total" value="${0 }"></c:set>
                                    <c:forEach items="${objOrder.listProduct }" var="itemProduct">
                                        <c:choose>
                                            <c:when test="${itemProduct.product.newprice!=0 }">
                                                <c:set var="total"
                                                       value="${total+itemProduct.product.newprice * itemProduct.quantity }"></c:set>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="total"
                                                       value="${total+itemProduct.product.price * itemProduct.quantity }"></c:set>
                                            </c:otherwise>
                                        </c:choose>
                                        <tr class="odd gradeX">
                                            <td>${itemProduct.product.name }</td>
                                            <td>${itemProduct.quantity }</td>
                                            <td>$ ${itemProduct.product.price } </td>
                                            <td>$ ${itemProduct.product.newprice } </td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                                </tbody>
                            </table>
                            <h4 style="color: green;text-align: right;">Tổng: $ ${total } </h4>
                            <c:if test="${objOrder.coupon_value!=0 }">
                                <h4 style="color: green;text-align: right;">Giảm còn:
                                    $ ${Math.round(total*(1-objOrder.coupon_value/100) *100)/100} </h4>
                            </c:if>
                            <input type="submit" value="Lưu" class="btn btn-success"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<!-- /.row col-size -->
<script>
    document.getElementById("order").classList.add('current');
</script>