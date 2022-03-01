package com.wrx.codeplatform.framework.service.impl;

import com.wrx.codeplatform.domain.framework.sql.container.Container;
import com.wrx.codeplatform.framework.service.ContainerService;
import org.springframework.stereotype.Service;

/**
 * @author 魏荣轩
 * @date 2022/3/1 21:27
 */
@Service("containerService")
public class ContainerServiceImpl implements ContainerService {
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
