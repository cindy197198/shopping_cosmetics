<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!-- header -->
<!DOCTYPE html>
<html>
<head>
    <title>Bootstrap Admin Theme v3</title>
    <link rel="shortcut icon" type="image/ico" href="${contextPath}/resources/admin/images/icon-180x180.png"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="${contextPath}/resources/admin/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- styles -->
    <link href="https://fonts.googleapis.com/css?family=Lobster" rel="stylesheet">
    <link href="${contextPath}/resources/admin/css/style1.css" rel="stylesheet">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css"
          integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
    <script type="text/javascript" src="${contextPath}/resources/admin/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="${contextPath}/resources/admin/ckfinder/ckfinder.js"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<tiles:insertAttribute name="header"/>
<div class="page-content">
    <div class="row">
        <div class="col-md-2">
            <tiles:insertAttribute name="left-bar"></tiles:insertAttribute>
        </div>
        <div class="col-md-10">
            <tiles:insertAttribute name="content"/>
        </div><!-- /.col-md-10 -->
    </div><!-- /.row -->
</div><!-- /.page-content -->

<tiles:insertAttribute name="footer"/>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://code.jquery.com/jquery.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${contextPath}/resources/admin/bootstrap/js/bootstrap.min.js"></script>
<script src="${contextPath}/resources/admin/js/custom.js"></script>
<script src="${contextPath}/resources/admin/js/jquery-3.2.1.js"></script>
</body>
</html>
<!-- /.footer -->