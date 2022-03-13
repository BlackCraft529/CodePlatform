package com.wrx.codeplatform.framework.mapper;

import com.wrx.codeplatform.domain.framework.sql.user.VerifyStatus;
import org.springframework.stereotype.Repository;

/**
 * @author 魏荣轩
 * @date 2022/3/11 22:58
 */
@Repository
public interface VerifyStatusMapper {
    /**
     * 根据手机号查询验证状态
     *
     * @param phone 手机号
     * @return      验证状态信息
     */
    VerifyStatus selectByPhone(String phone);

    /**
     * 根据账户名查找验证状态
     *
     * @param account  账户名
     * @return         验证状态信息
     */
    VerifyStatus selectByAccount(String account);

    /**
     * 更新验证状态信息
     *
     * @param verifyStatus   验证状态信息
     * @return               影响条数
     */
    int updateVerifyStatus(VerifyStatus verifyStatus);

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


}
