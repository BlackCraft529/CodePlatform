package com.wrx.codeplatform;

import com.wrx.codeplatform.utils.data.DataUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * TODO:
 *  BUG: 登录状态下密码错误仍然可以登录的问题
 *
 */
@MapperScan("com.wrx.codeplatform.framework.mapper")
@SpringBootApplication
public class CodePlatformApplication {

    public static void main(String[] args) {
        //加载本地数据到内存中
        DataUtil.loadLocalData();
        SpringApplication.run(CodePlatformApplication.class, args);
    }


}
