<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--<script  type="text/javascript">--%>
<%--$("#sansim-status").show();--%>
<%--setTimeout(function() { $("#sansim-status").hide(); }, 3000);--%>
<%--</script>--%>
<script src="<%=request.getContextPath()%>/assets/project/history/list.js"></script>
<section id="content" ng-app="sansimso"  ng-controller="sansimCtrl">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Home</a></li>
                <li><a href="#">System</a></li>
                <li><a href="javascript:void(0)">Lịch sử người dùng</a></li>
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

                        <div class="form-group">
                            <div class="col-md-6">
                                <div class="col-sm-8">
                                    <input name="username"  ng-model="username" my-enter="search()"  placeholder="Username" maxlength="50"  class="input-sm form-control"/>
                                </div>
                                <div class="col-sm-4"><a  ng-click="search()" class="btn btn-info btn-sm">Tìm kiếm</a></div>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>

                    </form>
                </div>

                <div class="table-responsive table-overflow-x-fix">
                    <table id="tbl" class="table table-striped table-bordered m-b-none text-sm">
                        <thead>
                        <tr>
                            <th class="box-shadow-inner small_col text-center">#</th>
                            <th class="box-shadow-inner small_col text-center">username</th>
                            <th class="box-shadow-inner small_col text-center">Module</th>
                            <th class="box-shadow-inner small_col text-center">Hành động</th>
                            <th class="box-shadow-inner small_col text-center">Ip</th>
                            <th class="box-shadow-inner small_col text-center">Thời gian</th>
                            <sec:authorize access="hasRole('ROLE_SYSTEM_LOG_VIEW')">
                            <th class="box-shadow-inner small_col text-center">Hoạt động</th>
                            </sec:authorize>
                        </tr>
                        </thead>
                        <tbody>

                        <tr ng-repeat="item in page.items track by $index">
                            <td class="align-center">{{(page.pageNumber-1)*page.numberPerPage + $index+1}}</td>
                            <td class="align-center">{{item[6]}}</td>
                            <td class="align-center">{{item[2]}}</td>
                            <td class="align-center"> {{item[4]}}</td>
                            <td class="align-center">{{item[3]}}</td>
                            <td class="align-center">{{item[5]|date:'dd/MM/yyyy HH:mm:ss'}}</td>
                            <sec:authorize access="hasRole('ROLE_SYSTEM_LOG_VIEW')">
                            <td class="align-center">
                                <div class="btn-group">
                                    <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i
                                            class="fa fa-random"></i></button>
                                    <ul class="dropdown-menu pull-right">
                                        <li><a href="<%=request.getContextPath()%>/system/history/{{item[1]}}"><i class="fa fa-pencil-square-o"></i>Xem log người dùng</a></li>
                                    </ul>
                                </div>
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

    <div class="modal fade"  id="Message"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
        <div class="modal-dialog" style="max-width: 500px;">
            <div class="modal-content"style="max-width: 500px;">
                <div class="modal-header" style="padding: 7px;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h5 class="modal-title">Lịch sử người dùng</h5>
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
