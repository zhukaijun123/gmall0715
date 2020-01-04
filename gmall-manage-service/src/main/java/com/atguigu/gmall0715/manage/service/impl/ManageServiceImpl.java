package com.atguigu.gmall0715.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall0715.bean.*;

import com.atguigu.gmall0715.manage.mapper.*;
import com.atguigu.gmall0715.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private SpuInfoMapper spuInfoMapper;

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
    public List<SpuInfo> getSpuInfoList(SpuInfo spuInfo) {
        return null;
    }

}
