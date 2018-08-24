<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script src="<%=request.getContextPath()%>/assets/project/content/article/list.js"></script>
<script  type="text/javascript">
    $("#sansim-status").show();
    setTimeout(function() { $("#sansim-status").hide(); }, 5000);
</script>
<section id="content" ng-app="sansimso"  ng-controller="sansimCtrl">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Home</a></li>
                <li><a href="#">Quản lý nội dung</a></li>
                <li><a href="#">Quản lý bài viết</a></li>
                <li><a href="javascript:void(0)">List</a></li>
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
                        <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>
                        <div class="form-group">
                            <div class="col-md-6">
                                <label class="col-sm-4 control-label">Tiêu đề</label>
                                <div class="col-sm-8">
                                    <input ng-model="name" name="name" placeholder="" maxlength="50" my-enter="search()"  class="input-sm form-control"/>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label class="col-sm-4 control-label">Chuyên mục</label>
                                <div class="col-sm-8">
                                    <select name="categoryId" class="select2-option" id="active-select" style="min-width:200px" ng-model="categoryId">
                                        <option value="">Tất cả</option>
                                        <option  ng-repeat="item in listCate" value="{{item.id}}">{{item.name}}</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>
                        <div class="form-group text-center">
                            <a ng-click="search()" class="btn btn-info btn-sm ">Tìm kiếm</a>
                            <sec:authorize access="hasRole('ROLE_CONTENT_ARTICLE_VIEW')">
                            <a class="btn btn-sm btn-primary pull-right"
                               href="<%=request.getContextPath()%>/content/article/add"><i class="fa fa-plus"></i> Thêm mới</a>
                            </sec:authorize>
                        </div>
                    </form>
                </div>

                <div class="table-responsive table-overflow-x-fix">
                    <table id="tbl" class="table table-striped table-bordered m-b-none text-sm">
                        <thead>
                        <tr>
                            <th class="box-shadow-inner small_col text-center">#</th>
                            <th class="box-shadow-inner small_col text-center">Tiêu đề</th>
                            <th class="box-shadow-inner small_col text-center">Ngày đăng</th>
                            <th class="box-shadow-inner small_col text-center">Tình trạng</th>
                            <th class="box-shadow-inner small_col text-center">Chuyên mục</th>
                            <sec:authorize access="hasAnyRole('ROLE_CONTENT_CATEGORY_EDIT','ROLE_CONTENT_CATEGORY_DELETE')">
                            <th class="box-shadow-inner small_col text-center">Hoạt động</th>
                            </sec:authorize>
                        </tr>
                        </thead>
                        <tbody>
                        <tr ng-repeat="item in page.items track by $index">
                            <td class="align-center">{{(page.pageNumber-1)*page.numberPerPage + $index+1}}</td>
                            <td class="align-center">{{item[1]}}</td>
                            <td class="align-center">{{item[2] | date:'dd/MM/yyyy HH:mm:ss'}}</td>
                            <td class="align-center" ng-switch on="item[3]">
                                <span ng-switch-when="true">Hiển thị</span>
                                <span ng-switch-default>Không hiển thị</span>
                            </td>
                            <td class="align-center">{{item[4]}}</td>
                            <sec:authorize access="hasAnyRole('ROLE_CONTENT_CATEGORY_EDIT','ROLE_CONTENT_CATEGORY_DELETE')">
                            <td class="align-center">
                                <div class="btn-group">
                                    <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i
                                            class="fa fa-random"></i></button>
                                    <ul class="dropdown-menu pull-right">
                                        <sec:authorize access="hasAnyRole('ROLE_CONTENT_CATEGORY_EDIT')">
                                        <li><a href="<%=request.getContextPath()%>/content/article/edit/{{item[0]}}"><i class="fa fa-pencil-square-o"></i>Sửa</a></li>
                                        </sec:authorize>
                                        <sec:authorize access="hasAnyRole('ROLE_CONTENT_CATEGORY_DELETE')">
                                        <li><a href="javascript:void(0)" data-toggle="modal" data-target="#deleteItem" ng-click="deleteItem(item[0])"><i class="fa fa-pencil-square-o"></i>Xóa</a></li>
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
                                <span>Tổng số <code>{{page.rowCount}}</code> dữ liệu</span>
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
<sec:authorize access="hasAnyRole('ROLE_CONTENT_CATEGORY_DELETE')">
    <div class="modal fade"  id="deleteItem"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
        <div class="modal-dialog" style="max-width: 500px;">
            <div class="modal-content"style="max-width: 500px;">
                <div class="modal-header" style="padding: 7px;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h5 class="modal-title" id="myModalLable">Xóa bài viết</h5>
                </div>
                <form id="filter" method="POST"  action="<%=request.getContextPath()%>/content/article/delete" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                    <div class="modal-body"  style="padding: 10px;">
                        <div class="form-group">
                            <label class="control-label">Bạn xác nhận xóa bài viết này?</label>
                            <input name="id"  type="hidden" class="form-control" value="{{itemDeleteId}}"/>
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
</sec:authorize>
</section>

