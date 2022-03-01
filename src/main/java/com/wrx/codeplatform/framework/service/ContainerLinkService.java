package com.wrx.codeplatform.framework.service;

import com.wrx.codeplatform.domain.framework.sql.container.ContainerLink;
import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/1 21:03
 */
public interface ContainerLinkService {
    /**
     * 更新关系合集列表
     *
     * @param containerLinkList  合集列表
     * @return                   更新条数
     */
    int updateContainerLinkList(List<ContainerLink> containerLinkList);

    /**
     * 更新某一条
     *
     * @param containerLink   关系列表
     * @return                影响条数
     */
    int updateContainerLink(ContainerLink containerLink);

    /**
     * 根据ID更新容器信息
     *
     * @param id             id
     * @param containerId    容器ID
     * @return               影响条数
     */
    int updateContainerIdById(int id, int containerId);

    /**
     * 根据id更新文件信息
     *
     * @param id             ID
     * @param fileId         文件ID
     * @return               影响条数
     */
    int updateFileIdById(int id, int fileId);

    /**
     * 删除容器信息
     *
     * @param id       ID
     * @return         影响条数
     */
    int deleteContainerLinkById(int id);


    /**
     * 插入新的容器关联信息
     *
     * @param containerId   容器ID
     * @param fileId        文件ID
     * @return              影响条数
     */
    int insertContainerLink(int containerId, int fileId);
}
