package com.wrx.codeplatform;

import com.wrx.codeplatform.framework.config.common.PwdEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

/**
 * @author 魏荣轩
 * @date 2022/2/9 22:34
 */
public class UUIDTest {
    @Test
    public void printUUID(){
        //be2d0acb-7dda-4835-90d7-2f8db2052ca1
        System.out.println(UUID.randomUUID().toString());
    }
    @Test
    public void getEncodePwd(){
        System.out.println(PwdEncoder.getPasswordEncoder().encode("123456"));
    }
}
