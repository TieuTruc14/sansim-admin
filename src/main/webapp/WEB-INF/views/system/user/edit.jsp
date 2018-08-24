<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Home</a></li>
                <li><a href="#">User</a></li>
                <li><a href="javascript:void(0)">Sửa thông tin tài khoản</a></li>
            </ul>
            <div class="m-b-md">
                <h3 class="m-b-none">Sửa thông tin tài khoản: <code>${item.username}</code></h3><br/>
                <span style="color:red">${messageError}</span>
            </div>

            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i>Sửa thông tin tài khoản</header>
                <div class="panel-body" style="min-height: 600px;">
                    <form method="post" action="<%=request.getContextPath()%>/system/user/edit" theme="simple"  enctype="multipart/form-data" class="form-horizontal" cssStyle="" validate="true">
                        <div class="form-group">
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                    <input type="hidden" name="id"  value="${item.id}" class="form-control"/>
                                    <input type="hidden" name="username"  value="${user.username}" class="form-control"/>
                                </div>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" style="line-height: 30px">Họ tên <span style="color: red">*</span></label>
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-envelope" style="font-size: 11px" aria-hidden="true"></i>
                                        </span>
                                    <input name="fullName" placeholder="Họ tên" value="${item.fullName}" style="width:100%;" class="form-control"/>
                                </div>
                                <form:errors style="color: red" path="user.fullName" />
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Mô tả </label>
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-pencil" aria-hidden="true" style="font-size: 10px"></i>
                                        </span>
                                    <input name="description" value="${item.description}" maxlength="200" cssStyle="width:100%;" placeholder="Mô tả" class="form-control" />
                                </div>

                            </div>
                        </div>


                        <div class="line line-dashed line-lg pull-in" style="clear:both ;margin-bottom: 50px"></div>
                        <div class="form-group">
                            <div class="col-sm-4 col-sm-offset-5">
                                <a href="<%=request.getContextPath()%>/system/user/list" class="btn btn-default">Hủy bỏ</a>
                                <button type="submit" data-loading-text="Cập nhật" class="btn btn-primary">Cập nhật</button>
                            </div>
                        </div>
                    </form>

                </div>
            </section>
        </section>
        <footer class="footer bg-white b-t b-light"><small>Sàn sim số &copy; 2018</small></footer>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
</section>
