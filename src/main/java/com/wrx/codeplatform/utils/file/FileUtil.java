package com.wrx.codeplatform.utils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author 魏荣轩
 * @date 2022/3/22 19:44
 */
public class FileUtil {

    /**
     * 读取路径文本内容
     *
     * @param filePath  文件路径
     * @return          文件内容
     */
    public static String readFileContent(String filePath) {
        File file = new File(filePath);
        if (!file.exists()){
            return "";
        }
        BufferedReader reader = null;
        StringBuilder sbf = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr).append("\n");
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }
}
