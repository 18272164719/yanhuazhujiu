package com.test.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;


@Table(name = "t_group_user")
public class GroupUser extends BaseEntity{
    private static final long serialVersionUID = -296912398783610553L;
    @Column(name = "groupId",type = MySqlTypeConstant.BIGINT,length = 10)
    private Group group;
    @Column(name = "userId",type = MySqlTypeConstant.BIGINT,length = 10)
    private User user;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
