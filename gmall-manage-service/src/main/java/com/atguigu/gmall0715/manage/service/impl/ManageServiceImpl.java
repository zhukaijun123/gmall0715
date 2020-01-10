package com.atguigu.gmall0715.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall0715.bean.*;
import com.atguigu.gmall0715.manage.mapper.*;
import com.atguigu.gmall0715.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service

public class ManageServiceImpl implements ManageService{
    @Autowired
    private BaseCatalog1Mapper baseCatalog1Mapper;
    @Autowired
    private BaseCatalog2Mapper baseCatalog2Mapper;
    @Autowired
    private BaseCatalog3Mapper baseCatalog3Mapper;
    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;
    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;

    @Autowired //默认type
    private SpuInfoMapper spuInfoMapper;

    @Autowired
    private SpuImageMapper spuImageMapper;

    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;

    @Autowired
    private SpuSaleAttrValueMapper spuSaleAttrValueMapper;

    //@Autowired
    @Resource//也是注入   默然是按照name注入  如果没有name 则去找type
    private BaseSaleAttrMapper baseSaleAttrMapper;

    @Autowired
    private SkuAttrValueMapper skuAttrValueMapper;

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Autowired
    private SkuImageMapper skuImageMapper;

    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;


    //select * from BaseCatalog1
    @Override
    public List<BaseCatalog1> getCatalog1() {
        return baseCatalog1Mapper.selectAll();
    }

    @Override
    public List<BaseCatalog2> getCatalog2(String cataLog1Id) {
        //select * fron BaseCatalog2 where cataLog1Id = ?
        BaseCatalog2 baseCatalog2 = new BaseCatalog2();
        baseCatalog2.setCatalog1Id(cataLog1Id);
        return baseCatalog2Mapper.select(baseCatalog2);
    }

    @Override
    public List<BaseCatalog2> getCatalog2(BaseCatalog2 baseCatalog2) {
        return baseCatalog2Mapper.select(baseCatalog2);
    }

    @Override
    public List<BaseCatalog3> getCatalog3(BaseCatalog3 baseCatalog3) {
        return baseCatalog3Mapper.select(baseCatalog3);
    }

    @Override
    public List<BaseAttrInfo> getAttrInfoList(BaseAttrInfo baseAttrInfo) {
       //select * from baseAttrInfo where catalog3Id = ?   sql语句执行完成之后只显示运行内存 和机身内存
      // baseAttrInfoMapper.xxx();
        return baseAttrInfoMapper.select(baseAttrInfo);
    }

    @Override
    @Transactional
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        //baseAttrInfo baseAttrValue、
        //前端传过来的数据 到了这里 都保存在BaseAttrInfo中
        //保存   /   修改
            //看baseAttrInfo  id 是否存在   有id  则说明有数据  进行修改  没有id 则是进行保存
        if(baseAttrInfo.getId() != null && baseAttrInfo.getId().length() > 0){
            //进行修改操作
            baseAttrInfoMapper.updateByPrimaryKeySelective(baseAttrInfo);
        }else{
            //直接保存平台属性
            baseAttrInfoMapper.insertSelective(baseAttrInfo);
        }

       //int i = 1/0;
        //BaseAttrValue  的修改
        //先将原有的数据删除   然后再新增数据
        BaseAttrValue baseAttrValueDel = new BaseAttrValue();
        baseAttrValueDel.setAttrId(baseAttrInfo.getId());
        baseAttrValueMapper.delete(baseAttrValueDel);
        //保存平台属性值
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        //遍历集合
        //判断集合不能为空
        //要先判断对象不能为空 再判断集合的长度>0
        if(attrValueList !=null && attrValueList.size() >0){
            for(BaseAttrValue baseAttrValue : attrValueList){
                //平台属性值id 主键自增   平台属性id  baseAttrValue.id = baseAttrInfo.id
                baseAttrValue.setAttrId(baseAttrInfo.getId());//baseAttrInfo.getId() 获取当前主键自增值
                //其实插入的是平台属性值得 名
                baseAttrValueMapper.insertSelective(baseAttrValue);
            }
        }

    }

    @Override
    public List<BaseAttrValue> getAttrValueList(String attrId) {

        //select * from BaseAttrValue where attrId=?
        BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(attrId);
       return  baseAttrValueMapper.select(baseAttrValue);

    }

    @Override
    public BaseAttrInfo getBaseAttrInfo(String attrId) {
        //select * from BaseAttrInfo where id= attrId
        BaseAttrInfo baseAttrInfo = baseAttrInfoMapper.selectByPrimaryKey(attrId);
        //查询平台属性值集合
        baseAttrInfo.setAttrValueList(getAttrValueList(attrId));//调用的是上面重载的方法 public List<BaseAttrValue> getAttrValueList(String attrId)
        return baseAttrInfo;
    }

    @Override
    public List<SpuInfo> getSpuInfoList(String catalog3Id) {
        //select * from sup_info where catalog3_id=62
        SpuInfo spuInfo = new SpuInfo();
        return spuInfoMapper.select(spuInfo);
    }

    @Override
    public List<BaseSaleAttr> getBaseSaleAttrList() {
        return baseSaleAttrMapper.selectAll();
    }

    @Override
    @Transactional
    public void saveSpuInfo(SpuInfo spuInfo) {
        /**
         * 要用到4张表
         * spuInfo
         * spuImage
         * spuSaleAttr
         * spuSaleAttr
         */
        spuInfoMapper.insertSelective(spuInfo);
        //spuImage
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        if(spuImageList != null && spuImageList.size() >0){
            for(SpuImage spuImage : spuImageList){
                //因为在SpuImage 中有一个属性 ：商品id （spuId） 在没有保存之前我们没有  所以要获取
                spuImage.setSpuId(spuInfo.getId());
                spuImageMapper.insertSelective(spuImage);
            }
        }

        //销售属性
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        if(spuSaleAttrList != null && spuSaleAttrList.size() >0){
            for(SpuSaleAttr spuSaleAttr : spuSaleAttrList){
                //因为在SpuSaleAttr 中有一个属性 ：商品id （spuId） 在没有保存之前我们没有  所以要获取
                spuSaleAttr.setSpuId(spuInfo.getId());
                   spuSaleAttrMapper.insertSelective(spuSaleAttr);
                //销售属性值
                List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
                if(spuSaleAttrValueList != null && spuSaleAttrValueList.size() >0){
                    for(SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList){
                        spuSaleAttrValue.setSpuId(spuInfo.getId());
                        spuSaleAttrValueMapper.insertSelective(spuSaleAttrValue);
                    }
                }
            }
        }




    }

    @Override
    public List<SpuImage> getSpuImageList(SpuImage spuImage) {
        //select * from SpuImage where spuId = ? (spuImage.getSupId()) 通用mapper 对单张表进行CRUD

        return spuImageMapper.select(spuImage);
    }


    @Override
    public List<BaseAttrInfo> getAttrInfoList(String catalog3Id) {
        //select * from base_attr_info bai inner join base_attr_value bav ON bai.id = bav.id where bai.catalog3 = 61;
        return baseAttrInfoMapper.selectBaseAttrInfoListByCatalog3Id (catalog3Id);

    }

    @Override
    public List<SpuSaleAttr> getSpuSaleAttrList(String spuId) {
    //SELECT * from spu_sale_attr ssa INNER JOIN spu_sale_attr_value ssav ON ssa.spu_id = ssav.spu_id AND ssa.sale_attr_id = ssav.sale_attr_id
    //    WHERE ssa.spu_id=61;

        return spuSaleAttrMapper.selectSpuSaleAttrList(spuId);
    }

    @Override
    @Transactional //4张表关联 需要添加事务
    public void saveSkuInfo(SkuInfo skuInfo) {
        //skuInfo
        skuInfoMapper.insertSelective(skuInfo);

        //SkuAttrValue 平台属性
        List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
        if(skuAttrValueList != null && skuAttrValueList.size() >0){
            for(SkuAttrValue skuAttrValue : skuAttrValueList){
                skuAttrValue.setSkuId(skuInfo.getId());
                skuAttrValueMapper.insertSelective(skuAttrValue);
            }
        }
        //skuSaleAttrValue 销售属性
        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
        if(skuSaleAttrValueList!= null && skuSaleAttrValueList.size() >0){
            for(SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList){
                skuSaleAttrValue.setSkuId(skuInfo.getId());
                skuSaleAttrValueMapper.insertSelective(skuSaleAttrValue);
            }
        }
        //skuimage 图片表
        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        if(skuImageList!= null && skuImageList.size()>0){
            for(SkuImage skuImage : skuImageList){
                skuImage.setSkuId(skuInfo.getId());
                skuImageMapper.insertSelective(skuImage);
            }
        }

    }


}
