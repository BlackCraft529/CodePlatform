package com.wrx.codeplatform.framework.service.impl;

import com.wrx.codeplatform.domain.framework.sql.common.SysRequestPath;
import com.wrx.codeplatform.framework.mapper.SysRequestPathMapper;
import com.wrx.codeplatform.framework.service.SysRequestPathService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * 请求路径(SysRequestPath)表服务实现类
 *
 * @author makejava
 * @since 2019-09-04 17:11:16
 */
@Service("sysRequestPathService")
public class SysRequestPathServiceImpl implements SysRequestPathService {
    @Resource
    private SysRequestPathMapper sysRequestPathMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysRequestPath queryById(Integer id) {
        return this.sysRequestPathMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysRequestPath> queryAllByLimit(int offset, int limit) {
        return this.sysRequestPathMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysRequestPath 实例对象
     * @return 实例对象
     */
    @Override
    public SysRequestPath insert(SysRequestPath sysRequestPath) {
        this.sysRequestPathMapper.insert(sysRequestPath);
        return sysRequestPath;
    }

    /**
     * 修改数据
     *
     * @param sysRequestPath 实例对象
     * @return 实例对象
     */
    @Override
    public SysRequestPath update(SysRequestPath sysRequestPath) {
        this.sysRequestPathMapper.update(sysRequestPath);
        return this.queryById(sysRequestPath.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.sysRequestPathMapper.deleteById(id) > 0;
    }
}