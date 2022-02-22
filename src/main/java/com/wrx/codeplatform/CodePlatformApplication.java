package com.wrx.codeplatform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@MapperScan("com.wrx.codeplatform.framework.mapper.interfaces")
@EnableGlobalMethodSecurity(securedEnabled = true)
public class CodePlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodePlatformApplication.class, args);
    }

}
