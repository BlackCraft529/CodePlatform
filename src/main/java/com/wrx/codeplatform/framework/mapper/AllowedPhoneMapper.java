package com.wrx.codeplatform.framework.mapper;

import com.wrx.codeplatform.domain.framework.sql.phone.AllowedPhone;
import org.springframework.stereotype.Repository;

/**
 * @author 魏荣轩
 * @date 2022/5/4 14:00
 */
@Repository
public interface AllowedPhoneMapper {

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

}
