package com.wrx.codeplatform.framework.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrx.codeplatform.domain.enums.ResultCode;
import com.wrx.codeplatform.domain.framework.entity.code.CodeTotalInfo;
import com.wrx.codeplatform.domain.framework.sql.code.Code;
import com.wrx.codeplatform.domain.framework.sql.user.SysUser;
import com.wrx.codeplatform.domain.result.JsonResult;
import com.wrx.codeplatform.framework.service.CodeService;
import com.wrx.codeplatform.framework.service.SysUserService;
import com.wrx.codeplatform.utils.common.TokenUtil;
import com.wrx.codeplatform.utils.data.SessionStorage;
import com.wrx.codeplatform.utils.file.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/19 21:54
 */
@RestController
public class CodeController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CodeService codeService;

    /**
     * json工具对象
     */
    private final ObjectMapper jsonObjectMapper =  new ObjectMapper();

    /**
     * 获取服务器代码文件内容
     *
     * @param jsonData  json数据
     * @return          数据
     * @throws JsonProcessingException   json转换错误
     */
    @PreAuthorize("hasAnyAuthority('view_self')")
    @RequestMapping(value = "/getCodeContent",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getCodeContent(@RequestBody String jsonData) throws JsonProcessingException {
        JsonNode node = jsonObjectMapper.readTree(jsonData);
        String token = node.get("token").asText();
        int codeId = node.get("codeId").asInt();
        String account = TokenUtil.validToken(token);
        if (SessionStorage.pwdMap.get(account) == null ){
            //用户不存在
            JsonResult jsonResult = new JsonResult(false);
            jsonResult.setErrorCode(ResultCode.USER_ACCOUNT_NOT_EXIST.getCode());
            jsonResult.setErrorMsg("无效的登入状态!");
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }
        String codePath = codeService.selectCodeById(codeId).getFilePath();
        String content = FileUtil.readFileContent(codePath);
        JsonResult jsonResult = new JsonResult(true);
        jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
        jsonResult.setData(content);
        return jsonObjectMapper.valueToTree(jsonResult).toString();
    }

        /**
         * 获取用户最近的代码列表
         *
         * @param jsonData  json数据
         * @return          代码列表
         * @throws JsonProcessingException  json转换错误
         */
    @PreAuthorize("hasAnyAuthority('view_self')")
    @RequestMapping(value = "/getCodeList",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getCodeList(@RequestBody String jsonData) throws JsonProcessingException{
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
        SysUser sysUser = sysUserService.selectByAccount(account);
        List<Code> codeList = codeService.selectRecentCode(sysUser.getId(), 6);
        JsonResult jsonResult = new JsonResult(true);
        jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
        jsonResult.setData(codeList);
        return jsonObjectMapper.valueToTree(jsonResult).toString();
    }

    /**
     * 根据下标递增查询代码信息
     *
     * @param jsonData  json数据
     * @return          代码列表
     * @throws JsonProcessingException  json转换错误
     */
    @PreAuthorize("hasAnyAuthority('view_self')")
    @RequestMapping(value = "/getCodesByIndex",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getCodesByIndex(@RequestBody String jsonData) throws JsonProcessingException{
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
        SysUser sysUser = sysUserService.selectByAccount(account);
        int start = node.get("start").asInt();
        int add = node.get("add").asInt();
        List<Code> codeList = codeService.selectCodeByIndex(start, add, sysUser.getId());
        JsonResult jsonResult = new JsonResult(true);
        jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
        jsonResult.setData(codeList);
        return jsonObjectMapper.valueToTree(jsonResult).toString();
    }

    /**
     * 获取代码统计信息
     *
     * @param jsonData   json数据
     * @return           统计信息
     * @throws JsonProcessingException   json转换错误
     */
    @PreAuthorize("hasAnyAuthority('view_self')")
    @RequestMapping(value = "/getCodeTotalInfo",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getCodeTotalInfo(@RequestBody String jsonData) throws JsonProcessingException{
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
        SysUser sysUser = sysUserService.selectByAccount(account);
        int codesCount = codeService.selectCodesByUserId(sysUser.getId()).size();
        CodeTotalInfo codeTotalInfo = new CodeTotalInfo(codesCount);
        JsonResult jsonResult = new JsonResult(true);
        jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
        jsonResult.setData(codeTotalInfo);
        return jsonObjectMapper.valueToTree(jsonResult).toString();
    }



}
