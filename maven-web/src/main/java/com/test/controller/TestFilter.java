package com.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TestFilter implements Filter{

    private static final Logger logger = LoggerFactory.getLogger(TestFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse rep = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        rep.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        rep.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        rep.setHeader("Access-Control-Max-Age", "0");
        rep.setHeader("P3P", "CP=CAO PSA OUR");
        rep.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
        rep.setHeader("Access-Control-Allow-Credentials", "true");
        rep.setHeader("XDomainRequestAllowed","1");
        logger.info("过滤器进来了-----------------------");
        chain.doFilter(request,response);

    }

    @Override
    public void destroy() {

    }
}
