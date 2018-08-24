<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="<%=request.getContextPath()%>/assets/project/user/change.my.pass.js"></script>
<section id="content" ng-app="sansimso"  ng-controller="sansimCtrl">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Home</a></li>
                <li><a href="#">User</a></li>
                <li><a href="javascript:void(0)">Đổi mật khẩu</a></li>
            </ul>
            <div class="m-b-md">
                <h3 class="m-b-none">Đổi mật khẩu</h3><br/>
            </div>

            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i>Đẩu mật khẩu</header>
                <div class="panel-body" style="min-height: 600px;">
                    <form method="post"  theme="simple"  enctype="multipart/form-data" class="form-horizontal" cssStyle="" validate="true">
                        <div class="form-group">
                            <label class="col-sm-2 control-label" style="line-height: 30px">Mật khẩu cũ <span style="color: red">*</span></label>
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-lock" aria-hidden="true"></i>
                                        </span>
                                    <input type="password" ng-model="passwordCurrent" maxlength="50" style="width:100%;"
                                           placeholder="Mật khẩu cũ" class="form-control"/>
                                </div>
                                <span style="color:red">{{messageCurrent}}</span>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" style="line-height: 30px">Mật khẩu mới<span style="color: red">*</span></label>
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-lock"></i>
                                        </span>
                                    <input type="password" ng-model="passwordNew" placeholder="Mật khẩu mới" maxlength="50" style="width:100%;"
                                           class="form-control"/>
                                </div>
                                <span style="color:red">{{messageNew}}</span>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Xác nhận mật khẩu <span style="color: red">*</span></label>
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-lock"></i>
                                        </span>
                                    <input type="password" ng-model="confirmPassword" maxlength="50" style="width:100%;" placeholder="Xác nhận mật khẩu" class="form-control" />
                                </div>
                                <span style="color:red">{{messageConfirm}}</span>
                            </div>
                        </div>

                        <div class="line line-dashed line-lg pull-in" style="clear:both ;margin-bottom: 50px"></div>
                        <div class="form-group">
                            <div class="col-sm-4 col-sm-offset-5">
                                <a href="<%=request.getContextPath()%>/" class="btn btn-default">Hủy bỏ</a>
                                <a ng-click="change()"  class="btn btn-primary" id="btn-check">Đổi mật khẩu</a>
                            </div>
                        </div>
                    </form>

                </div>
            </section>
        </section>
    </section>
    <a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
    <div class="modal fade"  id="Message"  role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
        <div class="modal-dialog" style="max-width: 500px;">
            <div class="modal-content"style="max-width: 500px;">
                <div class="modal-header" style="padding: 7px;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h5 class="modal-title">Đổi mật khẩu</h5>
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
