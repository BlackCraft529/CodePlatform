package com.wrx.codeplatform.framework.service.impl;

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
        //获取所有文件的容器
        List<Code> codeList = codeMapper.selectCodesByUserId(userId);
        //初始化容器列表
        List<Container> containers = new ArrayList<>();
        for (Code code : codeList){
            //获取当前文件所投入的所有的容器
            List<Container> containerList = selectContainerByFileId(code.getId());
            for (Container container : containerList){
                if (!containers.contains(container)){
                    containers.add(container);
                }
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
        return 0;
    }

    /**
     * 更新容器信息
     *
     * @param container 容器实体类
     * @return 影响条数
     */
    @Override
    public int updateContainer(Container container) {
        return 0;
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
        return 0;
    }
}
