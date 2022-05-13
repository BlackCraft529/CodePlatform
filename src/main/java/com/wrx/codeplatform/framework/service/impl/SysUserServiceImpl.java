package com.wrx.codeplatform.framework.service.impl;

import com.wrx.codeplatform.domain.framework.sql.user.SysUser;
import com.wrx.codeplatform.domain.framework.sql.user.SysUserRoleRelation;
import com.wrx.codeplatform.framework.mapper.SysUserMapper;
import com.wrx.codeplatform.framework.mapper.SysUserRoleRelationMapper;
import com.wrx.codeplatform.framework.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户表(SysUser)表服务实现类
 *
 * @author makejava
 * @since 2019-08-29 15:22:19
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleRelationMapper sysUserRoleRelationMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysUser queryById(Integer id) {
        return this.sysUserMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SysUser> queryAllByLimit(int offset, int limit) {
        return this.sysUserMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 根据RoleId查询所有用户
     *
     * @param roleId roleId
     * @return 角色列表
     */
    @Override
    public List<SysUser> selectUserByRoleId(int roleId) {
        List<SysUserRoleRelation> roleRelations = sysUserRoleRelationMapper.selectSysUserRoleRelationByRoleId(roleId);
        List<SysUser> sysUsers = new ArrayList<>();
        for (SysUserRoleRelation sysUserRoleRelation: roleRelations){
            SysUser sysUser = sysUserMapper.queryById(sysUserRoleRelation.getUserId());
            if (sysUser!=null){
                sysUsers.add(sysUser);
            }
        }
        return sysUsers;
    }

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(SysUser sysUser) {
        return this.sysUserMapper.insert(sysUser);
    }

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    @Override
    public int update(SysUser sysUser) {
        return this.sysUserMapper.update(sysUser);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.sysUserMapper.deleteById(id) > 0;
    }

    @Override
    public SysUser selectByAccount(String account) {
        return this.sysUserMapper.selectByName(account);
    }
}