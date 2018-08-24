<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script>
    var msisdnId=${item.id};
    var confirmStatus=${item.confirmStatus};

</script>
<script src="<%=request.getContextPath()%>/assets/project/msisdn/detail.js"></script>
<section id="content" ng-app="sansimso"  ng-controller="sansimCtrl">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Home</a></li>
                <li><a href="#">Manager</a></li>
                <li><a href="#">Quản lý đăng số</a></li>
                <li><a href="javascript:void(0)">Chi tiết thuê bao</a></li>
            </ul>
            <div class="m-b-md">
                <h3 class="m-b-none">Chi tiết thuê bao ${item.msisdn}</h3>
            </div>

            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i>Thông tin thuê bao</header>
                <div class="panel-body" style="min-height: 600px;">
                    <form method="post" action="add" theme="simple"  enctype="multipart/form-data" class="form-horizontal" cssStyle="" validate="true">
                        <div class="form-group">
                            <div class=" col-md-6">
                                <label class="col-sm-4 control-label" >Số thuê bao:</label>
                                <div class="col-sm-8">
                                    <p class="form-control-static">${item.msisdn}</p>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label class="col-sm-4 control-label" >Trạng thái xác thực:</label>
                                <div class="col-sm-8">
                                    <p class="form-control-static" >
                                        <c:choose>
                                            <c:when test="${item.confirmStatus}">
                                                Đã xác thực
                                            </c:when>
                                            <c:otherwise>
                                                Chưa xác thực
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                </div>
                            </div>
                        </div>

                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <div class="col-md-6">
                                <label class="col-sm-4 control-label">Giá bán(VNĐ):</label>
                                <div class="col-sm-8">
                                    <p class="form-control-static currencyHtml" >${item.price}</p>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label class="col-sm-4 control-label">Thời gian hết hạn xác thực:</label>
                                <div class="col-sm-8">
                                    <p class="form-control-static"><fmt:formatDate value="${item.confirmExpired}" pattern="dd/MM/yyyy HH:mm:ss"/></p>
                                </div>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <div class="col-md-6">
                                <label class="col-sm-4 control-label" >Thời gian đăng:</label>
                                <div class="col-sm-8">
                                    <p class="form-control-static"><fmt:formatDate value="${item.genDate}" pattern="dd/MM/yyyy HH:mm:ss"/></p>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label class="col-sm-4 control-label" >Trạng thái:</label>
                                <div class="col-sm-8">
                                    <p class="form-control-static" >
                                        <c:choose>
                                            <c:when test="${item.status==1}">
                                                Hiển thị
                                            </c:when>
                                            <c:otherwise>
                                                Đã khóa
                                            </c:otherwise>
                                        </c:choose>
                                    </p>

                                </div>
                            </div>
                        </div>
                        <c:if test="${item.description.length()>0}">
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <div class="col-md-6">
                                <label class="col-sm-4 control-label" >Mô tả:</label>
                                <div class="col-sm-8">
                                    <p class="form-control-static">${item.description}</p>
                                </div>
                            </div>
                        </div>
                        </c:if>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div  ng-if="confirmStatus">
                            <h3>Lịch sử xác thực</h3>
                            <div class="table-responsive table-overflow-x-fix" >
                                <table id="tbl" class="table table-striped table-bordered m-b-none text-sm">
                                    <thead>
                                    <tr>
                                        <th class="box-shadow-inner small_col text-center">#</th>
                                        <th class="box-shadow-inner small_col text-center">Thời gian gửi</th>
                                        <th class="box-shadow-inner small_col text-center">MO</th>
                                        <th class="box-shadow-inner small_col text-center">MT</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    <tr ng-repeat="item in page.items track by $index">

                                        <td class="align-center">{{(page.pageNumber-1)*page.numberPerPage + $index+1}}</td>
                                        <td class="align-center">{{item[2]|date:'dd/MM/yyyy HH:mm:ss'}}</td>
                                        <td class="align-center">{{item[1]}}</td>
                                        <td class="align-center" >{{item[3]}}</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <footer class="panel-footer">
                                <div class="row">
                                    <div class="col-sm-4 hidden-xs"></div>
                                    <div class="col-sm-4 text-center">
                                        <small class="text-muted inline m-t-sm m-b-sm"></small>
                                    </div>
                                    <div class="col-sm-12 text-right text-center-xs">
                                        <ul class="pagination pagination-sm m-t-none m-b-none">
                                            <li ng-if="page.pageNumber>1"><a ng-click="loadPage(1)">«</a></li>
                                            <li ng-repeat="item in page.pageList">
                                                <a ng-if="item==page.pageNumber" style="color:mediumvioletred;"> {{item}}</a>
                                                <a ng-if="item!=page.pageNumber" ng-click="loadPage(item)"> {{item}}</a>
                                            </li>
                                            <li ng-if="page.pageNumber<page.pageCount" ><a ng-click="loadPage(page.pageCount)">»</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </footer>
                        </div>
                    </form>

                </div>
            </section>
        </section>

    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
</section>
