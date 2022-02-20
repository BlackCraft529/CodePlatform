package com.wrx.codeplatform.framework.config.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrx.codeplatform.domain.result.LoginResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 魏荣轩
 * @date 2022/2/20 23:00
 */
@Component
public class CPAuthenticationFailHandler implements AuthenticationFailureHandler {

    /**
     * json工具对象
     */
    private final ObjectMapper jsonObjectMapper =  new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException {
        // 允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 允许自定义请求头token(允许head跨域)
        response.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        response.setStatus(200);
        response.setContentType("application/json;charset=UTF-8");
        //设置登录成功后返回的用户信息
        String reason = e.getLocalizedMessage().equalsIgnoreCase("Bad credentials")?"密码错误":e.getLocalizedMessage();
        LoginResult loginResult = new LoginResult("null", "null", false, reason);
        response.getWriter().append(jsonObjectMapper.writeValueAsString(loginResult));
    }


}
