package com.wrx.codeplatform.utils.code.string.exception;

import org.springframework.http.HttpStatus;

/**
 * @author 李广帅
 * @date 2020/10/16 2:30 下午
 */
public class CustomException extends RuntimeException {
    private HttpStatus code;

    private String message;

    public CustomException(String message) {
        this.message = message;
    }

    public CustomException(String message, HttpStatus code) {
        this.message = message;
        this.code = code;
    }

    public CustomException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getCode() {
        return code;
    }
}
