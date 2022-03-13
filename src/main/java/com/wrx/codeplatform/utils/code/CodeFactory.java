package com.wrx.codeplatform.utils.code;

/**
 * @author 魏荣轩
 * @date 2022/3/13 14:24
 */
public interface CodeFactory {

    /**
     * 将原始代码格式化为统一的代码格式
     * 暂时只支持 C++
     *
     * @param originalCode   原始代码
     * @return               清洗后的代码片段
     */
    String getCleanCodePiece(String originalCode);

    /**
     * 清除代码中的注释 和 回车
     * 注：空格不需要删除，模拟自然语言空格
     *
     * @param originalCode    原始代码
     * @return                清洗后的代码片段
     */
    String clearAnnotationAndEnterPiece(String originalCode);

    /**
     * 清除变量定义语句
     *
     * @param originalCode    原始代码
     * @return                清洗后的代码
     */
    String clearDefineVariablePiece(String originalCode);

    /**
     * 将代码片段中的所有变量替换为统一字符
     *
     * @param originalCode       原始代码
     * @param forReplaceCode     替换为的代码
     * @return                   清洗后的代码片段
     */
    String replaceAllVariablePiece(String originalCode, String forReplaceCode);

    
}
