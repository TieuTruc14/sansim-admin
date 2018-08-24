
<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html lang="en" class="bg-dark">
<head>
    <meta charset="utf-8"/>
    <title>Sàn Sim Số</title>
    <meta name="description" content="app, san sim"/>
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

    <div class="row m-n">
        <div class="col-sm-4 col-sm-offset-4">
            <div class="text-center m-b-lg">
                <h1 class="h text-white animated fadeInDownBig">404</h1>
            </div>
            <article class="media">
                <div class="pull-left">
                    <span class="pull-left thumb-sm"><img src="<%=request.getContextPath()%>/assets/images/icon/cry.png" class="img-circle" style="opacity: 0.5"></span>
                </div>
                <div class="media-body">
                    <a href="javascript:void(0)" class="h4 text-warning">KHÔNG TÌM THẤY DỮ LIỆU!</a>
                    <small class="block m-t-xs">Hãy bấm vào link bên dưới để trở lại trang chủ</small>
                </div>
            </article>
            <div class="list-group m-b-sm bg-white m-b-lg" style="margin-top:10px;">
                <a href="<%=request.getContextPath()%>/" class="list-group-item ">
                    <i class="fa fa-chevron-right icon-muted"></i>
                    <i class="fa fa-fw fa-home icon-muted"></i> <span class="text-info">Trở về trang chủ</span>
                </a>

            </div>
        </div>
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

</body>
</html>