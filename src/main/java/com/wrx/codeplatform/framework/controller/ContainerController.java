package com.wrx.codeplatform.framework.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrx.codeplatform.domain.enums.ResultCode;
import com.wrx.codeplatform.domain.framework.entity.container.ContainerInfo;
import com.wrx.codeplatform.domain.framework.sql.container.Container;
import com.wrx.codeplatform.domain.framework.sql.container.ContainerLink;
import com.wrx.codeplatform.domain.framework.sql.user.SysUser;
import com.wrx.codeplatform.domain.result.JsonResult;
import com.wrx.codeplatform.framework.service.ContainerLinkService;
import com.wrx.codeplatform.framework.service.ContainerService;
import com.wrx.codeplatform.framework.service.SysUserService;
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
 * @date 2022/3/21 22:37
 */
@RestController
public class ContainerController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ContainerService containerService;
    @Autowired
    private ContainerLinkService containerLinkService;


    /**
     * json工具对象
     */
    private final ObjectMapper jsonObjectMapper =  new ObjectMapper();

    /**
     * 根据创建者查询容器
     *
     * @param jsonData  数据
     * @return          容器列表
     * @throws JsonProcessingException   json数据
     */
    @PreAuthorize("hasAnyAuthority('view_self')")
    @RequestMapping(value = "/getContainerInfoListByCreator",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getContainerInfoListByCreator(@RequestBody String jsonData) throws JsonProcessingException {
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
        int creator = node.get("teacher").asInt();
        List<Container> containers = containerService.selectContainerByCreator(creator);
        List<ContainerInfo> containerInfos = new ArrayList<>();
        for (Container container: containers){
            containerInfos.add(new ContainerInfo(container));
        }
        JsonResult jsonResult = new JsonResult(true);
        jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
        jsonResult.setData(containerInfos);
        return jsonObjectMapper.valueToTree(jsonResult).toString();
    }

    /**
     * 根据创建者查询容器
     *
     * @param jsonData  数据
     * @return          容器列表
     * @throws JsonProcessingException   json数据
     */
    @PreAuthorize("hasAnyAuthority('view_self')")
    @RequestMapping(value = "/getContainerListByCreator",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getContainerListByCreator(@RequestBody String jsonData) throws JsonProcessingException {
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
        int creator = node.get("teacher").asInt();
        List<Container> containers = containerService.selectContainerByCreator(creator);
        JsonResult jsonResult = new JsonResult(true);
        jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
        jsonResult.setData(containers);
        return jsonObjectMapper.valueToTree(jsonResult).toString();
    }


    /**
     * 获取用户参加的班级信息
     *
     * @param jsonData  json数据
     * @return          班级列表
     * @throws JsonProcessingException  转换错误
     */
    @PreAuthorize("hasAnyAuthority('view_self')")
    @RequestMapping(value = "/getContainerList",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getContainerList(@RequestBody String jsonData) throws JsonProcessingException {
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
        List<Container> containers = containerService.selectUsersAllContainer(sysUser.getId());
        List<ContainerInfo> containerInfos = new ArrayList<>();
        for (Container container: containers){
            containerInfos.add(new ContainerInfo(container));
        }
        JsonResult jsonResult = new JsonResult(true);
        jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
        jsonResult.setData(containerInfos);
        return jsonObjectMapper.valueToTree(jsonResult).toString();
    }
    /**
     * 获取页数班级
     *
     * @param jsonData json数据
     * @return 数据
     * @throws JsonProcessingException json转换错误
     */
    @PreAuthorize("hasAnyAuthority('view_self')")
    @RequestMapping(value = "/getAllContainersByPage", produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getAllClassesByPage(@RequestBody String jsonData) throws JsonProcessingException {
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
        int page = node.get("page").asInt();
        List<Container> containers = containerService.selectContainerByPages(page);
        List<ContainerInfo> containerInfos = new ArrayList<>();
        for (Container container: containers){
            containerInfos.add(new ContainerInfo(container));
        }
        JsonResult jsonResult = new JsonResult(true);
        jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
        jsonResult.setData(containerInfos);
        return jsonObjectMapper.valueToTree(jsonResult).toString();
    }

    /**
     * 获取班级总数
     *
     * @param jsonData json数据
     * @return 数据
     * @throws JsonProcessingException json转换错误
     */
    @PreAuthorize("hasAnyAuthority('view_self')")
    @RequestMapping(value = "/getTotalContainers", produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getTotalContainers(@RequestBody String jsonData) throws JsonProcessingException {
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
        int total = containerService.getTotalContainers();
        JsonResult jsonResult = new JsonResult(true);
        jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
        jsonResult.setData(total);
        return jsonObjectMapper.valueToTree(jsonResult).toString();
    }

    /**
     * 查询所有管理的班级
     *
     * @param jsonData json数据
     * @return 数据
     * @throws JsonProcessingException json转换错误
     */
    @PreAuthorize("hasAnyAuthority('view_self')")
    @RequestMapping(value = "/getTotalContainersByCreator", produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getTotalContainersByCreator(@RequestBody String jsonData) throws JsonProcessingException {
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
        int creator = node.get("teacher").asInt();
        int total = containerService.getTotalContainersByCreator(creator);
        JsonResult jsonResult = new JsonResult(true);
        jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
        jsonResult.setData(total);
        return jsonObjectMapper.valueToTree(jsonResult).toString();

    }

    /**
     * 获取自己所创建的所有容器信息
     *
     * @param jsonData json数据
     * @return 数据
     * @throws JsonProcessingException json转换错误
     */
    @PreAuthorize("hasAnyAuthority('view_self')")
    @RequestMapping(value = "/getAllContainersByPageAndCreator", produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getAllContainersByPageAndCreator(@RequestBody String jsonData) throws JsonProcessingException {
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
        int page = node.get("page").asInt();
        int creator = node.get("teacher").asInt();
        List<Container> containers = containerService.selectContainersByPageAndCreator(page, creator);
        List<ContainerInfo> containerInfos = new ArrayList<>();
        for (Container container: containers){
            containerInfos.add(new ContainerInfo(container));
        }
        JsonResult jsonResult = new JsonResult(true);
        jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
        jsonResult.setData(containerInfos);
        return jsonObjectMapper.valueToTree(jsonResult).toString();
    }

    /**
     * 删除容器相关ID信息
     *
     * @param jsonData  json数据
     * @return          影响条数
     * @throws JsonProcessingException   转换错误
     */
    @PreAuthorize("hasAnyAuthority('con_delete')")
    @RequestMapping(value = "/delContainerById", produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String delContainerById(@RequestBody String jsonData) throws JsonProcessingException {
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
        System.out.println("删除容器ID: "+containerId);
        if (containerService.deleteContainer(containerId) == 1){
            //删除与之相关的关联
            containerLinkService.deleteContainerByContainerId(containerId);
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
     * 创建容器
     *
     * @param jsonData   json数据
     * @return           影响条数
     * @throws JsonProcessingException  转换错误
     */
    @PreAuthorize("hasAnyAuthority('con_delete')")
    @RequestMapping(value = "/createContainer", produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String createContainer(@RequestBody String jsonData) throws JsonProcessingException {
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
        int creator = node.get("creator").asInt();
        String name = node.get("name").asText();
        String desc = node.get("desc").asText();
        if (containerService.insertContainer(name, creator, desc) == 1){
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
