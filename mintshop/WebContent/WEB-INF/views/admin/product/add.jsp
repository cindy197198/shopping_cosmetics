<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/templates/taglib.jsp" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<div class="row">
    <div class="col-md-12 panel-info">
        <a href="${contextPath }/admincp/product"><h3><i class="fas fa-chevron-circle-left"></i> Trở về</h3></a>
        <div class="content-box-header panel-heading">
            <div class="panel-title ">Thêm sản phẩm</div>
        </div>
        <div>
            <p style="color: red; font-style: italic;">${msg}</p>
        </div>
        <div class="content-box-large box-with-header">
            <div>
                <div class="row mb-10"></div>
                <form action="${contextPath }/admincp/product/add" method="POST"
                      enctype="multipart/form-data">
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label for="name">Tên sản phẩm</label> <input type="text"
                                                                              class="form-control"
                                                                              placeholder="Nhập tên sản phẩm"
                                                                              name="name">
                                <form:errors cssStyle="color:red;font-weight:bold;"
                                             path="objproduct.name"></form:errors>
                            </div>
                            <div class="form-group">
                                <label>Danh mục sản phẩm</label> <select class="form-control"
                                                                         name="cat.id">
                                <c:forEach items="${listCat }" var="objCat">
                                    <option value="${objCat.id }">${objCat.name }</option>
                                </c:forEach>
                            </select>
                                <form:errors cssStyle="color:red;font-weight:bold;"
                                             path="objproduct.cat.id"></form:errors>
                            </div>
                            <div class="form-group">
                                <label>Thêm hình ảnh</label> <input type="file"
                                                                    class="btn btn-default" id="exampleInputFile1"
                                                                    name="pictureReview" multiple="multiple">
                                <p class="help-block">
                                    <em>Định dạng: jpg, png, jpeg,...</em>
                                </p>
                                <c:if test="${param['msg'] eq 'fileError'}">
                                    <p style="color: red;">Vui lòng chọn hình ảnh đúng định
                                        dạng</p>
                                </c:if>
                            </div>

                            <div class="col-sm-12">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="name">Giá ($)</label> <input type="text"
                                                                                 class="form-control"
                                                                                 placeholder="Nhập giá sản phẩm"
                                                                                 name="price">
                                        <form:errors cssStyle="color:red;font-weight:bold;"
                                                     path="objproduct.price"></form:errors>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label for="name">Giá khuyến mãi ($)</label> <input
                                            type="text" class="form-control"
                                            placeholder="Nhập giá khuyến mãi" value="0" name="newprice">

                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="name">Nhãn hiệu (nếu có)</label> <input type="text"
                                                                                    class="form-control"
                                                                                    placeholder="Nhập nhãn hiệu"
                                                                                    name="brand">
                            </div>
                            <div class="form-group">
                                <label for="name">Số lượng </label> <input type="text"
                                                                           class="form-control"
                                                                           placeholder="Nhập số lượng" name="amount">
                                <form:errors cssStyle="color:red;font-weight:bold;"
                                             path="objproduct.amount"></form:errors>
                            </div>
                            <div class="form-group">
                                <label for="name">Mô tả</label>
                                <textarea class="form-control" rows="7" name="description"
                                          placeholder="Nhập mô tả"></textarea>
                                <form:errors cssStyle="color:red;font-weight:bold;"
                                             path="objproduct.description"></form:errors>
                            </div>

                        </div>
                        <div class="col-sm-12">
                            <div class="form-group">
                                <label>Chi tiết sản phẩm</label>
                                <textarea class="form-control" rows="7" name="detail"
                                          id="detail"></textarea>
                                <form:errors cssStyle="color:red;font-weight:bold;"
                                             path="objproduct.detail"></form:errors>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-sm-12">
                            <input type="submit" value="Thêm" class="btn btn-success"/> <input
                                type="reset" value="Nhập lại" class="btn btn-default"/>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- /.row col-size -->
<script>
    document.getElementById("product").classList.add('current');
    var editor = CKEDITOR.replace('detail');
    CKFinder.setupCKEditor(editor, '${pageContext.request.contextPath}/resources/admincp/ckfinder/');
</script>