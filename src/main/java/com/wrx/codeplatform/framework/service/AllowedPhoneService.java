package com.wrx.codeplatform.framework.service;

import com.wrx.codeplatform.domain.framework.sql.phone.AllowedPhone;

/**
 * @author 魏荣轩
 * @date 2022/5/4 14:02
 */
public interface AllowedPhoneService {
    /**
     * 根据数据ID查询数据
     *
     * @param id  id
     * @return    手机数据
     */
    AllowedPhone selectPhoneById(int id);

    /**
     * 根据手机号查询数据
     *
     * @param phone 手机号
     * @return      数据
     */
    AllowedPhone selectPhoneByPhone(String phone);

    /**
     * 手机号是否被允许注册
     *
     * @param phone  手机号
     * @return       是否可以注册
     */
    boolean phoneIsAllowed(String phone);
}
