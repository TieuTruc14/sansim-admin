package com.osp.security;


/**
 * Created by tieut on 10/17/2017.
 */
import com.osp.model.User;
import com.osp.web.service.logaccess.LogAccessService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    LogAccessService logAccessService;
    private Logger logger= LogManager.getLogger(MyLogoutSuccessHandler.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Authentication authentication) throws IOException, ServletException {
        if (authentication != null && authentication.getDetails() != null) {
            try {
                User user=(User)authentication.getPrincipal();
                if(user!=null && user.getId()!=null){
                    WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) authentication.getDetails();
                    String remoteIpAddress = webAuthenticationDetails.getRemoteAddress();
                    logAccessService.addLogWithUserId(user.getId(),"Đăng xuất","Hệ thống",remoteIpAddress);
                }
                httpServletRequest.getSession().invalidate();
            } catch (Exception e) {
                logger.error("Have an error onLogoutSuccess:"+e.getMessage());
            }
        }
        String urlRedirect=httpServletRequest.getContextPath()+"/login";
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        //redirect to login
        httpServletResponse.sendRedirect(urlRedirect);
    }

}
