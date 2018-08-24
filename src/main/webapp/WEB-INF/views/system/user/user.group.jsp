<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="<%=request.getContextPath()%>/assets/js/checkbox.js"></script>
<section id="content">
    <section class="vbox">
        <section class="scrollable padder">
            <ul class="breadcrumb no-border no-radius b-b b-light pull-in">
                <li><a href="<%=request.getContextPath()%>/"><i class="fa fa-home"></i>&nbsp;Home</a></li>
                <li><a href="<%=request.getContextPath()%>/system/user/list">Người dùng</a></li>
                <li><a href="javascript:void(0)">Phân quyền</a></li>
            </ul>
            <div class="m-b-md">
                <h3 class="m-b-none">Phân nhóm quyền người dùng <span style="color:red">${user.username}</span></h3><br/>
                <span style="color:red">${messageError}</span>
            </div>

            <section class="panel panel-default">
                <header class="panel-heading"><i class="fa fa-caret-square-o-right"></i>Phân nhóm quyền</header>
                <div class="panel-body" style="min-height: 600px;">
                    <form method="post" action="<%=request.getContextPath()%>/system/user/user-group" theme="simple"  enctype="multipart/form-data" class="form-horizontal" cssStyle="" validate="true">
                        <input type="hidden" name="id" value="${user.id}"/>
                        <div class="form-group m-b-xs">
                            <div class="col-lg-12" style="margin-left: 40px">
                                <div><span><input type="checkbox" id="grantCheckAll" name="checkAll" /> &nbsp;<label class="font-bold">Tất cả các nhóm quyền</label></span></div>
                                <c:forEach var="item" items="${allGroups}">
                                    <div class="col-md-4"  >
                                        <table style="line-height: 2.0; border: 0">
                                            <tbody>
                                                <tr>
                                                    <td  style="padding-left: 15px; vertical-align: top;">
                                                        <input type="checkbox"  name="listGroup" value="${item.id}" /><label> ${item.groupName}</label>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </c:forEach>
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
<script>
    $(document).ready(function () {
        /*Quy uoc cha con cho checkbox*/
        $('#grantCheckAll').checkboxes({
            itemChild: 'list'
        });
        /*load quyen*/
        var grant = "${groups}".split(",");
        $("input[name='listGroup']").each(function() {
            for(var i=0; i<grant.length;i++){
                if(grant[i]==this.value) {
                    $(this).prop('checked', true);
                }
            }
        });

        /*Kiem tra checkbox trong bang dc check toan bo khong*/
        function checkAllCheck(){
            var result=true;
            $("input[name='listGroup']").each(function() {
                if(!$(this).is(':checked')){
                    result=false;
                    return result;
                }

            });
            return result;
        }
        var checkAllCheck=checkAllCheck();
        if(checkAllCheck){
            $('#grantCheckAll').prop('checked', true);
        }

    });

</script>