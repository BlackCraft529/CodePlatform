package com.wrx.codeplatform.framework.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrx.codeplatform.domain.enums.ResultCode;
import com.wrx.codeplatform.domain.framework.entity.code.CodeInfo;
import com.wrx.codeplatform.domain.framework.entity.code.CodeInfoX;
import com.wrx.codeplatform.domain.framework.entity.user.CodeUserInfo;
import com.wrx.codeplatform.domain.framework.sql.code.Code;
import com.wrx.codeplatform.domain.framework.sql.container.ContainerLink;
import com.wrx.codeplatform.domain.framework.sql.user.UserInfo;
import com.wrx.codeplatform.domain.result.JsonResult;
import com.wrx.codeplatform.framework.service.CodeService;
import com.wrx.codeplatform.framework.service.ContainerLinkService;
import com.wrx.codeplatform.framework.service.UserInfoService;
import com.wrx.codeplatform.utils.common.TokenUtil;
import com.wrx.codeplatform.utils.data.SessionStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/4/25 0:08
 */
@RestController
public class ContainerLinkController {
    @Autowired
    private ContainerLinkService containerLinkService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private CodeService codeService;

    /**
     * json工具对象
     */
    private final ObjectMapper jsonObjectMapper =  new ObjectMapper();

    @PreAuthorize("hasAnyAuthority('view_self')")
    @RequestMapping(value = "/getClassAllStudent",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getClassAllStudent(@RequestBody String jsonData) throws JsonProcessingException {
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
        int containerId = node.get("containerId").asInt();
        List<ContainerLink> containerLinkList = containerLinkService.selectContainerLinkByContainerId(containerId);
        List<CodeUserInfo> userInfos = new ArrayList<>();
        for (ContainerLink containerLink: containerLinkList){
            Code code = codeService.selectCodeById(containerLink.getFileId());
            if (code == null){
                continue;
            }
            UserInfo userInfo = userInfoService.selectByUserId(code.getUserId());
            if (userInfo == null){
                continue;
            }
            userInfos.add(new CodeUserInfo(userInfo,code));
        }
        JsonResult jsonResult = new JsonResult(true);
        jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
        jsonResult.setData(userInfos);
        return jsonObjectMapper.valueToTree(jsonResult).toString();
    }

    /**
     * 提交作业到容器中
     *
     * @param jsonData  json数据
     * @return          结果
     * @throws JsonProcessingException  转换错误
     */
    @PreAuthorize("hasAnyAuthority('view_self')")
    @RequestMapping(value = "/commitCodesToContainer",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String commitCodesToContainer(@RequestBody String jsonData) throws JsonProcessingException {
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
        List<Integer> codesId = new ArrayList<>();
        for (String strId: node.get("commitCodesId").asText().split(",")){
            codesId.add(Integer.parseInt(strId));
        }
        //添加关联信息
        int sum = 0;
        int containerId = node.get("containerId").asInt();
        for (int codeId: codesId){
            sum+=containerLinkService.insertContainerLink(containerId, codeId);
        }
        if (sum > 0){
            JsonResult jsonResult = new JsonResult(true);
            jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }
        JsonResult jsonResult = new JsonResult(false);
        jsonResult.setErrorCode(ResultCode.SQL_ERROR.getCode());
        jsonResult.setErrorMsg(ResultCode.SQL_ERROR.getMessage());
        return jsonObjectMapper.valueToTree(jsonResult).toString();
    }

    /**
     * 获取当前容器可以添加的代码列表
     *
     * @param jsonData  json数据
     * @return          结果
     * @throws JsonProcessingException  转换错误
     */
    @PreAuthorize("hasAnyAuthority('view_self')")
    @RequestMapping(value = "/getCanCommitCodes",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getCanCommitCodes(@RequestBody String jsonData) throws JsonProcessingException {
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
        int userId = node.get("userId").asInt();
        int containerId = node.get("containerId").asInt();
        List<Code> allCodes = codeService.selectCodesByUserId(userId);
        List<ContainerLink> containerLinks = containerLinkService.selectContainerLinkByContainerId(containerId);
        List<CodeInfoX> codeInfos = new ArrayList<>();
        //查找可以再次添加的代码
        for (Code code : allCodes){
            boolean canJoin = true;
            for (ContainerLink containerLink: containerLinks){
                if (code.getId() == containerLink.getFileId()){
                    canJoin = false;
                    break;
                }
            }
            if (canJoin){
                codeInfos.add(new CodeInfoX(code));
            }
        }
        JsonResult jsonResult = new JsonResult(true);
        jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
        jsonResult.setData(codeInfos);
        return jsonObjectMapper.valueToTree(jsonResult).toString();
    }

    /**
     * 删除容器内的代码信息
     *
     * @param jsonData  json数据
     * @return          结果
     * @throws JsonProcessingException  转换错误
     */
    @PreAuthorize("hasAnyAuthority('con_modify')")
    @RequestMapping(value = "/delCodeFromContainer",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String delCodeFromContainer(@RequestBody String jsonData) throws JsonProcessingException {
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
        int containerId = node.get("containerId").asInt();
        int fileId = node.get("codeId").asInt();
        if (containerLinkService.deleteContainerLinkByCodeIdAndContainerId(containerId, fileId) > 0){
            JsonResult jsonResult = new JsonResult(true);
            jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }
        JsonResult jsonResult = new JsonResult(false);
        jsonResult.setErrorCode(ResultCode.SQL_ERROR.getCode());
        jsonResult.setErrorMsg(ResultCode.SQL_ERROR.getMessage());
        return jsonObjectMapper.valueToTree(jsonResult).toString();
    }
}
