package com.test.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

import java.io.Serializable;
import java.util.Date;

public class BaseEntity implements Serializable{
    private static final long serialVersionUID = 7754589602587604629L;

    @Column(name = "id",type = MySqlTypeConstant.BIGINT,length = 10,isNull=false,isKey = true,isAutoIncrement=true)
    private Long id;
    @Column(name = "createDate" ,type = MySqlTypeConstant.DATETIME)
    private Date createDate;
    @Column(name = "modifyDate" ,type = MySqlTypeConstant.DATETIME)
    private Date modifyDate;
    @Column(name = "createUser",type = MySqlTypeConstant.VARCHAR,length = 100)
    private String createUser;
    @Column(name = "modifyUser",type = MySqlTypeConstant.VARCHAR,length = 100)
    private String modifyUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }
}
