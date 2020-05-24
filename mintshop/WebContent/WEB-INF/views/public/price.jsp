<%@page import="vn.edu.vinaenter.utils.StringUtil" %>
<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!-- BREADCRUMB -->
<div id="breadcrumb">
    <div class="container">
        <ul class="breadcrumb">
            <li><a href="${contextPath }">Home</a></li>
            <li class="active">${cat.name }</li>
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
            <!-- ASIDE -->
            <div id="aside" class="col-md-3">
                <!-- aside widget -->
                <div class="aside">
                    <h3 class="aside-title">Lọc theo giá</h3>
                    <div id="price-slider"></div>
                    <button class="primary-btn" onclick="return getPrice(${cat.id})">Lọc</button>
                </div>
                <div class="aside header-search">
                    <h3 class="aside-title">Tìm kiếm theo</h3>
                    <form action="${contextPath }/tim-kiem/${cat.name}-${cat.id}"
                          method="post">
                        <input class="input search-input" type="text"
                               placeholder="Nhập từ khoá" name="keyword" value="${keyword }"> <select
                            class="input search-categories" name="status-search">
                        <c:choose>
                            <c:when test="${status_search==1 }">
                                <option value="1" selected="selected">Tên sản phẩm</option>
                            </c:when>
                            <c:otherwise>
                                <option value="1">Tên sản phẩm</option>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${status_search==2 }">
                                <option value="2" selected="selected">Nhãn hiệu</option>
                            </c:when>
                            <c:otherwise>
                                <option value="2">Nhãn hiệu</option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                        <button class="search-btn">
                            <i class="fa fa-search"></i>
                        </button>
                    </form>
                </div>
                <!-- aside widget -->
            </div>
            <!-- /ASIDE -->

            <!-- MAIN -->
            <div id="main" class="col-md-9">
                <!-- store top filter -->
                <div class="store-filter clearfix">
                    <div class="pull-left">
                        <h3>Lọc theo giá: Thấp nhất: $ ${lowprice }- Cao nhất: $ ${highprice }</h3>
                    </div>
                    <div class="pull-right">
                        <ul class="store-pages">
                            <li><span class="text-uppercase">Page:</span></li>
                            <c:choose>
                                <c:when test="${page==1}">
                                    <li><i class="fa fa-caret-left"></i></li>
                                </c:when>
                                <c:otherwise>
                                    <li><a
                                            href="${contextPath }/price/${cat.id }/${lowprice }-${highprice }/page-${page-1}"><i
                                            class="fa fa-caret-left"></i></a></li>
                                </c:otherwise>
                            </c:choose>
                            <c:forEach begin="1" end="${sumPage }" var="i">
                                <c:choose>
                                    <c:when test="${i== page}">
                                        <c:set var="active" value="active"></c:set>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="active" value=""></c:set>
                                    </c:otherwise>
                                </c:choose>
                                <a
                                        href="${contextPath }/price/${cat.id }/${lowprice }-${highprice }/page-${i}">
                                    <li
                                            class="${active }">${i}</li>
                                </a>
                            </c:forEach>
                            <c:choose>
                                <c:when test="${page==sumPage}">
                                    <li><i class="fa fa-caret-right"></i></li>
                                </c:when>
                                <c:otherwise>
                                    <li><a
                                            href="${contextPath }/price/${cat.id }/${lowprice }-${highprice }/page-${page+1}"><i
                                            class="fa fa-caret-right"></i></a></li>
                                </c:otherwise>
                            </c:choose>
                        </ul>
                    </div>
                </div>
                <!-- /store top filter -->

                <!-- STORE -->
                <div id="store">
                    <!-- row -->
                    <div class="row">
                        <c:forEach items="${listProduct }" var="obj" varStatus="status">
                            <!-- Product Single -->
                            <div class="col-md-4 col-sm-6 col-xs-6">
                                <div class="product product-single" style="border: none;">
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
                                                    <del class="product-old-price">$ ${obj.price } </del>
                                                </c:otherwise>
                                            </c:choose>
                                        </h3>
                                        <h2 class="product-name">
                                            <a
                                                    href="${contextPath }/chi-tiet/${StringUtil.makeSlug(obj.name)}-${obj.id}.html">${obj.name }</a>
                                        </h2>
                                        <c:if test="${not empty obj.brand }">
                                            <p>
                                                <strong>Nhãn hiệu: </strong>${obj.brand }</p>
                                        </c:if>
                                        <div class="product-btns">
                                            <button class="primary-btn add-to-cart"
                                                    onclick="return addtocart(${obj.id})">
                                                <i class="fa fa-shopping-cart"></i> Thêm vào giỏ
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- /Product Single -->
                            <c:if test="${(status.index+1) %3==0 }">
                                <div
                                        class="clearfix visible-md visible-lg visible-sm visible-xs"></div>
                            </c:if>
                            <!-- -->
                        </c:forEach>

                    </div>
                    <!-- /row -->
                </div>
                <!-- /STORE -->
            </div>
            <!-- /MAIN -->
        </div>
        <!-- /row -->
    </div>
    <!-- /container -->
</div>
<!-- /section -->
<script>
    function getPrice(catid) {
        var elem = document.getElementsByClassName('noUi-tooltip');
        var low = elem[0].innerText;
        low = low.substring(0, low.indexOf("."));
        location.replace("${contextPath}/price/" + catid + "/" + low + "-" + elem[1].innerText);
    }
</script>