<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--<script  type="text/javascript">--%>
<%--$("#sansim-status").show();--%>
<%--setTimeout(function() { $("#sansim-status").hide(); }, 3000);--%>
<%--</script>--%>
<script src="<%=request.getContextPath()%>/assets/project/customer/list.js"></script>
<script src="<%=request.getContextPath()%>/assets/project/customer/complete.customer.js"></script>
<section id="content" ng-app="sansimso"  ng-controller="sansimCtrl">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Home</a></li>
                <li><a href="#">Khách hàng</a></li>
                <li><a href="javascript:void(0)">List</a></li>
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

                        <div class="form-group ">
                            <div class="col-md-6">
                                <label class="col-sm-3 control-label">Số điện thoại</label>
                                <div class="col-sm-9">
                                    <input type="text" ng-model="msisdn" name="msisdn" placeholder="" maxlength="50" class="input-sm form-control" onkeypress="return restrictCharacters(this, event, digitsOnly);"/>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label class="col-sm-3 control-label">Tên đăng nhập</label>
                                <div class="col-sm-9">
                                    <autocomplete ng-model="username" maxlength="50" my-enter="search()" data="names" on-type="whenChange" on-select="whenSelect" />
                                </div>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>
                        <div class="form-group">
                            <div class="col-md-6">
                                <label class="col-sm-3 control-label">Tên hiển thị</label>
                                <div class="col-sm-9">
                                    <input name="fullName" placeholder="" maxlength="20" ng-model="fullName" my-enter="search()" class="input-sm form-control" />
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label class="col-sm-3 control-label">Gói sử dụng</label>
                                <div class="col-sm-9">
                                    <input name="packageCode" placeholder="" ng-model="packageCode" maxlength="20" my-enter="search()" class="input-sm form-control col-md-6" />
                                </div>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>
                        <div class="form-group">
                            <div class="col-md-6">
                                <label class="col-sm-3 control-label">Trạng thái</label>
                                <div class="col-sm-9">
                                    <select name="active" class="select2-option" id="active-select" style="min-width:200px" ng-model="active">
                                        <option value="">Tất cả</option>
                                        <option value="1">Đã kích hoạt</option>
                                        <option value="0">Chưa kích hoạt</option>
                                        <option value="2">Đang bị khóa</option>
                                    </select>
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
                    <table id="tblUser" class="table table-striped table-bordered m-b-none text-sm">
                        <thead>
                        <tr>
                            <th class="box-shadow-inner small_col text-center">#</th>
                            <th class="box-shadow-inner small_col text-center">Ngày đăng ký</th>
                            <th class="box-shadow-inner small_col text-center">Số điện thoại</th>
                            <th class="box-shadow-inner small_col text-center">Tên đăng nhập</th>
                            <th class="box-shadow-inner small_col text-center">Tên hiển thị</th>
                            <th class="box-shadow-inner small_col text-center">Gói cước</th>
                            <th class="box-shadow-inner small_col text-center">Ngày hết hạn gói cước</th>
                            <th class="box-shadow-inner small_col text-center">Số lượng đã đăng</th>
                            <th class="box-shadow-inner small_col text-center">Mô tả</th>
                            <th class="box-shadow-inner small_col text-center">Địa chỉ</th>
                            <th class="box-shadow-inner small_col text-center">Trạng thái</th>
                            <sec:authorize access="hasRole('ROLE_MANAGER_CUSTOMER_EDIT')">
                            <th class="box-shadow-inner small_col text-center">Hoạt động</th>
                            </sec:authorize>
                        </tr>
                        </thead>
                        <tbody>

                            <tr ng-repeat="item in page.items track by $index">
                                <td class="align-center">{{(page.pageNumber-1)*page.numberPerPage + $index+1}}</td>
                                <td class="align-center">{{item[1]|date:'dd/MM/yyyy HH:mm:ss'}}</td>
                                <td class="align-center">{{item[2]}}</td>
                                <td class="align-center">{{item[3]}}</td>
                                <td class="align-center">{{item[4]}}</td>
                                <td class="align-center">{{item[5]}}</td>
                                <td class="align-center">{{item[6]|date:'dd/MM/yyyy HH:mm:ss'}}</td>
                                <td class="align-center">{{item[7]|currency:"":0}}</td>
                                <td class="align-center">{{item[8]}}</td>
                                <td class="align-center">{{item[9]}}</td>
                                <td class="align-center" ng-switch on="item[10]">
                                    <span ng-switch-when="0">Chưa kích hoạt</span>
                                    <span ng-switch-when="1">Đã kích hoạt</span>
                                    <span ng-switch-when="2">Đang bị khóa</span>
                                    <span ng-switch-default>Chưa xác định</span>
                                </td>
                                <sec:authorize access="hasRole('ROLE_MANAGER_CUSTOMER_EDIT')">
                                <td class="align-center" >
                                    <div class="btn-group">
                                        <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i
                                                class="fa fa-random"></i></button>
                                        <ul class="dropdown-menu pull-right" ng-switch on="item[10]">
                                            <li ng-switch-when="1"><a  href="javascript:void(0)" ng-click="lockItem(item)"><i class="fa fa-pencil-square-o"></i>Khóa</a></li>
                                            <li ng-switch-default ><a  href="javascript:void(0)" ng-click="unlockItem(item)"><i class="fa fa-pencil-square-o"></i>Kích hoạt</a></li>
                                            <li><a href="javascript:void(0)" ng-click="dialogGanGoiCuoc(item)"><i class="fa fa-pencil-square-o"></i>Gán gói cước</a></li>
                                        </ul>
                                    </div>
                                    <%--<a ng-switch-when="1" href="javascript:void(0)" ng-click="lockItem(item)"><i class="fa fa-pencil-square-o"></i>Khóa</a>--%>
                                    <%--<a ng-switch-default href="javascript:void(0)" ng-click="unlockItem(item)"><i class="fa fa-pencil-square-o"></i>Kích hoạt</a>--%>
                                </td>
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
    <sec:authorize access="hasRole('ROLE_MANAGER_CUSTOMER_EDIT')">
    <div class="modal fade"  id="lockItem"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
        <div class="modal-dialog" style="max-width: 500px;">
            <div class="modal-content"style="max-width: 500px;">
                <div class="modal-header" style="padding: 7px;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h5 class="modal-title" >Quản lý thông tin người bán</h5>
                </div>

                <div class="modal-body"  style="padding: 10px;">
                    <div class="form-group">
                        <label class="control-label">Bạn xác nhận khóa người bán <{{itemLock.username}}> ?</label>
                    </div>

                </div>
                <div class="modal-footer" style="padding: 10px;" >
                    <button type="button" class="btn btn-default" data-dismiss="modal">Hủy bỏ</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal"  ng-click="lockClick()">Khóa</button>
                </div>

            </div>
        </div>
    </div>
    <div class="modal fade"  id="unlockItem"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
        <div class="modal-dialog" style="max-width: 500px;">
            <div class="modal-content"style="max-width: 500px;">
                <div class="modal-header" style="padding: 7px;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h5 class="modal-title">Quản lý thông tin người bán</h5>
                </div>

                <div class="modal-body"  style="padding: 10px;">
                    <div class="form-group">
                        <label class="control-label">Bạn xác nhận kích hoạt người bán <{{itemLock.username}}> ?</label>
                    </div>

                </div>
                <div class="modal-footer" style="padding: 10px;" >
                    <button type="button" class="btn btn-default" data-dismiss="modal">Hủy bỏ</button>
                    <button type="button"  class="btn btn-success" data-dismiss="modal" ng-click="unlockClick()">Kích hoạt</button>
                </div>

            </div>
        </div>
    </div>
    </sec:authorize>


    <div class="modal fade"  id="Message"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
        <div class="modal-dialog" style="max-width: 500px;">
            <div class="modal-content"style="max-width: 500px;">
                <div class="modal-header" style="padding: 7px;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h5 class="modal-title">Quản lý thông tin người bán</h5>
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

    <%--gan goi cuoc--%>
    <style>
        .datepicker{z-index:9999;}
    </style>
    <div class="modal fade"  id="ganGoiCuoc"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
        <div class="modal-dialog" style="max-width: 900px;">
            <div class="modal-content"style="max-width: 900px;">
                <div class="modal-header" style="padding: 7px;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h5 class="modal-title" >Bổ sung gói cước người bán</h5>
                </div>

                <div class="modal-body"  style="padding: 10px;min-height:120px;">
                    <div class="form-group">
                            <label class="col-sm-3 control-label">Trạng thái</label>
                            <div class="col-sm-9">
                                <select class="select2-option" style="min-width:200px" ng-model="packageId" id="packageSelect">
                                    <option value="">Chọn gói</option>
                                    <option  ng-repeat="item in listPackage" value="{{item.id}}">{{item.packageCode}}</option>
                                </select>
                                <a style="color:red">{{errorPackageId}}</a>
                            </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>
                    <div class="form-group">
                            <label class="col-sm-3 control-label">Ngày hết hạn</label>
                            <div class="col-sm-9">
                                <input class="input-sm input-s datepicker-input form-control" size="10" type="text"  data-date-format="dd/mm/yyyy" id="dateExpired" onkeypress="return restrictCharacters(this, event, digitsAndSlash);" />
                                <a style="color:red">{{errorDateExpired}}</a>
                            </div>
                    </div>
                </div>
                <div class="modal-footer" style="padding: 10px;" >
                    <button type="button" class="btn btn-default" data-dismiss="modal">Hủy bỏ</button>
                    <button type="button" class="btn btn-info"  ng-click="setPackage()">Thêm</button>
                </div>

            </div>
        </div>
    </div>
</section>
