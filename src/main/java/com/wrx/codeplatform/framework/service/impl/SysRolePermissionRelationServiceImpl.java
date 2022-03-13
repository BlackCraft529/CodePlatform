package com.wrx.codeplatform.framework.service.impl;

import com.wrx.codeplatform.domain.framework.sql.permission.SysRolePermissionRelation;
import com.wrx.codeplatform.framework.mapper.SysRolePermissionRelationMapper;
import com.wrx.codeplatform.framework.service.SysRolePermissionRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/13 23:20
 */
@Service("sysRolePermissionRelationService")
public class SysRolePermissionRelationServiceImpl implements SysRolePermissionRelationService {

    @Autowired
    private SysRolePermissionRelationMapper sysRolePermissionRelationMapper;

    /**
     * 根据ID查询单条数据
     *
     * @param id id
     * @return 结果
     */
    @Override
    public SysRolePermissionRelation selectById(int id) {
        return null;
    }

    /**
     * 根据角色ID查询所有用户
     *
     * @param roleId 角色ID
     * @return 用户列表
     */
    @Override
    public List<SysRolePermissionRelation> selectByRoleId(int roleId) {
        return null;
    }

    /**
     * 插入新的用户关系数据
     *
     * @param sysRolePermissionRelation 用户关系数据
     * @return 影响条数
     */
    @Override
    public int insertNewSysRolePermissionRelation(SysRolePermissionRelation sysRolePermissionRelation) {
        return sysRolePermissionRelationMapper.insertNewSysRolePermissionRelation(sysRolePermissionRelation);
    }

    /**
     * 删除一条关系数据
     *
     * @param id 数据ID
     * @return 影响条数
     */
    @Override
    public int deleteSysRolePermissionRelationById(int id) {
        return 0;
    }

    /**
     * 删除一个用户的关系数据
     *
     * @param userId 用户
     * @return 影响条数
     */
    @Override
    public int deleteSysRolePermissionRelationByUserId(int userId) {
        return 0;
    }
}
