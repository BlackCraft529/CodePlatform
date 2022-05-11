package com.wrx.codeplatform.framework.service;

import com.wrx.codeplatform.domain.framework.sql.container.Container;
import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/1 21:23
 */
public interface ContainerService {

    /**
     * 查询容器信息
     *
     * @param id  Id
     * @return    容器信息
     */
    Container selectContainerById(int id);

    /**
     * 更新容器名称
     *
     * @param id        容器ID
     * @param name      容器名称
     * @return          影响条数
     */
    int updateContainerNameById(int id, String name);

    /**
     * 根据创建者获取最新的容器信息
     *
     * @param creator  创建者
     * @return         容器信息
     */
    Container selectContainerByCreatorDescByDate(int creator);

    /**
     * 更新容器描述
     *
     * @param id              容器ID
     * @param description     描述
     * @return                影响条数
     */
    int updateContainerDescriptionById(int id, String description);

    /**
     * 根据文件ID查询容器集合
     *
     * @param fileId   文件ID
     * @return         容器集合
     */
    List<Container> selectContainerByFileId(int fileId);

    /**
     * 查询用户所提交的所有文件的容器信息
     *
     * @param userId   用户ID
     * @return         容器信息
     */
    List<Container> selectUsersAllContainer(int userId);

    /**
     * 根据创建者查询容器信息
     *
     * @param creator  创建者
     * @return         容器列表
     */
    List<Container> selectContainerByCreator(int creator);

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

    /**
     * 根据页码查询班级信息
     *
     * @param page    页码
     * @return       代码
     */
    List<Container> selectContainerByPages(int page);

    /**
     * 获取班级总数
     *
     * @return 总数
     */
    int getTotalContainers();

    /**
     * 查询创建者的所有容器数量
     *
     * @param creator  创建者ID
     * @return         数量
     */
    int getTotalContainersByCreator(int creator);

    /**
     * 根据页码查询班级信息
     *
     * @param page   页码
     * @param creator 创建者
     * @return       代码
     */
    List<Container> selectContainersByPageAndCreator(int page, int creator);

}
