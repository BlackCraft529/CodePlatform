package com.wrx.codeplatform.framework.service.impl;

import com.wrx.codeplatform.domain.framework.sql.phone.AllowedPhone;
import com.wrx.codeplatform.framework.mapper.AllowedPhoneMapper;
import com.wrx.codeplatform.framework.service.AllowedPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 魏荣轩
 * @date 2022/5/4 14:03
 */
@Service("allowedPhoneService")
public class AllowedPhoneServiceImpl implements AllowedPhoneService {

    @Autowired
    private AllowedPhoneMapper allowedPhoneMapper;

    /**
     * 根据数据ID查询数据
     *
     * @param id id
     * @return 手机数据
     */
    @Override
    public AllowedPhone selectPhoneById(int id) {
        return allowedPhoneMapper.selectPhoneById(id);
    }

    /**
     * 根据手机号查询数据
     *
     * @param phone 手机号
     * @return 数据
     */
    @Override
    public AllowedPhone selectPhoneByPhone(String phone) {
        return allowedPhoneMapper.selectPhoneByPhone(phone);
    }

    /**
     * 手机号是否被允许注册
     *
     * @param phone 手机号
     * @return 是否可以注册
     */
    @Override
    public boolean phoneIsAllowed(String phone) {
        return allowedPhoneMapper.selectPhoneByPhone(phone)!=null;
    }
}
