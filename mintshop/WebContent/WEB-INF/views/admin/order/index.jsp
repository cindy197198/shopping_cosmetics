<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<div class="content-box-large">
    <div class="row">
        <div class="panel-heading">
            <div class="panel-title ">Quản lý đơn hàng</div>
        </div>
    </div>
    <hr>
    <div class="row">
        <div class="col-md-6"></div>
        <form action="${contextPath}/admincp/order/tim-kiem" method="post">
            <div class="col-md-6">
                <div class="row">
                    <div class="col-md-6">
                        <select class="form-control" name="property-search">
                            <option value="1">Người đặt hàng</option>
                            <option value="2">Địa chỉ</option>
                            s </select>
                    </div>
                    <div class="col-md-6">
                        <div class="input-group form">
                            <input type="text" class="form-control" placeholder="Search..."
                                   name="keyword"/> <span class="input-group-btn">
							<button class="btn btn-primary" type="submit">Tìm</button>
						</span>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>

    <div class="row">
        <div class="panel-body">
            <div>
                <p style="color: red; font-style: italic;">${msg}</p>
            </div>
            <table cellpadding="0" cellspacing="0" border="0"
                   class="table table-striped table-bordered center text-center"
                   id="example">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Người đặt hàng</th>
                    <th>Phone</th>
                    <th>Địa chỉ</th>
                    <th>Hình thức TT</th>
                    <th>Tổng tiền</th>
                    <th>Trạng thái TT</th>
                    <th>Trạng thái</th>
                    <th width="18%">Chức năng</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty listOrder }">
                    <c:forEach items="${listOrder }" var="objorder">
                        <c:set var="urlDel"
                               value="${contextPath}/admincp/order/del/page-${page }/${objorder.id }"></c:set>
                        <c:set var="urlView"
                               value="${contextPath}/admincp/order/view/page-${page }/${objorder.id }"></c:set>
                        <tr class="odd gradeX">
                            <td>${objorder.id }</td>
                            <td>${objorder.customer.fullname }</td>
                            <td>${objorder.customer.phone }</td>
                            <td>${objorder.customer.address }</td>
                            <td>${objorder.pay_method.name }</td>
                            <td>$${objorder.total}</td>
                            <c:choose>
                                <c:when test="${ objorder.payment_status==1}">
                                    <td>Đã thanh toán</td>
                                </c:when>
                                <c:otherwise>
                                    <td>Chưa thanh toán</td>
                                </c:otherwise>
                            </c:choose>
                            <td>${objorder.status.name }</td>
                            <td class="center text-center" width="18%"><a
                                    href="${urlView}"
                                    title="Xem" class="btn btn-primary"><i class="fas fa-eye"></i> Xem</a> <a
                                    href="${urlDel }" title="Xoá" class="btn btn-danger"
                                    onclick="return confirm('Bạn có chắc muốn xoá?')"><span
                                    class="glyphicon glyphicon-trash"></span> Xoá</a></td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>

            <!-- Pagination -->
            <nav class="text-center" aria-label="...">
                <ul class="pagination">
                    <c:choose>
                        <c:when test="${page==1}">
                            <c:set var="previous" value="disabled"></c:set>
                        </c:when>
                        <c:otherwise>
                            <c:set var="previous" value=""></c:set>
                        </c:otherwise>
                    </c:choose>
                    <li class="${previous}"><a
                            href="${contextPath}/admincp/order/${page-1}" aria-label="Previous"><span
                            aria-hidden="true">«</span></a></li>
                    <c:forEach begin="1" end="${sumPage }" var="i">
                        <c:choose>
                            <c:when test="${i== page}">
                                <c:set var="active" value="active"></c:set>
                            </c:when>
                            <c:otherwise>
                                <c:set var="active" value=""></c:set>
                            </c:otherwise>
                        </c:choose>
                        <li class="${active }"><a
                                href="${contextPath}/admincp/order/${i}">${i}</a></li>
                    </c:forEach>
                    <c:choose>
                        <c:when test="${page==sumPage}">
                            <c:set var="next" value="disabled"></c:set>
                        </c:when>
                        <c:otherwise>
                            <c:set var="next" value=""></c:set>
                        </c:otherwise>
                    </c:choose>
                    <li class="${next }"><a
                            href="${contextPath}/admincp/order/${page+1}" aria-label="Next"><span
                            aria-hidden="true">»</span></a></li>
                </ul>
            </nav>
            <!-- /.pagination -->

        </div>
    </div>
    <!-- /.row -->
</div>
<!-- /.content-box-large -->
<script>
    document.getElementById("order").classList.add('current');
</script>