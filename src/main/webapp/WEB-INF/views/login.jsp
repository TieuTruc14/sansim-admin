<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html lang="en" class="bg-dark">
<head>
    <meta charset="utf-8"/>
    <title>Sàn Sim Số</title>
    <meta name="description" content="app, uchi, v4, cong chung, off screen nav"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/assets/images/osp.ico"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/bootstrap.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/animate.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/font-awesome.min.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/font.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/app.css" type="text/css"/>
    <!--[if lt IE 9]>
    <script src="<%=request.getContextPath()%>/assets/note/js/ie/html5shiv.js"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/ie/respond.min.js"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/ie/excanvas.js"></script>
    <![endif]-->
</head>
<body class="">
<style>
    .navbar-brand img {
        max-height: 50px;
    }
</style>
<section id="content" class="m-t-lg wrapper-md animated fadeInUp">
    <div class="container aside-xxl">
        <%--<a class="navbar-brand block" href="index.html"><img src="#" class="img-rounded"></a>--%>
        <section class="panel panel-default bg-white m-t-lg">
            <header class="panel-heading text-center">
                <strong>Đăng Nhập</strong>
            </header>
            <form action="<c:url value='/j_spring_security_check'/>" class="panel-body wrapper-lg" method="post"
                  class="form-group ">
                <div class="form-group">
                    <label class="control-label">Tài khoản</label>
                    <input id="username" name="username" placeholder="username" class="form-control input-lg">
                </div>
                <div class="form-group">
                    <label class="control-label">Mật khẩu</label>
                    <input type="password" id="password" name="password" placeholder="password"
                           class="form-control input-lg">
                </div>
                <div class="checkbox">
                    <label>
                        <input type="checkbox" id="rememberme" name="remember-me"> Ghi nhớ mật khẩu
                    </label>
                </div>

                <button type="submit" class="btn btn-primary">Đăng nhập</button>
                <div class="line line-dashed"></div>
                <p class="text-primary">HỖ TRỢ KỸ THUẬT</p>
                <p class="text-muted">
                    <small>Di động:0977 258 852 (Mr. Phạm Mạnh)</small>
                </p>
            </form>
        </section>
    </div>
</section>

<!-- footer -->
<footer id="footer">
    <div class="text-center padder">
        <p>
            <small>Sàn sim số &copy; 2018</small>
        </p>
    </div>
</footer>
<!-- / footer -->
<script src="<%=request.getContextPath()%>/assets/note/js/jquery.min.js"></script>
<!-- Bootstrap -->
<script src="<%=request.getContextPath()%>/assets/note/js/bootstrap.js"></script>
<!-- App -->
<script src="<%=request.getContextPath()%>/assets/note/js/app.js"></script>
<script src="<%=request.getContextPath()%>/assets/note/js/slimscroll/jquery.slimscroll.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/note/js/app.plugin.js"></script>
<script>

</script>
</body>
</html>