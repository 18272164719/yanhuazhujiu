package com.test.entity;

import com.shyl.common.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name="t_sys_user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 3492262378182264628L;

    private String empId;
    private String name;
    private String pwd;

    private Role role;

    public User(String empId,String name){
        this.empId=empId;
        this.name=name;
    }


    @Column(length=20,unique = true, nullable = false)
    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    @Column(length=50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length=50)
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleId", foreignKey = @ForeignKey(name = "null"))
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
