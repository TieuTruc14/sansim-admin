<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<%--<script  type="text/javascript">--%>
<%--$("#sansim-status").show();--%>
<%--setTimeout(function() { $("#sansim-status").hide(); }, 3000);--%>
<%--</script>--%>
<script src="<%=request.getContextPath()%>/assets/project/msisdn/list.js"></script>
<script src="<%=request.getContextPath()%>/assets/project/customer/complete.customer.js"></script>
<section id="content" ng-app="sansimso"  ng-controller="sansimCtrl">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Home</a></li>
                <li><a href="#">Quản lý đăng số</a></li>
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
                        <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>
                        <div class="form-group">
                            <div class="col-md-6">
                                <label class="col-sm-4 control-label">Người bán(username)</label>
                                <div class="col-sm-8">
                                    <autocomplete ng-model="username" maxlength="50" data="names" on-type="whenChange" on-select="whenSelect"  my-enter="search()"  />
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label class="col-sm-4 control-label">Nhà mạng</label>
                                <div class="col-sm-8">
                                    <select data-placeholder="Nhà mạng" name="telco" class="select2-option" style="width:100%;height:30px;" ng-model="telco">
                                        <option value="">Tất cả</option>
                                        <option value="1">Viettel</option>
                                        <option value="2">Vinaphone</option>
                                        <option value="3">Mobifone</option>
                                        <option value="4">Gmobile</option>
                                        <option value="5">VietNameMobile</option>
                                        <option value="0">Khác</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>
                        <div class="form-group">
                            <div class="col-md-6">
                                <label class="col-sm-4 control-label">Số thuê bao</label>
                                <div class="col-sm-8">
                                    <input name="msisdn" placeholder="" maxlength="20" ng-model="msisdn" my-enter="search()" class="input-sm form-control" onkeypress="return restrictCharacters(this, event, digitsOnly);"/>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label class="col-sm-4 control-label">Giá bán</label>
                                <div class="col-sm-8">
                                    <input name="price" placeholder="" ng-model="price" maxlength="20" my-enter="search()" class="input-sm form-control col-md-6" onkeypress="return restrictCharacters(this, event, digitsOnly);"/>
                                </div>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>
                        <div class="form-group ">
                            <div class="col-md-6">
                                <label class="col-sm-4 control-label">Thời gian từ ngày</label>
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
                        <div class="form-group">
                            <div class="col-md-6">
                                <label class="col-sm-4 control-label">Trạng thái xác thực</label>
                                <div class="col-sm-8">
                                    <select name="confirmStatus" class="select2-option" id="active-select" style="min-width:200px" ng-model="confirmStatus" ng-change="changeConfirm()">
                                        <option value="">Tất cả</option>
                                        <option value="1">Đã xác thực</option>
                                        <option value="0">Chưa xác thực</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-6" ng-show="showConfirm">
                                <label class="col-sm-4 control-label">Số sắp hết hạn xác thực</label>
                                <div class="col-sm-8">
                                    <select name="confirmExpired" class="select2-option" id="active-select1" style="min-width:200px" ng-model="confirmExpired">
                                        <option value="">Tất cả</option>
                                        <option value="1">Trong 1 ngày tới</option>
                                        <option value="3">Trong 3 ngày tới</option>
                                        <option value="5">Trong 5 ngày tới</option>
                                        <option value="7">Trong 7 ngày tới</option>
                                        <option value="10">Trong 10 ngày tới</option>
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
                    <table id="tbl" class="table table-striped table-bordered m-b-none text-sm">
                        <thead>
                        <tr>
                            <th class="box-shadow-inner small_col text-center">#</th>
                            <th class="box-shadow-inner small_col text-center">Người bán</th>
                            <th class="box-shadow-inner small_col text-center">Số thuê bao</th>
                            <th class="box-shadow-inner small_col text-center">Giá bán</th>
                            <th class="box-shadow-inner small_col text-center">Nhà mạng</th>
                            <th class="box-shadow-inner small_col text-center">Thời gian đăng</th>
                            <th class="box-shadow-inner small_col text-center">Trạng thái xác thực</th>
                            <th class="box-shadow-inner small_col text-center">Hết hạn xác thực</th>
                            <th class="box-shadow-inner small_col text-center">Trạng thái</th>
                            <th class="box-shadow-inner small_col text-center">Hoạt động</th>
                        </tr>
                        </thead>
                        <tbody>

                        <tr ng-repeat="item in page.items track by $index">

                            <td class="align-center">{{(page.pageNumber-1)*page.numberPerPage + $index+1}}</td>
                            <td class="align-center">{{item[1]}}</td>
                            <td class="align-center">{{item[2]}}</td>
                            <td class="align-center" format="number"> {{item[3]|currency:"":0}}</td>
                            <td class="align-center" ng-switch on="item[8]">
                                <span ng-switch-when="1"><img src="<%=request.getContextPath()%>/assets/images/viettel.jpg" alt="viettel"></span>
                                <span ng-switch-when="2"><img src="<%=request.getContextPath()%>/assets/images/vinaphone.jpg" alt="mobifone"></span>
                                <span ng-switch-when="3"><img src="<%=request.getContextPath()%>/assets/images/mobifone.jpg" alt="vinaphone"></span>
                                <span ng-switch-when="4"><img src="<%=request.getContextPath()%>/assets/images/gmobile.jpg" alt="gmobile"></span>
                                <span ng-switch-when="5"><img src="<%=request.getContextPath()%>/assets/images/vietnamobile.jpg" alt="vietnamobile"></span>
                                <span ng-switch-default>Mạng khác</span>
                            </td>
                            <td class="align-center">{{item[4]|date:'dd/MM/yyyy HH:mm:ss'}}</td>
                            <td class="align-center" ng-switch on="item[5]">
                                <span ng-switch-when="true">Đã xác thực</span>
                                <span ng-switch-default>Chưa xác thực</span>
                            </td>
                            <td class="align-center">{{item[6]|date:'dd/MM/yyyy HH:mm:ss'}}</td>
                            <td class="align-center" ng-switch on="item[7]">
                                <span ng-switch-when="1">Hiển thị</span>
                                <span ng-switch-default >Đã khóa</span>
                            </td>
                            <td class="align-center"><a href="<%=request.getContextPath()%>/administer/msisdn/detail/{{item[0]}}">Xem</a></td>

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

    <div class="modal fade"  id="Message"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
        <div class="modal-dialog" style="max-width: 500px;">
            <div class="modal-content"style="max-width: 500px;">
                <div class="modal-header" style="padding: 7px;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h5 class="modal-title">Quản lý đăng số</h5>
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

