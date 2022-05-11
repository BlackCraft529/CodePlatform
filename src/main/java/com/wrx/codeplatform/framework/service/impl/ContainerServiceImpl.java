package com.wrx.codeplatform.framework.service.impl;

import com.wrx.codeplatform.domain.enums.Common;
import com.wrx.codeplatform.domain.framework.sql.code.Code;
import com.wrx.codeplatform.domain.framework.sql.container.Container;
import com.wrx.codeplatform.domain.framework.sql.container.ContainerLink;
import com.wrx.codeplatform.framework.mapper.CodeMapper;
import com.wrx.codeplatform.framework.mapper.ContainerLinkMapper;
import com.wrx.codeplatform.framework.mapper.ContainerMapper;
import com.wrx.codeplatform.framework.service.ContainerLinkService;
import com.wrx.codeplatform.framework.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/1 21:27
 */
@Service("containerService")
public class ContainerServiceImpl implements ContainerService {

    @Autowired
    private ContainerMapper containerMapper;
    @Autowired
    private ContainerLinkMapper containerLinkMapper;
    @Autowired
    private CodeMapper codeMapper;

    /**
     * 查询容器信息
     *
     * @param id Id
     * @return 容器信息
     */
    @Override
    public Container selectContainerById(int id) {
        return containerMapper.selectContainerById(id);
    }

    /**
     * 更新容器名称
     *
     * @param id   容器ID
     * @param name 容器名称
     * @return 影响条数
     */
    @Override
    public int updateContainerNameById(int id, String name) {
        return 0;
    }

    /**
     * 根据创建者获取最新的容器信息
     *
     * @param creator 创建者
     * @return 容器信息
     */
    @Override
    public Container selectContainerByCreatorDescByDate(int creator) {
        List<Container> containers = containerMapper.selectContainerByCreatorDescByDate(creator);
        return containers.size()>=1? containers.get(0) : null;
    }

    /**
     * 更新容器描述
     *
     * @param id          容器ID
     * @param description 描述
     * @return 影响条数
     */
    @Override
    public int updateContainerDescriptionById(int id, String description) {
        return 0;
    }

    /**
     * 根据文件ID查询容器集合
     *
     * @param fileId 文件ID
     * @return 容器集合
     */
    @Override
    public List<Container> selectContainerByFileId(int fileId) {
        List<ContainerLink> containerLinkList = containerLinkMapper.selectContainerLinkByFileId(fileId);
        List<Container> containers = new ArrayList<>();
        for (ContainerLink containerLink : containerLinkList){
            Container container = selectContainerById(containerLink.getContainerId());
            if (!containers.contains(container)){
                containers.add(container);
            }
        }
        return containers;
    }

    /**
     * 查询用户所提交的所有文件的容器信息
     *
     * @param userId   用户ID
     * @return         容器信息
     */
    @Override
    public List<Container> selectUsersAllContainer(int userId){
        List<ContainerLink> containerLinkList = containerLinkMapper.selectContainerLinkByUserId(userId);
        List<Container> containers = new ArrayList<>();
        for (ContainerLink containerLink: containerLinkList){
            Container container = containerMapper.selectContainerById(containerLink.getContainerId());
            if (!containers.contains(container)){
                containers.add(container);
            }
        }
        return containers;
    }

    /**
     * 根据创建者查询容器信息
     *
     * @param creator 创建者
     * @return 容器列表
     */
    @Override
    public List<Container> selectContainerByCreator(int creator) {
        return containerMapper.selectContainerByCreator(creator);
    }

    /**
     * 删除容器
     *
     * @param id 容器ID
     * @return 影响条数
     */
    @Override
    public int deleteContainer(int id) {
        return containerMapper.deleteContainerById(id);
    }

    /**
     * 更新容器信息
     *
     * @param container 容器实体类
     * @return 影响条数
     */
    @Override
    public int updateContainer(Container container) {
        return containerMapper.updateContainer(container);
    }

    /**
     * 插入新的容器信息
     *
     * @param name        容器名字
     * @param creator     创建者
     * @param description 描述
     * @return 影响条数
     */
    @Override
    public int insertContainer(String name, int creator, String description) {
        Container container = new Container();
        container.setCreateDate(new Date());
        container.setDescription(description);
        container.setCreator(creator);
        container.setName(name);
        return containerMapper.insertContainer(container);
    }

    /**
     * 根据页码查询班级信息
     *
     * @param page 页码
     * @return 代码
     */
    @Override
    public List<Container> selectContainerByPages(int page) {
        int start = (page-1) * Common.EVERY_PAGE_EVALUATIONS.getPar();
        return containerMapper.selectContainerByPages(start, Common.EVERY_PAGE_CONTAINER.getPar());
    }

    /**
     * 获取班级总数
     *
     * @return 总数
     */
    @Override
    public int getTotalContainers() {
        return containerMapper.getTotalContainers();
    }

    /**
     * 查询创建者的所有容器数量
     *
     * @param creator 创建者ID
     * @return 数量
     */
    @Override
    public int getTotalContainersByCreator(int creator) {
        return containerMapper.getTotalContainersByCreator(creator);
    }

    /**
     * 根据页码查询班级信息
     *
     * @param page   页码
     * @param creator 创建者
     * @return       代码
     */
    public List<Container> selectContainersByPageAndCreator(int page, int creator) {
        int start = (page-1) * Common.EVERY_PAGE_EVALUATIONS.getPar();
        return containerMapper.selectContainersByPageAndCreator(start, Common.EVERY_PAGE_CONTAINER.getPar(), creator);
    }


}
