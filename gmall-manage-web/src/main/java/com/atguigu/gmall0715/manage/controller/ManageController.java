package com.atguigu.gmall0715.manage.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall0715.bean.*;
import com.atguigu.gmall0715.service.ManageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@Controller
@RestController
@CrossOrigin
public class ManageController {
    @Reference
    private ManageService manageService;

    //返回所有一级分类数据
    @RequestMapping("getCatalog1")
    public List<BaseCatalog1> getCatalog1(){
        return manageService.getCatalog1();
    }


    //返回所有二级分类的数据
    //localhost:8082/getCatalog2?catalog1Id=16
    //@requestParam   获取参数     对象传值  传递过来的参数与实体类的属性名称一致
    @RequestMapping("getCatalog2")
    //select * fron BaseCatalog2 where cataLog1Id = ?
    public List<BaseCatalog2> getCatalog2(String cataLog1Id,BaseCatalog2 baseCatalog2){
       // return manageService.getCatalog2(cataLog1Id);
        return  manageService.getCatalog2(baseCatalog2);
    }

    @RequestMapping("getCatalog3")
    //http://localhost:8082/getCatalog3?catalog2Id=14
    public List<BaseCatalog3> getCatalog3(String catalog2Id,BaseCatalog3 baseCatalog3){
        return  manageService.getCatalog3(baseCatalog3);
    }

    @RequestMapping("attrInfoList")
    //http://localhost:8082/attrInfoList?catalog3Id=108
    public List<BaseAttrInfo> attrInfoList(String catalog3Id,BaseAttrInfo baseAttrInfo){
        return manageService.getAttrInfoList(baseAttrInfo);
    }

    //页面传递数据是json 后台接收对象为java的Object 所以需要进行类型转换 由json -->Object
    //@RequestBody 将json---->转为Object     @ResponseBody   将Object-----> json
    @RequestMapping("saveAttrInfo")
    //接受前台传过来的数据 json
    public void saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo){
        //调用服务层
        manageService.saveAttrInfo(baseAttrInfo);

    }

    @RequestMapping("getAttrValueList")
    public List<BaseAttrValue> getAttrValueList(String attrId){
        //从功能上来讲
       // return manageService.getAttrValueList(attrId);
        //从业务来讲
        //select * from BaseAttrInfo where id = attrId
        BaseAttrInfo baseAttrInfo  = manageService.getBaseAttrInfo(attrId);
       return  baseAttrInfo.getAttrValueList();
    }



}












































