package com.wrx.codeplatform.utils.mobile;

import java.security.MessageDigest;

/**
 * @author 魏荣轩
 * @date 2021/7/10 20:21
 */
public class CheckSumBuilder {
    //计算并获取checkSum
    public static String getCheckSum(String appSecret,String nonce,String curTime){
        return encode(appSecret+nonce+curTime);
    }

    private static String encode(String value){
        if(value==null){
            return null;
        }

        try {
            MessageDigest messageDigest=MessageDigest.getInstance("SHA");
            messageDigest.update(value.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFormattedText(byte[] bytes){
        int len=bytes.length;
        StringBuilder sb=new StringBuilder(len*2);
        for (byte aByte : bytes) {
            sb.append(HEX_DIGITS[(aByte >> 4) & 0x0f]);
            sb.append(HEX_DIGITS[aByte & 0x0f]);
        }
        return sb.toString();
    }

    private static final char[] HEX_DIGITS={'0','1','2','3','4','5','6',
            '7','8','9','a','b','c','d','e','f'};

}
