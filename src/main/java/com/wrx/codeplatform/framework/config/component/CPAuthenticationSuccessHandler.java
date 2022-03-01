package com.wrx.codeplatform.framework.config.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 魏荣轩
 * @date 2022/2/20 23:02
 */
@Component
public class CPAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    /**
     * json工具对象
     */
    private final ObjectMapper jsonObjectMapper =  new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication authentication) throws IOException {
        // 允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 允许自定义请求头token(允许head跨域)
        response.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");

        response.setStatus(200);
        response.setContentType("application/json;charset=UTF-8");
        //设置登录成功后返回的用户信息
//        LoginResult loginResult = new LoginResult(authentication.getName(), "testToken", authentication.isAuthenticated());
//        response.getWriter().append(jsonObjectMapper.writeValueAsString(loginResult));
    }

}