package com.wrx.codeplatform.utils.code;

/**
 * @author 魏荣轩
 * @date 2022/3/13 14:24
 */
public interface CodeFactory {

    /**
     * 代码查重
     *
     * @param range          范围
     * @param model          模式
     * @param targetClass    目标班级
     * @param targetTeacher  目标老师
     * @param targetCode     目标代码
     */
    void recheckingCode(String range, String model, int targetClass, int targetTeacher, int targetCode);


    
}
