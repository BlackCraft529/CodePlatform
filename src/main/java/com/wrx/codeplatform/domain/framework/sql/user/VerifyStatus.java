package com.wrx.codeplatform.domain.framework.sql.user;

import lombok.Data;
import java.util.Date;

/**
 * @author 魏荣轩
 * @date 2022/3/11 22:55
 */
@Data
public class VerifyStatus {
    private String phone;
    private String account;
    private String code;
    private Date sendDate;
    private boolean complete;
    private Date completeDate;

    public VerifyStatus(String account, String phone, String code){
        this.account = account;
        this.code = code;
        this.complete = false;
        this.sendDate = new Date();
        this.completeDate = new Date();
        this.phone = phone;
    }

}
