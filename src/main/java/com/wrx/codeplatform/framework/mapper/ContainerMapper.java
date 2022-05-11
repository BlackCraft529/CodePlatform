package com.wrx.codeplatform.framework.mapper;

import com.wrx.codeplatform.domain.framework.sql.container.Container;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/1 21:17
 */
@Repository
public interface ContainerMapper {
    /**
     * 查询容器信息
     *
     * @param id  Id
     * @return    容器信息
     */
    Container selectContainerById(int id);

    /**
     * 根据创建者查询容器列表
     *
     * @param creator  创建者
     * @return         容器列表
     */
    List<Container> selectContainerByCreator(int creator);

    /**
     * 根据创建者获取最新的容器信息
     *
     * @param creator  创建者
     * @return         容器信息
     */
    List<Container> selectContainerByCreatorDescByDate(int creator);

    /**
     * 更新容器
     *
     * @param container  容器信息实体
     * @return           影响条数
     */
    int updateContainer(Container container);

    /**
     * 删除容器信息
     *
     * @param id  容器ID
     * @return    影响条数
     */
    int deleteContainerById(int id);

    /**
     * 插入新的容器信息
     *
     * @param container   容器实体
     * @return            影响条数
     */
    int insertContainer(Container container);

    /**
     * 根据页码查询班级信息
     *
     * @param start  开始
     * @param add    偏移
     * @return       代码
     */
    List<Container> selectContainerByPages(int start, int add);

    /**
     * 根据页码查询班级信息
     *
     * @param start  开始
     * @param add    偏移
     * @param creator 创建者
     * @return       代码
     */
    List<Container> selectContainersByPageAndCreator(int start, int add, int creator);

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
}
