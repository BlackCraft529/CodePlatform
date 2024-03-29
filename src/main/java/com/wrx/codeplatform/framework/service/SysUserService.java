package com.wrx.codeplatform.framework.service;

import com.wrx.codeplatform.domain.framework.sql.user.SysUser;
import java.util.List;

/**
 * 用户表(SysUser)表服务接口
 *
 * @author makejava
 * @since 2019-08-29 15:22:19
 */
public interface SysUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysUser queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SysUser> queryAllByLimit(int offset, int limit);

    /**
     * 根据RoleId查询所有用户
     *
     * @param roleId  roleId
     * @return        角色列表
     */
    List<SysUser> selectUserByRoleId(int roleId);

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    int insert(SysUser sysUser);

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    int update(SysUser sysUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 根据用户名查询用户
     *
     * @param account  用户名
     * @return          用户名
     */
    SysUser selectByAccount(String account);

}