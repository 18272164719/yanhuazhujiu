package com.test.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;


/**
 * 用户表
 */
@Table(name = "t_user")
public class User extends BaseEntity{
    private static final long serialVersionUID = -4476767557094240485L;

    @Column(name = "empId",type = MySqlTypeConstant.VARCHAR,length = 100)
    private String empId;
    @Column(name = "name",type = MySqlTypeConstant.VARCHAR,length = 100)
    private String name;
    @Column(name = "pwd",type = MySqlTypeConstant.VARCHAR,length = 100)
    private String pwd;
    @Column(name = "isLocked",type = MySqlTypeConstant.INT,length = 10)
    private Integer isLocked = Integer.valueOf(0);
    @Column(name = "errTimes",type = MySqlTypeConstant.INT,length = 100)
    private Integer errTimes = Integer.valueOf(0);
    @Column(name = "isDisabled",type = MySqlTypeConstant.INT,length = 10)
    private Integer isDisabled = Integer.valueOf(0);
    @Column(name = "projectCode",type = MySqlTypeConstant.VARCHAR,length = 100)
    private String projectCode;
    @Column(name = "currentSessionId",type = MySqlTypeConstant.VARCHAR,length = 100)
    private String currentSessionId;

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Integer isLocked) {
        this.isLocked = isLocked;
    }

    public Integer getErrTimes() {
        return errTimes;
    }

    public void setErrTimes(Integer errTimes) {
        this.errTimes = errTimes;
    }

    public Integer getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Integer isDisabled) {
        this.isDisabled = isDisabled;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getCurrentSessionId() {
        return currentSessionId;
    }

    public void setCurrentSessionId(String currentSessionId) {
        this.currentSessionId = currentSessionId;
    }
}
