<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<ul class="pgwSlider">
    <c:forEach items="${listMostViews }" var="item">
        <li><img
                src="${contextPath}/fileUpload/${item.picture}"
                alt="" data-description="${item.lname }"
                data-large-src=""/>
        </li>
    </c:forEach>
</ul>