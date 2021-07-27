package com.imooc.service.impl;

import com.imooc.bo.AddressBO;
import com.imooc.common.enums.YesOrNoEnum;
import com.imooc.mapper.UserAddressMapper;
import com.imooc.pojo.UserAddress;
import com.imooc.service.AddressService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

public class AddressImpl implements AddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private Sid sid;

    @Override
    public List<UserAddress> queryAll(String userId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        return userAddressMapper.select(userAddress);
    }

    @Override
    public void addNewUserAddress(AddressBO addressBO) {
        // 1. 判断当前用户是否存在默认地址,如果没有则设置为默认地址
        int isDefault = 0;
        List<UserAddress> addressList = this.queryAll(addressBO.getUserId());
        if (CollectionUtils.isEmpty(addressList)) {
            isDefault = 1;
        }

        // 2. 生成addressId
        String addressId = sid.nextShort();
        UserAddress newAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO,newAddress);

        newAddress.setId(addressId);
        newAddress.setIsDefault(isDefault);
        newAddress.setCreatedTime(new Date());
        newAddress.setUpdatedTime(new Date());

        userAddressMapper.insert(newAddress);
    }

    @Override
    public void updateUserAddress(AddressBO addressBO) {
        String addressId = addressBO.getAddressId();

        UserAddress pendingAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO,pendingAddress);

        pendingAddress.setId(addressId);
        pendingAddress.setUpdatedTime(new Date());

        userAddressMapper.updateByPrimaryKeySelective(pendingAddress);
    }

    @Override
    public void deleteUserAddress(String userId, String addressId) {

        UserAddress queryAddress = new UserAddress();
        queryAddress.setUserId(userId);
        queryAddress.setId(addressId);

        userAddressMapper.delete(queryAddress);

        // 如果存在下一个参数,则设置为默认地址
        List<UserAddress> userAddresses = this.queryAll(userId);
        for (UserAddress userAddress : userAddresses) {
            if (YesOrNoEnum.YES.getType().equals(userAddress.getIsDefault())) {
                return;
            }
        }

        userAddresses.stream().findFirst().ifPresent(address -> {
            address.setIsDefault(YesOrNoEnum.YES.getType());
            userAddressMapper.updateByPrimaryKeySelective(address);
        });

    }

    @Override
    public void updateUserAddressToBeDefault(String userId, String addressId) {
        // 1. 查询默认地址 , 设置为不默认
        UserAddress queryAddress = new UserAddress();
        queryAddress.setUserId(userId);
        queryAddress.setIsDefault(YesOrNoEnum.YES.getType());
        List<UserAddress> list = userAddressMapper.select(queryAddress);
        for (UserAddress userAddress : list) {
            userAddress.setIsDefault(YesOrNoEnum.NO.getType());
            userAddressMapper.updateByPrimaryKeySelective(userAddress);
        }

        // 2. 根据地址id修改为默认的地址
        UserAddress defaultAddress = new UserAddress();
        defaultAddress.setId(addressId);
        defaultAddress.setUserId(userId);
        defaultAddress.setIsDefault(YesOrNoEnum.YES.getType());
        userAddressMapper.updateByPrimaryKeySelective(defaultAddress);
    }
}
