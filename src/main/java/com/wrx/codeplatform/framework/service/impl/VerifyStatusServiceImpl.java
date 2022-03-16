package com.wrx.codeplatform.framework.service.impl;

import com.wrx.codeplatform.domain.config.DataPool;
import com.wrx.codeplatform.domain.framework.sql.user.VerifyStatus;
import com.wrx.codeplatform.framework.mapper.VerifyStatusMapper;
import com.wrx.codeplatform.framework.service.VerifyStatusService;
import com.wrx.codeplatform.utils.common.TimeUtil;
import com.wrx.codeplatform.utils.mobile.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 魏荣轩
 * @date 2022/3/11 23:13
 */
@Service("verifyStatusService")
public class VerifyStatusServiceImpl implements VerifyStatusService {

    @Autowired
    private VerifyStatusMapper verifyStatusMapper;

    /**
     * 根据手机号查询验证状态
     *
     * @param phone 手机号
     * @return 验证状态信息
     */
    @Override
    public VerifyStatus selectVerifyStatusByPhone(String phone) {
        return verifyStatusMapper.selectByPhone(phone);
    }

    /**
     * 根据账户名查找验证状态
     *
     * @param account 账户名
     * @return 验证状态信息
     */
    @Override
    public VerifyStatus selectByAccount(String account) {
        return verifyStatusMapper.selectByAccount(account);
    }

    /**
     * 更新验证成功状态
     *
     * @param phone    手机
     * @param complete 成功状态
     * @return 影响条数
     */
    @Override
    public int updateVerifyStatusComplete(String phone, boolean complete) {
        VerifyStatus verifyStatus = verifyStatusMapper.selectByPhone(phone);
        verifyStatus.setComplete(complete);
        return verifyStatusMapper.updateVerifyStatus(verifyStatus);
    }

    /**
     * 插入一条验证状态信息
     *
     * @param verifyStatus 验证状态
     * @return 影响条数
     */
    @Override
    public int insertVerifyStatus(VerifyStatus verifyStatus) {
        return verifyStatusMapper.insertVerifyStatus(verifyStatus);
    }

    /**
     * 根据手机号删除一条验证状态信息
     *
     * @param phone 手机号
     * @return 影响条数
     */
    @Override
    public int deleteVerifyStatusByPhone(String phone) {
        return 0;
    }

    /**
     * 验证手机号 验证码是否匹配
     *
     * @param phone 手机号
     * @param code  验证码
     * @return 结果
     */
    @Override
    public boolean verifyCodeAndPhone(String phone, String code) {
        VerifyStatus verifyStatus = selectVerifyStatusByPhone(phone);
        if (verifyStatus == null){
            return false;
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return (TimeUtil.minuteBetween(df.format(verifyStatus.getSendDate()), df.format(new Date())) <= DataPool.codeUsefulMin && CodeUtil.verifyCode(phone, code));
        }catch (Exception exception){
            exception.printStackTrace();
            return false;
        }
    }
}
