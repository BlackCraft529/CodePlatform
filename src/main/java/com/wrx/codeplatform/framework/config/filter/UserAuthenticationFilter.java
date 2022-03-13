package com.wrx.codeplatform.framework.config.filter;

import com.wrx.codeplatform.domain.framework.sql.permission.SysPermission;
import com.wrx.codeplatform.domain.framework.sql.user.SysUser;
import com.wrx.codeplatform.framework.service.SysPermissionService;
import com.wrx.codeplatform.framework.service.SysUserService;
import com.wrx.codeplatform.utils.data.SessionStorage;
import com.wrx.codeplatform.utils.common.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 登录拦截器
 * @author 魏荣轩
 * @date 2022/2/20 23:22
 */
@Slf4j
public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysPermissionService sysPermissionService;
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
        String token = request.getParameter("token");
        if (token != null && !token.equals("")){
            System.out.println(token);
            try {
                String account = TokenUtil.validToken(token);
                System.out.println(account);
                SysUser sysUser = sysUserService.selectByAccount(account);
                if (sysUser == null) {
                    throw new RuntimeException("用户不存在");
                }
//                Collection<? extends GrantedAuthority> grantedAuthorities = new ArrayList<>();
                //获取该用户所拥有的权限
                List<SysPermission> sysPermissions = sysPermissionService.selectListByUser(sysUser.getId());
                StringBuilder auths = new StringBuilder();
                for (SysPermission per: sysPermissions){
                    auths.append(per.getPermissionCode()).append(",");
                }
                Collection<? extends GrantedAuthority> authorities =
                        Arrays.stream(auths.toString().split(","))
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList());
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(account, SessionStorage.pwdMap.get(account), authorities);
                return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            }catch (Exception ignore){
                //System.out.println(exception.getMessage());
            }
        }
        SessionStorage.pwdMap.put(request.getParameter("username"),request.getParameter("password"));
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getParameter("username"),
                        request.getParameter("password")));
    }



}