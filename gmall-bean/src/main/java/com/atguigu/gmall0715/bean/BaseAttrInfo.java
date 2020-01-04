package com.atguigu.gmall0715.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

//平台属性
@Data
public class BaseAttrInfo implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)//表示获取主键自增
    private String id;
    @Column
    private String attrName;
    @Column
    private String catalog3Id;

    @Transient //表示在数据库中没有的字段 但是在业务中需要
                //因为在数据显示时需要 三级分类id list<平台属性名称>  平台属性值名称
    private List<BaseAttrValue> attrValueList; //里面封装了平台属性值得信息  里面就有 平台属性值名称

}
