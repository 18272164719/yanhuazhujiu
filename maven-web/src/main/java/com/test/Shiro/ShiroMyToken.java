package com.test.Shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

public class ShiroMyToken extends UsernamePasswordToken {

    private static final long serialVersionUID = -2396099768046957053L;

    private String vcode;
    private boolean isAuth = true;
    private String userIP;

    public ShiroMyToken (String username, String password, String vcode,boolean isAuth,String userIP){
        super(username,password);
        this.vcode = vcode;
        this.isAuth = isAuth;
        this.userIP = userIP;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean auth) {
        isAuth = auth;
    }

    public String getUserIP() {
        return userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }
}
