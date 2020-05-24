<%@page import="vn.edu.vinaenter.defines.FileDefine" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<div class="content-box-large">
    <div class="row">
        <a href="${contextPath }/admincp/product"><h3><i class="fas fa-chevron-circle-left"></i> Trở về</h3></a>
        <div class="panel-heading">
            <div class="panel-title "> ${totalRow } kết quả tìm kiếm cho từ khoá "${keyword }"</div>
        </div>
    </div>
    <hr>
    <div class="row">
        <div class="col-md-8">
        </div>
        <form action="${contextPath}/admincp/product/tim-kiem" method="post">
            <div class="col-md-6">
                <div class="row">
                    <div class="col-md-6">
                        <select class="form-control" name="property-search">
                            <option value="1">Tên sản phẩm</option>
                            <c:choose>
                                <c:when test="${property_search==2 }">
                                    <option value="2" selected="selected">Tên người đăng</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="2">Tên người đăng</option>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${property_search==3 }">
                                    <option value="3" selected="selected">Tên nhãn hiệu</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="3">Tên nhãn hiệu</option>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${property_search==4 }">
                                    <option value="4" selected="selected">Tên danh mục</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="4">Tên danh mục</option>
                                </c:otherwise>
                            </c:choose>
                        </select>
                    </div>
                    <div class="col-md-6">
                        <div class="input-group form">
                            <input type="text" class="form-control" placeholder="Search..." value="${keyword }"
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
            <table cellpadding="0" cellspacing="0" border="0"
                   class="table table-striped table-bordered" id="example">
                <thead>
                <tr>
                    <th>ID</th>
                    <th width="15%">Tên</th>
                    <th>Ngày đăng</th>
                    <th>Danh mục</th>
                    <th>Người đăng</th>
                    <th>Hình ảnh</th>
                    <th>Giá gốc</th>
                    <th>Giá đã giảm</th>
                    <th>Nhãn hiệu</th>
                    <th width="18%">Chức năng</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${listProduct }" var="objproduct">
                    <c:set var="urlEdit"
                           value="${contextPath}/admincp/product/edit/page-${page}/${objproduct.id }"></c:set>
                    <c:set var="urlDel"
                           value="${contextPath}/admincp/product/del/${objproduct.id }"></c:set>
                    <tr class="odd gradeX">
                        <td>${objproduct.id }</td>
                        <td width="15%">${objproduct.name }</td>
                        <td>${objproduct.date_create }</td>
                        <td>${objproduct.cat.name }</td>
                        <td>${objproduct.user.username }</td>
                        <td class="center text-center"><c:choose>
                            <c:when test="${not empty objproduct.picture }">
                                <c:set var="fileNames" value="${objproduct.picture }"></c:set>
                                <img width="150px"
                                     src="${contextPath}/fileUpload/${fileNames.substring(0, fileNames.indexOf(' '))}"/>
                            </c:when>
                            <c:otherwise>
                                <p>Không có hình ảnh</p>
                            </c:otherwise>
                        </c:choose></td>
                        <td>${objproduct.price }</td>
                        <td>${objproduct.newprice }</td>
                        <td class="center">${objproduct.brand }</td>

                        <td class="center text-center" width="18%"><a href="${urlEdit }"
                                                                      title="" class="btn btn-primary"><span
                                class="glyphicon glyphicon-pencil "></span> Sửa</a> <a
                                href="${urlDel }" title="" class="btn btn-danger"
                                onclick="return confirm('Bạn có chắc muốn xoá?')"><span
                                class="glyphicon glyphicon-trash"></span> Xoá</a></td>
                    </tr>
                </c:forEach>
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
                            href="${contextPath}/admincp/product/tim-kiem/${property_search }/${keyword }/page-${page-1}"
                            aria-label="Previous"><span
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
                                href="${contextPath}/admincp/product/tim-kiem/${property_search }/${keyword }/page-${i}">${i}</a>
                        </li>
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
                            href="${contextPath}/admincp/product/tim-kiem/${property_search }/${keyword }/page-${page+1}"
                            aria-label="Next"><span
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
    document.getElementById("product").classList.add('current');
</script>