package com.test.Shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class AuthenticationFilter extends FormAuthenticationFilter{
    private String verifyCodeParam = "verifyCodeParam";
    private String loginUrl = "/login.jsp";
    private static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    public AuthenticationFilter(){

    }

    public String getVerifyCodeParam() {
        return verifyCodeParam;
    }

    public void setVerifyCodeParam(String verifyCodeParam) {
        this.verifyCodeParam = verifyCodeParam;
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse response) {
        String username = getUsername(servletRequest);
        String password = getPassword(servletRequest);
        String vcode = WebUtils.getCleanParam(servletRequest, "verifyCode");
        String auth = WebUtils.getCleanParam(servletRequest, "auth");
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String loginUrl = request.getServletPath();
        String userIP = request.getServerName();
        Boolean isAuth = true;
        this.loginUrl = loginUrl;
        if (auth != null && auth.equals("0")) {
            isAuth = false;
        }
        return new ShiroMyToken(username,password,vcode,isAuth,userIP);
    }

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        logger.info("进来了---------onAccessDenied");
        if(this.isLoginRequest(request,response)){
            logger.info("允许进入");
            return this.isLoginSubmission(request,response)?this.executeLogin(request,response):true;
        }else {
            System.out.println("不是登录请求" + this.loginUrl);
            HttpServletRequest req = (HttpServletRequest)request;
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print("<script type='text/javascript'>window.parent.location='" + req.getContextPath() + this.loginUrl + "'</script>");
            return false;
        }
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        logger.info("进来了---------onLoginSuccess");
        System.out.println("in onLoginSuccess==========");
        SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
        Session session = subject.getSession();
        if (savedRequest != null && savedRequest.getMethod().equalsIgnoreCase("GET")) {
            session.setAttribute("toPath", savedRequest.getRequestUrl());
        } else {
            session.setAttribute("toPath", ((HttpServletRequest)request).getContextPath() + "/index.jsp");
        }

        WebUtils.issueRedirect(request, response, super.getSuccessUrl(), (Map)null);
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        logger.info("进来了---------onLoginFailure");
        if (e.getClass().isInstance(new IncorrectCredentialsException())) {
            request.setAttribute("FAILMSG", "验证码错误");
        } else if (e.getClass().isInstance(new ExcessiveAttemptsException())) {
            request.setAttribute("FAILMSG", "CA证书认证失败");
        } else if (e.getClass().isInstance(new UnknownAccountException())) {
            request.setAttribute("FAILMSG", "用户名或密码错误");
        } else if (e.getClass().isInstance(new DisabledAccountException())) {
            System.out.println("您的帐号已被禁用");
            request.setAttribute("FAILMSG", "您的帐号已被禁用");
        } else if (e.getClass().isInstance(new LockedAccountException())) {
            System.out.println("您的帐号已被锁定");
            request.setAttribute("FAILMSG", "您的帐号已被锁定");
        }
        e.printStackTrace();
        return true;
    }
}
