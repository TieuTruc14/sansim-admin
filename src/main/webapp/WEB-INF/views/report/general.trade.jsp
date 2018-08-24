<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<script src="<%=request.getContextPath()%>/assets/project/customer/complete.customer.js"></script>
<script src="<%=request.getContextPath()%>/assets/project/report/general.trade.js"></script>
<section id="content" ng-app="sansimso"  ng-controller="sansimCtrl">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Home</a></li>
                <li><a href="#">Báo cáo</a></li>
                <li><a href="javascript:void(0)">Tổng hợp doanh thu sms</a></li>
            </ul>
            <div class="m-b-md">
                <h3 class="m-b-none" id="sansim-status" style="color: #009900">

                </h3>
            </div>
            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> Dữ liệu

                </header>
                <div class="text-sm wrapper bg-light lt">
                    <form cssClass="form-inline padder" action="list" role="form" theme="simple">
                        <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>
                        <div class="form-group">
                            <div class="col-md-6">
                                <label class="col-sm-4 control-label">Loại giao dịch</label>
                                <div class="col-sm-8">
                                    <select name="type" class="select2-option" id="active-select" style="min-width:200px" ng-model="type">
                                        <option value="">Tất cả</option>
                                        <option value="1">Đăng ký gói</option>
                                        <option value="2">Gia hạn gói</option>
                                        <option value="3">Xác thực số</option>
                                        <option value="4">Gia hạn xác thực số</option>
                                        <option value="5">Hệ thống hủy</option>
                                        <option value="6">Người dùng hủy</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label class="col-sm-4 control-label">Người bán(username)</label>
                                <div class="col-sm-8">
                                    <autocomplete ng-model="username" maxlength="50" data="names" on-type="whenChange" on-select="whenSelect" />
                                </div>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>
                        <div class="form-group ">
                            <div class="col-md-6">
                                <label class="col-sm-4 control-label">Từ ngày<code>*</code></label>
                                <div class="col-sm-8">
                                    <input class="input-sm input-s datepicker-input form-control" size="10" type="text"  data-date-format="dd/mm/yyyy"  my-enter="search()" id="from"  onkeypress="return restrictCharacters(this, event, digitsAndSlash);" />
                                    <a style="color:red">{{errorDateFrom}}</a>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label class="col-sm-4 control-label">Đến ngày</label>
                                <div class="col-sm-8">
                                    <input class="input-sm input-s datepicker-input form-control" size="10" type="text"  data-date-format="dd/mm/yyyy"  my-enter="search()" id="to"  onkeypress="return restrictCharacters(this, event, digitsAndSlash);" />
                                    <a style="color:red">{{errorDateTo}}</a>
                                </div>
                            </div>
                        </div>

                        <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>
                        <div class="form-group text-center">
                            <a ng-click="search()" class="btn btn-info btn-sm ">Thống kê</a>
                            <button type="reset" class="btn btn-default btn-sm" ng-click="clear()">Xóa điều kiện</button>
                            <a ng-if="page.items.length>0" class="btn btn-primary btn-sm" ng-click="download()" style="float:right"><i class="fa fa-download"></i>Tải file excel</a>
                        </div>

                    </form>
                </div>

                <div class="table-responsive table-overflow-x-fix">
                    <table id="tbl" class="table table-striped table-bordered m-b-none text-sm">
                        <thead>
                        <tr>
                            <th class="box-shadow-inner small_col text-center">#</th>
                            <th class="box-shadow-inner small_col text-center">Loại giao dịch</th>
                            <th class="box-shadow-inner small_col text-center">Username người bán</th>
                            <th class="box-shadow-inner small_col text-center">Tên người bán</th>
                            <th class="box-shadow-inner small_col text-center">Số lượng</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr ng-repeat="item in page.items track by $index">
                            <td class="align-center">{{(page.pageNumber-1)*page.numberPerPage + $index+1}}</td>
                            <td class="align-center" ng-switch on="item[0]">
                                <span ng-switch-when="1">Đăng ký gói</span>
                                <span ng-switch-when="2">Gia hạn gói</span>
                                <span ng-switch-when="3">Xác thực số</span>
                                <span ng-switch-when="4">Gia hạn xác thực</span>
                                <span ng-switch-when="5">Hệ thống hủy</span>
                                <span ng-switch-when="6">Người dùng hủy</span>
                                <span ng-switch-default >Chưa xác định</span>
                            </td>
                            <td class="align-center">{{item[1]}}</td>
                            <td class="align-center">{{item[2]}}</td>
                            <td class="align-center">{{item[4]}}</td>
                        </tr>
                        <tr ng-if="page.items.length==0">
                            <td class="align-center" style="border: 0px solid #ddd!important;"></td>
                            <td class="align-center" style="border: 0px solid #ddd!important;"><h3>{{noData}}</h3></td>
                            <td class="align-center" style="border: 0px solid #ddd!important;"></td>
                            <td class="align-center" style="border: 0px solid #ddd!important;"></td>
                            <td class="align-center" style="border: 0px solid #ddd!important;"></td>
                        </tr>

                        </tbody>
                    </table>
                </div>
                <footer class="panel-footer">
                    <div class="row">
                        <div class="col-sm-12 text-right text-center-xs">
                            <div class="col-sm-6 text-left">
                                <span>Tổng số <code>{{page.rowCount|currency:"":0}}</code> dữ liệu</span>
                            </div>
                            <div class="col-sm-6">
                                <ul class="pagination pagination-sm m-t-none m-b-none">
                                    <li ng-if="page.pageNumber>1"><a href="javascript:void(0)"  ng-click="loadPage(1)">«</a></li>
                                    <li ng-repeat="item in page.pageList">
                                        <a href="javascript:void(0)" ng-if="item==page.pageNumber" style="color:mediumvioletred;"> {{item}}</a>
                                        <a href="javascript:void(0)" ng-if="item!=page.pageNumber" ng-click="loadPage(item)"> {{item}}</a>
                                    </li>
                                    <li ng-if="page.pageNumber<page.pageCount" ><a href="javascript:void(0)" ng-click="loadPage(page.pageCount)">»</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </footer>
            </section>
        </section>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>

</section>

