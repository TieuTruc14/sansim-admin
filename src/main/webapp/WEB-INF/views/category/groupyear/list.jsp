<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="<%=request.getContextPath()%>/assets/project/category/groupyear/list.js"></script>
<script  type="text/javascript">
    $("#sansim-status").show();
    setTimeout(function() { $("#sansim-status").hide(); }, 5000);
</script>
<section id="content" ng-app="sansimso"  ng-controller="sansimCtrl">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Home</a></li>
                <li><a href="#">Danh mục</a></li>
                <li><a href="javascript:void(0)">Sim theo giá</a></li>
            </ul>
            <div class="m-b-md" id="sansim-status">
                <h3 class="m-b-none"  style="color: #009900">
                    <c:if test="${success.length()>0}">${success}</c:if>
                </h3>
                <h3 class="m-b-none"  style="color:red">
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
                                <div class="col-sm-8">
                                    <input type="text" ng-model="name" placeholder="Tên hiển thị" maxlength="50" class="input-sm form-control" />
                                </div>
                                <div class="col-sm-4"><a ng-click="search()" class="btn btn-info btn-sm">Tìm kiếm</a></div>
                            </div>
                            <div class="col-md-6">
                                <sec:authorize access="hasRole('ROLE_CATEGORY_GROUP_YEAR_EDIT')">
                                    <a class="btn btn-sm btn-info pull-right" style="margin-left:10px;" data-toggle="modal" data-target="#SapXepItem" ><i class="fa fa-retweet"></i> Sắp xếp</a>
                                </sec:authorize>
                                <sec:authorize access="hasRole('ROLE_CATEGORY_GROUP_YEAR_ADD')">
                                    <a class="btn btn-sm btn-primary pull-right"
                                       href="<%=request.getContextPath()%>/category/group-year/add"><i class="fa fa-plus"></i> Thêm mới</a>
                                </sec:authorize>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>
                    </form>
                </div>

                <div class="table-responsive table-overflow-x-fix">
                    <table id="tblUser" class="table table-striped table-bordered m-b-none text-sm">
                        <thead>
                        <tr>
                            <th class="box-shadow-inner small_col text-center">#</th>
                            <th class="box-shadow-inner small_col text-center">Giá trị năm</th>
                            <th class="box-shadow-inner small_col text-center">Tên hiển thị</th>
                            <th class="box-shadow-inner small_col text-center">Thứ tự</th>
                            <th class="box-shadow-inner small_col text-center">Mô tả</th>
                            <th class="box-shadow-inner small_col text-center">Trạng thái</th>
                            <sec:authorize access="hasRole('ROLE_CATEGORY_GROUP_YEAR_EDIT')">
                                <th class="box-shadow-inner small_col text-center">Hoạt động</th>
                            </sec:authorize>
                        </tr>
                        </thead>
                        <tbody>

                        <tr ng-repeat="item in page.items track by $index">
                            <td class="align-center">{{(page.pageNumber-1)*page.numberPerPage + $index+1}}</td>
                            <td class="align-center">{{item.year}}</td>
                            <td class="align-center">{{item.name}}</td>
                            <td class="align-center">{{item.orderNumber}}</td>
                            <td class="align-center">{{item.description}}</td>
                            <td class="align-center" ng-switch on="item.active">
                                <span ng-switch-when="true" >Đang hoạt động</span>
                                <span ng-switch-default >Đã khóa</span>
                            </td>
                            <sec:authorize access="hasAnyRole('ROLE_CATEGORY_GROUP_YEAR_EDIT','ROLE_CATEGORY_GROUP_YEAR_DELETE')">
                            <td class="align-center">
                                <div class="btn-group">
                                    <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i
                                            class="fa fa-random"></i></button>
                                    <ul class="dropdown-menu pull-right">
                                        <sec:authorize access="hasAnyRole('ROLE_CATEGORY_GROUP_YEAR_EDIT')">
                                            <li><a href="<%=request.getContextPath()%>/category/group-year/edit/{{item.id}}"><i class="fa fa-pencil-square-o"></i>Sửa</a></li>
                                        </sec:authorize>
                                        <sec:authorize access="hasAnyRole('ROLE_CATEGORY_GROUP_YEAR_DELETE')">
                                            <li><a href="javascript:void(0)" data-toggle="modal" data-target="#deleteItem" ng-click="deleteItem(item.id)"><i class="fa fa-pencil-square-o"></i>Xóa</a></li>
                                        </sec:authorize>
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
    <div class="modal fade"  id="SapXepItem"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
        <div class="modal-dialog" style="max-width: 500px;">
            <div class="modal-content"style="max-width: 500px;">
                <div class="modal-header" style="padding: 7px;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h5 class="modal-title" >Sắp xếp thứ tự</h5>
                </div>

                <div class="modal-body"  style="padding: 10px;">
                    <div class="form-group">
                        <ul class="list-group gutter list-group-lg list-group-sp sortable" id="nabar-list-group">
                            <c:forEach var="group" items="${listAll}">
                                <li class="list-group-item box-shadow">
                                <span class="pull-left media-xs">
                                    <i class="fa fa-sort icon-muted fa m-r-sm"></i>
                                  </span>
                                    <input type="hidden" value="${group.id}"/>
                                    <div class="clear" >
                                            ${group.name}
                                    </div>
                                </li>
                            </c:forEach>

                        </ul>
                    </div>

                </div>
                <div class="modal-footer" style="padding: 10px;" >
                    <button type="button" class="btn btn-default" data-dismiss="modal">Hủy bỏ</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal"   ng-click="genValues()">Cập nhật</button>
                </div>

            </div>
        </div>
    </div>
    <div class="modal fade" id="errorEdit" role="dialog">
        <div class="modal-dialog">
            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Error</h4>
                </div>
                <div class="modal-body">
                    <p>Có lỗi xảy ra. Hãy thử lại sau! </p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">OK</button>
                </div>
            </div>

        </div>
    </div>
    <div class="modal fade" id="showOke" role="dialog">
        <div class="modal-dialog">
            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Thông báo</h4>
                </div>
                <div class="modal-body">
                    <p>Thay đổi thành công! Reload lại trang để xem thay đổi. </p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" ng-click="reload()">OK</button>
                </div>
            </div>

        </div>
    </div>
    <div class="modal fade"  id="deleteItem"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
        <div class="modal-dialog" style="max-width: 500px;">
            <div class="modal-content"style="max-width: 500px;">
                <div class="modal-header" style="padding: 7px;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h5 class="modal-title" id="myModalLable">Xóa nhóm</h5>
                </div>
                <form id="filter" method="POST"  action="<%=request.getContextPath()%>/category/group-year/delete" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                    <div class="modal-body"  style="padding: 10px;">
                        <div class="form-group">
                            <label class="control-label">Bạn xác nhận xóa nhóm này?</label>
                            <input name="id"  type="hidden" class="form-control info_id" value="{{itemDeleteId}}"/>
                        </div>

                    </div>
                    <div class="modal-footer" style="padding: 10px;" >
                        <button type="button" class="btn btn-default" data-dismiss="modal">Hủy bỏ</button>
                        <button type="submit" class="btn btn-danger">Xóa</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
<script src="<%=request.getContextPath()%>/assets/note/js/sortable/jquery.sortable.js"></script>