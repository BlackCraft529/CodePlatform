package com.wrx.codeplatform.framework.service.impl;

import com.wrx.codeplatform.domain.framework.sql.user.SysUserRoleRelation;
import com.wrx.codeplatform.framework.mapper.SysUserRoleRelationMapper;
import com.wrx.codeplatform.framework.service.SysUserRoleRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/13 23:42
 */
@Service("sysUserRoleRelationService")
public class SysUserRoleRelationServiceImpl implements SysUserRoleRelationService {

    @Autowired
    private SysUserRoleRelationMapper sysUserRoleRelationMapper;

    /**
     * 根据数据ID查询用户角色
     *
     * @param id id
     * @return 结果
     */
    @Override
    public SysUserRoleRelation selectSysUserRoleRelationById(int id) {
        return null;
    }

    /**
     * 根据UserId查询用户角色关联信息
     *
     * @param userId 用户ID
     * @return 列表
     */
    @Override
    public List<SysUserRoleRelation> selectSysUserRoleRelationByUserId(int userId) {
        return sysUserRoleRelationMapper.selectSysUserRoleRelationByUserId(userId);
    }

    /**
     * 根据RoleId获取用户角色关联信息
     *
     * @param roleId 角色ID
     * @return 列表
     */
    @Override
    public List<SysUserRoleRelation> selectSysUserRoleRelationByRoleId(int roleId) {
        return sysUserRoleRelationMapper.selectSysUserRoleRelationByRoleId(roleId);
    }

    /**
     * 插入新的角色用户关联信息
     *
     * @param sysUserRoleRelation 用户角色关联
     * @return 影响条数
     */
    @Override
    public int insertNewSysUserRoleRelation(SysUserRoleRelation sysUserRoleRelation) {
        return sysUserRoleRelationMapper.insertNewSysUserRoleRelation(sysUserRoleRelation);
    }

    /**
     * 根据数据ID删除SysUserRoleRelation
     *
     * @param id 数据ID
     * @return 影响条数
     */
    @Override
    public int deleteSysUserRoleRelationById(int id) {
        return 0;
    }

    /**
     * 根据用户ID删除SysUserRoleRelation
     *
     * @param userId 用户ID
     * @return 影响条数
     */
    @Override
    public int deleteSysUserRoleRelationByUserId(int userId) {
        return 0;
    }
}
