package com.wrx.codeplatform.domain.framework.sql.user;

import lombok.Data;

/**
 * @author 魏荣轩
 * @date 2022/3/13 23:34
 */
@Data
public class SysUserRoleRelation {
    private Integer id;
    private Integer userId;
    private Integer roleId;

    public SysUserRoleRelation(int userId, int roleId){
        this.userId = userId;
        this.roleId = roleId;
    }
}
