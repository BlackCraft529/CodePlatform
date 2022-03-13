package com.wrx.codeplatform.framework.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrx.codeplatform.domain.result.JsonResult;
import com.wrx.codeplatform.utils.common.ResultUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Hutengfei
 * @Description: 登出成功处理逻辑
 * @Date Create in 2019/9/4 10:17
 */
@Component
public class CustomizeLogoutSuccessHandler implements LogoutSuccessHandler {
    /**
     * json工具对象
     */
    private final ObjectMapper jsonObjectMapper =  new ObjectMapper();

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        JsonResult result = ResultUtil.success();
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(jsonObjectMapper.writeValueAsString(result));
    }
}
