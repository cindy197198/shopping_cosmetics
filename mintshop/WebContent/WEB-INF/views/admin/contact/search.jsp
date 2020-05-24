<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<div class="content-box-large">
    <div class="row">
        <div class="panel-heading">
            <div class="panel-title "> ${totalRow } kết quả tìm kiếm cho từ khoá "${keyword }"</div>
        </div>
    </div>
    <hr>
    <div class="row">
        <div class="col-md-8">

        </div>
        <form action="${contextPath}/admincp/contact/tim-kiem" method="post">
            <div class="col-md-4">
                <div class="input-group form">
                    <input type="text" class="form-control" placeholder="Search..." value="${keyword }"
                           name="keyword"/> <span class="input-group-btn">
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
            </div>
            <table cellpadding="0" cellspacing="0" border="0"
                   class="table table-striped table-bordered center text-center"
                   id="example">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Tên người gửi</th>
                    <th>Email</th>
                    <th>Chủ đề</th>
                    <th>Nội dung</th>
                    <th>Ngày gửi</th>
                    <th>Chức năng</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty listContact }">
                    <c:forEach items="${listContact }" var="objContact">
                        <c:set var="urlDel"
                               value="${contextPath}/admincp/contact/del/page-${page }/${objContact.id }"></c:set>
                        <c:set var="urlView"
                               value="${contextPath}/admincp/contact/view/page-${page }/${objContact.id }"></c:set>
                        <tr class="odd gradeX">
                            <td>${objContact.id }</td>
                            <td>${objContact.fullname }</td>
                            <td>${objContact.email }</td>
                            <td>${objContact.subject }</td>
                            <td>${objContact.content }</td>
                            <td>${objContact.date_create }</td>
                            <td class="center text-center">
                                <a href="${urlView}" title="Xem" class="btn btn-primary"><i class="fas fa-eye"></i> Xem</a>
                                <a href="${urlDel }" title="Xoá" class="btn btn-danger"
                                   onclick="return confirm('Bạn có chắc muốn xoá?')"><span
                                        class="glyphicon glyphicon-trash"></span> Xoá</a>
                            </td>
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
                    <li class="${previous}"><a href="${contextPath}/admincp/contact/tim-kiem/${keyword }/page-${page-1}"
                                               aria-label="Previous"><span aria-hidden="true">«</span></a></li>
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
                                href="${contextPath}/admincp/contact/tim-kiem/${keyword }/page-${i}">${i}</a></li>
                    </c:forEach>
                    <c:choose>
                        <c:when test="${page==sumPage}">
                            <c:set var="next" value="disabled"></c:set>
                        </c:when>
                        <c:otherwise>
                            <c:set var="next" value=""></c:set>
                        </c:otherwise>
                    </c:choose>
                    <li class="${next }"><a href="${contextPath}/admincp/contact/tim-kiem/${keyword }/page-${page+1}"
                                            aria-label="Next"><span aria-hidden="true">»</span></a></li>
                </ul>
            </nav>
            <!-- /.pagination -->

        </div>
    </div>
    <!-- /.row -->
</div>
<!-- /.content-box-large -->
<script>
    document.getElementById("contact").classList.add('current');
</script>