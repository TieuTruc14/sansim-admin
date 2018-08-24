<%--<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>
<%@ taglib uri="http://cksource.com/ckfinder" prefix="ckfinder" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/assets/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/assets/ckfinder/ckfinder.js"></script>
<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Home</a></li>
                <li><a href="#">Quản lý nội dung</a></li>
                <li><a href="#">Bài viết</a></li>
                <li><a href="javascript:void(0)">Thêm mới</a></li>
            </ul>
            <div class="m-b-md">
                <h3 class="m-b-none">Thêm bài viết</h3><br/>
                <span style="color:red">${messageError}</span>
            </div>

            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i></header>
                <div class="panel-body" style="min-height: 600px;">
                    <form method="post" action="add" theme="simple"  enctype="multipart/form-data" class="form-horizontal" cssStyle="" validate="true">
                        <div class="form-group">
                            <label class="col-sm-2 control-label" style="line-height: 30px">Danh mục <code>*</code></label>
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                    <select   id="category" name="category.id"  class="select2-option" style="min-width:200px">
                                        <c:forEach items="${categories}" var="category">
                                            <option value="${category.id}">${category.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <form:errors cssStyle="color: red" path="article.category.id" />
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" style="line-height: 30px">Tiêu đề <code>*</code></label>
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-rotate-right" style="font-size: 11px" aria-hidden="true"></i>
                                        </span>
                                    <input name="title" placeholder="" maxlength="500" value="${item.title}" style="width:100%;" class="form-control"/>
                                </div>
                                <form:errors cssStyle="color: red" path="article.title" />
                            </div>
                        </div>

                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" style="line-height: 30px">Nội dung tóm tắt <code>*</code> </label>
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-rotate-right" style="font-size: 11px" aria-hidden="true"></i>
                                        </span>
                                    <textarea name="shortContent" placeholder="" maxlength="2000" value="${item.shortContent}" style="width:100%;" class="form-control">${item.shortContent}</textarea>
                                </div>
                                <form:errors cssStyle="color: red" path="article.shortContent" />
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" style="line-height: 30px">Nội dung </label>
                            <div class="col-sm-10">
                                     <textarea id="contentnews" name="content" class="fomr-control" >${item.content}</textarea>
                                     <ckfinder:setupCKEditor basePath="../assets/ckfinder/" editor="contentnews" />
                                     <ckeditor:replace replace="contentnews" basePath="../assets/ckeditor/" />

                            <%--<textarea name="content" rows="3" class="form-control ckeditor" style="width:100%;" id="content" ></textarea>--%>
                                     <%--<ckeditor:replace replace="content" basePath="../../assets/ckeditor/" />--%>
                                    <%--<ckfinder:setupCKEditor basePath="../../assets/ckfinder/" editor="content" />--%>
                                <%--<ckeditor:editor basePath="../../assets/ckfinder/" editor="content" />--%>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" style="line-height: 30px">Ảnh </label>
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                    <input type="file" name="file" id="file" accept="image/*"/>
                                    <img  style="max-width: 500px"  id="idImagePlaceHolder"/>
                                </div>
                            </div>
                        </div>

                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" style="line-height: 30px">Trạng thái</label>
                            <div class="col-sm-10">
                                <div class="radio">
                                    <label class="radio-custom">
                                        <input type="radio" value="true" name="active" ${item.active==true?'checked="checked"':''}  checked>
                                        <i class="fa fa-circle-o"></i>
                                        Hiển thị
                                    </label>
                                </div>
                                <div class="radio">
                                    <label class="radio-custom">
                                        <input type="radio" value="false" name="active" ${item.active==false?'checked="checked"':''} >
                                        <i class="fa fa-circle-o"></i>
                                        Không hiển thị
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" style="line-height: 30px">Thẻ title</label>
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-rotate-right" style="font-size: 11px" aria-hidden="true"></i>
                                        </span>
                                    <textarea  name="metaTitle" placeholder="" maxlength="2000" value="${item.metaTitle}" style="width:100%;" class="form-control"  path="metaTitle">${item.metaTitle}</textarea>
                                </div>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" style="line-height: 30px">Thẻ meta-description </label>
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-rotate-right" style="font-size: 11px" aria-hidden="true"></i>
                                        </span>
                                    <textarea name="metaDescription" placeholder="" maxlength="2000" value="${item.metaDescription}" style="width:100%;" class="form-control">${item.metaDescription}</textarea>
                                </div>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" style="line-height: 30px">Thẻ meta-keyword </label>
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-rotate-right" style="font-size: 11px" aria-hidden="true"></i>
                                        </span>
                                    <textarea name="metaKeyword" placeholder="" maxlength="2000" value="${item.metaKeyword}" style="width:100%;" class="form-control">${item.metaKeyword}</textarea>
                                </div>
                            </div>
                        </div>
                        <div class="line line-dashed line-lg pull-in"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label" style="line-height: 30px">Thẻ alt ảnh </label>
                            <div class="col-sm-10">
                                <div class="input-group m-b">
                                        <span class="input-group-addon">
                                            <i class="fa fa-rotate-right" style="font-size: 11px" aria-hidden="true"></i>
                                        </span>
                                    <textarea name="altImage" placeholder="" maxlength="2000" value="${item.altImage}" style="width:100%;" class="form-control">${item.altImage}</textarea>
                                </div>
                            </div>
                        </div>


                        <div class="line line-dashed line-lg pull-in" style="clear:both ;margin-bottom: 50px"></div>
                        <div class="form-group">
                            <div class="col-sm-4 col-sm-offset-5">
                                <a href="<%=request.getContextPath()%>/content/article/list" class="btn btn-default">Hủy bỏ</a>
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

<script type="text/javascript">

    function readURL(input) {

        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $(input).next('#idImagePlaceHolder').attr('src', e.target.result);
            };
            reader.readAsDataURL(input.files[0]);
        }
    }

    $(function(){

        $("#file").change(function () { //set up a common class
            readURL(this);
        });
    });

    var editor=CKEDITOR.replace( 'contentnews',
        {
            filebrowserBrowseUrl : '<%=request.getContextPath()%>/assets/ckfinder/ckfinder.html',
            filebrowserImageBrowseUrl : '<%=request.getContextPath()%>/assets/ckfinder/ckfinder.html?type=Images',
            filebrowserUploadUrl :
                '<%=request.getContextPath()%>/assets/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files',
            filebrowserImageUploadUrl :
                '<%=request.getContextPath()%>/assets/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',
        });

//    CKEDITOR.replace('contentnews');
//    var editor=CKEDITOR.replace('contentnews');
    CKFinder.setupCKEditor(editor,'<%=request.getContextPath()%>/assets/ckfinder/');

</script>