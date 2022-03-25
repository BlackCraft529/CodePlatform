package com.wrx.codeplatform.domain.framework.sql.code;

import lombok.Data;
import java.util.Date;

/**
 * @author 魏荣轩
 * @date 2022/3/1 19:02
 */
@Data
public class Code {
    private int id;
    private String name;
    private String description;
    private int userId;
    private String filePath;
    private double score;
    private Date lastCheckDate;
    private Date uploadDate;
    private String result;

}
