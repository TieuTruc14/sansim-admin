<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html lang="en" class="app">
<head>
    <meta charset="utf-8"/>
    <title><tiles:insertAttribute name="title"/></title>
    <link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/assets/images/osp.ico" />
    <meta name="description" content="san sim"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/bootstrap.css" type="text/css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/animate.css" type="text/css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/font-awesome.min.css" type="text/css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/font.css" type="text/css" cache="false"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/js/datatables/datatables.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/js/select2/select2.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/js/select2/theme.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/js/fuelux/fuelux.css" type="text/css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/js/slider/slider.css" type="text/css" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/js/datepicker/datepicker.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/style.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/note/css/app.css" type="text/css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/project/angularjs/autocomplete/autocomplete.css" type="text/css"/>
    <%--<[if lt IE 9]>--%>
    <script src="<%=request.getContextPath()%>/assets/note/js/ie/html5shiv.js" cache="false"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/ie/respond.min.js" cache="false"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/ie/excanvas.js" cache="false"></script>
    <%--<[endif]>--%>

    <script src="<%=request.getContextPath()%>/assets/note/js/jquery.min.js"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/bootstrap.js"></script>
    <%--<script src="<%=request.getContextPath()%>/assets/note/js/jquery-ui-1.10.3.custom.min.js"></script>--%>
    <script src="<%=request.getContextPath()%>/assets/note/js/datepicker/bootstrap-datepicker.js"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/app.js"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/app.plugin.js"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/datatables/jquery.dataTables.min.js"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/select2/select2.min.js"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/fuelux/fuelux.js"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/slider/bootstrap-slider.js"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/libs/moment.min.js"></script>
    <script src="<%=request.getContextPath()%>/assets/note/js/combodate/combodate.js"></script>


    <script src="<%=request.getContextPath()%>/assets/project/angularjs/angular.min.js"></script>
    <script src="<%=request.getContextPath()%>/assets/project/angularjs/angular-sanitize.min.js"></script>
    <script src="<%=request.getContextPath()%>/assets/project/common.js"></script>
    <script src="<%=request.getContextPath()%>/assets/js/CommonFunction.js"></script>
    <script src="<%=request.getContextPath()%>/assets/project/angularjs/autocomplete/autocomplete.js"></script>

    <script>
        var preUrl='<%=request.getContextPath()%>';
    </script>
</head>
<body>
<section class="vbox">
    <tiles:insertAttribute name="header" />
    <section>
        <section class="hbox stretch">
            <tiles:insertAttribute name="leftpanel" />
            <tiles:insertAttribute name="page" />
        </section>
    </section>
</section>
</body></html>
