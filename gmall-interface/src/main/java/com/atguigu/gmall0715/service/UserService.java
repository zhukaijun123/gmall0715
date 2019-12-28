package com.atguigu.gmall0715.service;

import com.atguigu.gmall0715.bean.UserAddress;
import com.atguigu.gmall0715.bean.UserInfo;

import java.util.List;
//业务层接口
public interface UserService {
    /**
     * 查询所有用户信息
     * @return
     */
    List<UserInfo> findAll();

    /**
     * 根基用户的id查询用户地址
     * @param userId
     * @return
     */
    List<UserAddress> findUserAddressByUserId(String userId);

}
