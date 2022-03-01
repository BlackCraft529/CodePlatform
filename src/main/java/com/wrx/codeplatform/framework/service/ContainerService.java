package com.wrx.codeplatform.framework.service;

import com.wrx.codeplatform.domain.framework.sql.container.Container;

/**
 * @author 魏荣轩
 * @date 2022/3/1 21:23
 */
public interface ContainerService {

    /**
     * 更新容器名称
     *
     * @param id        容器ID
     * @param name      容器名称
     * @return          影响条数
     */
    int updateContainerNameById(int id, String name);

    /**
     * 更新容器描述
     *
     * @param id              容器ID
     * @param description     描述
     * @return                影响条数
     */
    int updateContainerDescriptionById(int id, String description);

    /**
     * 删除容器
     *
     * @param id  容器ID
     * @return    影响条数
     */
    int deleteContainer(int id);

    /**
     * 更新容器信息
     *
     * @param container   容器实体类
     * @return            影响条数
     */
    int updateContainer(Container container);

    /**
     * 插入新的容器信息
     *
     * @param name           容器名字
     * @param creator        创建者
     * @param description    描述
     * @return               影响条数
     */
    int insertContainer(String name, int creator, String description);

}
