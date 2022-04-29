package com.wrx.codeplatform.utils.code.string.pretreatment;

import java.util.regex.Pattern;

/**
 * @author 魏荣轩
 * @date 2020/10/28 22:27
 */
public class DelComments {
    private static final Pattern pattern = Pattern.compile("/\\*.+?\\*/", Pattern.DOTALL);
	private static final char MARK = '"';
    private static final char SLASH = '/';
    private static final char BACKSLASH = '\\';
    private static final char STAR = '*';
    private static final char NEWLINE = '\n';
    /**
     * 引号
     */
    private static final int TYPE_MARK = 1;
    /**
     * 斜杠
     */
    private static final int TYPE_SLASH = 2;
    /**
     * 反斜杠
     */
    private static final int TYPE_BACKSLASH = 3;
    /**
     * 星号
     */
    private static final int TYPE_STAR = 4;
    /**
     * 双斜杠类型的注释
     */
    private static final int TYPE_DSLASH = 5;
    /**  
     * 删除char[]数组中_start位置到_end位置的元素  
     *   
     * @param target  目标值
     * @param start  开始地址
     * @param end  结束地址
     * @return  字符数组
     */  
    private static char[] del(char[] target, int start, int end) {
        char[] tmp = new char[target.length - (end - start + 1)];
        System.arraycopy(target, 0, tmp, 0, start);
        System.arraycopy(target, end + 1, tmp, start, target.length - end - 1);
        return tmp;   
    }

    /**
     * 删除代码中的注释
     *
     * @param content  开始地址
     * @return  处理后字符串
     */
    public static String delComments(String content) {
        content.replaceAll("//.+\\r\\n", "");
        return pattern.matcher(content).replaceAll("");
    }

    /**  
     * 删除代码中的注释  
     *   
     * @param target  开始地址
     * @return  处理后字符串

    public static String delComments(String target) {
        int preType = 0;   
        int mark = -1, cur, token = -1;
        // 输入字符串   
        char[] input =  target.toCharArray();
        for (cur = 0; cur < input.length; cur++) {   
            if (input[cur] == MARK) {   
                // 首先判断是否为转义引号   
                if (preType == TYPE_BACKSLASH) {
                    continue;
                }
                // 已经进入引号之内   
                if (mark > 0) {   
                    // 引号结束   
                    mark = -1;   
                } else {   
                    mark = cur;   
                }   
                preType = TYPE_MARK;   
            } else if (input[cur] == SLASH) {   
                // 当前位置处于引号之中   
                if (mark > 0) {
                    continue;
                }
                // 如果前一位是*，则进行删除操作   
                if (preType == TYPE_STAR) {   
                    input = del(input, token, cur);   
                    // 退回一个位置进行处理   
                    cur = token - 1;   
                    preType = 0;   
                } else if (preType == TYPE_SLASH) {   
                    token = cur - 1;   
                    preType = TYPE_DSLASH;   
                } else {   
                    preType = TYPE_SLASH;   
                }   
            } else if (input[cur] == BACKSLASH) {   
                preType = TYPE_BACKSLASH;   
            } else if (input[cur] == STAR) {   
                // 当前位置处于引号之中   
                if (mark > 0){
                    continue;
                }
                // 如果前一个位置是/,则记录注释开始的位置   
                if (preType == TYPE_SLASH) {   
                    token = cur - 1;   
                }   
                preType = TYPE_STAR;   
            } else if(input[cur] == NEWLINE){
                if(preType == TYPE_DSLASH){
                    input = del(input, token, cur);   
                    // 退回一个位置进行处理   
                    cur = token - 1;   
                    preType = 0;   
                }   
            }   
  
        }   
        return new String(input);   
    }  */
}
