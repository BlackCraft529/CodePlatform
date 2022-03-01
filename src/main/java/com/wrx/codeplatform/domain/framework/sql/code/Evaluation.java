package com.wrx.codeplatform.domain.framework.sql.code;

import lombok.Data;
import java.util.Date;

/**
 * @author 魏荣轩
 * @date 2022/3/1 20:44
 */
@Data
public class Evaluation {
    private int id;
    private int fileId;
    private int publisherId;
    private String context;
    private Date publishedDate;
    private Date modifyDate;
}
