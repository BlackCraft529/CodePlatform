package com.wrx.codeplatform.utils.code.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrx.codeplatform.domain.framework.entity.rechecking.RecheckingResultInit;
import com.wrx.codeplatform.domain.framework.sql.code.Code;
import com.wrx.codeplatform.domain.framework.sql.container.ContainerLink;
import com.wrx.codeplatform.framework.service.CodeService;
import com.wrx.codeplatform.framework.service.ContainerLinkService;
import com.wrx.codeplatform.framework.service.ContainerService;
import com.wrx.codeplatform.utils.code.CodeFactory;
import com.wrx.codeplatform.utils.code.TransformersModel;
import com.wrx.codeplatform.utils.common.ServiceFactory;
import com.wrx.codeplatform.utils.file.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 魏荣轩
 * @date 2022/3/13 14:12
 */
@Component
public class CodeFactoryImpl implements CodeFactory {

    /**
     * json工具对象
     */
    private final ObjectMapper jsonObjectMapper =  new ObjectMapper();

    private final String RANGE_TYPE_CLASS = "type-class", RANGE_SYSTEM = "system";


    /**
     * 代码查重
     *
     * @param range         范围
     * @param model         模式
     * @param targetClass   目标班级
     * @param targetTeacher 目标老师
     * @param targetCode    目标代码
     */
    @Override
    public void recheckingCode(String range, String model, int targetClass, int targetTeacher, int targetCode) {
        if (range.equalsIgnoreCase(RANGE_TYPE_CLASS)){
            classRechecking(model, targetClass, targetCode);
        }else {
            systemRechecking(model, targetCode);
        }
    }

    /**
     * 班级查重
     *
     * @param model          模式
     * @param targetClassId    指定班级
     * @param targetCodeId     指定代码
     * @notice 目标代码禁止存在空路径
     */
    private void classRechecking(String model, int targetClassId, int targetCodeId){
        //获取当前班级的所有文件关系集合
        List<ContainerLink> containerLinkList = ServiceFactory.containerLinkService.selectContainerLinkByContainerId(targetClassId);
        List<Code> codeList = new ArrayList<>();
        for (ContainerLink containerLink : containerLinkList){
            Code code = ServiceFactory.codeService.selectCodeById(containerLink.getFileId());
            if (code!=null){
                codeList.add(code);
            }
        }
        System.out.println("targetClassId:"+targetClassId+"  targetCodeId:"+targetCodeId);
        System.out.println("班级作业长度: "+codeList.size());
        Code targetCode = ServiceFactory.codeService.selectCodeById(targetCodeId);
        String targetCodeContent = FileUtil.readFileContent(targetCode.getFilePath());
        RecheckingResultInit recheckingResult = new RecheckingResultInit(targetClassId, targetCodeId, model, RANGE_TYPE_CLASS);
        Map<Integer, Double> perMap = new HashMap<>();
        //开始获取文本进行查重
        for (Code code: codeList){
            //新建模型对象
            TransformersModel transformersModel = new SentenceTransformersModel();
            String content = FileUtil.readFileContent(code.getFilePath());
            double result = -1.0;
            if (content.equals("")){
                perMap.put(code.getId(), result);
                continue;
            }
            String cleanResult = "";
            //分类别模式带入查重
            if (model.equalsIgnoreCase("ai")){
                //带入模型获取结果 - Sentence-BERT
                cleanResult = transformersModel.getCleanResult(targetCodeContent, content);
            }else if (model.equalsIgnoreCase("str")){
                //带入模型获取结果 - 子串
                cleanResult = transformersModel.getStringCompareResult(targetCodeContent, content);
            }else {
                cleanResult = transformersModel.getWeightedResult(targetCodeContent, content);
            }
            try{
                result = Double.parseDouble(cleanResult);
                BigDecimal setter = new BigDecimal(result);
                result =  setter.setScale(2, RoundingMode.HALF_UP).doubleValue();
                perMap.put(code.getId(), result);
            }catch (Exception exception){
                result = -1.0;
                perMap.put(code.getId(), result);
            }
            System.out.println("查询结果输入: "+code.getId()+"::"+result);
        }
        recheckingResult.setResult(slotMapByValue(perMap));
        System.out.println("排序完成: "+recheckingResult.getResult().size());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        recheckingResult.setCompleteDate(sdf.format(new Date()));
        //将结果转换为Json存入数据库中
        String jsonResult = jsonObjectMapper.valueToTree(recheckingResult).toString();
        System.out.println("结果: "+jsonResult);
        //设置结果
        targetCode.setResult(jsonResult);
        ServiceFactory.codeService.updateCode(targetCode);
        System.out.println("查询完成,结果写入");
    }

    /**
     * 系统查重
     *
     * @param model        模式
     * @param targetCode   目标代码
     */
    private void systemRechecking(String model, int targetCode){

    }

    /**
     * 排序后并截取20位
     *
     * @param maps  map
     * @return      list
     */
    private List<String> slotMapByValue(Map<Integer, Double> maps){
        //自定义比较器
        Comparator<Map.Entry<Integer, Double>> valCmp = (o1, o2) -> (int)(o2.getValue()-o1.getValue());
        List<Map.Entry<Integer, Double>> list = new ArrayList<>(maps.entrySet());
        Collections.sort(list,valCmp);
        List<String> result = new ArrayList<>();
        //输出map
        for (Map.Entry<Integer, Double> integerDoubleEntry : list) {
            result.add(integerDoubleEntry.getKey() + "::" + integerDoubleEntry.getValue());
            if (result.size() >= 20){
                break;
            }
        }
        return result;
    }
}
