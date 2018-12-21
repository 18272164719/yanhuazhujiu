package com.test.controller;

import com.test.annotation.MyAutowired;
import com.test.annotation.MyController;
import com.test.annotation.MyRequestMapping;
import com.test.annotation.MyRequestParam;
import com.test.service.IEnjoyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@MyController
@MyRequestMapping("/home")
public class TestController {

    @MyAutowired
    private IEnjoyService enjoyService;


    @MyRequestMapping("/query")
    public void query(HttpServletRequest req, HttpServletResponse resp,
                      @MyRequestParam("name") String name, @MyRequestParam("age") String age) {

        String result = enjoyService.query(name, age);
        try {
            PrintWriter pw = resp.getWriter();
            pw.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
