<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Home</a></li>
                <li><a href="#">Manager</a></li>
                <li><a href="#">Quản lý đăng số</a></li>
                <li><a href="javascript:void(0)">Thêm gói phí số</a></li>
            </ul>
            <div class="m-b-md">
                <h3 class="m-b-none">Thêm gói phí số</h3><br/>
                <span style="color:red">${messageError}</span>
            </div>

            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i>Thêm gói phí số</header>
                <div class="panel-body" style="min-height: 600px;">
                    <form method="post" action="add" theme="simple"  enctype="multipart/form-data" class="form-horizontal" cssStyle="" validate="true">
                        <div class="form-group">
                            <label class="col-sm-2 control-label" style="line-height: 30px">Mã gói <span style="color: red">*</span></label>
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-file-text" aria-hidden="true"></i>
                                        </span>
                                    <input name="packageCode" maxlength="50" style="width:100%;" value="${item.packageCode}"
                                           placeholder="Mã gói" class="form-control"/>

                                </div>
                                <form:errors cssStyle="color: red" path="configPackage.packageCode" />
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" style="line-height: 30px">Tên gói <span style="color: red">*</span></label>
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-file-text" aria-hidden="true"></i>
                                        </span>
                                    <input name="packageName" maxlength="50" style="width:100%;" value="${item.packageName}"
                                           placeholder="Tên gói" class="form-control"/>

                                </div>
                                <form:errors cssStyle="color: red" path="configPackage.packageName" />
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" style="line-height: 30px">Đến số lượng <span style="color: red">*</span></label>
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-chevron-left"></i>

                                        </span>
                                    <input name="maxQuantity" placeholder="Đến số lượng" maxlength="10" style="width:100%;" value="${item.maxQuantity}" onkeypress="return restrictCharacters(this, event, digitsOnly);"
                                           class="form-control"/>
                                </div>
                                <form:errors style="color: red" path="configPackage.maxQuantity" />
                            </div>
                        </div>

                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" style="line-height: 30px">Chu kỳ gói(tính theo ngày) <span style="color: red">*</span></label>
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-rotate-right" style="font-size: 11px" aria-hidden="true"></i>
                                        </span>
                                    <input name="period" placeholder="Chu kỳ" maxlength="5" value="${item.period}" style="width:100%;" class="form-control" onkeypress="return restrictCharacters(this, event, digitsOnly);"/>
                                </div>
                                <form:errors style="color: red" path="configPackage.period" />
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Phí mua/gia hạn <span style="color: red">*</span></label>
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-dollar" aria-hidden="true" style="font-size: 10px"></i>
                                        </span>
                                    <input name="fee" value="${item.fee}" maxlength="10" cssStyle="width:100%;" placeholder="phí" class="form-control" onkeypress="return restrictCharacters(this, event, digitsOnly);"/>
                                </div>
                                <form:errors cssStyle="color: red" path="configPackage.fee" />
                            </div>
                        </div>


                        <div class="line line-dashed line-lg pull-in" style="clear:both ;margin-bottom: 50px"></div>
                        <div class="form-group">
                            <div class="col-sm-4 col-sm-offset-5">
                                <a href="<%=request.getContextPath()%>/administer/package/list" class="btn btn-default">Hủy bỏ</a>
                                <button type="submit" data-loading-text="Thêm mới" class="btn btn-primary">Thêm mới</button>
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
