<%@page import="vn.edu.vinaenter.utils.StringUtil" %>
<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<div id="home">
    <!-- container -->
    <div class="container">
        <!-- home wrap -->
        <div class="home-wrap">
            <!-- home slick -->
            <div id="home-slick">
                <!-- banner -->
                <div class="banner banner-1">
                    <img class="banner-img"
                         src="${contextPath}/resources/public/img/banner-make-mint07.png"
                         alt="">
                    <div class="banner-caption">
                        <h3 class="black-color font-weak">Up to 50% Discount</h3>
                        <button class="primary-btn">Shop Now</button>
                    </div>
                </div>
                <!-- /banner -->

                <!-- banner -->
                <div class="banner banner-1">
                    <img class="banner-img"
                         src="${contextPath}/resources/public/img/banner-skincare-png.png"
                         alt="">
                    <div class="banner-caption">
                        <h1 class="primary-color">
                            HOT DEAL<br> <span class="black-color font-weak">Up
								to 50% OFF</span>
                        </h1>
                        <button class="primary-btn">Shop Now</button>
                    </div>
                </div>
                <!-- /banner -->

                <!-- banner -->
                <div class="banner banner-1">
                    <img class="banner-img"
                         src="${contextPath}/resources/public/img/obagi.png" alt="">
                    <div class="banner-caption">
                        <h1 class="black-color">
                            New Product <span>Collection</span>
                        </h1>
                        <button class="primary-btn">Shop Now</button>
                    </div>
                </div>
                <!-- /banner -->
            </div>
            <!-- /home slick -->
        </div>
        <!-- /home wrap -->
    </div>
    <!-- /container -->
</div>
<!-- /HOME -->
<div class="section">
    <!-- container -->
    <div class="container">
        <!-- row -->
        <div class="row">
            <!-- section title -->
            <div class="col-md-12">
                <div class="section-title">
                    <h2 class="title">Giảm giá</h2>
                </div>
            </div>
            <!-- section title -->
            <c:forEach items="${listDeal }" var="obj" end="3">
                <!-- Product Single -->
                <div class="col-md-3 col-sm-6 col-xs-6">
                    <div class="product product-single">
                        <div class="product-thumb">
                            <a
                                    href="${contextPath }/chi-tiet/${StringUtil.makeSlug(obj.name)}-${obj.id}.html">
                                <button
                                        class="main-btn quick-view">
                                    <i class="fa fa-search-plus"></i> Xem
                                </button>
                            </a> <img
                                src="${contextPath}/fileUpload/${obj.picture.substring(0, obj.picture.indexOf(' '))}"
                                alt="">
                        </div>
                        <div class="product-body">
                            <h3 class="product-price">
                                <c:choose>
                                    <c:when test="${obj.newprice==0 }">$ ${obj.price } </c:when>
                                    <c:otherwise>$ ${obj.newprice }
                                        <del class="product-old-price">$ ${obj.price }</del>
                                    </c:otherwise>
                                </c:choose>
                            </h3>
                            <h2 class="product-name">
                                <a
                                        href="${contextPath }/chi-tiet/${StringUtil.makeSlug(obj.name)}-${obj.id}.html">${obj.name }</a>
                            </h2>
                            <c:if test="${not empty obj.brand }"><p><strong>Nhãn hiệu: </strong>${obj.brand }</p></c:if>
                            <div class="product-btns">
                                <button class="primary-btn add-to-cart" onclick="return addtocart(${obj.id})">
                                    <i class="fa fa-shopping-cart"></i> Thêm vào giỏ
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /Product Single -->
            </c:forEach>
        </div>
        <!-- /row -->
        <c:forEach items="${listCat }" var="objCat" end="1">
            <c:set var="list" value="${catDAO.getItemsbyParent_id(objCat.id)}"></c:set>
            <!-- row -->
            <div class="row">
                <!-- section-title -->
                <div class="col-md-12">
                    <div class="section-title">
                        <h2 class="title">${objCat.name }</h2>
                        <div class="pull-right">
                            <div class="product-slick-dots-${objCat.id } custom-dots"></div>
                        </div>
                    </div>
                </div>
                <!-- /section-title -->
                <div class="col-md-3">
                    <ul class="list-links">
                        <li>
                            <c:forEach items="${list}" var="j">
                            <c:if test="${catDAO.getItemsbyParent_id(j.id).size()!=0 }">
                                <c:set var="list2" value="${catDAO.getItemsbyParent_id(j.id) }"></c:set>
                            <c:forEach items="${list2 }" var="jj">
                        <li><a href="${contextPath }/danh-muc/${StringUtil.makeSlug(jj.name)}-${jj.id}">${jj.name }</a>
                        </li>
                        <hr>
                        </c:forEach>
                        </c:if>
                        </c:forEach></li>
                    </ul>
                    <hr class="hidden-md hidden-lg">
                </div>
                <!-- Product Slick -->
                <div class="col-md-9 col-sm-6 col-xs-6">
                    <div class="row">
                        <div id="product-slick-${objCat.id}" class="product-slick">
                            <c:set var="list3" value="${productDAO.getItemsNew(objCat.id) }"></c:set>
                            <c:forEach items="${list3 }" var="itemProduct" end="5">
                                <!-- Product Single -->
                                <div class="product product-single">
                                    <div class="product-thumb">
                                        <a
                                                href="${contextPath }/chi-tiet/${StringUtil.makeSlug(itemProduct.name)}-${itemProduct.id}.html">
                                            <button
                                                    class="main-btn quick-view">
                                                <i class="fa fa-search-plus"></i> Xem
                                            </button>
                                        </a> <img
                                            src="${contextPath}/fileUpload/${itemProduct.picture.substring(0, itemProduct.picture.indexOf(' '))}"
                                            alt="">
                                    </div>
                                    <div class="product-body">
                                        <h3 class="product-price">
                                            <c:choose>
                                                <c:when test="${itemProduct.newprice==0 }">$ ${itemProduct.price } </c:when>
                                                <c:otherwise>$ ${itemProduct.newprice }
                                                    <del class="product-old-price">$ ${itemProduct.price } </del>
                                                </c:otherwise>
                                            </c:choose>
                                        </h3>
                                        <h2 class="product-name">
                                            <a href="${contextPath }/chi-tiet/${StringUtil.makeSlug(itemProduct.name)}-${itemProduct.id}.html">${itemProduct.name }</a>
                                        </h2>
                                        <c:if test="${not empty itemProduct.brand }"><p><strong>Nhãn
                                            hiệu: </strong>${itemProduct.brand }</p></c:if>
                                        <div class="product-btns">
                                            <button class="primary-btn add-to-cart"
                                                    onclick="return addtocart(${itemProduct.id})">
                                                <i class="fa fa-shopping-cart"></i> Thêm vào giỏ
                                            </button>
                                        </div>
                                    </div>
                                </div>
                                <!-- /Product Single -->
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <!-- /Product Slick -->
            </div>
            <!-- /row -->
        </c:forEach>
    </div>
    <!-- /container -->
</div>
<!-- /section -->
<script>
    document.getElementById("category-nav").classList.remove("show-on-click");
    document.getElementById("index").classList.add("active");
</script>