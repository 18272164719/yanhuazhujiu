package com.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shyl.common.entity.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="tableVa")
public class TableVa extends BaseEntity{

    public enum Status {
        notUse("未使用"),
        use("使用中");

        private String name;
        Status(String name){this.name=name;}
        public String getName(){return name;}
    }
    /**
     * 餐桌编号
     */
    private String code ;
    /**
     * 餐桌名
     */
    private String name;
    /**
     * 菜数量
     */
    private Integer num;
    /**
     * 消费总金额
     */
    private BigDecimal sum;
    /**
     * 使用次数
     */
    private Integer useNum;
    /**
     * 状态
     */
    private Status status;


    private Variety variety;
    /**
     * 菜
     */
    private Set<Variety> varietys =new HashSet<>();

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
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public Integer getUseNum() {
        return useNum;
    }

    public void setUseNum(Integer useNum) {
        this.useNum = useNum;
    }
    @Transient
    public String getStatusName (){
        return this.status == null ?"": this.status.getName();
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tableVariety", joinColumns = @JoinColumn(name = "tableVaId", foreignKey = @ForeignKey(name = "null")), inverseJoinColumns = @JoinColumn(name = "varietyId", foreignKey = @ForeignKey(name = "null")))
    public Set<Variety> getVarietys() {
        return varietys;
    }
    public void setVarietys(Set<Variety> varietys) {
        this.varietys = varietys;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "varietyId", foreignKey = @ForeignKey(name = "null"))
    public Variety getVariety() {
        return variety;
    }

    public void setVariety(Variety variety) {
        this.variety = variety;
    }
}
