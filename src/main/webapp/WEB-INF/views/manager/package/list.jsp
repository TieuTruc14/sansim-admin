<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--<script  type="text/javascript">--%>
<%--$("#sansim-status").show();--%>
<%--setTimeout(function() { $("#sansim-status").hide(); }, 3000);--%>
<%--</script>--%>
<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Home</a></li>
                <li><a href="#">Phí đăng số</a></li>
                <li><a href="javascript:void(0)">List</a></li>
            </ul>
            <div class="m-b-md"><h3 class="m-b-none" id="sansim-status" style="color: #009900">
                <c:if test="${success.length()>0}">${success}</c:if>
            </h3>
                <h3 class="m-b-none"  style="color: red"><c:if test="${messageError.length()>0}">${messageError}</c:if></h3>
            </div>

            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> Dữ liệu
                </header>
                <div class="text-sm wrapper bg-light lt">
                    <form cssClass="form-inline padder" action="list" role="form" id="formItem" theme="simple">

                        <div class="form-group ">
                            <div class="col-md-6">
                                <label class="col-sm-3 control-label">Mã gói</label>
                                <div class="col-sm-9">
                                    <input name="packageCode" type="text" placeholder="Mã gói" maxlength="50" value="${packageCode}" class="input-sm form-control" />
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label class="col-sm-3 control-label">Tên gói</label>
                                <div class="col-sm-9">
                                    <input name="packageName" type="text" placeholder="Tên gói" maxlength="50" value="${packageName}" class="input-sm form-control" />
                                </div>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>
                        <div class="form-group">
                            <div class="col-md-6">
                                <label class="col-sm-3 control-label">Từ giá</label>
                                <div class="col-sm-9">
                                    <input type="text" type="text" name="from" placeholder="Từ giá" maxlength="20" value="${from}" class="input-sm form-control" onkeypress="return restrictCharacters(this, event, digitsOnly);"/>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label class="col-sm-3 control-label">Đến giá</label>
                                <div class="col-sm-9">
                                    <input name="to" type="text" placeholder="Tới giá" value="${to}" maxlength="20" class="input-sm form-control col-md-6" onkeypress="return restrictCharacters(this, event, digitsOnly);"/>
                                </div>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>
                        <div class="form-group text-center">
                            <button type="submit" class="btn btn-info btn-sm">Tìm kiếm</button>
                            <a class="btn btn-default btn-sm" id="reset-form">Xóa điều kiện</a>
                            <sec:authorize access="hasRole('ROLE_MANAGER_CONFIG_PACKAGE_ADD')">
                            <a class="btn btn-sm btn-primary pull-right"
                               href="<%=request.getContextPath()%>/administer/package/add"><i class="fa fa-plus"></i> Thêm mới</a>
                            </sec:authorize>
                        </div>

                    </form>
                </div>
                <div class="table-responsive table-overflow-x-fix">
                    <table id="tblUser" class="table table-striped table-bordered m-b-none text-sm">
                        <thead>
                        <tr>
                            <th class="box-shadow-inner small_col text-center">#</th>
                            <th class="box-shadow-inner small_col text-center">Mã gói</th>
                            <th class="box-shadow-inner small_col text-center">Tên gói</th>
                            <th class="box-shadow-inner small_col text-center">Số lượng max</th>
                            <th class="box-shadow-inner small_col text-center">Phí gia hạn</th>
                            <th class="box-shadow-inner small_col text-center">Chu kỳ gói</th>
                            <th class="box-shadow-inner small_col text-center">Ngày tạo</th>
                            <th class="box-shadow-inner small_col text-center">Người tạo</th>
                            <th class="box-shadow-inner small_col text-center">Trạng thái</th>
                            <sec:authorize access="hasAnyRole('ROLE_MANAGER_CONFIG_PACKAGE_DELETE','ROLE_MANAGER_CONFIG_PACKAGE_EDIT')">
                            <th class="box-shadow-inner small_col text-center">Hoạt động</th>
                            </sec:authorize>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.items}" var="item" varStatus="stat">
                            <tr>
                                <td class="align-center">${stat.count+((page.pageNumber-1)*page.numberPerPage)}</td>
                                <td class="align-center">${item.packageCode}</td>
                                <td class="align-center">${item.packageName}</td>
                                <td class="align-center"><span class="currencyHtml">${item.maxQuantity}</span></td>
                                <td class="align-center"><span class="currencyHtml">${item.fee}</span></td>
                                <td class="align-center">${item.period}</td>
                                <td class="align-center">${item.genDate}</td>
                                <td class="align-center">${item.createBy}</td>
                                <td class="align-center">
                                    <c:choose>
                                        <c:when test="${item.status==1}">
                                            <a>Đang hoạt động</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a>Ngừng hoạt động</a>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <sec:authorize access="hasAnyRole('ROLE_MANAGER_CONFIG_PACKAGE_DELETE','ROLE_MANAGER_CONFIG_PACKAGE_EDIT')">
                                <td class="align-center">
                                    <div class="btn-group">
                                        <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i
                                                class="fa fa-random"></i></button>
                                        <ul class="dropdown-menu pull-right">
                                            <sec:authorize access="hasRole('ROLE_MANAGER_CONFIG_PACKAGE_EDIT')">
                                            <li><a href="<%=request.getContextPath()%>/administer/package/edit/${item.id}"><i class="fa fa-pencil-square-o"></i>Sửa</a></li>
                                                <c:choose>
                                                    <c:when test="${item.status==1}">
                                                        <li>
                                                            <a class="disableItem" href="javascript:void(0)"
                                                               data-toggle="modal" data-target="#disableItem"
                                                               data-id="${item.id}"
                                                               data-packagecode="${item.packageCode}">
                                                                <i class="fa fa-pencil-square-o"></i>Khóa gói</a>
                                                        </li>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <li>
                                                            <a class="enableItem " href="javascript:void(0)"
                                                               data-toggle="modal" data-target="#enableItem"
                                                               data-id="${item.id}"
                                                               data-packagecode="${item.packageCode}">
                                                                <i class="fa fa-pencil-square-o"></i>Mở gói</a>
                                                        </li>
                                                    </c:otherwise>
                                                </c:choose>

                                            </sec:authorize>
                                            <sec:authorize access="hasRole('ROLE_MANAGER_CONFIG_PACKAGE_DELETE')">
                                            <li>
                                                <a class="deleteItem "
                                                   data-toggle="modal" data-target="#deleteItem"
                                                   data-id="${item.id}"
                                                   data-packagecode="${item.packageCode}">
                                                    <i class="fa fa-pencil-square-o"></i> Xóa</a>
                                            </li>
                                            </sec:authorize>
                                        </ul>
                                    </div>
                                </td>
                                </sec:authorize>
                            </tr>

                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <footer class="panel-footer">
                    <div class="row">
                        <div class="col-sm-12 text-right text-center-xs">
                            <div class="col-sm-6 text-left">
                                <span>Tổng số <code  class="currencyHtml">${page.rowCount}</code> dữ liệu</span>
                            </div>
                            <div class="col-sm-6">
                            <ul class="pagination pagination-sm m-t-none m-b-none">
                                <c:if test="${page.pageNumber > 1}">
                                    <li>
                                        <a href="<%=request.getContextPath()%>/system/user/list.html?p=1&minQuantity=${minQuantity}&maxQuantity=${maxQuantity}&fee=${fee}">«</a>
                                    </li>
                                </c:if>
                                <c:forEach items="${page.pageList}" var="item" varStatus="stat">
                                    <c:choose>
                                        <c:when test="${page.pageNumber==item}">
                                            <li><a style="color: #aa1111"
                                                   href="<%=request.getContextPath()%>/system/user/list.html?p=${item}&minQuantity=${minQuantity}&maxQuantity=${maxQuantity}&fee=${fee}">${item}</a></li>
                                        </c:when>
                                        <c:otherwise>
                                            <li><a  href="<%=request.getContextPath()%>/system/user/list.html?p=${item}&minQuantity=${minQuantity}&maxQuantity=${maxQuantity}&fee=${fee}">${item}</a></li>
                                        </c:otherwise>
                                    </c:choose>

                                </c:forEach>
                                <c:if test="${page.pageNumber < page.getPageCount()}">
                                    <li>
                                        <a href="<%=request.getContextPath()%>/system/user/list.html?p=${page.getPageCount()}&&minQuantity=${minQuantity}&maxQuantity=${maxQuantity}&fee=${fee}">»</a>
                                    </li>
                                </c:if>
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
<sec:authorize access="hasRole('ROLE_MANAGER_CONFIG_PACKAGE_EDIT')">
    <div class="modal fade"  id="disableItem"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
        <div class="modal-dialog" style="max-width: 500px;">
            <div class="modal-content"style="max-width: 500px;">
                <div class="modal-header" style="padding: 7px;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h5 class="modal-title-disable" >Khóa gói phí</h5>
                </div>
                <form  method="POST"  action="<%=request.getContextPath()%>/administer/package/disable" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                    <div class="modal-body"  style="padding: 10px;">
                        <div class="form-group">
                            <label class="control-label">Bạn xác nhận khóa gói này?</label>
                            <input name="id"  type="hidden" class="form-control info_id_disable" />
                        </div>

                    </div>
                    <div class="modal-footer" style="padding: 10px;" >
                        <button type="button" class="btn btn-default" data-dismiss="modal">Hủy bỏ</button>
                        <button type="submit" class="btn btn-danger">Khóa</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="modal fade"  id="enableItem"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
        <div class="modal-dialog" style="max-width: 500px;">
            <div class="modal-content"style="max-width: 500px;">
                <div class="modal-header" style="padding: 7px;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h5 class="modal-title-enable" >Mở gói phí</h5>
                </div>
                <form  method="POST"  action="<%=request.getContextPath()%>/administer/package/enable" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                    <div class="modal-body"  style="padding: 10px;">
                        <div class="form-group">
                            <label class="control-label">Bạn xác nhận mở gói này?</label>
                            <input name="id"  type="hidden" class="form-control info_id_enable" />
                        </div>

                    </div>
                    <div class="modal-footer" style="padding: 10px;" >
                        <button type="button" class="btn btn-default" data-dismiss="modal">Hủy bỏ</button>
                        <button type="submit" class="btn btn-success">Mở gói</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_MANAGER_CONFIG_PACKAGE_DELETE')">
<div class="modal fade"  id="deleteItem"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
    <div class="modal-dialog" style="max-width: 500px;">
        <div class="modal-content"style="max-width: 500px;">
            <div class="modal-header" style="padding: 7px;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title" id="myModalLable">Xóa gói phí</h5>
            </div>
            <form id="filter" method="POST"  action="<%=request.getContextPath()%>/administer/package/delete" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                <div class="modal-body"  style="padding: 10px;">
                    <div class="form-group">
                        <label class="control-label">Bạn xác nhận xóa gói này?</label>
                        <input name="id"  type="hidden" class="form-control info_id" />
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
<script>
    $(document).ready(function () {

        $('#tblPackage').dataTable({
            "bFilter": false,
            "bPaginate": false,
            "bAutoWidth": false,
            "sPaginationType": "full_numbers"
        });

        $(".deleteItem").click(function(){
            var id=$(this).data('id');
            var name=$(this).data('packagecode');

            $(".info_id").val(id);
            $(".modal-title").text('Xóa gói cước "'+name+'"');
        });
        $(".disableItem").click(function(){
            var id=$(this).data('id');
            var name=$(this).data('packagecode');

            $(".info_id_disable").val(id);
            $(".modal-title-disable").text('Khóa gói cước "'+name+'"');
        });
        $(".enableItem").click(function(){
            var id=$(this).data('id');
            var name=$(this).data('packagecode');

            $(".info_id_enable").val(id);
            $(".modal-title-enable").text('Mở gói cước "'+name+'"');
        });

        $("input").keypress(function(event) {
            if (event.which == 13) {
                event.preventDefault();
                $("#formItem").submit();
            }
        });

        $('#reset-form').on('click', function()
        {
            $(this).closest('form').find("input[type=text], textarea").val("");
        });

    });
</script>
