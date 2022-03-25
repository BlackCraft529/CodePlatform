package com.wrx.codeplatform.framework.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrx.codeplatform.domain.enums.Common;
import com.wrx.codeplatform.domain.enums.ResultCode;
import com.wrx.codeplatform.domain.framework.sql.code.Evaluation;
import com.wrx.codeplatform.domain.result.JsonResult;
import com.wrx.codeplatform.framework.service.EvaluationService;
import com.wrx.codeplatform.utils.common.TokenUtil;
import com.wrx.codeplatform.utils.data.SessionStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/25 20:48
 */
@RestController
public class EvaluationController {
    @Autowired
    private EvaluationService evaluationService;

    /**
     * json工具对象
     */
    private final ObjectMapper jsonObjectMapper =  new ObjectMapper();

    /**
     * 获取当前代码的评论页数
     *
     * @param jsonData  json数据
     * @return          数据
     * @throws JsonProcessingException 转换错误
     */
    @PreAuthorize("hasAnyAuthority('evaluate')")
    @RequestMapping(value = "/getEvaluationsCounts",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getEvaluationsCounts(@RequestBody String jsonData)  {
        try {
            JsonNode node = jsonObjectMapper.readTree(jsonData);
            String token = node.get("token").asText();
            String account = TokenUtil.validToken(token);
            if (SessionStorage.pwdMap.get(account) == null) {
                //用户不存在
                JsonResult jsonResult = new JsonResult(false);
                jsonResult.setErrorCode(ResultCode.USER_ACCOUNT_NOT_EXIST.getCode());
                jsonResult.setErrorMsg("无效的登入状态!");
                return jsonObjectMapper.valueToTree(jsonResult).toString();
            }
            int codeId = node.get("codeId").asInt();
            int size = evaluationService.selectEvaluationByFileId(codeId).size();
            if (size >= 10) {
                size /= Common.EVERY_PAGE_EVALUATIONS.getPar();
            } else {
                size = 1;
            }
            JsonResult jsonResult = new JsonResult(true);
            jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
            jsonResult.setData(size);
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }catch (Exception exception){
            exception.printStackTrace();
            return null;
        }
    }

    /**
     * 获取评论信息
     *
     * @param jsonData   json数据
     * @return           数据
     * @throws JsonProcessingException   转换错误
     */
    @PreAuthorize("hasAnyAuthority('evaluate')")
    @RequestMapping(value = "/getEvaluations",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getEvaluations(@RequestBody String jsonData) throws JsonProcessingException {
        JsonNode node = jsonObjectMapper.readTree(jsonData);
        String token = node.get("token").asText();
        String account = TokenUtil.validToken(token);
        if (SessionStorage.pwdMap.get(account) == null ){
            //用户不存在
            JsonResult jsonResult = new JsonResult(false);
            jsonResult.setErrorCode(ResultCode.USER_ACCOUNT_NOT_EXIST.getCode());
            jsonResult.setErrorMsg("无效的登入状态!");
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }
        int codeId = node.get("codeId").asInt();
        int page = node.get("page").asInt();
        List<Evaluation> evaluations = evaluationService.selectEvaluationByPage(codeId, page);
        JsonResult jsonResult = new JsonResult(true);
        jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
        jsonResult.setData(evaluations);
        return jsonObjectMapper.valueToTree(jsonResult).toString();
    }

    /**
     * 插入新的评论数据
     *
     * @param jsonData  json数据
     * @return          结果
     * @throws JsonProcessingException  转换错误
     */
    @PreAuthorize("hasAnyAuthority('evaluate')")
    @RequestMapping(value = "/publishEvaluation",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String publishEvaluation(@RequestBody String jsonData) throws JsonProcessingException {
        JsonNode node = jsonObjectMapper.readTree(jsonData);
        String token = node.get("token").asText();
        String account = TokenUtil.validToken(token);
        if (SessionStorage.pwdMap.get(account) == null ){
            //用户不存在
            JsonResult jsonResult = new JsonResult(false);
            jsonResult.setErrorCode(ResultCode.USER_ACCOUNT_NOT_EXIST.getCode());
            jsonResult.setErrorMsg("无效的登入状态!");
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }
        int codeId = node.get("codeId").asInt();
        int publisherId = node.get("publisherId").asInt();
        String context = node.get("context").asText();
        double score = node.get("score").asDouble();
        Evaluation evaluation = new Evaluation(codeId, publisherId, context, score);
        evaluationService.insertNewEvaluation(evaluation);
        JsonResult jsonResult = new JsonResult(true);
        jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
        return jsonObjectMapper.valueToTree(jsonResult).toString();
    }
}
