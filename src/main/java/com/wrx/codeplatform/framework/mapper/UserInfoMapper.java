package com.wrx.codeplatform.framework.mapper;

import com.wrx.codeplatform.domain.framework.sql.user.UserInfo;
import org.springframework.stereotype.Repository;

/**
 * @author 魏荣轩
 * @date 2022/2/9 21:38
 */
@Repository
public interface UserInfoMapper {
    /**
     * 根据用户ID查询用户信息
     *
     * @param userId  用户ID
     * @return        用户信息
     */
    UserInfo selectByUserId(int userId);

    /**
     * 更新用户信息
     *
     * @param userInfo  用户信息
     * @return          影响条数
     */
    int updateUserInfo(UserInfo userInfo);

    /**
     * 插入新的用户信息
     *
     * @param userInfo  用户信息
     * @return          影响条数
     */
    int insertUserInfo(UserInfo userInfo);

    /**
     * 删除用户信息
     *
     * @param userId  userID
     * @return        影响条数
     */
    int deleteUserInfo(int userId);

}
