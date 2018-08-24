<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="<%=request.getContextPath()%>/assets/project/customer/complete.customer.js"></script>
<script src="<%=request.getContextPath()%>/assets/project/momt/list.js"></script>
<section id="content" ng-app="sansimso"  ng-controller="sansimCtrl">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Home</a></li>
                <li><a href="#">Manager</a></li>
                <li><a href="javascript:void(0)">Lịch sử MO/MT</a></li>
            </ul>
            <div class="m-b-md">
                <h3 class="m-b-none" id="sansim-status" style="color: #009900">
                    <c:if test="${success.length()>0}">${success}</c:if>
                    <c:if test="${messageError.length()>0}">${messageError}</c:if>
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
                                <label class="col-sm-4 control-label">Số thuê bao</label>
                                <div class="col-sm-8">
                                    <input name="msisdn" placeholder="" maxlength="20" ng-model="msisdn"  my-enter="search()" class="input-sm form-control" onkeypress="return restrictCharacters(this, event, digitsOnly);"/>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label class="col-sm-4 control-label">Người bán</label>
                                <div class="col-sm-8">
                                    <autocomplete ng-model="username" maxlength="50" my-enter="search()" data="names" on-type="whenChange" on-select="whenSelect" />
                                    <%--<input name="username" placeholder="" ng-model="username" maxlength="50" my-enter="search()" class="input-sm form-control col-md-6" />--%>
                                </div>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>
                        <div class="form-group ">
                            <div class="col-md-6">
                                <label class="col-sm-4 control-label">Thời gian từ ngày</label>
                                <div class="col-sm-8">
                                    <input class="input-sm input-s datepicker-input form-control" size="10" type="text"  data-date-format="dd/mm/yyyy" my-enter="search()" id="from"  onkeypress="return restrictCharacters(this, event, digitsAndSlash);" />
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
                            <a ng-click="search()" class="btn btn-info btn-sm">Tìm kiếm</a>
                            <button type="reset" class="btn btn-default btn-sm" ng-click="clear()">Xóa điều kiện</button>
                        </div>

                    </form>
                </div>

                <div class="table-responsive table-overflow-x-fix">
                    <table id="tbl" class="table table-striped table-bordered m-b-none text-sm">
                        <thead>
                        <tr>
                            <th class="box-shadow-inner small_col text-center">#</th>
                            <th class="box-shadow-inner small_col text-center">Ngày</th>
                            <th class="box-shadow-inner small_col text-center">Số thuê bao</th>
                            <th class="box-shadow-inner small_col text-center">Đầu số</th>
                            <th class="box-shadow-inner small_col text-center">Tên tài khoản</th>
                            <th class="box-shadow-inner small_col text-center">MO</th>
                            <th class="box-shadow-inner small_col text-center">MT</th>
                            <sec:authorize access="hasRole('ROLE_MANAGER_HISTORY_MO_MT_RESEND_MT')">
                            <th class="box-shadow-inner small_col text-center">Hoạt động</th>
                            </sec:authorize>
                        </tr>
                        </thead>
                        <tbody>

                        <tr ng-repeat="item in page.items track by $index">

                            <td class="align-center">{{(page.pageNumber-1)*page.numberPerPage + $index+1}}</td>
                            <td class="align-center">{{item[1]|date:'dd/MM/yyyy HH:mm:ss'}}</td>
                            <td class="align-center">{{item[2]}}</td>
                            <td class="align-center"> {{item[3]}}</td>
                            <td class="align-center">{{item[4]}}</td>
                            <td class="align-center">{{item[5]}}</td>
                            <td class="align-center">{{item[7]}}</td>
                            <sec:authorize access="hasRole('ROLE_MANAGER_HISTORY_MO_MT_RESEND_MT')">
                            <td class="align-center"><a style="color:#337ab7;" href="javascript:void(0)" ng-click="sendMT(item)">Gửi lại MT</a></td>
                            </sec:authorize>
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
    <div class="modal fade"  id="sendMT"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
        <div class="modal-dialog" style="max-width: 500px;">
            <div class="modal-content"style="max-width: 500px;">
                <div class="modal-header" style="padding: 7px;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h5 class="modal-title" >Quản lý lịch sử MO/MT</h5>
                </div>

                <div class="modal-body"  style="padding: 10px;">
                    <div class="form-group">
                        <label class="control-label">Bạn xác nhận gửi lại MT <{{MT.mtInfo}}> cho thuê bao <{{MT.mtNumber}}> ?</label>
                    </div>

                </div>
                <div class="modal-footer" style="padding: 10px;" >
                    <button type="button" class="btn btn-default" data-dismiss="modal">Hủy bỏ</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal"  ng-click="sendMTOK()">Gửi MT</button>
                </div>

            </div>
        </div>
    </div>
    <div class="modal fade"  id="Message"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
        <div class="modal-dialog" style="max-width: 500px;">
            <div class="modal-content"style="max-width: 500px;">
                <div class="modal-header" style="padding: 7px;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h5 class="modal-title">Quản lý MO/MT</h5>
                </div>

                <div class="modal-body"  style="padding: 10px;">
                    <div class="form-group">
                        <label class="control-label">{{messageStatus}}</label>
                    </div>

                </div>
                <div class="modal-footer" style="padding: 10px;" >
                    <button type="button" class="btn btn-default" data-dismiss="modal">OK</button>
                </div>

            </div>
        </div>
    </div>
</section>

<style>
    .input-sm{
        margin-bottom:10px;
    }
</style>
