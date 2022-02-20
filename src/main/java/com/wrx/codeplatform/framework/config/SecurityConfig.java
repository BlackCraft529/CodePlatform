package com.wrx.codeplatform.framework.config;

import com.mysql.cj.x.protobuf.MysqlxSession;
import com.wrx.codeplatform.framework.config.component.CPAuthenticationFailHandler;
import com.wrx.codeplatform.framework.config.component.CPAuthenticationSuccessHandler;
import com.wrx.codeplatform.framework.config.filter.UserAuthenticationFilter;
import com.wrx.codeplatform.framework.service.CPUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author 魏荣轩
 * @date 2022/2/9 22:20
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CPAuthenticationSuccessHandler CPAuthenticationSuccessHandler;
    @Autowired
    private CPAuthenticationFailHandler CPAuthenticationFailHandler;

    /**
     * 重写,设置userDetailsService
     *
     * @param auth  auth
     * @throws Exception 异常
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        // 使用自定义UserDetailsService
        auth.userDetailsService(getUserDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * 解决无法直接注入 AuthenticationManager
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //自定义登录界面
        http.formLogin()
                //.loginPage("/login")   //登录页面  前后端分离不需要
                .and().authorizeRequests().antMatchers("/","/index","/login").permitAll() //设置不需要认证的路径
                .anyRequest().authenticated()  //所有请求都可以访问
                .and().csrf().disable();  //关闭csrf防护
        //启用自定义的过滤器
        http.addFilterAt(userAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    UserDetailsService getUserDetailsService(){
        return new CPUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserAuthenticationFilter userAuthenticationFilter() throws Exception {
        UserAuthenticationFilter filter = new UserAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(CPAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(CPAuthenticationFailHandler);
        filter.setFilterProcessesUrl("/loginAction");
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }
}
