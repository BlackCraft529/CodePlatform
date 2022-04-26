package com.wrx.codeplatform.utils.code.impl;

import com.wrx.codeplatform.utils.code.TransformersModel;
import com.wrx.codeplatform.utils.code.util.DelComments;
import com.wrx.codeplatform.utils.code.util.InputStreamRunnable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/17 21:45
 */
public class SentenceTransformersModel implements TransformersModel {
    public SentenceTransformersModel(){ }
    private final String pythonPath = "D:\\代码\\sentence-transformers\\examples\\applications\\cross-encoder\\cross-encoder-interface.py",
            pythonEnvironment = "D:\\代码\\sentence-transformers\\venv\\Scripts\\python.exe";

    /**
     * 获取最后的输出结果
     *
     * @param args1  代码片段1
     * @param args2  代码片段2
     * @return       百分比结果
     */
    @Override
    public String getCleanResult(String args1, String args2){
        //简单格式化代码
        args1 = DelComments.delComments(args1);
        args2 = DelComments.delComments(args2);
        args1 = args1.replaceAll("\n","").replaceAll(" ","").replaceAll("\t","");
        args2 = args2.replaceAll("\n","").replaceAll(" ","").replaceAll("\t","");
        //获取输出流
        List<String> resultList = getModelOut(args1, args2);
        System.out.println("getCleanResult："+resultList.get(resultList.size()-1));
        //返回百分比
        return resultList.get(resultList.size()-1);
    }

    /**
     * 获取输出流
     *
     * @param args1 code1
     * @param args2 code2
     * @return 输出流
     */
    @Override
    public List<String> getModelOut(String args1, String args2){
        try {
            String[] cmdArray = new String[] {pythonEnvironment, pythonPath, args1, args2};
            return getStrings(cmdArray);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * 整合函数
     *
     * @param cmdArray 获取cmd输出
     * @return List
     * @throws IOException 错误
     */
    private List<String> getStrings(String[] cmdArray) throws Exception {
        BufferedReader bReader = null;
        InputStreamReader sReader = null;
        Process process = Runtime.getRuntime().exec(cmdArray);

        /* 为"错误输出流"单独开一个线程读取之,否则会造成标准输出流的阻塞 */
        Thread t = new Thread(new InputStreamRunnable(process.getErrorStream(), "ErrorStream"));
        t.start();

        /* "标准输出流"就在当前方法中读取 */
        BufferedInputStream bis = new BufferedInputStream(process.getInputStream());
        sReader = new InputStreamReader(bis, StandardCharsets.UTF_8);
        bReader = new BufferedReader(sReader);
        String line;
        List<String> returnedLine = new ArrayList<>();
        while ((line = bReader.readLine()) != null) {
            returnedLine.add(line);
        }
        t.stop();
        bReader.close();
        process.destroy();
        return returnedLine;
    }


}