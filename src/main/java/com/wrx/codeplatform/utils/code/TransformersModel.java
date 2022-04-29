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

    /**
     * 获取最后的输出结果
     *
     * @param args1  代码片段1
     * @param args2  代码片段2
     * @return       百分比结果
     */
    String getCleanResult(String args1, String args2);

    /**
     * 最长子串相似度
     *
     * @param code1 代码1
     * @param code2 代码2
     * @return      结果
     */
    String getStringCompareResult(String code1, String code2);
}
