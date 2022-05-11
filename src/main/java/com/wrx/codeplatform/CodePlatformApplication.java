package com.wrx.codeplatform;

import com.wrx.codeplatform.utils.data.DataUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * TODO:
 *  教师问询   &   同学提问的区别
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
