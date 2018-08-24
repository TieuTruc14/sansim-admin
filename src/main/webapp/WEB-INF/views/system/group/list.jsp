<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Home</a></li>
                <li><a href="<%=request.getContextPath()%>/system/group/list">Group</a></li>
                <li><a href="javascript:void(0)">List</a></li>
            </ul>
            <div class="m-b-md" id="sansim-status"><h3 class="m-b-none" style="color: #009900"><c:if test="${success.length()>0}">${success}</c:if></h3>
                <h3 class="m-b-none" style="color: red"><c:if test="${messageError.length()>0}">${messageError}</c:if></h3>
            </div>

            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i> Dữ liệu

                </header>

                <div class="text-sm wrapper bg-light lt">
                    <form cssClass="form-inline padder" action="list" id="formItem" role="form" theme="simple" >
                        <div class="form-group">
                            <div class="col-md-6">
                                <div class="col-sm-8">
                                    <input name="filterName"  placeholder="Tên nhóm" maxlength="50" value="${filterName}" class="input-sm form-control"/>
                                    <%--<input name="filterUsername" placeholder="Username" maxlength="50" value="${filterUsername}" cssClass="input-sm form-control"/>--%>
                                </div>
                                <div class="col-sm-4"><button type="submit" class="btn btn-info btn-sm">Tìm kiếm</button></div>
                            </div>
                            <div class="col-md-6">
                                <a class="btn btn-sm btn-primary pull-right"
                                   href="<%=request.getContextPath()%>/system/group/add"><i class="fa fa-plus"></i> Thêm mới</a>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in" style="clear:both ;border-top:0px"></div>
                    </form>
                </div>
                <div class="table-responsive table-overflow-x-fix">
                    <table id="tblGroup" class="table table-striped table-bordered m-b-none text-sm">
                        <thead>
                        <tr>
                            <th class="box-shadow-inner small_col text-center">#</th>
                            <th class="box-shadow-inner small_col text-center">Tên nhóm</th>
                            <th class="box-shadow-inner small_col text-center">Mô tả</th>
                            <sec:authorize access="hasAnyRole('ROLE_SYSTEM_GROUP_EDIT','ROLE_SYSTEM_GROUP_DELETE')">
                            <th class="box-shadow-inner small_col text-center">Hoạt động</th>
                            </sec:authorize>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.items}" var="item" varStatus="stat">
                            <tr>
                                <td class="align-center">${stat.count+((page.pageNumber-1)*page.numberPerPage)}</td>
                                <td class="align-center">${item.groupName}</td>
                                <td class="align-center">${item.description}</td>
                                <sec:authorize access="hasAnyRole('ROLE_SYSTEM_GROUP_EDIT','ROLE_SYSTEM_GROUP_DELETE')">
                                <td class="align-center">
                                    <div class="btn-group">
                                        <button class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown"><i
                                                class="fa fa-random"></i></button>
                                        <ul class="dropdown-menu pull-right">
                                            <sec:authorize access="hasAnyRole('ROLE_SYSTEM_GROUP_EDIT')">
                                            <li><a href="<%=request.getContextPath()%>/system/group/edit/${item.id}"><i class="fa fa-pencil-square-o"></i>Sửa</a></li>
                                            </sec:authorize>
                                            <sec:authorize access="hasAnyRole('ROLE_SYSTEM_GROUP_DELETE')">
                                            <li>
                                                <a class="deleteItem"
                                                   data-toggle="modal" data-target="#deleteItem"
                                                   data-item.id="${item.id}"
                                                   data-item.name="${item.groupName}">
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
                                        <a href="<%=request.getContextPath()%>/system/user/list.html?p=1&filterUsername=${filterUsername}">«</a>
                                    </li>
                                </c:if>
                                <c:forEach items="${page.pageList}" var="item" varStatus="stat">
                                    <c:choose>
                                        <c:when test="${page.pageNumber==item}">
                                            <li><a style="color: #aa1111"
                                                   href="<%=request.getContextPath()%>/system/user/list.html?p=${item}&filterUsername=${filterUsername}">${item}</a></li>
                                        </c:when>
                                        <c:otherwise>
                                            <li><a  href="<%=request.getContextPath()%>/system/user/list.html?p=${item}&filterUsername=${filterUsername}">${item}</a></li>
                                        </c:otherwise>
                                    </c:choose>

                                </c:forEach>
                                <c:if test="${page.pageNumber < page.getPageCount()}">
                                    <li>
                                        <a href="<%=request.getContextPath()%>/system/user/list.html?p=${page.getPageCount()}&&filterUsername=${filterUsername}">»</a>
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

<div class="modal fade"  id="deleteItem"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
    <div class="modal-dialog" style="max-width: 500px;">
        <div class="modal-content"style="max-width: 500px;">
            <div class="modal-header" style="padding: 7px;">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h5 class="modal-title" id="myModalLable">Xóa nhóm quyền</h5>
            </div>
            <form id="filter" method="POST"  action="<%=request.getContextPath()%>/system/group/delete" theme="simple" enctype="multipart/form-data" cssClass="form-horizontal" cssStyle="" validate="false">
                <div class="modal-body"  style="padding: 10px;">
                    <div class="form-group">
                        <label class="control-label">Bạn xác nhận xóa nhóm này?</label>
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
<script>
    $(document).ready(function () {

        $('#tblUser').dataTable({
            "bFilter": false,
            "bPaginate": false,
            "bAutoWidth": false,
            "sPaginationType": "full_numbers"
        });

        $(".deleteItem").click(function(){
            var id=$(this).data('item.id');
            var name=$(this).data('item.name');

            $(".info_id").val(id);
            $(".modal-title").text('Xóa nhóm quyền "'+name+'"');
        });
        $("input").keypress(function(event) {
            if (event.which == 13) {
                event.preventDefault();
                $("#formItem").submit();
            }
        });
    });
</script>
