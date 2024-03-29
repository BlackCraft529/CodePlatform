package com.wrx.codeplatform.framework.service;

import com.wrx.codeplatform.domain.framework.sql.user.SysUser;
import com.wrx.codeplatform.domain.framework.sql.user.UserInfo;

import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/1 20:34
 */
public interface UserInfoService {

    /**
     * 根据角色ID查询角色信息
     *
     * @param roleId   角色Id
     * @return         角色信息列表
     */
    List<UserInfo> selectUserInfoByRoleId(int roleId);

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId  用户ID
     * @return        用户信息
     */
    UserInfo selectByUserId(int userId);

    /**
     * 根据用户账户查找用户信息
     *
     * @param sysUser   账户
     * @return          用户信息
     */
    UserInfo selectByUserAccount(SysUser sysUser);

    /**
     * 根据用户ID更新用户描述
     *
     * @param userId         用户ID
     * @param description    用户描述
     * @return               影响条数
     */
    int updateDescriptionByUserId(int userId, String description);

    /**
     * 更新用户邮箱
     *
     * @param userId  用户ID
     * @param email   邮箱
     * @return        影响条数
     */
    int updateEmailByUserId(int userId, String email);

    /**
     * 更新用户位置
     *
     * @param userId     用户ID
     * @param location   位置
     * @return           影响条数
     */
    int updateLocationByUserId(int userId, String location);

    /**
     * 更新用户学校
     *
     * @param userId    用户ID
     * @param school    学校
     * @return          影响条数
     */
    int updateSchoolByUserId(int userId, String school);

    /**
     * 更新用户昵称
     *
     * @param userId      用户ID
     * @param nickName    昵称
     * @return            影响条数
     */
    int updateNickNameByUserId(int userId, String nickName);

    /**
     * 更新用户信息-全部
     *
     * @param userInfo   用户实体类
     * @return           影响条数
     */
    int updateUserInfo(UserInfo userInfo);

    /**
     * 插入新的用户信息
     *
     * @param userInfo 用户数据信息
     * @return 影响条数
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
