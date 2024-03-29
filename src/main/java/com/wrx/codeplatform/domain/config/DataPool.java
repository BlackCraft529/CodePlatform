package com.wrx.codeplatform.domain.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/11 14:45
 */
public class DataPool {
    public static List<String> schools = new ArrayList<>();
    public static List<String> locations = new ArrayList<>();
    //验证码冷却时间
    public static int codeCoolDownMin = 3, codeUsefulMin = 15;
    //文件保存地址
    public static final String fileSavePath = "D:"+ File.separator +"CodePlatform_saveFiles";
}
