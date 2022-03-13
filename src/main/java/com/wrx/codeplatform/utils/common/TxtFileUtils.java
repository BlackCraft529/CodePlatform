package com.wrx.codeplatform.utils.common;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author 魏荣轩
 * @date 2020/10/16 22:32
 */
public class TxtFileUtils {
    /**
     * 读取TXT文件内容
     *
     * @param filePath 文件地址
     */
    public static StringBuffer readTxtFile(String filePath)  {
        InputStream readFileStream = null;
        Reader reader = null;
        try {
            Resource resource = new ClassPathResource(filePath);
            if(resource.isFile() && resource.exists()){
                //ClassPathResource classPathResource = new ClassPathResource(filePath);
                readFileStream =resource.getInputStream();
                reader = new InputStreamReader(readFileStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(reader);
                StringBuffer returnString = new StringBuffer();
                String line;
                while(( line = bufferedReader.readLine() )!= null){
                    returnString.append(line);
                }
                readFileStream.close();
                reader.close();
                bufferedReader.close();
                return returnString;
            }else{
                System.out.println("Cannot find file:"+filePath);
                return new StringBuffer();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new StringBuffer();
    }
}
