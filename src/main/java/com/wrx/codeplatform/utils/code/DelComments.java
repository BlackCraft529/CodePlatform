package com.wrx.codeplatform.utils.code;

import java.util.regex.Pattern;

/**
 * @author 魏荣轩
 * @date 2020/10/28 22:27
 *
 * 删除注释
 */
public class DelComments {
    private static final Pattern pattern = Pattern.compile("/\\*.+?\\*/", Pattern.DOTALL);

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
        content = content.replaceAll("//.+\\r\\n", "");
        return pattern.matcher(content).replaceAll("");
    }
}
