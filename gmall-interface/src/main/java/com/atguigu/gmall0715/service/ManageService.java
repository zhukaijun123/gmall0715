package com.atguigu.gmall0715.service;

import com.atguigu.gmall0715.bean.*;

import java.util.List;

public interface ManageService {
    /**
     * 查询一级分类的数据
     * @return
     */
     List<BaseCatalog1> getCatalog1();

    /**
     * 通过一级分类id查询二级分类的数据
     * @param cataLog1Id
     * @return
     */
    List<BaseCatalog2> getCatalog2(String cataLog1Id);

    /**
     * 通过二级分类对象来查询数据
     * @param baseCatalog2
     * @return
     */
    List<BaseCatalog2> getCatalog2(BaseCatalog2 baseCatalog2);

    /**
     * 通过三级分类对象查询数据
     * @param baseCatalog3
     * @return
     */
    List<BaseCatalog3> getCatalog3(BaseCatalog3 baseCatalog3);

    /**
     * 通过三级分类id查询数据
     * @param baseAttrInfo
     * @return
     */
    List<BaseAttrInfo> getAttrInfoList(BaseAttrInfo baseAttrInfo);


    /**
     * 根据三级分类id  来添加平台属性以及平台属性值  然后点击保存 价格数据添加到数据库中
     * 保存平台属性   平台属性值
     * @param baseAttrInfo
     */
    void saveAttrInfo(BaseAttrInfo baseAttrInfo);

    /**
     * 根据attrId 回显数据
     * @param attrId
     * @return
     */
    List<BaseAttrValue> getAttrValueList(String attrId);


    /**
     * 通过attrId 来查询baseAttrInfo
     * @param attrId
     * @return
     */
    BaseAttrInfo getBaseAttrInfo(String attrId);

    /**
     * 根据三级分类id 查询 商品信息
     * @param catalog3Id
     * @return
     */
    List<SpuInfo> getSpuInfoList(String catalog3Id);

    /**
     * 查询所有的丝销售属性
     * @return
     */
    List<BaseSaleAttr> getBaseSaleAttrList();

    /**
     * 保存spuInfo
     * @param spuInfo
     */
    void saveSpuInfo(SpuInfo spuInfo);

    /**
     * 根据 spuId 查询列表(spku 回显图片)
     * @param spuImage
     * @return
     */
    List<SpuImage> getSpuImageList(SpuImage spuImage);

    /**
     * 通过三级分类进行查询
     * @param catalog3Id
     * @return
     */
    List<BaseAttrInfo> getAttrInfoList(String catalog3Id);

    /**
     * 通过spuId 查询销售属性集合
     * @param spuId
     * @return
     */
    List<SpuSaleAttr> getSpuSaleAttrList(String spuId);

    /**
     * 保存商品信息
     */
    void saveSkuInfo(SkuInfo skuInfo);
}
