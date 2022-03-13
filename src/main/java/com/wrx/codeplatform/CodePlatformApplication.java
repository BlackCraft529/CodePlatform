package com.wrx.codeplatform;

import com.wrx.codeplatform.utils.data.DataUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wrx.codeplatform.framework.mapper")

public class CodePlatformApplication {

    public static void main(String[] args) {
        //加载本地数据到内存中
        DataUtil.loadLocalData();
        SpringApplication.run(CodePlatformApplication.class, args);
    }


}
