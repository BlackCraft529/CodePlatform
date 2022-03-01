package com.wrx.codeplatform.domain.framework.sql.permission;

import lombok.Data;

import java.io.Serializable;

/**
 * 权限表(SysPermission)实体类
 *
 * @author makejava
 * @since 2019-08-29 21:13:29
 */
@Data
public class SysPermission implements Serializable {
    private static final long serialVersionUID = -71969734644822184L;
    //主键id
    private Integer id;
    //权限code
    private String permissionCode;
    //权限名
    private String permissionName;


}