<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <title>MINT-SHOP</title>

    <!-- Google font -->
    <link href="https://fonts.googleapis.com/css?family=Hind:400,700"
          rel="stylesheet">

    <!-- Bootstrap -->
    <link type="text/css" rel="stylesheet" href="${contextPath}/resources/public/css/bootstrap.min.css"/>

    <!-- Slick -->
    <link type="text/css" rel="stylesheet" href="${contextPath}/resources/public/css/slick.css"/>
    <link type="text/css" rel="stylesheet" href="${contextPath}/resources/public/css/slick-theme.css"/>

    <!-- nouislider -->
    <link type="text/css" rel="stylesheet" href="${contextPath}/resources/public/css/nouislider.min.css"/>

    <!-- Font Awesome Icon -->
    <link rel="stylesheet" href="${contextPath}/resources/public/css/font-awesome.min.css">

    <!-- Custom stlylesheet -->
    <link type="text/css" rel="stylesheet" href="${contextPath}/resources/public/css/style.css"/>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
              <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
              <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
            <![endif]-->
    <script
            src="https://www.paypalobjects.com/api/checkout.js">
    </script>
</head>

<body>
<tiles:insertAttribute name="header"></tiles:insertAttribute>
<tiles:insertAttribute name="content"></tiles:insertAttribute>
<tiles:insertAttribute name="footer"/>
<!-- jQuery Plugins -->
<script src="${contextPath}/resources/public/js/jquery.min.js"></script>
<script src="${contextPath}/resources/public/js/bootstrap.min.js"></script>
<script src="${contextPath}/resources/public/js/slick.min.js"></script>
<script src="${contextPath}/resources/public/js/nouislider.min.js"></script>
<script src="${contextPath}/resources/public/js/jquery.zoom.min.js"></script>
<script src="${contextPath}/resources/public/js/main.js"></script>
<script type="text/javascript">
    function addtocart(id_product) {
        var qty = $("#quanty").val();
        if (qty == null) qty = 1;
        $.ajax({
            url: "${contextPath}/addtocart/" + id_product + "/" + qty,
            cache: false,
            data: {},
            error: function () {
                alert('Có lỗi xảy ra');
            },
            success: function (data) {
                $("#cart").html(data);
                alert("Đã thêm vào giỏ hàng")
            }
        });
        return false;
    }
</script>
</body>
</html>



