package com.wrx.codeplatform.domain.framework.sql.permission;

import lombok.Data;

/**
 * @author 魏荣轩
 * @date 2022/3/13 23:11
 */
@Data
public class SysRolePermissionRelation {
    private Integer id;
    private Integer roleId;
    private Integer permissionId;

    public SysRolePermissionRelation(int roleId, int permissionId){
        this.roleId = roleId;
        this.permissionId = permissionId;
    }
}
