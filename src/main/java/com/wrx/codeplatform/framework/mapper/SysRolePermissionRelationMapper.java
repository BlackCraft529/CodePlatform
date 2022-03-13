package com.wrx.codeplatform.framework.mapper;

import com.wrx.codeplatform.domain.framework.sql.permission.SysRolePermissionRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/13 23:13
 */
@Repository
public interface SysRolePermissionRelationMapper {
    /**
     * 根据ID查询单条数据
     *
     * @param id   id
     * @return     结果
     */
    SysRolePermissionRelation selectById(@Param("id") int id);

    /**
     * 根据角色ID查询所有权限
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
     * 删除一个角色的关系数据
     *
     * @param roleId  用户
     * @return        影响条数
     */
    int deleteSysRolePermissionRelationByRoleId(int roleId);

}
