<%@include file="/WEB-INF/templates/taglib.jsp" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<div class="section">
    <!-- container -->
    <div class="container">
        <form id="checkout-form" class="clearfix" action="${contextPath }/lien-he" method="post">
            <div class="col-md-6">
                <div class="billing-details">
                    <div class="section-title">
                        <h3 class="title">Liên hệ</h3>
                    </div>
                    <div>
                        <p style="color:red;font-style:italic;">${msg }</p>
                        <form:errors cssStyle="color:red;font-style:italic;"
                                     path="objContact.*"></form:errors></div>
                    <div class="form-group">
                        <input class="form-control" type="text" name="fullname"
                               placeholder="Họ tên">
                    </div>
                    <div class="form-group">
                        <input class="form-control" type="email" name="email"
                               placeholder="Email">
                    </div>
                    <div class="form-group">
                        <input class="form-control" type="text" name="subject" placeholder="Tiêu đề">
                    </div>
                    <div class="form-group">
                        <textarea class="form-control" name="content" placeholder="Nội dung"></textarea>
                    </div>
                </div>
                <button class="primary-btn">Gửi liên hệ</button>
            </div>

        </form>
    </div>
</div>
<script>
    document.getElementById("contact").classList.add('active');
</script>