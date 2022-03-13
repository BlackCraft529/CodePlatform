package com.wrx.codeplatform.framework.service;

import com.wrx.codeplatform.domain.framework.sql.user.SysUserRoleRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/13 23:42
 */
public interface SysUserRoleRelationService {
    /**
     * 根据数据ID查询用户角色
     *
     * @param id  id
     * @return    结果
     */
    SysUserRoleRelation selectSysUserRoleRelationById(@Param("id") int id);

    /**
     * 根据UserId查询用户角色关联信息
     *
     * @param userId   用户ID
     * @return         列表
     */
    List<SysUserRoleRelation> selectSysUserRoleRelationByUserId(int userId);

    /**
     * 根据RoleId获取用户角色关联信息
     *
     * @param roleId   角色ID
     * @return         列表
     */
    List<SysUserRoleRelation> selectSysUserRoleRelationByRoleId(int roleId);

    /**
     * 插入新的角色用户关联信息
     *
     * @param sysUserRoleRelation   用户角色关联
     * @return                      影响条数
     */
    int insertNewSysUserRoleRelation(SysUserRoleRelation sysUserRoleRelation);

    /**
     * 根据数据ID删除SysUserRoleRelation
     *
     * @param id  数据ID
     * @return    影响条数
     */
    int deleteSysUserRoleRelationById(int id);

    /**
     * 根据用户ID删除SysUserRoleRelation
     *
     * @param userId   用户ID
     * @return         影响条数
     */
    int deleteSysUserRoleRelationByUserId(int userId);
}
