<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<div class="row">
    <form action="${contextPath }/admincp/user/edit-profile/${objUser.username}" method="post">
        <div class="col-md-12 panel-info">
            <div class="content-box-header panel-heading">
                <div class="panel-title ">Chỉnh sửa thông tin cá nhân</div>
            </div>
            <div class="content-box-large box-with-header">
                <div>
                    <div class="row mb-10" style="color: red;font-style: italic;font-weight: bold;">
                        ${msg }
                    </div>

                    <div class="row">
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label for="name">Tên đăng nhập</label>
                                <input type="text" value="${objUser.username }"
                                       name="username" class="form-control"
                                       placeholder="Nhập tên đăng nhập" readonly="readonly">
                            </div>
                            <div class="form-group">
                                <label for="name">Họ tên</label>
                                <input type="text" name="fullname" value="${objUser.fullname }"
                                       class="form-control" placeholder="Nhập họ tên">
                            </div>
                            <div class="form-group">
                                <label for="name">Mật khẩu</label>
                                <input type="text" value=""
                                       name="password" class="form-control" placeholder="Nhập mật khẩu">
                            </div>
                            <div class="form-group">
                                <label for="name">Số điện thoại</label> <input type="text"
                                                                               name="phone" class="form-control"
                                                                               placeholder="Nhập số điện thoại"
                                                                               value="${objUser.phone }">
                            </div>
                            <div class="form-group">
                                <label for="name">Email</label> <input type="text"
                                                                       name="email" class="form-control"
                                                                       placeholder="Nhập Email"
                                                                       value="${objUser.email }">
                            </div>
                            <div class="form-group">
                                <label for="name">Địa chỉ</label> <input type="text"
                                                                         name="address" class="form-control"
                                                                         placeholder="Nhập địa chỉ"
                                                                         value="${objUser.address }">
                            </div>
                        </div>
                    </div>

                    <hr>

                    <div class="row">
                        <div class="col-sm-12">
                            <input type="submit" value="Lưu" class="btn btn-success"/> <input
                                type="reset" value="Nhập lại" class="btn btn-default"/>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </form>
</div>
<!-- /.row col-size -->