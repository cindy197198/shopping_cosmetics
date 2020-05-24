<%@include file="/WEB-INF/templates/taglib.jsp" %>
<%@page import="vn.edu.vinaenter.utils.StringUtil" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!-- BREADCRUMB -->
<div id="breadcrumb">
    <div class="container">
        <ul class="breadcrumb">
            <li><a href="${contextPath }/">Trang chủ</a></li>
            <li class="active">${objProduct.name }</li>
        </ul>
    </div>
</div>
<!-- /BREADCRUMB -->

<!-- section -->
<div class="section">
    <!-- container -->
    <div class="container">
        <!-- row -->
        <div class="row">
            <!--  Product Details -->
            <div class="product product-details clearfix">
                <div class="col-md-6">
                    <div id="product-main-view">
                        <c:forEach items="${listFileNames}" var="objImage">
                            <div class="product-view">
                                <img style="width: 400px;" src="${contextPath}/fileUpload/${objImage}" alt="">
                            </div>
                        </c:forEach>
                    </div>
                    <div id="product-view">
                        <c:forEach items="${listFileNames}" var="objImage">
                            <div class="product-view">
                                <img style="width: 140px;" src="${contextPath}/fileUpload/${objImage}" alt="">
                            </div>
                        </c:forEach>
                    </div>
                    <%-- <img style="width: inherit;"
                        src="${contextPath}/fileUpload/${objProduct.picture}" alt=""> --%>
                </div>
                <div class="col-md-6">
                    <div class="product-body">
                        <h2 class="product-name">${objProduct.name }</h2>
                        <h3 class="product-price">
                            <c:choose>
                                <c:when test="${objProduct.newprice==0 }"> $ ${objProduct.price } </c:when>
                                <c:otherwise>$ ${objProduct.newprice }
                                    <del class="product-old-price">$ ${objProduct.price } </del>
                                </c:otherwise>
                            </c:choose>
                        </h3>
                        <c:if test="${not empty objProduct.brand }">
                            <p>
                                <strong>Nhãn hiệu:</strong> ${objProduct.brand }
                            </p>
                        </c:if>

                        <p>${objProduct.description }</p>
                        <p>
                            <strong>Số lượng còn lại:</strong> ${objProduct.amount }
                        </p>
                        <div class="product-btns">
                            <div class="qty-input">
                                <span class="text-uppercase">Số lượng: </span> <input
                                    class="input" type="number" value="1" min="1" max="${objProduct.amount }"
                                    id="quanty">
                            </div>
                            <button class="primary-btn add-to-cart"
                                    onclick="return addtocart(${objProduct.id})">
                                <i class="fa fa-shopping-cart"></i> Thêm vào giỏ
                            </button>
                        </div>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="product-tab">
                        <ul class="tab-nav">
                            <li class="active"><a data-toggle="tab">Chi tiết</a></li>
                        </ul>
                        <div class="tab-content">
                            <div id="tab1" class="tab-pane fade in active">
                                <p>${objProduct.detail}</p>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
            <!-- /Product Details -->
        </div>
        <!-- /row -->
    </div>
    <!-- /container -->
</div>
<!-- /section -->

<!-- section -->
<div class="section">
    <!-- container -->
    <div class="container">
        <!-- row -->
        <div class="row">
            <!-- section title -->
            <div class="col-md-12">
                <div class="section-title">
                    <h2 class="title">Sản phẩm liên quan</h2>
                </div>
            </div>
            <!-- section title -->
            <c:forEach items="${relatedProducts }" var="item">
                <!-- Product Single -->
                <div class="col-md-3 col-sm-6 col-xs-6">
                    <div class="product product-single">
                        <div class="product-thumb">
                            <a
                                    href="${contextPath }/chi-tiet/${StringUtil.makeSlug(item.name)}-${item.id}.html">
                                <button
                                        class="main-btn quick-view">
                                    <i class="fa fa-search-plus"></i> Xem
                                </button>
                            </a> <img
                                src="${contextPath}/fileUpload/${item.picture.substring(0, item.picture.indexOf(' '))}"
                                alt="">
                        </div>
                        <div class="product-body">
                            <h3 class="product-price">
                                <c:choose>
                                    <c:when test="${item.newprice==0 }">$ ${item.price } </c:when>
                                    <c:otherwise>$ ${item.newprice }
                                        <del class="product-old-price">$ ${item.price } </del>
                                    </c:otherwise>
                                </c:choose>
                            </h3>
                            <h2 class="product-name">
                                <a
                                        href="${contextPath }/chi-tiet/${StringUtil.makeSlug(item.name)}-${item.id}.html">${item.name }</a>
                            </h2>
                            <c:if test="${not empty item.brand }">
                                <p>
                                    <strong>Nhãn hiệu: </strong>${item.brand }</p>
                            </c:if>
                            <div class="product-btns">
                                <button class="primary-btn add-to-cart" onclick="return addtocart(${item.id})">
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
    </div>
    <!-- /container -->
</div>
<!-- /section -->
