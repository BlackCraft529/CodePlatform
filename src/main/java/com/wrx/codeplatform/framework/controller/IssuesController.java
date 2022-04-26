package com.wrx.codeplatform.framework.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrx.codeplatform.domain.enums.ResultCode;
import com.wrx.codeplatform.domain.framework.entity.code.CodeIssuesInfo;
import com.wrx.codeplatform.domain.framework.sql.code.CodeIssues;
import com.wrx.codeplatform.domain.result.JsonResult;
import com.wrx.codeplatform.framework.service.CodeIssuesService;
import com.wrx.codeplatform.utils.common.TokenUtil;
import com.wrx.codeplatform.utils.data.SessionStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/4/20 14:46
 */
@RestController
public class IssuesController {
    @Autowired
    private CodeIssuesService codeIssuesService;

    /**
     * json工具对象
     */
    private final ObjectMapper jsonObjectMapper = new ObjectMapper();

    /**
     * 获取总共的问题数量
     *
     * @param jsonData  json数据
     * @return          问题数量
     * @throws JsonProcessingException   json转换错误
     */
    @PreAuthorize("hasAnyAuthority('view_self')")
    @RequestMapping(value = "/getIssuesCount", produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getIssuesCount(@RequestBody String jsonData) throws JsonProcessingException {
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
        int count = codeIssuesService.selectCodeIssuesByFileId(codeId).size();
        JsonResult jsonResult = new JsonResult(true);
        jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
        jsonResult.setData(count);
        return jsonObjectMapper.valueToTree(jsonResult).toString();
    }

    /**
     * 根据页数获取问题
     *
     * @param jsonData  json数据
     * @return          问题列表
     * @throws JsonProcessingException   转换错误
     */
    @PreAuthorize("hasAnyAuthority('view_self')")
    @RequestMapping(value = "/getAllIssues", produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getAllIssuesByPages(@RequestBody String jsonData) throws JsonProcessingException {
        JsonNode node = jsonObjectMapper.readTree(jsonData);
        String token = node.get("token").asText();
        String account = TokenUtil.validToken(token);
        System.out.println("Token: "+token);
        if (SessionStorage.pwdMap.get(account) == null ){
            //用户不存在
            JsonResult jsonResult = new JsonResult(false);
            jsonResult.setErrorCode(ResultCode.USER_ACCOUNT_NOT_EXIST.getCode());
            jsonResult.setErrorMsg("无效的登入状态!");
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }
        int page = node.get("page").asInt();
        int codeId = node.get("codeId").asInt();
        System.out.println("Page: "+page+"   CodeId: "+codeId);
        List<CodeIssues> codeIssues = codeIssuesService.selectCodeIssuesByPage(codeId, page);
        List<CodeIssuesInfo> codeIssuesInfos = new ArrayList<>();
        System.out.println("计数:"+codeIssues.size());
        for (CodeIssues issues: codeIssues){
            codeIssuesInfos.add(new CodeIssuesInfo(issues));
        }
        JsonResult jsonResult = new JsonResult(true);
        jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
        jsonResult.setData(codeIssuesInfos);
        return jsonObjectMapper.valueToTree(jsonResult).toString();

    }

    /**
     * 提交一个新的问题
     *
     * @param jsonData  json数据
     * @return          是否成功
     * @throws JsonProcessingException 转换错误
     */
    @PreAuthorize("hasAnyAuthority('view_self')")
    @RequestMapping(value = "/openNewIssues", produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String openNewIssues(@RequestBody String jsonData) throws JsonProcessingException {
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
        int proposerId = node.get("proposerId").asInt();
        String context = node.get("context").asText();
        CodeIssues codeIssues = new CodeIssues(context, codeId, proposerId);
        if (codeIssuesService.insertNewCodeIssues(codeIssues) == 1){
            JsonResult jsonResult = new JsonResult(true);
            jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }else {
            JsonResult jsonResult = new JsonResult(false);
            jsonResult.setErrorCode(ResultCode.PARAM_TYPE_ERROR.getCode());
            jsonResult.setErrorMsg(ResultCode.PARAM_TYPE_ERROR.getMessage());
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }
    }

    /**
     * 问题回复
     *
     * @param jsonData  json数据
     * @return          数据
     * @throws JsonProcessingException  json转换
     */
    @PreAuthorize("hasAnyAuthority('view_self')")
    @RequestMapping(value = "/replayIssues", produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String replayIssues(@RequestBody String jsonData) throws JsonProcessingException {
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
        int issuesId = node.get("issuesId").asInt();
        int solverId = node.get("solverId").asInt();
        String context = node.get("context").asText();
        CodeIssues codeIssues =  codeIssuesService.selectCodeIssuesById(issuesId);
        if (codeIssues == null){
            JsonResult jsonResult = new JsonResult(false);
            jsonResult.setErrorCode(ResultCode.ISSUES_IS_NOT_EXISTS.getCode());
            jsonResult.setErrorMsg(ResultCode.ISSUES_IS_NOT_EXISTS.getMessage());
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }
        if (!codeIssues.isOpen()){
            JsonResult jsonResult = new JsonResult(false);
            jsonResult.setErrorCode(ResultCode.ISSUES_IS_NOT_OPEN.getCode());
            jsonResult.setErrorMsg(ResultCode.ISSUES_IS_NOT_OPEN.getMessage());
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }
        codeIssues.setReplay(context);
        codeIssues.setSolver(solverId);
        codeIssues.setCloseDate(new Date());
        codeIssues.setOpen(false);
        if (codeIssuesService.updateCodeIssues(codeIssues) == 1){
            JsonResult jsonResult = new JsonResult(true);
            jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }else {
            JsonResult jsonResult = new JsonResult(false);
            jsonResult.setErrorCode(ResultCode.PARAM_TYPE_ERROR.getCode());
            jsonResult.setErrorMsg(ResultCode.PARAM_TYPE_ERROR.getMessage());
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }
    }
}
