<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<header class="bg-dark dk header navbar navbar-fixed-top-xs">

    <div class="navbar-header aside-md ">
        <a class="btn btn-link visible-xs" data-toggle="class:nav-off-screen,open" data-target="#nav,html">
            <i class="fa fa-bars"></i>
        </a>
        <a href="<%=request.getContextPath()%>/" class="navbar-brand" data-toggle="fullscreen1111"><img src="<%=request.getContextPath()%>/assets/images/logo.png" class="m-r-sm ">Sàn sim</a>
        <a class="btn btn-link visible-xs" data-toggle="dropdown" data-target=".nav-user">
            <i class="fa fa-cog"></i>
        </a>
    </div>

    <ul class="nav navbar-nav navbar-right hidden-xs nav-user " >
        <li class="hidden-xs">
            <a href="#" class="dropdown-toggle dk" data-toggle="dropdown">
                Trợ giúp
            </a>
            <section class="dropdown-menu aside-xl">
                <section class="panel bg-white">
                    <header class="panel-heading b-light bg-light">
                        <strong>Chọn tài liệu</strong>
                    </header>
                    <div class=" list-group-alt animated fadeInRight">
                        <a href="#" class="media list-group-item">
                              <span class="pull-left thumb-sm">
                                 <i class="fa fa-book fa-3x"></i>
                              </span>
                            <span class="media-body block m-b-none">
                                hỗ trợ
                              </span>
                        </a>
                    </div>
                </section>
            </section>
        </li>

        <li class="dropdown" style="margin-right:14px;">
            <a href="#" class=" dropdown-toggle "  data-toggle="dropdown" > <span class="thumb-sm avatar pull-left"> <img src="<%=request.getContextPath()%>/assets/images/user.png"> </span></span> <sec:authentication property="principal.fullName" /> <b class="caret"></b> </a>
            <ul class="dropdown-menu animated fadeInRight "><span class="arrow top"></span>
                <li><a href="#">Profile</a></li>
                <li ><a href="<%=request.getContextPath()%>/system/user/change-my-pass">Đổi mật khẩu</a></li>
                <li class="divider"></li>
                <li><a href="<%=request.getContextPath()%>/j_spring_security_logout">Đăng xuất</a></li>
            </ul>
        </li>

    </ul>
</header>


