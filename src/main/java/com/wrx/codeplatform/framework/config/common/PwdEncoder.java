package com.wrx.codeplatform.framework.config.common;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 魏荣轩
 * @date 2022/3/14 0:00
 */
public class PwdEncoder {
    public static PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
