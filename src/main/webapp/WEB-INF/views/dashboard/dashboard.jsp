<%@ page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
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
</style>
<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="/"><i class="fa fa-home"></i>&nbsp;Home</a></li>
                <li class="active">Dashboard</li>
            </ul>
            <div class="m-b-md"><h3 class="m-b-none">Workset</h3>
                <small>Tèo, mừng bạn trở lại.</small>
            </div>


            <section class="panel panel-default">
                <div class="right_col " role="main">
                    <!-- top tiles -->
                    <div class="row tile_count m-l-none m-r-none bg-light lter">
                        <div class="col-md-3 col-sm-6 col-xs-6  padder-v b-r b-light">
                            <span class=" nau-xam"><i class="fa fa-user"></i> Tổng khách hàng</span>
                            <div class="count">2500</div>
                            <span class=" nau-xam"><i class="green-special">4% </i> Từ cuối tuần trước</span>
                        </div>
                        <div class="col-md-3 col-sm-6 col-xs-6  padder-v b-r b-light">
                            <span class=" nau-xam"><i class="fa fa-male"></i> Khách hoạt động</span>
                            <div class="count  green-special">123.50</div>
                            <span class=" nau-xam"><i class="green-special"><i class="fa fa-sort-asc"></i>3% </i> Từ cuối tuần trước</span>
                        </div>
                        <div class="col-md-3 col-sm-6 col-xs-6  padder-v b-r b-light">
                            <span class=" nau-xam"><i class="fa fa-phone"></i> Tổng thuê bao</span>
                            <div class="count green">2,500</div>
                            <span class=" nau-xam"><i class="green-special"><i class="fa fa-sort-asc"></i>34% </i> Từ cuối tuần trước</span>
                        </div>
                        <div class="col-md-3 col-sm-6 col-xs-6  padder-v b-r b-light">
                            <span class=" nau-xam"><i class="fa fa-phone-square"></i> Thuê bao hiển thị</span>
                            <div class="count  green-special">4,567</div>
                            <span class=" nau-xam"><i class="red"><i class="fa fa-sort-desc"></i>12% </i> Từ cuối tuần trước</span>
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
                            <span class="h3 block m-t-xs"><strong>50</strong></span>
                            <small class="text-muted text-uc">Khách hàng mới</small>
                        </a>
                    </div>
                    <div class="col-sm-6 col-md-4 padder-v b-r b-light">
                                    <span
                                            class="fa-stack fa-2x pull-left m-r-sm"> <i
                                            class="fa fa-circle fa-stack-2x text-primary"></i> <i
                                            class="fa fa-phone-square fa-stack-1x text-white"></i>
                                    </span>
                        <a class="clear" href="#">
                            <span class="h3 block m-t-xs"><strong>476</strong></span>
                            <small class="text-muted text-uc">Thuê bao mới</small>
                        </a>
                    </div>
                    <div class="col-sm-6 col-md-4 padder-v b-r b-light">
                                   <span
                                           class="fa-stack fa-2x pull-left m-r-sm"> <i
                                           class="fa fa-circle fa-stack-2x text-success"></i> <i
                                           class="fa fa-phone fa-stack-1x text-white"></i>
                                    </span>
                        <a class="clear" href="#">
                            <span class="h3 block m-t-xs"><strong>140</strong></span>
                            <small class="text-muted text-uc">Thuê bao đăng lại</small>
                        </a>
                    </div>
                </div>

            </section>
            <div class="row">
                <div class="col-md-8">
                    <section class="panel panel-default">
                        <header class="panel-heading font-bold">Biểu đồ đăng mới thuê bao trong năm</header>
                        <div class="panel-body">
                            <div id="flot-1ine" style="height:350px"></div>
                        </div>
                        <footer class="panel-footer bg-white no-padder">
                            <div class="row text-center no-gutter">
                                <div class="col-xs-3 b-r b-light"><span
                                        class="h4 font-bold m-t block">50</span>
                                    <small class="text-muted m-b block">Hôm nay</small>
                                </div>
                                <div class="col-xs-3 b-r b-light"><span class="h4 font-bold m-t block">500</span>
                                    <small class="text-muted m-b block">Tuần này</small>
                                </div>
                                <div class="col-xs-3 b-r b-light"><span class="h4 font-bold m-t block">5000</span>
                                    <small class="text-muted m-b block">Tháng này</small>
                                </div>
                                <div class="col-xs-3"><span class="h4 font-bold m-t block">50000</span>
                                    <small class="text-muted m-b block">Năm</small>
                                </div>
                            </div>
                        </footer>
                    </section>
                </div>
                <div class="col-md-4">
                    <section class="panel panel-default">
                        <header class="panel-heading font-bold">
                            Biểu đồ tỷ lệ phương tiện
                        </header>
                        <div class="panel-body">
                            <div id="flot-pie1" style="height:350px">
                            </div>
                        </div>
                    </section>
                </div>
            </div>
            <div class="row">
                <section class="panel panel-default">
                    <header class="panel-heading font-bold">Biểu đồ đăng mới thuê bao trong năm</header>
                    <div class="panel-body">
                        <div id="flot-1ine1" style="height:350px"></div>
                    </div>
                    <footer class="panel-footer bg-white no-padder">
                        <div class="row text-center no-gutter">
                            <div class="col-xs-3 b-r b-light"><span
                                    class="h4 font-bold m-t block">50</span>
                                <small class="text-muted m-b block">Hôm nay</small>
                            </div>
                            <div class="col-xs-3 b-r b-light"><span class="h4 font-bold m-t block">500</span>
                                <small class="text-muted m-b block">Tuần này</small>
                            </div>
                            <div class="col-xs-3 b-r b-light"><span class="h4 font-bold m-t block">5000</span>
                                <small class="text-muted m-b block">Tháng này</small>
                            </div>
                            <div class="col-xs-3"><span class="h4 font-bold m-t block">50000</span>
                                <small class="text-muted m-b block">Năm</small>
                            </div>
                        </div>
                    </footer>
                </section>
            </div>

        </section>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
</section>

<!-- Bootstrap --> <!-- App -->
<script src="<%=request.getContextPath()%>/assets/note/js/charts/sparkline/jquery.sparkline.min.js" cache="false"></script>
<script src="<%=request.getContextPath()%>/assets/note/js/charts/easypiechart/jquery.easy-pie-chart.js" cache="false"></script>
<script src="<%=request.getContextPath()%>/assets/js/highcharts/highcharts.js" cache="false"></script>
<script src="<%=request.getContextPath()%>/assets/js/highcharts/highcharts-more.js" cache="false"></script>
<script src="<%=request.getContextPath()%>/assets/js/highcharts/exporting.js" cache="false"></script>
<script src="<%=request.getContextPath()%>/assets/note/js/charts/flot/jquery.flot.min.js" ></script>
<script src="<%=request.getContextPath()%>/assets/note/js/charts/flot/jquery.flot.tooltip.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/note/js/charts/flot/jquery.flot.resize.js"></script>
<script src="<%=request.getContextPath()%>/assets/note/js/charts/flot/jquery.flot.grow.js"></script>
<script src="<%=request.getContextPath()%>/assets/note/js/charts/flot/demo.js"></script>