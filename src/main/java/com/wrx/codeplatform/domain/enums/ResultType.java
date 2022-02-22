package com.wrx.codeplatform.domain.enums;

/**
 * @author 魏荣轩
 * @date 2022/2/22 22:00
 */
public enum ResultType {
    NO_PERMISSION("noPermission");

    public String getReason(){
        return reason;
    }

    ResultType(String reason){
        this.reason = reason;
    }
    String reason;
}
