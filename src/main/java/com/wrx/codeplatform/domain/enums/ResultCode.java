package com.wrx.codeplatform.domain.enums;

/**
 * @author 魏荣轩
 * @date 2022/2/23 0:31
 * 规定:
 * #1表示成功
 * #1001～1999 区间表示参数错误
 * #2001～2999 区间表示用户错误
 * #3001～3999 区间表示接口异常
 */
public enum ResultCode {
    /* 成功 */
    SUCCESS(200, "成功"),

    /* 默认失败 */
    COMMON_FAIL(999, "失败"),

    /* 参数错误：1000～1999 */
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),

    /* 用户错误 */
    USER_NOT_LOGIN(2001, "用户未登录"),
    USER_ACCOUNT_EXPIRED(2002, "账号已过期"),
    USER_CREDENTIALS_ERROR(2003, "密码错误"),
    USER_CREDENTIALS_EXPIRED(2004, "密码过期"),
    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    USER_ACCOUNT_LOCKED(2006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(2008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(2009, "账号下线"),

    /* 业务错误 */
    NO_PERMISSION(3001, "没有权限"),
    CODE_COOL_DOWN(3002,"验证码功能冷却中"),
    SQL_ERROR(3003, "数据库业务错误"),
    SERVICE_ERROR(3004,"服务器业务错误"),
    CODE_ERROR(3005,"验证码不匹配"),
    CODE_OVERDUE(3006,"验证码失效"),
    PHONE_HAS_NULL_CODE(3007,"手机号暂未存在有效验证码"),
    PHONE_SERVICE_ERROR(3008,"运营商错误"),
    PHONE_USED(3009,"手机号已被注册"),
    PHONE_NOT_EXISTS(3010,"手机号不存在"),
    CODE_NOT_EXISTS(3011,"代码文件不存在"),
    RECHECKING_RESULT_NOT_EXISTS(3012,"代码查重结果为空"),
    ISSUES_IS_NOT_OPEN(3013,"问题已经被关闭,回复失败!"),
    ISSUES_IS_NOT_EXISTS(3014, "问题不存在,请尝试刷新界面!"),
    ROLE_OPEN_ISSUES_IS_FAIL(3015,"您当前无法在%role%版块发布问题/意见!");

    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 根据code获取message
     *
     * @param code
     * @return
     */
    public static String getMessageByCode(Integer code) {
        for (ResultCode ele : values()) {
            if (ele.getCode().equals(code)) {
                return ele.getMessage();
            }
        }
        return null;
    }
}
