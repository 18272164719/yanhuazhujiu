package com.test.util;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 3614360416661305543L;
    private boolean success = true;
    private String msgcode = "";
    private String msg = "";
    private Object data = "";

    public Message() {
    }

    public Message(String msg) {
        this.msg = msg;
    }

    public Message(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public Message(boolean success, String msg, Object data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsgcode() {
        return this.msgcode;
    }

    public void setMsgcode(String msgcode) {
        this.msgcode = msgcode;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static enum MsgCode {
        ok("成功"),
        err01("错误01"),
        err02("错误02");

        private String name;

        private MsgCode(String s) {
            this.name = s;
        }

        public String getName() {
            return this.name;
        }
    }
}