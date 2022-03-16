package com.wrx.codeplatform.framework.config;

import com.wrx.codeplatform.framework.config.common.PwdEncoder;
import com.wrx.codeplatform.framework.config.filter.JwtAuthenticationFilter;
import com.wrx.codeplatform.framework.config.filter.UserAuthenticationFilter;
import com.wrx.codeplatform.framework.config.handler.*;
import com.wrx.codeplatform.framework.config.service.CPPersistentTokenBasedRememberMeServices;
import com.wrx.codeplatform.framework.config.service.CPUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import javax.sql.DataSource;


/**
 * @author 魏荣轩
 * @date 2022/2/9 22:20
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //登录成功处理逻辑
    @Autowired
    CustomizeAuthenticationSuccessHandler authenticationSuccessHandler;

    //登录失败处理逻辑
    @Autowired
    CustomizeAuthenticationFailureHandler authenticationFailureHandler;

    //权限拒绝处理逻辑
    @Autowired
    CustomizeAccessDeniedHandler accessDeniedHandler;

    //匿名用户访问无权限资源时的异常
    @Autowired
    CustomizeAuthenticationEntryPoint authenticationEntryPoint;

    //会话失效(账号被挤下线)处理逻辑
    @Autowired
    CustomizeSessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    //登出成功处理逻辑
    @Autowired
    CustomizeLogoutSuccessHandler logoutSuccessHandler;

    //访问决策管理器
    @Autowired
    CustomizeAccessDecisionManager accessDecisionManager;

    //实现权限拦截
    @Autowired
    CustomizeFilterInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    private CustomizeAbstractSecurityInterceptor securityInterceptor;


    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;
    @Autowired
    private CPUserDetailsService userDetailsService;

    private static final String rememberMeKey = "CodePlatform88L";


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
        http.formLogin().loginProcessingUrl("/loginAction").permitAll().and()
                .cors().and().csrf().disable();
        http.authorizeRequests().
                //antMatchers("/getUser").hasAuthority("query_user").
                //antMatchers("/**").fullyAuthenticated().
                        withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(accessDecisionManager);//决策管理器
                        o.setSecurityMetadataSource(securityMetadataSource);//安全元数据源
                        return o;
                    }
                }).
                //登出
                        and().logout().
                permitAll().//允许所有用户
                logoutSuccessHandler(logoutSuccessHandler).//登出成功处理逻辑
                deleteCookies("JSESSIONID").//登出之后删除cookie
                //登入
//                        and().formLogin().
//                permitAll().//允许所有用户
//                successHandler(authenticationSuccessHandler).//登录成功处理逻辑
//                failureHandler(authenticationFailureHandler).//登录失败处理逻辑
                //异常处理(权限拒绝、登录失效等)
                        and().exceptionHandling().
                accessDeniedHandler(accessDeniedHandler).//权限拒绝处理逻辑
                authenticationEntryPoint(authenticationEntryPoint).//匿名用户访问无权限资源时的异常处理
                //记住我功能实现
                    and().rememberMe()
                .key(rememberMeKey)
                .rememberMeServices(getPersistentTokenBasedRememberMeServices())
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(60 * 60 * 24)
                .userDetailsService(userDetailsService).
                //无权限即可访问的页面管理
                    and().authorizeRequests().
                antMatchers("/getSchools","/getLocations","/checkOrCreateVerifyStatus",
                        "/verifyCodeAndRegister").permitAll().
                //会话管理
                        and().sessionManagement().
                maximumSessions(1).//同一账号同时登录最大用户数
                expiredSessionStrategy(sessionInformationExpiredStrategy);//会话失效(账号被挤下线)处理逻辑
        http.addFilterBefore(securityInterceptor, FilterSecurityInterceptor.class);
        http.addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//        //自定义登录界面
//        http.formLogin()
//                .and().authorizeRequests().antMatchers("/","/test","/login").permitAll() //设置不需要认证的路径
//                //指用户只有admins权限才能访问/test/index路径
//                //.antMatchers("/test/index").hasAnyAuthority("admins")
//                //方法二 多个权限
//                //.antMatchers("/test/index").hasAnyAuthority("admins,index")
//                //方法三 角色权限控制  用户需要有角色权限: "ROLE_player"
//                //.antMatchers("/test/index").hasRole("player")
//                //方法四 角色权限控制  用户需要有角色权限: "ROLE_player" 或者"ROLE_creator"
//                .antMatchers("/full").hasRole("player,creator")
//                .anyRequest().authenticated()  //所有请求都可以访问
//                //设置令牌-记住我 功能
//                .and()
//                .rememberMe()
//                .key(rememberMeKey)
//                .rememberMeServices(getPersistentTokenBasedRememberMeServices())
//                .tokenRepository(persistentTokenRepository())
//                .tokenValiditySeconds(60 * 60 * 24)
//                .userDetailsService(userDetailsService)
//                .and()
//                //设置无权限处理方案
//                .exceptionHandling().accessDeniedHandler(getAccessDeniedHandler())
//                .and().csrf().disable();  //关闭csrf防护
//        //启用自定义的过滤器
        http.addFilterAt(userAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

//    @Bean
//    public UsernamePasswordAuthenticationFilter userAuthenticationFilter() {
//        return new UsernamePasswordAuthenticationFilter();
//    }

    @Bean
    UserDetailsService getUserDetailsService(){
        return new CPUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return PwdEncoder.getPasswordEncoder();
    }

    @Bean
    public UserAuthenticationFilter userAuthenticationFilter() throws Exception {
        UserAuthenticationFilter filter = new UserAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
        filter.setFilterProcessesUrl("/loginAction");
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }


    @Bean
    public PersistentTokenBasedRememberMeServices getPersistentTokenBasedRememberMeServices(){
        return new CPPersistentTokenBasedRememberMeServices(rememberMeKey, userDetailsService, persistentTokenRepository());
    }

    /**
     * 持久化Token
     *
     * @return PersistentTokenRepository
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

}
