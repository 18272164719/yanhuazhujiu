package com.test.Shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class AuthenticationFilter extends FormAuthenticationFilter {

    private String loginUrl = "/login.jsp";


    protected org.apache.shiro.authc.AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse)
    {
        String username = getUsername(servletRequest);
        String password = getPassword(servletRequest);

        return createToken(username, password,servletRequest,servletResponse);
    }

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception
    {
        if (isLoginRequest(request, response))
        {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            }
            return true;
        }
        HttpServletRequest req = (HttpServletRequest)request;
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().print("<script type='text/javascript'>window.parent.location='" + req

                .getContextPath() + this.loginUrl + "'</script>");


        return false;
    }

    protected boolean onLoginSuccess(org.apache.shiro.authc.AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response)
            throws Exception
    {
        SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
        Session session = subject.getSession();
        if ((savedRequest != null) && (savedRequest.getMethod().equalsIgnoreCase("GET"))) {
            session.setAttribute("toPath", savedRequest.getRequestUrl());
        } else {
            session.setAttribute("toPath", ((HttpServletRequest)request).getContextPath() + "/index.jsp");
        }
        WebUtils.issueRedirect(request, response, super.getSuccessUrl(), null);



        return false;
    }

    protected boolean onLoginFailure(org.apache.shiro.authc.AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response)
    {
        System.out.println("e:" + e.getClass().getName());
        if (e.getClass().isInstance(new IncorrectCredentialsException()))
        {
            request.setAttribute("FAILMSG", "验证码错误");
        }
        else if (e.getClass().isInstance(new ExcessiveAttemptsException()))
        {
            request.setAttribute("FAILMSG", "CA证书认证失败");
        }
        else if (e.getClass().isInstance(new UnknownAccountException()))
        {
            request.setAttribute("FAILMSG", "用户名或密码错误");
        }
        else if (e.getClass().isInstance(new DisabledAccountException()))
        {
            System.out.println("您的帐号已被禁用");
            request.setAttribute("FAILMSG", "您的帐号已被禁用");
        }
        else if (e.getClass().isInstance(new LockedAccountException()))
        {
            System.out.println("您的帐号已被锁定");
            request.setAttribute("FAILMSG", "您的帐号已被锁定");
        }
        e.printStackTrace();

        return true;
    }
}
