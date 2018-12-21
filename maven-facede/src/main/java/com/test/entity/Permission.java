package com.test.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

/**
 * 权限表
 */
@Table(name = "t_permission")
public class Permission extends BaseEntity{
    private static final long serialVersionUID = -4661508666354457797L;

    @Column(name = "groupId",type = MySqlTypeConstant.BIGINT,length = 10)
    private Group group;
    @Column(name = "resourceId",type = MySqlTypeConstant.BIGINT,length = 10)
    private Resource resource;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
