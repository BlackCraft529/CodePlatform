package com.wrx.codeplatform.framework.config.service;

import com.wrx.codeplatform.domain.framework.sql.permission.SysPermission;
import com.wrx.codeplatform.domain.framework.sql.user.SysUser;
import com.wrx.codeplatform.framework.service.SysPermissionService;
import com.wrx.codeplatform.framework.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/2/9 21:39
 */
@Service("userDetailsService")
public class CPUserDetailsService implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysPermissionService sysPermissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || "".equals(username)) {
            throw new RuntimeException("用户不能为空");
        }
        //根据用户名查询用户
        SysUser sysUser = sysUserService.selectByName(username);
        if (sysUser == null) {
            throw new RuntimeException("用户不存在");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        //获取该用户所拥有的权限
        List<SysPermission> sysPermissions = sysPermissionService.selectListByUser(sysUser.getId());
        // 声明用户授权
        sysPermissions.forEach(sysPermission -> {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
                    sysPermission.getPermissionCode());
            grantedAuthorities.add(grantedAuthority);
        });
        return new User(sysUser.getAccount(),
                sysUser.getPassword(),
                sysUser.getEnabled(),
                sysUser.getAccountNonExpired(),
                sysUser.getCredentialsNonExpired(),
                sysUser.getAccountNonLocked(),
                grantedAuthorities);
    }
}
