package com.test.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

/**
 * 组
 */
@Table(name = "t_group")
public class Group extends BaseEntity{
    private static final long serialVersionUID = 5275654471021036320L;

    @Column(name = "name",type = MySqlTypeConstant.VARCHAR,length = 100)
    private String name;
    @Column(name = "parentId",type = MySqlTypeConstant.BIGINT,length = 10)
    private Long parentId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
