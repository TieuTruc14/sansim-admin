<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--<script src="<%=request.getContextPath()%>/assets/project/category/groupprice/list.js"></script>--%>
<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Home</a></li>
                <li><a href="#">Danh mục</a></li>
                <li><a href="#">Sim theo năm sinh</a></li>
                <li><a href="javascript:void(0)">Sửa thông tin</a></li>
            </ul>
            <div class="m-b-md">
                <h3 class="m-b-none">Sửa mục sim theo năm sinh</h3><br/>
                <span style="color:red">${messageError}</span>
            </div>

            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i></header>
                <div class="panel-body" style="min-height: 600px;">
                    <form method="post" action="<%=request.getContextPath()%>/category/group-year/edit" theme="simple"  enctype="multipart/form-data" class="form-horizontal" cssStyle="" validate="true">
                        <input name="id" maxlength="100" style="width:100%;" value="${item.id}"
                               placeholder="" class="form-control" type="hidden"/>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" style="line-height: 30px">Giá trị năm <span style="color: red">*</span></label>
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-file-text" aria-hidden="true"></i>
                                        </span>
                                    <input name="year" maxlength="4" style="width:100%;" value="${item.year}"
                                           placeholder="" class="form-control"/>
                                </div>
                                <form:errors cssStyle="color: red" path="groupYear.year" />
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" style="line-height: 30px">Tên hiển thị <span style="color: red">*</span></label>
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-file-text" aria-hidden="true"></i>
                                        </span>
                                    <input name="name" maxlength="100" style="width:100%;" value="${item.name}"
                                           placeholder="" class="form-control"/>

                                </div>
                                <form:errors cssStyle="color: red" path="groupYear.name" />
                            </div>
                        </div>

                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" style="line-height: 30px"> </label>
                            <div class="col-sm-10">
                                <div class="radio">
                                    <label class="radio-custom">
                                        <input type="radio" value="true" name="active" ${item.active==true?'checked="checked"':''} >
                                        <i class="fa fa-circle-o"></i>
                                        Đang hoạt động
                                    </label>
                                </div>
                                <div class="radio">
                                    <label class="radio-custom">
                                        <input type="radio" value="false" name="active" ${item.active==false?'checked="checked"':''} >
                                        <i class="fa fa-circle-o"></i>
                                        Đang khóa
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" style="line-height: 30px">Mô tả </label>
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-rotate-right" style="font-size: 11px" aria-hidden="true"></i>
                                        </span>
                                    <input name="period" placeholder="" maxlength="500" value="${item.description}" style="width:100%;" class="form-control"/>
                                </div>
                            </div>
                        </div>

                        <div class="line line-dashed line-lg pull-in" style="clear:both ;margin-bottom: 50px"></div>
                        <div class="form-group">
                            <div class="col-sm-4 col-sm-offset-5">
                                <a href="<%=request.getContextPath()%>/category/group-year/list" class="btn btn-default">Hủy bỏ</a>
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
