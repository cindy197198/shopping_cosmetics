<%@page import="java.util.Calendar" %>
<%@page import="vn.edu.vinaenter.utils.AuthUtil" %>
<%@page
        import="vn.edu.vinaenter.controller.admins.AdminCategoryController" %>
<%@page import="vn.edu.vinaenter.models.Category" %>
<%@page import="java.util.ArrayList" %>
<%@page import="vn.edu.vinaenter.daos.CatDAO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<div class="content-box-large">
    <div class="row">
        <div class="panel-heading">
            <div class="panel-title ">Doanh thu</div>
        </div>
    </div>
    <hr>
    <div class="row">
        <div class="panel-body">
            <p style="color: red; font-style: italic;">${msg}</p>
            <form action="${contextPath}/admincp/proceeds" method="post">
                <div class="input-checkbox">
                    <input type="radio" name="status" value="1" checked>
                    <label for="payments-1">Doanh thu trong ngày</label>
                    <input type="date" name="date"/>
                </div>
                <div class="input-checkbox">
                    <input type="radio" name="status" value="2">
                    <label for="payments-2">Doanh thu trong tháng</label>
                    <input type="month" name="month" value="${dateFormat1 }"/>
                </div>
                <div class="input-checkbox">
                    <input type="radio" name="status" value="3">
                    <label for="payments-1">Doanh thu trong năm</label>
                    <input type="number" name="year" min="2000" value="${dateFormat2 }"/>
                </div>

                <button class="btn btn-primary" type="submit">Xem</button>

            </form>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12 panel-info">
            <div class="content-box-header panel-heading">
                <div class="panel-title ">Doanh thu ${date }</div>
            </div>
            <div class="content-box-large box-with-header">
                <div>
                    <div class="row mb-10"></div>
                    <div class="row">
                        <div class="col-sm-6">
                            <h2>Tổng: $ ${sum }</h2>
                        </div>
                    </div>
                    <hr>

                    <div class="row">
                        <div class="col-sm-12">
                            <h3>Sản phẩm đã bán</h3>
                            <table cellpadding="0" cellspacing="0" border="0"
                                   class="table table-striped table-bordered center text-center"
                                   id="example">
                                <thead>
                                <tr>
                                    <th>Mã sản phẩm</th>
                                    <th>Tên sản phẩm</th>
                                    <th>Số lượng</th>
                                    <th>Giá</th>
                                    <th>Tổng</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:if test="${not empty listProduct }">
                                    <c:set var="total" value="${0 }"></c:set>
                                    <c:forEach items="${listProduct }" var="itemProduct">
                                        <c:choose>
                                            <c:when test="${itemProduct.product.newprice!=0 }">
                                                <c:set var="price" value="${itemProduct.product.newprice }"></c:set>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="price" value="${itemProduct.product.price }"></c:set>
                                            </c:otherwise>
                                        </c:choose>
                                        <tr class="odd gradeX">
                                            <td> ${itemProduct.product.id} </td>
                                            <td>${itemProduct.product.name }</td>
                                            <td>${itemProduct.quantity }</td>
                                            <td>$ ${price } </td>
                                            <td>$ ${price*itemProduct.quantity } </td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- /.content-box-large -->
<script>
    document.getElementById("proceeds").classList.add('current');
    var today = new Date();
    if (today.getMonth() >= 10) { // Nếu tháng >= tháng 10

        if (today.getDate() >= 10) { // Nếu ngày >= ngày 10

            var date = today.getFullYear() + '-' + (today.getMonth() + 1) + '-' + today.getDate();
        } else { // Nếu ngày < ngày 10 thì sẽ là 09, 08, 07,...

            var date = today.getFullYear() + '-' + (today.getMonth() + 1) + '-0' + today.getDate();
        }
    } else { // Nếu tháng < tháng 10

        if (today.getDate() >= 10) { // Nếu ngày >= ngày 10

            var date = today.getFullYear() + '-0' + (today.getMonth() + 1) + '-' + today.getDate();
        } else { // Nếu ngày < ngày 10 thì sẽ là 09, 08, 07,...

            var date = today.getFullYear() + '-0' + (today.getMonth() + 1) + '-0' + today.getDate();
        }
    }
    var dateControl = document.querySelector('input[type="date"]');
    dateControl.value = date;
</script>
