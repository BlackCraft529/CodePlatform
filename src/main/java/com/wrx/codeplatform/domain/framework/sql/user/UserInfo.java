package com.wrx.codeplatform.domain.framework.sql.user;

import lombok.Data;

/**
 * @author 魏荣轩
 * @date 2022/3/1 19:38
 */
@Data
public class UserInfo {
    private int userId;
    private String description;
    private String email;
    private String phone;
    private String location;
    private String school;
    private String nickName;

}
