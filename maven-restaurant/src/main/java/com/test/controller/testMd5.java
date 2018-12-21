package com.test.controller;

import org.apache.commons.codec.digest.DigestUtils;

public class testMd5 {
    public static void main(String[] args) {
        String password = "1111";
        String pwd = DigestUtils.md5Hex(password);
        System.out.println("pwd:-------"+pwd);


    }
}
