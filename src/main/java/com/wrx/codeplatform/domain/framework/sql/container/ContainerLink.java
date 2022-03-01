package com.wrx.codeplatform.domain.framework.sql.container;

import lombok.Data;

/**
 * @author 魏荣轩
 * @date 2022/3/1 20:46
 */
@Data
public class ContainerLink {
    private int id;
    private int containerId;
    private int fileId;
}
