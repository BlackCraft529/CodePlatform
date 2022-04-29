package com.wrx.codeplatform.utils.code.string.util;

import com.wrx.codeplatform.utils.code.string.exception.CustomException;
import com.wrx.codeplatform.utils.code.string.pretreatment.CodeVectorTools;
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
                throw new CustomException("Cannot find file:"+filePath);
            }
        } catch (IOException e) {
            throw new CustomException("Read file error:"+e.toString());
        }
    }

    /**
     * 更新语料
     *
     * @param codeListFolder 代码文件夹 as: "codes/JavaFile"
     * @param saveTrainTxtPath 保存训练文本路径 as: "D:\代码\语料\语料.txt"
     * @return 是否训练成功
     */
    public static boolean buildTrainTxtByCodeList(String codeListFolder , String saveTrainTxtPath){
        try {
            Resource resource = new ClassPathResource(codeListFolder);
            File baseFile = resource.getFile();
            File[] files = baseFile.listFiles();
            StringBuilder preCodes = new StringBuilder();
            assert files != null;
            for(File file : files){
                if(file.exists() && file.isFile()){
                    preCodes.append(CodeVectorTools.getCodePretreatmentAsString(
                            TxtFileUtils.readTxtFile(codeListFolder + "/" + file.getName()).toString()));
                }
            }
            FileWriter fileWriter = new FileWriter(saveTrainTxtPath,false);
            fileWriter.write(preCodes.toString().replaceAll("\\?"," "));
            fileWriter.flush();
            fileWriter.close();
            return true;
        }catch (Exception exception){
            System.out.println("系统错误:" + exception);
            return false;
        }

    }
}
