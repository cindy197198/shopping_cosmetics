<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/templates/taglib.jsp" %>
<security:authentication property="principal" var="principal"/>
<div class="header">
    <div class="container">
        <div class="row">
            <div class="col-md-5">
                <!-- Logo -->
                <div class="logo">
                    <h1><a href="${pageContext.request.contextPath }/admincp/index">VNE-Admin</a></h1>
                </div>
            </div>
            <div class="col-md-5">
                <div class="row">
                    <div class="col-lg-12"></div>
                </div>
            </div>
            <div class="col-md-2">
                <div class="navbar navbar-inverse" role="banner">
                    <nav class="collapse navbar-collapse bs-navbar-collapse navbar-right" role="navigation">
                        <ul class="nav navbar-nav">
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><security:authentication
                                        property="principal.username"/> <b class="caret"></b></a>
                                <ul class="dropdown-menu animated fadeInUp">
                                    <li>
                                        <a href="${pageContext.request.contextPath }/admincp/user/edit-profile/${principal.username}">Chỉnh
                                            sửa thông tin cá nhân</a></li>
                                    <li><a href="${pageContext.request.contextPath }/admincp/admin-logout">Logout</a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- /.header -->