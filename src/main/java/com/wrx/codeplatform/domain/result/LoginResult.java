package com.wrx.codeplatform.domain.result;

import lombok.Data;

import java.util.Date;

/**
 * 返回给前端的 登陆后的认证信息
 * @author 魏荣轩
 * @date 2022/2/20 23:59
 */
@Data
public class LoginResult {
    private String userName;
    private String token;
    private boolean success;
    private Date date;
    private String reason;

    public LoginResult(String userName, String token, boolean success){
        this.userName = userName;
        this.token = token;
        this.success = success;
        this.date = new Date();
    }

    public LoginResult(String userName, String token, boolean success, String reason){
        this.userName = userName;
        this.token = token;
        this.success = success;
        this.date = new Date();
        this.reason = reason;
    }
}
