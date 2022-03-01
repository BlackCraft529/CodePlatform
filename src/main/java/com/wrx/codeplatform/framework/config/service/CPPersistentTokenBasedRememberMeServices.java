package com.wrx.codeplatform.framework.config.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 魏荣轩
 * @date 2022/2/22 23:30
 */
public class CPPersistentTokenBasedRememberMeServices extends PersistentTokenBasedRememberMeServices {
    public CPPersistentTokenBasedRememberMeServices(String key, UserDetailsService userDetailsService, PersistentTokenRepository tokenRepository) {
        super(key, userDetailsService, tokenRepository);
    }

    @Override
    protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {
        boolean rememberMeFlag= super.rememberMeRequested(request, parameter);
        String RememberMeParameter=super.getParameter();
        String paramValue =(String) request.getAttribute(RememberMeParameter);
        if (paramValue != null) {
            if (paramValue.equalsIgnoreCase("true") || paramValue.equalsIgnoreCase("on")
                    || paramValue.equalsIgnoreCase("yes") || paramValue.equals("1")) {
                System.out.println("记住我设置");
                return true;
            }
        }
        return rememberMeFlag;
    }
}
