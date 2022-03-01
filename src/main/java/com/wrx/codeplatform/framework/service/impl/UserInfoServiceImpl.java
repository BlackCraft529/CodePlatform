package com.wrx.codeplatform.framework.service.impl;

import com.wrx.codeplatform.domain.framework.sql.user.UserInfo;
import com.wrx.codeplatform.framework.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * @author 魏荣轩
 * @date 2022/3/1 20:42
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
    /**
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @Override
    public UserInfo selectByUserId(int userId) {
        return null;
    }

    /**
     * 根据用户ID更新用户描述
     *
     * @param userId      用户ID
     * @param description 用户描述
     * @return 影响条数
     */
    @Override
    public int updateDescriptionByUserId(int userId, String description) {
        return 0;
    }

    /**
     * 更新用户邮箱
     *
     * @param userId 用户ID
     * @param email  邮箱
     * @return 影响条数
     */
    @Override
    public int updateEmailByUserId(int userId, String email) {
        return 0;
    }

    /**
     * 更新用户位置
     *
     * @param userId   用户ID
     * @param location 位置
     * @return 影响条数
     */
    @Override
    public int updateLocationByUserId(int userId, String location) {
        return 0;
    }

    /**
     * 更新用户学校
     *
     * @param userId 用户ID
     * @param school 学校
     * @return 影响条数
     */
    @Override
    public int updateSchoolByUserId(int userId, String school) {
        return 0;
    }

    /**
     * 更新用户昵称
     *
     * @param userId   用户ID
     * @param nickName 昵称
     * @return 影响条数
     */
    @Override
    public int updateNickNameByUserId(int userId, String nickName) {
        return 0;
    }

    /**
     * 更新用户信息-全部
     *
     * @param userInfo 用户实体类
     * @return 影响条数
     */
    @Override
    public int updateUserInfo(UserInfo userInfo) {
        return 0;
    }

    /**
     * 插入新的用户信息
     *
     * @param description 描述
     * @param email       邮箱
     * @param phone       手机号
     * @param location    定位
     * @param school      学校
     * @param nickName    昵称
     * @return 影响条数
     */
    @Override
    public int insertUserInfo(String description, String email, String phone, String location, String school, String nickName) {
        return 0;
    }


    /**
     * 删除用户信息
     *
     * @param userId userID
     * @return 影响条数
     */
    @Override
    public int deleteUserInfo(int userId) {
        return 0;
    }
}