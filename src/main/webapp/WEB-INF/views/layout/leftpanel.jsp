<%@ page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%!
    public boolean isActive(String navPath,HttpServletRequest request) {
        String namespace = (String)request.getAttribute("javax.servlet.forward.request_uri");
        if (namespace.startsWith(navPath)) {
            return true;
        }
        return false;
    };
    public boolean isActiveIndex(String navPath,HttpServletRequest request){
        String namespace = (String)request.getAttribute("javax.servlet.forward.request_uri");
        if (namespace.equals(navPath)) {
            return true;
        }
        return false;
    }
    public boolean isNavXs(HttpServletRequest request){
        if(request.getSession().getAttribute("nav-xs")!=null){
            Boolean thugon=(Boolean) request.getSession().getAttribute("nav-xs");
            if(thugon.equals(true)){
                return true;
            }
        }
        return false;
    }
//    public boolean isSun(HttpServletRequest request){
//        if(request.getSession().getAttribute("sun-moon")!=null){
//            Boolean sun=(Boolean) request.getSession().getAttribute("sun-moon");
//            return sun;
//        }
//        return false;
//    }
%>
<%--<aside class="bg-light lter b-r aside-md hidden-print" id="nav">--%>
<aside class="bg-dark lter b-r aside-md hidden-print hidden-xs <%= isNavXs(request) ? "nav-xs" : "" %>" id="nav">
    <section class="vbox">
        <header class="header bg-primary lter text-center clearfix">
            <div class="btn-group">
                <button type="button" class="btn btn-sm btn-dark btn-icon"><i class="fa fa-envelope"></i></button>
                <div class="btn-group hidden-nav-xs">
                    <a href="#" class="btn btn-sm btn-primary" >Phản hồi</a>
                </div>
            </div>
        </header>
        <section class="w-f scrollable">
            <div class="slim-scroll" data-height="auto" data-disable-fade-out="true" data-distance="0" data-size="5px" data-color="#333333">
                <nav class="nav-primary hidden-xs">
                    <ul class="nav">
                        <li class="<%= isActiveIndex(request.getContextPath()+"/",request) ? "active" : "" %>"><a href="<%=request.getContextPath()%>/">
                            <i class="fa fa-dashboard icon">
                                <b class="bg-info"></b>
                            </i><span>Dash Board</span></a>
                        </li>
                        <sec:authorize access="hasAnyRole('ROLE_MANAGER_CONFIG_PACKAGE_VIEW','ROLE_MANAGER_CUSTOMER_VIEW','ROLE_MANAGER_TRANSPAY_VIEW','ROLE_MANAGER_HISTORY_CUSTOMER_MSISDN_VIEW','ROLE_MANAGER_HISTORY_MO_MT_VIEW')">
                        <li class="<%= isActive(request.getContextPath()+"/administer",request) ? "active" : "" %>"><a href="#" class=""> <i class="fa fa-paste icon"> <b class="bg-success"></b> </i> <span class="pull-right"> <i class="fa fa-angle-down text"></i> <i class="fa fa-angle-up text-active"></i> </span> <span>Quản lý</span> </a>
                            <ul class="nav lt">
                                <sec:authorize access="hasRole('ROLE_MANAGER_CONFIG_PACKAGE_VIEW')">
                                <li class="<%= isActive(request.getContextPath()+"/administer/package",request) ? "active" : "" %>"><a href="<%=request.getContextPath()%>/administer/package/list" class=""> <i class="fa fa-angle-right"></i> <span>Khai báo gói cước</span> </a></li>
                                </sec:authorize>
                                <sec:authorize access="hasRole('ROLE_MANAGER_CUSTOMER_VIEW')">
                                <li class="<%= isActive(request.getContextPath()+"/administer/customer/",request) ? "active" : "" %>"><a href="<%=request.getContextPath()%>/administer/customer/list" class=""> <i class="fa fa-angle-right"></i> <span>Người bán</span> </a></li>
                                </sec:authorize>
                                <sec:authorize access="hasRole('ROLE_MANAGER_TRANSPAY_VIEW')">
                                <li class="<%= isActive(request.getContextPath()+"/administer/pack-cus",request) ? "active" : "" %>"><a href="<%=request.getContextPath()%>/administer/pack-cus/list" class=""> <i class="fa fa-angle-right"></i> <span>Gói cước</span> </a></li>
                                </sec:authorize>
                                <sec:authorize access="hasRole('ROLE_MANAGER_HISTORY_CUSTOMER_MSISDN_VIEW')">
                                <li class="<%= isActive(request.getContextPath()+"/administer/msisdn",request) ? "active" : "" %>"><a href="<%=request.getContextPath()%>/administer/msisdn/list" class=""> <i class="fa fa-angle-right"></i> <span>Đăng số</span> </a></li>
                                </sec:authorize>
                                <sec:authorize access="hasRole('ROLE_MANAGER_HISTORY_MO_MT_VIEW')">
                                <li class="<%= isActive(request.getContextPath()+"/administer/mo-mt",request) ? "active" : "" %>"><a href="<%=request.getContextPath()%>/administer/mo-mt/list" class=""> <i class="fa fa-angle-right"></i> <span>Lịch sử MO/MT</span> </a></li>
                                </sec:authorize>
                            </ul>

                        </li>
                        </sec:authorize>
                        <sec:authorize access="hasAnyRole('ROLE_REPORT_GENERAL_SALE_SMS','ROLE_REPORT_DETAIL_SALE_SMS','ROLE_REPORT_GENERAL_TRADE','ROLE_REPORT_DETAIL_TRADE','ROLE_REPORT_ADD_AND_DELETE_MSISDN')">
                        <li class="<%= isActive(request.getContextPath()+"/report",request) ? "active" : "" %>"><a href="#" class=""> <i class="fa fa-file-text icon"> <b class="bg-primary"></b> </i> <span class="pull-right"> <i class="fa fa-angle-down text"></i> <i class="fa fa-angle-up text-active"></i> </span> <span>Báo cáo thống kê</span> </a>
                            <ul class="nav lt">
                                <sec:authorize access="hasRole('ROLE_REPORT_GENERAL_SALE_SMS')">
                                <li class="<%= isActive(request.getContextPath()+"/report/general-sale-sms",request) ? "active" : "" %>"><a href="<%=request.getContextPath()%>/report/general-sale-sms" class=""> <i class="fa fa-angle-right"></i> <span>Tổng hợp doanh thu SMS</span> </a></li>
                                </sec:authorize>
                                <sec:authorize access="hasRole('ROLE_REPORT_DETAIL_SALE_SMS')">
                                <li class="<%= isActive(request.getContextPath()+"/report/detail-sale-sms",request) ? "active" : "" %>"><a href="<%=request.getContextPath()%>/report/detail-sale-sms" class=""> <i class="fa fa-angle-right"></i> <span>Chi tiết doanh thu SMS</span> </a></li>
                                </sec:authorize>
                                <sec:authorize access="hasRole('ROLE_REPORT_GENERAL_TRADE')">
                                <li class="<%= isActive(request.getContextPath()+"/report/general-trade",request) ? "active" : "" %>"><a href="<%=request.getContextPath()%>/report/general-trade" class=""> <i class="fa fa-angle-right"></i> <span>Tổng hợp lượng giao dịch</span> </a></li>
                                </sec:authorize>
                                <sec:authorize access="hasRole('ROLE_REPORT_DETAIL_TRADE')">
                                <li class="<%= isActive(request.getContextPath()+"/report/detail-trade",request) ? "active" : "" %>"><a href="<%=request.getContextPath()%>/report/detail-trade" class=""> <i class="fa fa-angle-right"></i> <span>Chi tiết lượng giao dịch</span> </a></li>
                                </sec:authorize>
                                <sec:authorize access="hasRole('ROLE_REPORT_ADD_AND_DELETE_MSISDN')">
                                <li class="<%= isActive(request.getContextPath()+"/report/crud-msisdn",request) ? "active" : "" %>"><a href="<%=request.getContextPath()%>/report/crud-msisdn" class=""> <i class="fa fa-angle-right"></i> <span>Thêm và xóa số</span> </a></li>
                                </sec:authorize>
                            </ul>
                        </li>
                        </sec:authorize>
                        <sec:authorize access="hasAnyRole('ROLE_CATEGORY_GROUP_PRICE_VIEW','ROLE_CATEGORY_GROUP_YEAR_VIEW','ROLE_CATEGORY_GROUP_MSISDN_VIEW')">
                            <li class="<%= isActive(request.getContextPath()+"/category",request) ? "active" : "" %>"><a href="#" class=""> <i class="fa fa-file-text icon"> <b class="bg-primary"></b> </i> <span class="pull-right"> <i class="fa fa-angle-down text"></i> <i class="fa fa-angle-up text-active"></i> </span> <span>Danh mục</span> </a>
                                <ul class="nav lt">
                                    <sec:authorize access="hasRole('ROLE_CATEGORY_GROUP_PRICE_VIEW')">
                                        <li class="<%= isActive(request.getContextPath()+"/category/group-price",request) ? "active" : "" %>"><a href="<%=request.getContextPath()%>/category/group-price/list" class=""> <i class="fa fa-angle-right"></i> <span>Sim theo giá</span> </a></li>
                                    </sec:authorize>
                                    <sec:authorize access="hasRole('ROLE_CATEGORY_GROUP_YEAR_VIEW')">
                                        <li class="<%= isActive(request.getContextPath()+"/category/group-year",request) ? "active" : "" %>"><a href="<%=request.getContextPath()%>/category/group-year/list" class=""> <i class="fa fa-angle-right"></i> <span>Sim theo năm sinh</span> </a></li>
                                    </sec:authorize>
                                    <sec:authorize access="hasRole('ROLE_CATEGORY_GROUP_MSISDN_VIEW')">
                                        <li class="<%= isActive(request.getContextPath()+"/category/group-type",request) ? "active" : "" %>"><a href="<%=request.getContextPath()%>/category/group-type/list" class=""> <i class="fa fa-angle-right"></i> <span>Sim theo loại</span> </a></li>
                                    </sec:authorize>
                                </ul>
                            </li>
                        </sec:authorize>
                        <sec:authorize access="hasAnyRole('ROLE_CONTENT_CATEGORY_VIEW','ROLE_CONTENT_ARTICLE_VIEW')">
                            <li class="<%= isActive(request.getContextPath()+"/content",request) ? "active" : "" %>"><a href="#" class=""> <i class="fa fa-file-text icon"> <b class="bg-primary"></b> </i> <span class="pull-right"> <i class="fa fa-angle-down text"></i> <i class="fa fa-angle-up text-active"></i> </span> <span>Quản lý nội dung</span> </a>
                                <ul class="nav lt">
                                    <sec:authorize access="hasRole('ROLE_CONTENT_CATEGORY_VIEW')">
                                        <li class="<%= isActive(request.getContextPath()+"/content/category",request) ? "active" : "" %>"><a href="<%=request.getContextPath()%>/content/category/list" class=""> <i class="fa fa-angle-right"></i> <span>Chuyên mục</span> </a></li>
                                    </sec:authorize>
                                    <sec:authorize access="hasRole('ROLE_CONTENT_ARTICLE_VIEW')">
                                        <li class="<%= isActive(request.getContextPath()+"/content/article",request) ? "active" : "" %>"><a href="<%=request.getContextPath()%>/content/article/list" class=""> <i class="fa fa-angle-right"></i> <span>Bài viết</span> </a></li>
                                    </sec:authorize>
                                </ul>
                            </li>
                        </sec:authorize>
                        <sec:authorize access="hasAnyRole('ROLE_SYSTEM_USER_VIEW','ROLE_SYSTEM_GROUP_VIEW','ROLE_SYSTEM_LOG_VIEW')">
                        <li class="<%= isActive(request.getContextPath()+"/system",request) ? "active" : "" %>"><a href="#" class=""> <i class="fa fa-cogs icon"> <b class="bg-warning"></b> </i> <span class="pull-right"> <i class="fa fa-angle-down text"></i> <i class="fa fa-angle-up text-active"></i> </span> <span>Quản trị hệ thống</span> </a>
                            <ul class="nav lt">
                                <sec:authorize access="hasRole('ROLE_SYSTEM_USER_VIEW')">
                                <li class="<%= isActive(request.getContextPath()+"/system/user",request) ? "active" : "" %>"><a href="<%=request.getContextPath()%>/system/user/list" class=""> <i class="fa fa-angle-right"></i> <span>Người dùng</span> </a></li>
                                </sec:authorize>
                                <sec:authorize access="hasRole('ROLE_SYSTEM_GROUP_VIEW')">
                                <li class="<%= isActive(request.getContextPath()+"/system/group",request) ? "active" : "" %>"><a href="<%=request.getContextPath()%>/system/group/list" class=""> <i class="fa fa-angle-right"></i> <span>Nhóm quyền</span> </a></li>
                                </sec:authorize>
                                <sec:authorize access="hasRole('ROLE_SYSTEM_LOG_VIEW')">
                                <li class="<%= isActive(request.getContextPath()+"/system/history",request) ? "active" : "" %>"><a href="<%=request.getContextPath()%>/system/history/list" class=""> <i class="fa fa-angle-right"></i> <span>Lịch sử hệ thống</span> </a></li>
                                </sec:authorize>
                            </ul>
                        </li>
                        </sec:authorize>
                        <li class="<%= isActive(request.getContextPath()+"/history",request) ? "active" : "" %>"><a href="<%=request.getContextPath()%>/history">
                            <i class="fa fa-clock-o icon">
                                <b class="bg-danger dker"></b>
                            </i><span>Lịch sử của tôi</span></a>
                        </li>
                    </ul>
                </nav>
            </div>
        </section>
        <%--<footer class="footer lt hidden-xs b-light">--%>
        <footer class="footer lt hidden-xs b-t b-dark">
            <%--<a href="#nav" data-toggle="class:nav-xs" class="pull-right btn btn-sm btn-default btn-icon">--%>
                <%--<i class="fa fa-angle-left text"></i><i class="fa fa-angle-right text-active"></i>--%>
            <%--</a>--%>
            <a href="#nav" id="changeNavXS" data-toggle="class:nav-xs" class="pull-right btn btn-smbtn-dark btn-icon">
                <i class="fa fa-angle-left text"></i>
                <i class="fa fa-angle-right text-active"></i>
            </a>
        </footer>

    </section>
</aside>

<script type="text/javascript">
    $(document).ready(function() {
        $('#changeNavXS').click(function (){
            $.ajax({
                type: "get",
                url: "<%=request.getContextPath()%>/change-nav",
                success: function(msg){
                }
            });
        });
        <%--$.ajax({--%>
            <%--type: "get",--%>
            <%--url: "<%=request.getContextPath()%>/sun-moon",--%>
            <%--success: function(msg){--%>
            <%--}--%>
        <%--});--%>

    });
</script>