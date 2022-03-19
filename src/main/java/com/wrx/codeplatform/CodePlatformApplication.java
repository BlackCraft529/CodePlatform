package com.wrx.codeplatform;

import com.wrx.codeplatform.utils.data.DataUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * TODO:
 *  1.代码列表界面的隐藏
 *  2.代码界面的响应式：使用表格布局
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
