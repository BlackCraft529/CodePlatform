package com.wrx.codeplatform.domain.framework.entity.container;

import com.wrx.codeplatform.domain.framework.sql.container.Container;
import com.wrx.codeplatform.domain.framework.sql.user.UserInfo;
import com.wrx.codeplatform.utils.common.ServiceFactory;
import lombok.Data;

import java.util.Date;

/**
 * @author 魏荣轩
 * @date 2022/4/24 21:41
 */
@Data
public class ContainerInfo {
    private int id;
    private String creator;
    private String name;
    private String description;
    private Date createDate;

    public ContainerInfo(){}

    public ContainerInfo(Container container){
        this.id = container.getId();
        this.creator = ServiceFactory.userInfoService.selectByUserId(container.getCreator()).getNickName();
        this.name = container.getName();
        this.description = container.getDescription();
        this.createDate = container.getCreateDate();
    }
}
