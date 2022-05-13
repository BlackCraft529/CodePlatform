package com.wrx.codeplatform.utils.code.impl;

import com.wrx.codeplatform.utils.code.TransformersModel;
import com.wrx.codeplatform.utils.code.string.CodesCompare;
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
    private final String
            pythonJavaInterfacePath = "D:\\代码\\sentence-transformers\\examples\\applications\\cross-encoder\\cross-encoder-interface.py",
            pythonJavaEnvironment = "D:\\代码\\sentence-transformers\\venv\\Scripts\\python.exe",
            pythonInterfacePath = "D:\\代码\\SBERT-Interface\\sbert-interface.py",
            pythonEnvironment = "D:\\IDE\\python37\\python.exe";

    /**
     * 获取最后的输出结果
     *
     * @param args1  代码片段1
     * @param args2  代码片段2
     * @return       百分比结果
     * 空格问题、代码类型问题
     */
    @Override
    public String getCleanResult(String args1, String args2){
        String REGEX_CHINESE = "[\u4e00-\u9fa5]";
        //简单格式化代码
        args1 = DelComments.delComments(args1);
        args2 = DelComments.delComments(args2);
        args1 = args1.replaceAll("\n","")
                .replaceAll("- ","")
                .replaceAll(" -","")
                .replaceAll(" +","<-%space%->")
                .replaceAll("\t","")
                .replaceAll(REGEX_CHINESE, "");
        args2 = args2.replaceAll("\n","")
                .replaceAll("- ","")
                .replaceAll(" -","")
                .replaceAll(" +","<-%space%->")
                .replaceAll("\t","")
                .replaceAll(REGEX_CHINESE, "");
        //获取输出流
        List<String> resultList = getModelOut(args1, args2);
        //返回百分比
        return resultList.get(resultList.size()-1);
    }

    /**
     * 最长子串相似度
     *
     * @param code1 代码1
     * @param code2 代码2
     * @return 结果
     */
    @Override
    public String getStringCompareResult(String code1, String code2) {
        return new CodesCompare().getSimilarity(code1, code2)+"";
    }

    /**
     * 获取加权比对结果
     *
     * @param code1 代码1
     * @param code2 代码2
     * @return 结果
     */
    @Override
    public String getWeightedResult(String code1, String code2) {
        final double aiWeight = 0.6, strWeight = 0.4;
        double aiResult = -1, strResult = -1;
        try {
            aiResult = Double.parseDouble(getCleanResult(code1,code2));
        }catch (Exception ignore){}
        try {
            strResult = Double.parseDouble(getStringCompareResult(code1,code2));
        }catch (Exception ignore){}
        double result = aiResult * strWeight +
                    strResult * aiWeight;
        return result+"";
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
            String[] cmdArray = new String[] {pythonEnvironment, pythonInterfacePath, args1, args2};
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
