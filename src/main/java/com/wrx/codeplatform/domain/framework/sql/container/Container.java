package com.wrx.codeplatform.domain.framework.sql.container;

import lombok.Data;
import java.util.Date;

/**
 * @author 魏荣轩
 * @date 2022/3/1 20:46
 */
@Data
public class Container {
    private int id;
    private int creator;
    private String name;
    private String description;
    private Date createDate;

}
