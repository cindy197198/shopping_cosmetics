<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<div class="row">
    <form action="${contextPath }/admincp/cat/edit/page-${page }/${objCat.id }"
          method="post" enctype="multipart/form-data">
        <a href="${contextPath }/admincp/cat"><h3><i class="fas fa-chevron-circle-left"></i> Trở về</h3></a>
        <div class="col-md-12 panel-info">
            <div class="content-box-header panel-heading">
                <div class="panel-title ">Sửa danh mục</div>
            </div>
            <div class="content-box-large box-with-header">
                <div>
                    <div class="row mb-10">
                        <form:errors cssStyle="color:red;font-weight:bold;"
                                     path="objCat.*"></form:errors>
                    </div>

                    <div class="row">
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label for="name">Tên danh mục</label> <input type="text"
                                                                              name="name" class="form-control"
                                                                              placeholder="Nhập tên danh mục"
                                                                              value="${objCat.name }">
                            </div>
                            <div class="form-group">
                                <label>Danh mục cha</label>
                                <select class="form-control"
                                        name="parent_id">
                                    <option value="0">Không</option>
                                    <c:forEach items="${listCat }" var="itemCat">
                                        <c:choose>
                                            <c:when test="${itemCat.id==objCat.parent_id }">
                                                <option selected="selected"
                                                        value="${itemCat.id }">${itemCat.name }</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${itemCat.id }">${itemCat.name }</option>
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
    document.getElementById("category").classList.add('current');
</script>