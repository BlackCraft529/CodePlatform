package com.wrx.codeplatform.framework.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 魏荣轩
 * @date 2022/2/9 16:53
 */
@RestController
public class UserController {

    /**
     * json工具对象
     */
    private final ObjectMapper jsonObjectMapper =  new ObjectMapper();

    @RequestMapping(value = "/test",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String welcome(@RequestBody String jsonData){
        System.out.println(jsonData);
        return "Hello Test";
    }

//    @RequestMapping(value = "/login",produces = {"text/plain;charset=UTF-8"})
//    @ResponseBody
//    public String test(@RequestBody String jsonData) throws JsonProcessingException {
//        JsonNode node = jsonObjectMapper.readTree(jsonData);
//        String account = node.get("account").asText();
//        String password = node.get("password").asText();
//
//        return "Hello";
//    }
}
