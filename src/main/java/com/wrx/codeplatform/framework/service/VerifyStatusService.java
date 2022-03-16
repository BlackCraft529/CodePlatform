package com.wrx.codeplatform.framework.service;

import com.wrx.codeplatform.domain.framework.sql.user.VerifyStatus;

/**
 * @author 魏荣轩
 * @date 2022/3/11 23:09
 */
public interface VerifyStatusService {
    /**
     * 根据手机号查询验证状态
     *
     * @param phone 手机号
     * @return      验证状态信息
     */
    VerifyStatus selectVerifyStatusByPhone(String phone);

    /**
     * 根据账户名查找验证状态
     *
     * @param account  账户名
     * @return         验证状态信息
     */
    VerifyStatus selectByAccount(String account);

    /**
     * 更新验证成功状态
     *
     * @param phone      手机
     * @param complete   成功状态
     * @return           影响条数
     */
    int updateVerifyStatusComplete(String phone, boolean complete);

    /**
     * 插入一条验证状态信息
     *
     * @param verifyStatus  验证状态
     * @return              影响条数
     */
    int insertVerifyStatus(VerifyStatus verifyStatus);

    /**
     * 根据手机号删除一条验证状态信息
     *
     * @param phone   手机号
     * @return        影响条数
     */
    int deleteVerifyStatusByPhone(String phone);

    /**
     * 验证手机号 验证码是否匹配
     *
     * @param phone  手机号
     * @param code   验证码
     * @return       结果
     */
    boolean verifyCodeAndPhone(String phone, String code);
}
