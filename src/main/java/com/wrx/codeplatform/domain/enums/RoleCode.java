package com.wrx.codeplatform.domain.enums;

/**
 * @author 魏荣轩
 * @date 2022/3/13 23:24
 */
public enum RoleCode {

    STUDENT(3),
    TEACHER(2),
    ADMIN(1);

    private Integer code;
    RoleCode(Integer code){
        this.code = code;
    }

    public int getRoleCode(){
        return this.code;
    }
}
