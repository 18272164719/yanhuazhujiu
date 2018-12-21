package com.test.entity;

import com.shyl.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_sys_role")
public class Role extends BaseEntity{


    private static final long serialVersionUID = -5124863842247919311L;

    private String code;
    private String name;

    @Column(length=20,unique = true, nullable = false)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    @Column(length=50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
