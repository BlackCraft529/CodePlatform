package com.wrx.codeplatform.framework.service.impl;

import com.wrx.codeplatform.domain.framework.sql.container.ContainerLink;
import com.wrx.codeplatform.framework.mapper.ContainerLinkMapper;
import com.wrx.codeplatform.framework.service.ContainerLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/1 21:04
 */
@Service("containerLinkService")
public class ContainerLinkServiceImpl implements ContainerLinkService {

    @Autowired
    private ContainerLinkMapper containerLinkMapper;

    /**
     * 更新关系合集列表
     *
     * @param containerLinkList 合集列表
     * @return 更新条数
     */
    @Override
    public int updateContainerLinkList(List<ContainerLink> containerLinkList) {
        return 0;
    }

    /**
     * 更新某一条
     *
     * @param containerLink 关系列表
     * @return 影响条数
     */
    @Override
    public int updateContainerLink(ContainerLink containerLink) {
        return 0;
    }

    /**
     * 根据容器ID查询关系集合
     *
     * @param containerId 容器ID
     * @return 关系集合
     */
    @Override
    public List<ContainerLink> selectContainerLinkByContainerId(int containerId) {
        return containerLinkMapper.selectContainerLinkByContainerId(containerId);
    }

    /**
     * 根据ID更新容器信息
     *
     * @param id          id
     * @param containerId 容器ID
     * @return 影响条数
     */
    @Override
    public int updateContainerIdById(int id, int containerId) {
        return 0;
    }

    /**
     * 根据id更新文件信息
     *
     * @param id     ID
     * @param fileId 文件ID
     * @return 影响条数
     */
    @Override
    public int updateFileIdById(int id, int fileId) {
        return 0;
    }

    /**
     * 删除容器信息
     *
     * @param id ID
     * @return 影响条数
     */
    @Override
    public int deleteContainerLinkById(int id) {
        return 0;
    }

    /**
     * 插入新的容器关联信息
     *
     * @param containerId 容器ID
     * @param fileId      文件ID
     * @return 影响条数
     */
    @Override
    public int insertContainerLink(int containerId, int fileId) {
        return 0;
    }

}
