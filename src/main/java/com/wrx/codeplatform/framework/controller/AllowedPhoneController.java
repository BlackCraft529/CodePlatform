package com.wrx.codeplatform.framework.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrx.codeplatform.domain.enums.ResultCode;
import com.wrx.codeplatform.domain.result.JsonResult;
import com.wrx.codeplatform.framework.service.AllowedPhoneService;
import com.wrx.codeplatform.utils.common.TokenUtil;
import com.wrx.codeplatform.utils.data.SessionStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 魏荣轩
 * @date 2022/5/4 14:07
 */
@RestController
public class AllowedPhoneController {
    @Autowired
    private AllowedPhoneService allowedPhoneService;
    /**
     * json工具对象
     */
    private final ObjectMapper jsonObjectMapper =  new ObjectMapper();

    /**
     * 查询手机号是否被允许注册
     *
     * @param jsonData  json数据
     * @return          结果
     * @throws JsonProcessingException  转换错误
     */
    @RequestMapping(value = "/checkPhone",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String checkPhone(@RequestBody String jsonData) throws JsonProcessingException {
        JsonNode node = jsonObjectMapper.readTree(jsonData);
        String phone = node.get("phone").asText();
        System.out.println(phone);
        JsonResult jsonResult = new JsonResult(true);
        jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
        jsonResult.setData(allowedPhoneService.phoneIsAllowed(phone));
        return jsonObjectMapper.valueToTree(jsonResult).toString();
    }
}
