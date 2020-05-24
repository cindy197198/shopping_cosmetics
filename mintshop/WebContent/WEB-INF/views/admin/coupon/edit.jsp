<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<div class="row">
    <form action="${contextPath }/admincp/coupon/edit/page-${page }/${objCoupon.id }"
          method="post" enctype="multipart/form-data">
        <a href="${contextPath }/admincp/coupon"><h3><i class="fas fa-chevron-circle-left"></i> Trở về</h3></a>
        <div class="col-md-12 panel-info">
            <div class="content-box-header panel-heading">
                <div class="panel-title ">Sửa mã giảm giá</div>
            </div>
            <div class="content-box-large box-with-header">
                <div>
                    <div class="row mb-10">
                        <form:errors cssStyle="color:red;font-weight:bold;"
                                     path="objCoupon.*"></form:errors>
                    </div>

                    <div class="row">
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label for="name">Tên mã</label> <input type="text"
                                                                        name="name" class="form-control"
                                                                        placeholder="Nhập tên"
                                                                        value="${objCoupon.name }" readonly="readonly">
                            </div>
                            <div class="form-group">
                                <label for="name">Số lượng</label> <input type="number"
                                                                          name="quantity" class="form-control"
                                                                          placeholder="Nhập số lượng"
                                                                          value="${objCoupon.quantity }">
                            </div>
                            <div class="form-group">
                                <label>Giá trị</label>
                                <select class="form-control"
                                        name="value">
                                    <c:choose>
                                        <c:when test="${objCoupon.value==10 }">
                                            <option value="10" selected="selected">10 %</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="10">10 %</option>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${objCoupon.value==20 }">
                                            <option value="20" selected="selected">20 %</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="20">20 %</option>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${objCoupon.value==50 }">
                                            <option value="50" selected="selected">50 %</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="50">50 %</option>
                                        </c:otherwise>
                                    </c:choose>
                                </select>
                            </div>
                        </div>
                    </div>

                    <hr>

                    <div class="row">
                        <div class="col-sm-12">
                            <input type="submit" value="Sửa" class="btn btn-success"/> <input
                                type="reset" value="Nhập lại" class="btn btn-default"/>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </form>
</div>
<!-- /.row col-size -->
<script>
    document.getElementById("coupon").classList.add('current');
</script>