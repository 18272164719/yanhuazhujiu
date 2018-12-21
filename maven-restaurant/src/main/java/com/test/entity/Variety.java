package com.test.entity;

import com.shyl.common.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

@Entity
@Table(name="variety")
public class Variety extends BaseEntity {

    /**
     * 编码
     */
    private String code ;
    /**
     * 菜名
     */
    private String name;
    /**
     * 价格
     */
    private BigDecimal price;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
