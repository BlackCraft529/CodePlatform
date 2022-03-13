package com.wrx.codeplatform.framework.controller.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrx.codeplatform.domain.config.DataPool;
import com.wrx.codeplatform.domain.enums.ResultCode;
import com.wrx.codeplatform.domain.result.JsonResult;
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

}
