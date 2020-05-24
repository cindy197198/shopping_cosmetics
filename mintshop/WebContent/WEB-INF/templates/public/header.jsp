<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@page import="vn.edu.vinaenter.utils.StringUtil" %>
<%@include file="/WEB-INF/templates/taglib.jsp" %>
<security:authentication property="principal" var="principal"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!-- HEADER -->
<header>
    <!-- top Header -->
    <div id="top-header">
        <div class="container">
            <div class="pull-left">
                <span>Welcome to Mint-shop!</span>
            </div>
            <div class="pull-right">
                <ul class="header-top-links">
                    <li><a href="#">Store</a></li>
                    <li><a href="#">Newsletter</a></li>
                    <li><a href="#">FAQ</a></li>
                    <li class="dropdown default-dropdown"><a
                            class="dropdown-toggle" data-toggle="dropdown"
                            aria-expanded="true">ENG <i class="fa fa-caret-down"></i></a>
                        <ul class="custom-menu">
                            <li><a href="#">English (ENG)</a></li>
                            <li><a href="#">Russian (Ru)</a></li>
                            <li><a href="#">French (FR)</a></li>
                            <li><a href="#">Spanish (Es)</a></li>
                        </ul>
                    </li>
                    <li class="dropdown default-dropdown"><a
                            class="dropdown-toggle" data-toggle="dropdown"
                            aria-expanded="true">USD <i class="fa fa-caret-down"></i></a>
                        <ul class="custom-menu">
                            <li><a href="#">USD ($)</a></li>
                            <li><a href="#">EUR (€)</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <!-- /top Header -->

    <!-- header -->
    <div id="header">
        <div class="container">
            <div class="pull-left">
                <!-- Logo -->
                <div class="header-logo">
                    <a class="logo" href="${contextPath}"> <img
                            src="${contextPath}/resources/public/img/logo-web.png" alt="">
                    </a>
                </div>
                <!-- /Logo -->

                <!-- Search -->
                <div class="header-search">
                    <form action="${contextPath }/tim-kiem" method="post">
                        <input class="input search-input" type="text" value="${key_search }"
                               placeholder="Nhập từ khoá" name="key_search"> <select
                            class="input search-categories" name="property_search">
                        <option value="0">Tất cả</option>
                        <c:forEach items="${listCat }" var="i">
                            <c:choose>
                                <c:when test="${i.id==property_search }">
                                    <option value="${i.id }" selected="selected">${i.name }</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${i.id }">${i.name }</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                        <button class="search-btn">
                            <i class="fa fa-search"></i>
                        </button>
                    </form>
                </div>
                <!-- /Search -->
            </div>
            <div class="pull-right">
                <ul class="header-btns">
                    <!-- Account -->
                    <li class="header-account dropdown default-dropdown">
                        <security:authorize
                                access="isAuthenticated()">
                            <div class="dropdown-toggle" role="button"
                                 data-toggle="dropdown" aria-expanded="true">
                                <div class="header-btns-icon">
                                    <i class="fa fa-user-o"></i>
                                </div>
                                <strong class="text-uppercase">Chào,
                                    <strong><security:authentication
                                            property="principal.username"/></strong>
                                    <i class="fa fa-caret-down"></i>
                                </strong>
                            </div>

                            <ul class="custom-menu">
                                <li><a href="${contextPath }/my-account"><i
                                        class="fa fa-user-o"></i> Tài khoản của tôi</a></li>
                                <li><a href="${contextPath }/customer-logout"><i
                                        class="fa fa-unlock-alt"></i>Đăng xuất</a></li>
                            </ul>
                        </security:authorize>
                        <security:authorize
                                access="isAnonymous()">
                            <div class="dropdown-toggle" role="button"
                                 data-toggle="dropdown" aria-expanded="true">
                                <div class="header-btns-icon">
                                    <i class="fa fa-user-o"></i>
                                </div>
                            </div>
                            <ul class="custom-menu">
                                <li><a href="${contextPath }/customer-login"><i
                                        class="fa fa-unlock-alt"></i> Đăng nhập</a></li>
                                <li><a href="${contextPath }/register"><i
                                        class="fa fa-user-plus"></i> Đăng kí tài khoản</a></li>
                            </ul>
                        </security:authorize>
                    </li>
                    <!-- /Account -->

                    <!-- Cart -->
                    <li class="header-cart dropdown default-dropdown" id="aaaaa">
                        <div id="cart" class="header-cart dropdown default-dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown"
                               aria-expanded="true">
                                <div class="header-btns-icon">
                                    <i class="fa fa-shopping-cart"></i><span class="qty" id="qty">
										<c:choose>
                                            <c:when test="${not empty cart}">${cart.size() }</c:when>
                                            <c:otherwise>0</c:otherwise>
                                        </c:choose>
									</span>
                                </div>
                                <strong class="text-uppercase">Giỏ hàng</strong> <br>
                            </a>
                            <div class="custom-menu">
                                <div id="shopping-cart">
                                    <div class="shopping-cart-list">
                                        <c:choose>
                                            <c:when test="${not empty cart }">
                                                <c:forEach items="${cart }" var="item">
                                                    <div class="product product-widget">
                                                        <div class="product-thumb">
                                                            <img height="60px"
                                                                 src="${contextPath}/fileUpload/${item.product.picture.substring(0, item.product.picture.indexOf(' '))}"
                                                                 alt="">
                                                        </div>
                                                        <div class="product-body">
                                                            <h3 class="product-price">
                                                                <c:choose>
                                                                    <c:when test="${item.product.newprice!=0 }">$ ${item.product.newprice }</c:when>
                                                                    <c:otherwise>$ ${item.product.price }</c:otherwise>
                                                                </c:choose>
                                                                <span class="qty">x ${item.quantity }</span>
                                                            </h3>
                                                            <h2 class="product-name">
                                                                <a href="${contextPath }/chi-tiet/${StringUtil.makeSlug(item.product.name)}-${item.product.id}.html">${item.product.name }</a>
                                                            </h2>
                                                        </div>
                                                    </div>
                                                </c:forEach>
                                                <div class="shopping-cart-btns">
                                                    <a href="${contextPath }/gio-hang">
                                                        <button
                                                                class="main-btn" style="font-size: 12px;">Xem
                                                            giỏ hàng
                                                        </button>
                                                    </a> <a href="${contextPath }/thanh-toan">
                                                    <button
                                                            class="primary-btn" style="font-size: 12px;">
                                                        Thanh toán <i class="fa fa-arrow-circle-right"></i>
                                                    </button>
                                                </a>
                                                </div>
                                            </c:when>
                                            <c:otherwise>Giỏ hàng trống!</c:otherwise>
                                        </c:choose>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </li>
                    <!-- /Cart -->

                    <!-- Mobile nav toggle-->
                    <li class="nav-toggle">
                        <button class="nav-toggle-btn main-btn icon-btn">
                            <i class="fa fa-bars"></i>
                        </button>
                    </li>
                    <!-- / Mobile nav toggle -->
                </ul>
            </div>
        </div>
        <!-- header -->
    </div>
    <!-- container -->
</header>
<!-- /HEADER -->

<!-- NAVIGATION -->
<div id="navigation">
    <!-- container -->
    <div class="container">
        <div id="responsive-nav">
            <!-- category nav -->
            <div class="category-nav show-on-click" id="category-nav">
                <span class="category-header">Danh mục <i class="fa fa-list"></i></span>
                <ul class="category-list">
                    <c:forEach items="${listCat }" var="i">
                        <c:choose>
                            <c:when test="${catDAO.getItemsbyParent_id(i.id).size()==0 }">
                                <li>
                                    <a href="${contextPath}/danh-muc/${StringUtil.makeSlug(i.name)}-${i.id}">${i.name }</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="dropdown side-dropdown"><a
                                        class="dropdown-toggle" data-toggle="dropdown"
                                        aria-expanded="true">${i.name } <i
                                        class="fa fa-angle-right"></i>
                                </a>
                                    <div class="custom-menu">
                                        <div class="row">
                                            <c:set var="list"
                                                   value="${catDAO.getItemsbyParent_id(i.id) }"></c:set>
                                            <c:forEach var="item" items="${list}">
                                                <div class="col-md-4">

                                                    <ul class="list-links">
                                                        <li><a
                                                                href="${contextPath }/danh-muc/${StringUtil.makeSlug(item.name)}-${item.id}.html">
                                                            <h3 class="list-links-title">${item.name }</h3>
                                                        </a>
                                                            <c:if
                                                                    test="${catDAO.getItemsbyParent_id(item.id).size()!=0 }">
                                                                <c:set var="list2"
                                                                       value="${catDAO.getItemsbyParent_id(item.id) }"></c:set>
                                                            <c:forEach items="${list2 }" var="j">
                                                        <li><a
                                                                href="${contextPath }/danh-muc/${StringUtil.makeSlug(j.name)}-${j.id}.html">${j.name }</a>
                                                        </li>
                                                        </c:forEach>
                                                        </c:if></li>
                                                    </ul>
                                                    <hr class="hidden-md hidden-lg">
                                                </div>
                                            </c:forEach>
                                        </div>
                                            <%-- <div class="row hidden-sm hidden-xs">
                                                <div class="col-md-12">
                                                    <hr>
                                                    <a class="banner banner-1" href="#"> <img
                                                        src="${contextPath}/resources/public/img/banner05.jpg"
                                                        alt="">
                                                        <div class="banner-caption text-center">
                                                            <h2 class="white-color">NEW COLLECTION</h2>
                                                            <h3 class="white-color font-weak">HOT DEAL</h3>
                                                        </div>
                                                    </a>
                                                </div>
                                            </div> --%>
                                    </div>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <li><a href="${contextPath}/giam-gia">Giảm giá</a></li>
                </ul>
            </div>
            <!-- /category nav -->

            <!-- menu nav -->
            <div class="menu-nav">
                <span class="menu-header">Menu <i class="fa fa-bars"></i></span>
                <ul class="menu-list">
                    <li id="index"><a href="${contextPath}">Trang chủ</a></li>
                    <c:forEach items="${listCat }" var="i">
                        <c:choose>
                            <c:when test="${catDAO.getItemsbyParent_id(i.id).size()==0 }">
                                <li><a
                                        href="${contextPath}/danh-muc/${StringUtil.makeSlug(i.name)}-${i.id}">${i.name }</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="dropdown mega-dropdown"><a
                                        class="dropdown-toggle" data-toggle="dropdown"
                                        aria-expanded="true">${i.name } <i class="fa fa-caret-down"></i>
                                </a>
                                    <div class="custom-menu">
                                        <div class="row">
                                            <c:set var="list"
                                                   value="${catDAO.getItemsbyParent_id(i.id) }"></c:set>
                                            <c:forEach var="item" items="${list}">
                                                <div class="col-md-4">
                                                    <ul class="list-links">
                                                        <li><a
                                                                href="${contextPath }/danh-muc/${StringUtil.makeSlug(item.name)}-${item.id}.html">
                                                            <h3 class="list-links-title">${item.name }</h3>
                                                        </a>
                                                            <c:if
                                                                    test="${catDAO.getItemsbyParent_id(item.id).size()!=0 }">
                                                                <c:set var="list2"
                                                                       value="${catDAO.getItemsbyParent_id(item.id) }"></c:set>
                                                            <c:forEach items="${list2 }" var="j">
                                                        <li><a
                                                                href="${contextPath }/danh-muc/${StringUtil.makeSlug(j.name)}-${j.id}.html">${j.name }</a>
                                                        </li>
                                                        </c:forEach>
                                                        </c:if></li>
                                                    </ul>
                                                    <hr class="hidden-md hidden-lg">
                                                </div>
                                            </c:forEach>
                                        </div>
                                            <%-- <div class="row hidden-sm hidden-xs">
                                                <div class="col-md-12">
                                                    <hr>
                                                    <a class="banner banner-1" href="#"> <img
                                                        src="${contextPath}/resources/public/img/banner05.jpg"
                                                        alt="">
                                                        <div class="banner-caption text-center">
                                                            <h2 class="white-color">NEW COLLECTION</h2>
                                                            <h3 class="white-color font-weak">HOT DEAL</h3>
                                                        </div>
                                                    </a>
                                                </div>
                                            </div> --%>
                                    </div>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <li id="contact"><a href="${contextPath}/lien-he">Liên hệ</a></li>
                </ul>
            </div>
            <!-- menu nav -->
        </div>
    </div>
    <!-- /container -->
</div>
<!-- /NAVIGATION -->