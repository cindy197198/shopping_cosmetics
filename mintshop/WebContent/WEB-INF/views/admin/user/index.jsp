<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<div class="content-box-large">
    <div class="row">
        <div class="panel-heading">
            <div class="panel-title ">Quản lý người dùng</div>
        </div>
    </div>
    <hr>
    <div class="row">
        <div class="col-md-8">
            <a href="${contextPath}/admincp/user/add" class="btn btn-success"><span
                    class="glyphicon glyphicon-plus" aria-hidden="true"></span>&nbsp;Thêm</a>

        </div>
        <form action="${contextPath}/admincp/user/tim-kiem" method="post">
            <div class="col-md-4">
                <div class="input-group form">
                    <input type="text" class="form-control" placeholder="Search..."
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
                    <th>Tên đăng nhập</th>
                    <th>Họ tên</th>
                    <th>Vai trò</th>
                    <th>Số điện thoại</th>
                    <th>Email</th>
                    <th>Địa chỉ</th>
                    <th>Trạng thái</th>
                    <th>Chức năng</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty listUsers }">
                    <c:forEach items="${listUsers }" var="objUser">
                        <c:set var="urlEdit"
                               value="${contextPath}/admincp/user/edit/page-${page }/${objUser.id }"></c:set>
                        <c:set var="urlDel"
                               value="${contextPath}/admincp/user/del/page-${page }/${objUser.id }"></c:set>
                        <tr class="odd gradeX">
                            <td>${objUser.id }</td>
                            <td>${objUser.username }</td>
                            <td>${objUser.fullname }</td>
                            <td>${objUser.role.name }</td>
                            <td>${objUser.phone }</td>
                            <td>${objUser.email }</td>
                            <td>${objUser.address }</td>
                            <td><c:choose>
                                <c:when test="${objUser.active == 1 }">
                                    <div id="state-${objUser.id }">
                                        <a href="javascript:void(0)"
                                           onclick="return nactive(${objUser.id })" title=""
                                           style="color: green;"><i class="fas fa-check-circle"></i></a>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div id="state-${objUser.id }">
                                        <a href="javascript:void(0)" title=""
                                           onclick="return active(${objUser.id })"><i
                                                class="fas fa-times-circle" style="color: red;"></i></a>
                                    </div>
                                </c:otherwise>
                            </c:choose></td>
                            <td class="center text-center"><a href="${urlEdit }"
                                                              title="Sửa" class="btn btn-primary"><span
                                    class="glyphicon glyphicon-pencil "></span> Sửa</a></td>
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
                            href="${contextPath}/admincp/user/${page-1}" aria-label="Previous"><span
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
                                href="${contextPath}/admincp/user/${i}">${i}</a></li>
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
                            href="${contextPath}/admincp/user/${page+1}" aria-label="Next"><span
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
    document.getElementById("user").classList.add('current');

    function nactive(id) {
        var tmp = "#state-" + id;
        $.ajax({
            url: "${contextPath}/admincp/user/active/" + id,
            cache: false,
            data: {},
            complete: function (data) {
                $(tmp).html('<a href="javascript:void(0)" title="" onclick="return active(' + id + ')"><i style="color:red;" class="fas fa-times-circle"></i></a>');
            }
        });
        return false;
    }

    function active(id) {
        var tmp = "#state-" + id;
        $.ajax({
            url: "${contextPath}/admincp/user/active/" + id,
            cache: false,
            data: {},
            complete: function (data) {
                $(tmp).html('<a href="javascript:void(0)" onclick="return nactive(' + id + ')" title="" style="color: green;" ><i class="fas fa-check-circle"></i></a>');
            }
        });
        return false;
    }
</script>