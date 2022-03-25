package com.wrx.codeplatform.framework.controller.common;

import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrx.codeplatform.domain.config.DataPool;
import com.wrx.codeplatform.domain.enums.ResultCode;
import com.wrx.codeplatform.domain.framework.entity.rechecking.RecheckingResult;
import com.wrx.codeplatform.domain.framework.entity.rechecking.RecheckingResultInit;
import com.wrx.codeplatform.domain.framework.sql.code.Code;
import com.wrx.codeplatform.domain.result.JsonResult;
import com.wrx.codeplatform.framework.service.CodeService;
import com.wrx.codeplatform.utils.code.CodeFactory;
import com.wrx.codeplatform.utils.code.impl.CodeFactoryImpl;
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
 * @date 2022/3/11 14:42
 */
@RestController
public class CommonController {

    /**
     * json工具对象
     */
    private final ObjectMapper jsonObjectMapper =  new ObjectMapper();
    @Autowired
    private CodeService codeService;

    /**
     * 获取院校信息
     *
     * @return  院校列表
     */
    @RequestMapping(value = "/getSchools",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getSchools(){
        return jsonObjectMapper.valueToTree(new JsonResult(true, ResultCode.SUCCESS, DataPool.schools)).toString();
    }

    /**
     * 获取所在地列表
     *
     * @return  所在地
     */
    @RequestMapping(value = "/getLocations",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getLocations(){
        return jsonObjectMapper.valueToTree(new JsonResult(true, ResultCode.SUCCESS, DataPool.locations)).toString();
    }

    /**
     * 代码查重
     *
     * @param jsonData json数据
     * @return         是否成功查重
     * @throws JsonProcessingException  json数据
     */
    @PreAuthorize("hasAnyAuthority('view_self')")
    @RequestMapping(value = "/recheckingCode",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String recheckingCode(@RequestBody String jsonData) throws JsonProcessingException {
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
        final String range = node.get("range").asText();
        final String model = node.get("model").asText();
        final int chosenClass = node.get("class").asInt();
        final int chosenTeacher = node.get("teacher").asInt();
        final int codeId = node.get("codeId").asInt();
        //异步查重后写入数据库结果
        new Thread(() -> {
            CodeFactory codeFactory = new CodeFactoryImpl();
            codeFactory.recheckingCode(range, model,chosenClass, chosenTeacher, codeId);
        }).start();
        JsonResult jsonResult = new JsonResult(true);
        jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
        return jsonObjectMapper.valueToTree(jsonResult).toString();
    }

    /**
     * 查询Code的上次查重结果
     *
     * @param jsonData  json数据
     * @return          结果实体
     * @throws JsonProcessingException  json转换
     */
    @PreAuthorize("hasAnyAuthority('view_self')")
    @RequestMapping(value = "/getRecheckingCodeResult",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getRecheckingCodeResult(@RequestBody String jsonData) {
        try {
            JsonNode node = jsonObjectMapper.readTree(jsonData);
            String token = node.get("token").asText();
            String account = TokenUtil.validToken(token);
            if (SessionStorage.pwdMap.get(account) == null) {
                //用户不存在
                JsonResult jsonResult = new JsonResult(false);
                jsonResult.setErrorCode(ResultCode.USER_ACCOUNT_NOT_EXIST.getCode());
                jsonResult.setErrorMsg(ResultCode.USER_ACCOUNT_NOT_EXIST.getMessage());
                return jsonObjectMapper.valueToTree(jsonResult).toString();
            }
            int codeId = node.get("codeId").asInt();
            Code code = codeService.selectCodeById(codeId);
            //代码信息不存在
            if (code == null) {
                JsonResult jsonResult = new JsonResult(false);
                jsonResult.setErrorCode(ResultCode.CODE_NOT_EXISTS.getCode());
                jsonResult.setErrorMsg(ResultCode.CODE_NOT_EXISTS.getMessage());
                return jsonObjectMapper.valueToTree(jsonResult).toString();
            }
            //代码查重信息为空
            if (code.getResult().equals("{}") || code.getResult().equals("")) {
                JsonResult jsonResult = new JsonResult(false);
                jsonResult.setErrorCode(ResultCode.RECHECKING_RESULT_NOT_EXISTS.getCode());
                jsonResult.setErrorMsg(ResultCode.RECHECKING_RESULT_NOT_EXISTS.getMessage());
                return jsonObjectMapper.valueToTree(jsonResult).toString();
            }
            RecheckingResultInit recheckingResult = jsonObjectMapper.readValue(code.getResult(),RecheckingResultInit.class);
            RecheckingResult result = new RecheckingResult(recheckingResult);
            JsonResult jsonResult = new JsonResult(true);
            jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
            jsonResult.setData(result);
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }catch (Exception exception){
            exception.printStackTrace();
            JsonResult jsonResult = new JsonResult(false);
            jsonResult.setErrorCode(ResultCode.SERVICE_ERROR.getCode());
            jsonResult.setErrorMsg(ResultCode.SERVICE_ERROR.getMessage());
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }
    }

}
