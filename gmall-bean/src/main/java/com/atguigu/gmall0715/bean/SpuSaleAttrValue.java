package com.atguigu.gmall0715.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

@Data
public class SpuSaleAttrValue implements Serializable{

    @Id
    @Column
    String id ;

    @Column
    String spuId;

    @Column
    String saleAttrId;

    @Column
    String saleAttrValueName;

    //销售属性值    判断当前销售属性值是否被选中
    @Transient
    String isChecked;

}
