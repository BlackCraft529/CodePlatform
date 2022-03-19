package com.wrx.codeplatform.utils.common;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.wrx.codeplatform.domain.exception.NotLoginException;
import com.wrx.codeplatform.domain.framework.sql.permission.SysPermission;
import com.wrx.codeplatform.domain.framework.sql.user.SysUser;
import com.wrx.codeplatform.framework.service.SysPermissionService;
import com.wrx.codeplatform.framework.service.SysUserService;
import com.wrx.codeplatform.utils.data.SessionStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 魏荣轩
 * @date 2022/2/23 23:57
 */
@Component
@Slf4j
public class TokenUtil {
    /**
     * 创建秘钥
     */
    private static final byte[] SECRET = "6MNSobBRCHGIO0fS6MNSobBRCHGIO0fS".getBytes();

    @Autowired
    private SysUserService sysUserServiceAuto;
    @Autowired
    private SysPermissionService sysPermissionServiceAuto;
    @Autowired
    private AuthenticationManager authenticationManagerAuto;

    /**
     * 过期时间5秒
     */
    private static final long EXPIRE_TIME = 1000 * 3600;
    private static SysUserService sysUserService;
    private static SysPermissionService sysPermissionService;
    private static AuthenticationManager authenticationManager;
    private static final String COOKIE_TOKEN = "token";



    @PostConstruct
    public void init() {
        this.setSysUserService();
        this.setSysPermissionService();
        this.setAuthenticationManager();

    }
    public void setAuthenticationManager(){
        TokenUtil.authenticationManager = authenticationManagerAuto;
    }
    public void setSysPermissionService(){
        TokenUtil.sysPermissionService = sysPermissionServiceAuto;
    }
    public void setSysUserService(){
        TokenUtil.sysUserService = sysUserServiceAuto;
    }

    public static Authentication getAuthentication(HttpServletRequest httpServletRequest) {
        Cookie cookie = WebUtils.getCookie(httpServletRequest, COOKIE_TOKEN);
        String token = cookie != null ? cookie.getValue() : null;
        if (token != null && !token.equals("")) {
            try {
                String account = validToken(token);
                SysUser sysUser = sysUserService.selectByAccount(account);
                if (sysUser == null) {
                    throw new RuntimeException("用户不存在");
                }
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
            } catch (Exception exception) {
                //System.out.println("Token Error:"+exception.getMessage());
            }
        }
        return null;
    }


    /**
     * 生成Token
     * @param account
     * @return
     */
    public static String buildJWT(String account) {
        try {
            /**
             * 1.创建一个32-byte的密匙
             */
            MACSigner macSigner = new MACSigner(SECRET);
            /**
             * 2. 建立payload 载体
             */
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject("doi")
                    .issuer("http://www.wrx.com")
                    .expirationTime(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                    .claim("ACCOUNT",account)
                    .build();

            /**
             * 3. 建立签名
             */
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(macSigner);

            /**
             * 4. 生成token
             */
            String token = signedJWT.serialize();
            return token;
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 校验token
     * @param token  token
     * @return       用户名
     */
    public static String validToken(String token) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SECRET);
            //校验是否有效
            if (!jwt.verify(verifier)) {
                throw new NotLoginException("身份无效");
            }

            //校验超时
            Date expirationTime = jwt.getJWTClaimsSet().getExpirationTime();
            if (new Date().after(expirationTime)) {
                throw new NotLoginException("身份已过期");
            }

            //获取载体中的数据
            Object account = jwt.getJWTClaimsSet().getClaim("ACCOUNT");
            //是否有openUid
            if (Objects.isNull(account)){
                throw new NotLoginException("账号为空");
            }
            return account.toString();
        } catch (ParseException | JOSEException e) {
//            e.printStackTrace();
            return null;
        }
    }
}
