package com.wrx.codeplatform.framework.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 * @author 魏荣轩
 * @date 2022/2/20 23:22
 */
@Slf4j
public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * spring security接受登录请求
     * 前端Vue使用form表单格式提交数据
     *
     * @param request  请求
     * @param response 回应
     * @return  Authentication spring security 登录状态
     * @throws AuthenticationException 错误
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getParameter("username"),
                        request.getParameter("password")));
    }



}