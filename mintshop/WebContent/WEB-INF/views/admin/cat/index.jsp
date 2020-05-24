<%@page import="vn.edu.vinaenter.utils.AuthUtil" %>
<%@page import="vn.edu.vinaenter.controller.admins.AdminCategoryController" %>
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
            <div class="panel-title ">Quản lý danh mục</div>
        </div>
    </div>
    <hr>
    <div class="row">
        <div class="col-md-8">
            <a href="${contextPath}/admincp/cat/add" class="btn btn-success"><span
                    class="glyphicon glyphicon-plus" aria-hidden="true"></span>&nbsp;Thêm</a>

        </div>
        <form action="${contextPath}/admincp/cat/tim-kiem" method="post">
            <div class="col-md-4">
                <div class="input-group form">

                    <input type="text" class="form-control" placeholder="Search..." name="keyword"/>
                    <span class="input-group-btn">
					<button class="btn btn-primary" type="submit">Search</button>
				</span>

                </div>
            </div>
        </form>
    </div>

    <div class="row">
        <div class="panel-body">
            <div>
                <p style="color: red; font-style: italic;">${msg}</p>
                <p style="color: red; font-style: italic;">${msg2}</p>
            </div>
            <table cellpadding="0" cellspacing="0" border="0"
                   class="table table-striped table-bordered center "
                   id="example">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Tên danh mục</th>
                    <th>Chức năng</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty listCat }">
                    <%
                        CatDAO categoryDAO = (CatDAO) request.getAttribute("catDAO");
                        ArrayList<Category> categories = (ArrayList<Category>) request.getAttribute("listCat");
                        for (Category objCat : categories) {
                            if (objCat.getParent_id() == 0) {

                    %>
                    <tr class="odd gradeX">
                        <td style="vertical-align: inherit;"><%=objCat.getId() %>
                        </td>
                        <td style="text-align: left;"><%=objCat.getName() %>
                            <%categoryDAO.showCategory(categories, objCat.getId(), request, out); %>
                        </td>
                        <td class="center text-center" style="vertical-align: inherit;"><a
                                href="${contextPath}/admincp/cat/edit/page-${page }/<%=objCat.getId() %>"
                                title="Sửa" class="btn btn-primary"><span
                                class="glyphicon glyphicon-pencil "></span> Sửa</a> <a
                                href="${contextPath}/admincp/cat/del/<%=objCat.getId() %>" title="Xoá"
                                class="btn btn-danger"
                                onclick="return confirm('Bạn có chắc muốn xoá?')"><span
                                class="glyphicon glyphicon-trash"></span> Xoá</a></td>
                    </tr>
                    <%
                            }
                        }
                    %>
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
                    <li class="${previous}"><a href="${contextPath}/admincp/cat/${page-1}" aria-label="Previous"><span
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
                                href="${contextPath}/admincp/cat/${i}">${i}</a></li>
                    </c:forEach>
                    <c:choose>
                        <c:when test="${page==sumPage}">
                            <c:set var="next" value="disabled"></c:set>
                        </c:when>
                        <c:otherwise>
                            <c:set var="next" value=""></c:set>
                        </c:otherwise>
                    </c:choose>
                    <li class="${next }"><a href="${contextPath}/admincp/cat/${page+1}" aria-label="Next"><span
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
    document.getElementById("category").classList.add('current');
</script> 
