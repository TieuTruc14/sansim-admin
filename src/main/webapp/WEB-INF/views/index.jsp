<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
    .count{
        font-size:30px;
        font-weight: 600;
    }
    .green-special{
        color:#1ABB9C;
    }
    .nau-xam{
        color:#73879C;
        font-weight: 400;
        font-size: 13px;
    }
    .red{
        color:#E74C3C;
    }
    #container {
        min-height: 350px;
        max-height: 600px;
        margin: 0 auto;
    }
</style>
<script src="<%=request.getContextPath()%>/assets/project/index/index.js"></script>
<section id="content"  ng-app="sansimso"  ng-controller="sansimCtrl">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Home</a></li>
            </ul>
            <div class="m-b-md"><h3 class="m-b-none">Workset</h3>
                <small><span class="text-success"><sec:authentication property="principal.fullName" /></span>, mừng bạn trở lại.</small>
            </div>
            <section class="panel panel-default">
                <div class="right_col " role="main">
                    <!-- top tiles -->
                    <div class="row tile_count m-l-none m-r-none bg-light lter">
                        <div class="col-md-2 col-sm-6 col-xs-6  padder-v b-r b-light">
                            <span class=" nau-xam"><i class="fa fa-user"></i> Tổng người bán</span>
                            <div class="count text-info currencyHtml">${item.cusTotal}</div>
                            <span class="currencyHtml nau-xam">
                                 <i class="${percentCusTotal>0? "green-special":"red"} "><i class="fa ${percentCusTotal>0? "fa-sort-asc":"fa-sort-desc"}"></i>${percentCusTotal}</i>
                                 &nbsp;Từ cuối tuần trước</span>
                        </div>
                        <div class="col-md-2 col-sm-6 col-xs-6  padder-v b-r b-light">
                            <span class=" nau-xam"><i class="fa fa-male"></i> Người bán hoạt động</span>
                            <div class="count  green-special currencyHtml">${item.cusActive}</div>
                            <span class="currencyHtml nau-xam"><i class="${percentCusActive>0? "green-special":"red"} "><i class="fa ${percentCusActive>0? "fa-sort-asc":"fa-sort-desc"}"></i>${percentCusActive}</i> Từ cuối tuần trước</span>
                        </div>
                        <div class="col-md-2 col-sm-6 col-xs-6  padder-v b-r b-light">
                            <span class=" nau-xam"><i class="fa fa-lock"></i> Người bán bị khóa</span>
                            <div class="count currencyHtml">${item.cusTotal-item.cusActive}</div>
                            <span class="currencyHtml nau-xam"><i class="currencyHtml ${percentCusLock>0? "green-special":"red"} "><i class="fa ${percentCusLock>0? "fa-sort-asc":"fa-sort-desc"}"></i>${percentCusLock}</i> Từ cuối tuần trước</span>
                        </div>
                        <div class="col-md-2 col-sm-6 col-xs-6  padder-v b-r b-light">
                            <span class=" nau-xam"><i class="fa fa-phone"></i> Tổng thuê bao</span>
                            <div class="count text-info currencyHtml">${item.msisdnTotal}</div>
                            <span class="currencyHtml nau-xam"><i class="${percentMsisdnTotal>0? "green-special":"red"} "><i class="fa ${percentMsisdnTotal>0? "fa-sort-asc":"fa-sort-desc"}"></i>${percentMsisdnTotal}</i> Từ cuối tuần trước</span>
                        </div>
                        <div class="col-md-2 col-sm-6 col-xs-6  padder-v b-r b-light">
                            <span class=" nau-xam"><i class="fa fa-phone-square"></i> Thuê bao hiển thị</span>
                            <div class="count  green-special currencyHtml">${item.msisdnActive}</div>
                            <span class="currencyHtml nau-xam"><i class="${percentMsisdnActive>0? "green-special":"red"} "><i class="fa ${percentMsisdnActive>0? "fa-sort-asc":"fa-sort-desc"}"></i>${percentMsisdnActive}</i> Từ cuối tuần trước</span>
                        </div>
                        <div class="col-md-2 col-sm-6 col-xs-6  padder-v b-r b-light">
                            <span class=" nau-xam"><i class="fa fa-lock"></i> Thuê bao khóa</span>
                            <div class="count currencyHtml">${item.msisdnTotal-item.msisdnActive}</div>
                            <span class="currencyHtml nau-xam"><i class="${percentMsisdnLock>0? "green-special":"red"} "><i class="fa ${percentMsisdnLock>0? "fa-sort-asc":"fa-sort-desc"}"></i>${percentMsisdnLock}</i> Từ cuối tuần trước</span>
                        </div>
                    </div>
                </div>
            </section>
            <section class="panel panel-default">
                <div class="row m-l-none m-r-none bg-light lter">
                    <div class="col-sm-6 col-md-4 padder-v b-r b-light">
                                    <span
                                            class="fa-stack fa-2x pull-left m-r-sm"> <i
                                            class="fa fa-circle fa-stack-2x text-info"></i> <i
                                            class="fa fa-male fa-stack-1x text-white"></i>
                                    </span>
                        <a class="clear" href="#">
                            <span class="h3 block m-t-xs"><strong>${item.cusNew}</strong></span>
                            <small class="text-muted text-uc">Người bán mới hôm nay</small>
                        </a>
                    </div>
                    <div class="col-sm-6 col-md-4 padder-v b-r b-light">
                                    <span
                                            class="fa-stack fa-2x pull-left m-r-sm"> <i
                                            class="fa fa-circle fa-stack-2x text-primary"></i> <i
                                            class="fa fa-phone-square fa-stack-1x text-white"></i>
                                    </span>
                        <a class="clear" href="#">
                            <span class="h3 block m-t-xs"><strong>${item.msisdnNewReal}</strong></span>
                            <small class="text-muted text-uc">Thuê bao mới hôm nay</small>
                        </a>
                    </div>
                    <div class="col-sm-6 col-md-4 padder-v b-r b-light">
                                   <span
                                           class="fa-stack fa-2x pull-left m-r-sm"> <i
                                           class="fa fa-circle fa-stack-2x text-success"></i> <i
                                           class="fa fa-phone fa-stack-1x text-white"></i>
                                    </span>
                        <a class="clear" href="#">
                            <span class="h3 block m-t-xs"><strong>${item.msisdnNew-item.msisdnNewReal}</strong></span>
                            <small class="text-muted text-uc">Thuê bao đăng lại hôm nay</small>
                        </a>
                    </div>
                </div>
            </section>
            <section class="panel panel-default pos-rlt clearfix">
                <header class="panel-heading " onclick="toggler('chitieu');" >
                    <ul class="nav nav-pills pull-right">
                        <li>
                            <a href="javascript:void(0)"  class="text-muted chitieu"><i class="fa fa-caret-down text-active"></i><i class="fa fa-caret-up text"></i></a>
                        </li>
                    </ul>
                    <a class="text-info chitieu" style="font-weight:600;" href="javascript:void(0)">CHỌN CHỈ TIÊU BÁO CÁO</a>
                </header>
                <div class="panel-body clearfix" style="display:none" id="chitieu" >
                    <div class="form-group ">
                        <div class="col-md-3">
                                <select name="type" class="select2-option" style="width:100%" ng-model="type" ng-change="changeType(type)">
                                    <option value="">Chọn chỉ tiêu</option>
                                    <option value="0">Tuần</option>
                                    <option value="1">Tháng</option>
                                    <option value="2">Năm</option>
                                </select>
                            <span class="red">{{typeError}}</span>
                        </div>
                        <div class="col-md-3" ng-show="showYear">
                                <select name="year" class="select2-option" style="width:100%" ng-model="year">
                                    <option value="">Chọn năm</option>
                                    <option  ng-repeat="item in listYear" value="{{item}}">{{item}}</option>
                                </select>
                            <span class="red">{{yearError}}</span>
                        </div>
                        <div class="col-md-3" ng-show="showMonth">
                                <select name="month" class="select2-option" style="width:100%" ng-model="month">
                                    <option value="">Chọn tháng</option>
                                    <option value="1">Tháng 1</option>
                                    <option value="2">Tháng 2</option>
                                    <option value="3">Tháng 3</option>
                                    <option value="4">Tháng 4</option>
                                    <option value="5">Tháng 5</option>
                                    <option value="6">Tháng 6</option>
                                    <option value="7">Tháng 7</option>
                                    <option value="8">Tháng 8</option>
                                    <option value="9">Tháng 9</option>
                                    <option value="10">Tháng 10</option>
                                    <option value="11">Tháng 11</option>
                                    <option value="12">Tháng 12</option>
                                </select>
                            <span class="red">{{monthError}}</span>
                        </div>
                        <div class="col-md-3" ng-show="showWeek">
                                <select name="week" class="select2-option" style="width:100%" ng-model="week">
                                    <option value="">Chọn tuần</option>
                                    <option value="1">Tuần 1</option>
                                    <option value="2">Tuần 2</option>
                                    <option value="3">Tuần 3</option>
                                    <option value="4">Tuần 4</option>
                                    <option value="5">Tuần 5</option>
                                    <option value="6">Tuần 6</option>
                                    <option value="7">Tuần 7</option>
                                    <option value="8">Tuần 8</option>
                                    <option value="9">Tuần 9</option>
                                    <option value="10">Tuần 10</option>
                                    <option value="11">Tuần 11</option>
                                    <option value="12">Tuần 12</option>
                                </select>
                            <span class="red">{{weekError}}</span>
                        </div>
                    </div>

                    <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>
                    <div class="form-group text-center">
                        <a ng-click="search()" class="btn btn-info btn-sm">Áp dụng</a>
                    </div>
                </div>
            </section>

            <div class="row">
                <div class="col-md-8">
                    <section class="panel panel-default">
                        <header class="panel-heading font-bold">Biểu đồ doanh thu {{nameTitle}}</header>
                        <div class="panel-body">
                            <div id="container"></div>
                            <%--<button id="plain" class="btn btn-success btn-sm">Dạng cột</button>--%>
                            <%--<button id="inverted" class="btn btn-primary btn-sm">Dạng ngang</button>--%>
                            <%--<button id="polar" class="btn btn-info btn-sm">Đường đối cực</button>--%>
                        </div>

                    </section>
                </div>
                <div class="col-md-4">
                    <section class="panel panel-default">
                        <header class="panel-heading font-bold">Tỷ lệ doanh thu loại giao dịch {{nameTitle}}</header>
                        <div class=" " style="height:410px">
                            <div class="bieudotron" style="text-align:center">
                                <canvas class="canvasDoughnutDoanhThu" height="220" width="220" style="margin: 15px 10px 10px 0"></canvas>
                                <table class="table table-striped m-b-none" style="text-align:left">
                                    <tbody>
                                    <tr>
                                        <td>
                                                 <span class="fa-stack fa pull-right m-r-sm">
                                                     <i class="fa fa-square fa-stack-2x" style="color:#3498DB;"></i> <i class="fa fa-stack-1x text-white"></i>
                                                 </span>
                                        </td>
                                        <td>Đăng ký</td>
                                        <td style="text-align: right;">
                                            {{doanhthuloaigiaodich.dangky|currency:"":0}}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                                <span class="fa-stack fa pull-right m-r-sm">
                                                     <i class="fa fa-square fa-stack-2x" style="color:#9B59B6;"></i> <i class="fa fa-stack-1x text-white"></i>
                                                 </span>
                                        </td>
                                        <td>Gia hạn</td>
                                        <td style="text-align: right;">
                                            {{doanhthuloaigiaodich.giahan|currency:"":0}}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                                <span class="fa-stack fa pull-right m-r-sm">
                                                     <i class="fa fa-square fa-stack-2x" style="color:#26B99A;"></i> <i class="fa fa-stack-1x text-white"></i>
                                                 </span>
                                        </td>
                                        <td>Xác thực</td>
                                        <td style="text-align: right;">
                                            {{doanhthuloaigiaodich.xacthuc|currency:"":0}}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                                <span class="fa-stack fa pull-right m-r-sm">
                                                     <i class="fa fa-square fa-stack-2x" style="color:#BDC3C7;"></i> <i class="fa fa-stack-1x text-white"></i>
                                                 </span>
                                        </td>
                                        <td>Gia hạn xác thực</td>
                                        <td style="text-align: right;">
                                            {{doanhthuloaigiaodich.giahanxt|currency:"":0}}
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                    </section>
                </div>
            </div>
            <%--BIEU DO TURN 2--%>
            <div class="row">
                <div class="col-md-8">
                    <section class="panel panel-default">
                        <header class="panel-heading font-bold">Biểu đồ khách hàng đăng ký {{nameTitle}}</header>
                        <div class="panel-body">
                            <div id="khachhangmoi" style="height:350px"></div>
                        </div>
                    </section>
                    <section class="panel panel-default">
                        <header class="panel-heading font-bold">Biểu đồ đăng mới thuê bao {{nameTitle}}</header>
                        <div class="panel-body">
                            <div id="thuebaomoi" style="height:350px"></div>
                        </div>
                    </section>
                </div>
                <div class="col-md-4">
                    <section class="panel panel-default">
                        <header class="panel-heading font-bold">Tỷ lệ số lượng loại giao dịch {{nameTitle}}</header>
                        <div class=" " style="height:447px">
                            <div class="bieudotron" style="text-align:center">
                                <canvas class="canvasDoughnut" height="220" width="220" style="margin: 15px 10px 10px 0"></canvas>
                                <table class="table table-striped m-b-none" style="text-align:left">
                                    <tbody>
                                    <tr>
                                        <td>
                                                 <span class="fa-stack fa pull-right m-r-sm">
                                                     <i class="fa fa-square fa-stack-2x" style="color:#3498DB;"></i> <i class="fa fa-stack-1x text-white"></i>
                                                 </span>
                                        </td>
                                        <td>Đăng ký</td>
                                        <td style="text-align: right;">
                                            {{sanluonggiaodich.dangky|currency:"":0}}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                                <span class="fa-stack fa pull-right m-r-sm">
                                                     <i class="fa fa-square fa-stack-2x" style="color:#9B59B6;"></i> <i class="fa fa-stack-1x text-white"></i>
                                                 </span>
                                        </td>
                                        <td>Gia hạn</td>
                                        <td style="text-align: right;">
                                            {{sanluonggiaodich.giahan|currency:"":0}}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                                <span class="fa-stack fa pull-right m-r-sm">
                                                     <i class="fa fa-square fa-stack-2x" style="color:#26B99A;"></i> <i class="fa fa-stack-1x text-white"></i>
                                                 </span>
                                        </td>
                                        <td>Xác thực</td>
                                        <td style="text-align: right;">
                                            {{sanluonggiaodich.xacthuc|currency:"":0}}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                                <span class="fa-stack fa pull-right m-r-sm">
                                                     <i class="fa fa-square fa-stack-2x" style="color:#BDC3C7;"></i> <i class="fa fa-stack-1x text-white"></i>
                                                 </span>
                                        </td>
                                        <td>Gia hạn xác thực</td>
                                        <td style="text-align: right;">
                                            {{sanluonggiaodich.giahanxt|currency:"":0}}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                                <span class="fa-stack fa pull-right m-r-sm">
                                                     <i class="fa fa-square fa-stack-2x" style="color:#E74C3C;"></i> <i class="fa fa-stack-1x text-danger"></i>
                                                 </span>
                                        </td>
                                        <td>Hủy</td>
                                        <td style="text-align: right;">
                                            {{sanluonggiaodich.huyHT+sanluonggiaodich.huyNguoiDung |currency:"":0}}
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                    </section>

                </div>
            </div>

            <%--<div class="row">--%>
                <%--<div class="col-md-8">--%>
                    <%--<section class="panel panel-default">--%>
                        <%--<header class="panel-heading font-bold">Biểu đồ đăng mới thuê bao</header>--%>
                        <%--<div class="panel-body">--%>
                            <%--<div id="thuebaomoi" style="height:380px"></div>--%>
                        <%--</div>--%>

                    <%--</section>--%>
                <%--</div>--%>
            <%--</div>--%>
        </section>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
</section>

<!-- Bootstrap --> <!-- App -->
<script src="<%=request.getContextPath()%>/assets/js/jquery-3.1.1.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/note/js/charts/sparkline/jquery.sparkline.min.js" cache="false"></script>
<script src="<%=request.getContextPath()%>/assets/note/js/charts/easypiechart/jquery.easy-pie-chart.js" cache="false"></script>
<script src="<%=request.getContextPath()%>/assets/js/highcharts/highcharts.js" cache="false"></script>
<script src="<%=request.getContextPath()%>/assets/js/highcharts/highcharts-more.js" cache="false"></script>
<script src="<%=request.getContextPath()%>/assets/js/highcharts/exporting.js" cache="false"></script>
<script src="<%=request.getContextPath()%>/assets/note/js/charts/flot/jquery.flot.min.js" ></script>
<script src="<%=request.getContextPath()%>/assets/note/js/charts/flot/jquery.flot.tooltip.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/note/js/charts/flot/jquery.flot.resize.js"></script>
<script src="<%=request.getContextPath()%>/assets/note/js/charts/flot/jquery.flot.grow.js"></script>
<%--<script src="<%=request.getContextPath()%>/assets/note/js/charts/flot/demo.js"></script>--%>

<%--<script src="<%=request.getContextPath()%>/assets/project/index/demo.js"></script>--%>

<%--Cho bieu do` tron`--%>
<script src="<%=request.getContextPath()%>/assets/vendors/Chart.js/dist/Chart.min.js"></script>
<%--<script src="<%=request.getContextPath()%>/assets/project/index/bieudotron.js"></script>--%>
<script>
    function toggler(divId) {
        $("#" + divId).toggle();
    }
    //giup cai mui ten doi chieu khi click
    $(document).on('click', '.chitieu', function(e){
        e && e.preventDefault();
        var $this = $(e.target), $target;
        if (!$this.is('a')) $this = $this.closest('a');
        $target = $this.closest('.panel');
        $this.toggleClass('active');
    });
</script>