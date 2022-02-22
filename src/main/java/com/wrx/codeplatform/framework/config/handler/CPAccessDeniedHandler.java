package com.wrx.codeplatform.framework.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrx.codeplatform.domain.enums.ResultType;
import com.wrx.codeplatform.domain.result.PermissionResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author 魏荣轩
 * @date 2022/2/22 21:55
 */
public class CPAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * json工具对象
     */
    private final ObjectMapper jsonObjectMapper =  new ObjectMapper();

    /**
     * 重写自定义返回信息
     *
     * @param request  请求
     * @param response  相应
     * @param accessDeniedException  accessDeniedException
     * @throws IOException  IO错误
     * @throws ServletException  servlet错误
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(200);
        response.setContentType("application/json;charset=UTF-8");
        //设置无权限提示
        PermissionResult permissionResult = new PermissionResult(ResultType.NO_PERMISSION.getReason(), new Date());
        response.getWriter().append(jsonObjectMapper.writeValueAsString(permissionResult));
    }
}
