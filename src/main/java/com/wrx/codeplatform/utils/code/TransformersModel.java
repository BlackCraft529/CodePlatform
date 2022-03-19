package com.wrx.codeplatform.utils.code;

import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/17 21:44
 */
public interface TransformersModel {

    /**
     * 获取输出流
     *
     * @param args1 参数1
     * @param args2 参数2
     * @return 输出流
     */
    List<String> getModelOut(String args1,String args2);

}
