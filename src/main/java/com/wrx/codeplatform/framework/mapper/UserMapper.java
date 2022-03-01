package com.wrx.codeplatform.framework.mapper;

import com.wrx.codeplatform.domain.user.SysUser;
import org.springframework.stereotype.Repository;

/**
 * @author 魏荣轩
 * @date 2022/2/9 21:38
 */
@Repository
public interface UserMapper {
    /**
     * 根据用户名查询用户
     *
     * @param userName
     * @return
     */
    SysUser selectByName(String userName);

}
