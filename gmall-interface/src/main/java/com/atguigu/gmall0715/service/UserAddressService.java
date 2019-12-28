package com.atguigu.gmall0715.service;


import com.atguigu.gmall0715.bean.UserAddress;

import java.util.List;

public interface UserAddressService {
    /**
     * 根基用户的id查询用户地址
     * @param userId
     * @return
     */
    List<UserAddress> findUserAddressByUserId(String userId);

}
