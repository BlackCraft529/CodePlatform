package com.wrx.codeplatform.utils.code.util;

import java.util.Collections;
import java.util.HashSet;

/**
 * @author 魏荣轩
 * @date 2020/11/5 19:31
 *
 * 删除变量
 */
public class DelVariables {
    private static HashSet<String> keyWordSet = new HashSet<String>();
    private static String keyWords = "this|and|asm|auto|bad_cast|bad_typeid|bool|break|case|catch|char|class|const|const_cast" +
            "|continue|default|delete|do|double|dynamic_cast|else|enum|except|explicit|extern|false|finally|float|for" +
            "|friend|goto|if|inline|int|long|mutable|namespace|new|operator|or|private|protected|public|register|reinterpret_cast" +
            "|return|short|signed|sizeof|static|static_cast|struct|switch|template|this|throw|true|try|type_info|typedef" +
            "|typeid|typename|union|unsigned|using|virtual|void|volatile|wchar_t|while";

    public static String delVariables(String code,String replaceCode0, String replaceCode1){
        String[] list = keyWords.split("\\|");
        Collections.addAll(keyWordSet, list);
        code = "   "+code+"  ";
        int pos1 = 0,pos2=0;
        int len = code.length();
        boolean isVariables=false;
        StringBuilder ret = new StringBuilder();
        while(pos1<len){
            pos2++;
            if(isVariables){
                if("".equals(code.substring(pos2,pos2+2).replaceAll("[0-9a-zA-Z_][^a-zA-Z_]", ""))){
                    isVariables = false;
                    String vv = code.substring(pos1,pos2+1);
                    if(keyWordSet.contains(vv)){
                        ret.append(vv+" "+replaceCode0);
                    }
                    pos1 = pos2+1;
                }
            }else{
                if("".equals(code.substring(pos2,pos2+2).replaceAll("[^._a-zA-Z][_a-zA-Z]", ""))){
                    isVariables = true;
                    ret.append(code+" "+replaceCode1, pos1,pos2+1);
                    pos1 = pos2+1;
                }
            }
            if(pos2 == len-2){
                break;
            }
        }
        return ret.toString().trim().replaceAll("\t","");
    }
}
