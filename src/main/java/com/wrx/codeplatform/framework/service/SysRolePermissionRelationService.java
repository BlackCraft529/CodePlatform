package com.wrx.codeplatform.framework.service;

import com.wrx.codeplatform.domain.framework.sql.permission.SysRolePermissionRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/13 23:20
 */
public interface SysRolePermissionRelationService {
    /**
     * 根据ID查询单条数据
     *
     * @param id   id
     * @return     结果
     */
    SysRolePermissionRelation selectById(@Param("id") int id);

    /**
     * 根据角色ID查询所有用户
     *
     * @param roleId   角色ID
     * @return         用户列表
     */
    List<SysRolePermissionRelation> selectByRoleId(int roleId);

    /**
     * 插入新的用户关系数据
     *
     * @param sysRolePermissionRelation   用户关系数据
     * @return                            影响条数
     */
    int insertNewSysRolePermissionRelation(SysRolePermissionRelation sysRolePermissionRelation);

    /**
     * 删除一条关系数据
     *
     * @param id  数据ID
     * @return    影响条数
     */
    int deleteSysRolePermissionRelationById(int id);

    /**
     * 删除一个用户的关系数据
     *
     * @param userId  用户
     * @return        影响条数
     */
    int deleteSysRolePermissionRelationByUserId(int userId);
}
