package com.wrx.codeplatform.utils.common;

import com.wrx.codeplatform.framework.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

/**
 * @author 魏荣轩
 * @date 2022/3/25 0:21
 */
@Component
public class ServiceFactory {
    @Autowired
    private SysUserService sysUserServiceAuto;
    @Autowired
    private SysPermissionService sysPermissionServiceAuto;
    @Autowired
    private UserInfoService userInfoServiceAuto;
    @Autowired
    private CodeService codeServiceAuto;
    @Autowired
    private ContainerLinkService containerLinkServiceAuto;
    @Autowired
    private ContainerService containerServiceAuto;

    public static SysUserService sysUserService;
    public static SysPermissionService sysPermissionService;
    public static UserInfoService userInfoService;
    public static CodeService codeService;
    public static ContainerLinkService containerLinkService;
    public static ContainerService containerService;

    @PostConstruct
    public void init() {
        this.setSysUserService();
        this.setSysPermissionService();
        this.setUserInfoService();
        this.setCodeService();
        this.setContainerLinkService();
        this.setContainerService();
    }

    public void setContainerService(){ServiceFactory.containerService = containerServiceAuto;}
    public void setContainerLinkService(){ServiceFactory.containerLinkService = containerLinkServiceAuto;}
    public void setCodeService(){
        ServiceFactory.codeService = codeServiceAuto;
    }
    public void setUserInfoService(){
        ServiceFactory.userInfoService = userInfoServiceAuto;
    }
    public void setSysPermissionService(){
        ServiceFactory.sysPermissionService = sysPermissionServiceAuto;
    }
    public void setSysUserService(){
        ServiceFactory.sysUserService = sysUserServiceAuto;
    }
}
