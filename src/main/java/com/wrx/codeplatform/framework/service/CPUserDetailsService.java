package com.wrx.codeplatform.framework.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wrx.codeplatform.domain.user.User;
import com.wrx.codeplatform.framework.mapper.interfaces.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/2/9 21:39
 */
@Service("userDetailsService")
public class CPUserDetailsService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 加载用户数据信息
     *
     * @param username  用户名
     * @return          用户信息
     * @throws UsernameNotFoundException  用户不存在
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //条件构造器
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //条件查询
        wrapper.eq("account", username);
        //查询
        User user = userMapper.selectOne(wrapper);
        if (user == null){
            //抛出异常 或者 返回用户不存在的值
            throw new UsernameNotFoundException("");
        }
        List<GrantedAuthority> authorities =
                AuthorityUtils.commaSeparatedStringToAuthorityList("role");
        //密码数据库内已经加密，无需再次加密
        return new org.springframework.security.core.userdetails.User(user.getAccount(),
                user.getPassword(), authorities);
    }
}
