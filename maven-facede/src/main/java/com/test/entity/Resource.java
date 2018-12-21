package com.test.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

/**
 * 资源表
 */
@Table(name = "t_resource")
public class Resource extends BaseEntity{
    private static final long serialVersionUID = 3493704050670128043L;

    @Column(name = "parentId",type = MySqlTypeConstant.BIGINT,length = 10)
    private Long parentId;
    @Column(name = "name",type = MySqlTypeConstant.VARCHAR,length = 100)
    private String name;
    @Column(name = "type",type = MySqlTypeConstant.VARCHAR,length = 100)
    private String type;
    @Column(name = "sort",type = MySqlTypeConstant.BIGINT,length = 10)
    private Integer sort;
    @Column(name = "url",type = MySqlTypeConstant.VARCHAR,length = 100)
    private String url;
    @Column(name = "permCode",type = MySqlTypeConstant.VARCHAR,length = 100)
    private String permCode;
    @Column(name = "treePath",type = MySqlTypeConstant.VARCHAR,length = 100)
    private String treePath;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermCode() {
        return permCode;
    }

    public void setPermCode(String permCode) {
        this.permCode = permCode;
    }

    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }
}
