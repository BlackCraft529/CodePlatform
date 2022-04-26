package com.wrx.codeplatform.framework.mapper;

import com.wrx.codeplatform.domain.framework.sql.container.ContainerLink;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/1 20:49
 */
@Repository
public interface ContainerLinkMapper {

    /**
     * 根据容器ID查询关系列表
     *
     * @param containerId  容器ID
     * @return             获取容器详情列表
     */
    List<ContainerLink> selectContainerLinkByContainerId(int containerId);

    /**
     * 根据ID查询关系列表
     *
     * @param id    ID
     * @return      获取容器详情
     */
    ContainerLink selectContainerLinkById(int id);

    /**
     * 根据fileId查询关系列表
     *
     * @param fileId    fileId
     * @return      获取容器详情列表
     */
    List<ContainerLink> selectContainerLinkByFileId(int fileId);

    /**
     * 更新关系列表信息
     *
     * @param containerLink  容器关系列表
     * @return               影响条数
     */
    int updateContainerLink(ContainerLink containerLink);

    /**
     * 删除容器列表信息
     *
     * @param id   id
     * @return     影响条数
     */
    int deleteContainerLink(int id);

    /**
     * 插入新的容器关联信息
     *
     * @param containerLink   容器关联信息
     * @return                影响条数
     */
    int insertContainerLink(ContainerLink containerLink);

    /**
     * 根据容器ID和代码ID获取容器关联信息
     *
     * @param containerId 容器ID
     * @param codeId      代码ID
     * @return            关联实体
     */
    List<ContainerLink> selectContainerLinkByCodeIdAndContainerId(int containerId, int codeId);
}
