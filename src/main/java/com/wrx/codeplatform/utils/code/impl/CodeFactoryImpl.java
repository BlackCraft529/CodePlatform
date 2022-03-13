package com.wrx.codeplatform.utils.code.impl;

import com.wrx.codeplatform.utils.code.CodeFactory;
import com.wrx.codeplatform.utils.code.DelComments;
import com.wrx.codeplatform.utils.code.DelVariables;

import java.util.Collections;
import java.util.HashSet;

/**
 * @author 魏荣轩
 * @date 2022/3/13 14:12
 */
public class CodeFactoryImpl implements CodeFactory {
    private HashSet<String> keyWordSet = new HashSet<String>();
    private final String forReplaceCode = "Variable";

    /**
     * C/C++关键字
     * 初始化工厂方法
     */
    public CodeFactoryImpl(){
        String keyWords = "and|asm|auto|bad_cast|bad_typeid|bool|break|case|catch|char|class|const|const_cast" +
                "|continue|default|delete|do|double|dynamic_cast|else|enum|except|explicit|extern|false|finally|float|for" +
                "|friend|goto|if|inline|int|long|mutable|namespace|new|operator|or|private|protected|public|register|reinterpret_cast" +
                "|return|short|signed|sizeof|static|static_cast|struct|switch|template|this|throw|true|try|type_info|typedef" +
                "|typeid|typename|union|unsigned|using|virtual|void|volatile|wchar_t|while";
        String[] list = keyWords.split("\\|");
        Collections.addAll(keyWordSet, list);
    }

    /**
     * 将原始代码格式化为统一的代码格式
     * 暂时只支持 C
     *
     * @param originalCode   原始代码
     * @return               清洗后的代码片段
     */
    @Override
    public String getCleanCodePiece(String originalCode){
        System.out.println(originalCode);
        System.out.println("=========================");
        String typeCode1 = clearAnnotationAndEnterPiece(originalCode);
        System.out.println(typeCode1);
        System.out.println("=========================");
        String typeCode2 = clearDefineVariablePiece(originalCode);
        System.out.println(typeCode2);
        System.out.println("=========================");
        return originalCode;
    }

    /**
     * 清除代码中的注释 和 回车
     * 注：空格不需要删除，模拟自然语言空格
     *
     * @param originalCode 原始代码
     * @return 清洗后的代码片段
     */
    @Override
    public String clearAnnotationAndEnterPiece(String originalCode) {
        String code="";
        try{
            //删除所有注释
            code = DelComments.delComments(originalCode);
            //删除所有换行
//            code=code.replaceAll("\\r", "").replaceAll("\\n", "");
        }catch(Exception e){
            e.printStackTrace();
        }
        return code;
    }

    /**
     * 清除变量定义语句
     *
     * @param originalCode 原始代码
     * @return 清洗后的代码
     */
    @Override
    public String clearDefineVariablePiece(String originalCode) {
        int pos1 = 0,pos2 = 0;
        int len = originalCode.length();
        boolean isString = false;
        StringBuilder ret = new StringBuilder();
        while(pos1<len){
            pos2++;
            if(isString){
                if(pos2<len-1){
                    if("\"".equals(originalCode.substring(pos2, pos2+1)) && !"\\".contentEquals(originalCode.subSequence(pos2-1, pos2))){
                        isString  = false;
                        ret.append(DelVariables.delVariables(originalCode.substring(pos1, pos2 + 1),"",""));
                        pos1 = pos2+1;
                    }
                }else{
                    break;
                }
            }else{
                if(pos2<len-1){
                    if("\"".equals(originalCode.substring(pos2, pos2+1))){
                        isString  = true;
                        ret.append(DelVariables.delVariables(originalCode.substring(pos1, pos2),"",""));
                        pos1 = pos2;
                    }
                }else{
                    ret.append(DelVariables.delVariables(originalCode.substring(pos1),"",""));
                    break;
                }
            }
        }
        return ret.toString();
    }

    /**
     * 将代码片段中的所有变量替换为统一字符
     *
     * @param originalCode   原始代码
     * @param forReplaceCode 替换为的代码
     * @return 清洗后的代码片段
     */
    @Override
    public String replaceAllVariablePiece(String originalCode, String forReplaceCode) {
        return originalCode;
    }


}
