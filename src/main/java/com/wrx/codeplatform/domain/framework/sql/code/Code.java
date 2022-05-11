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

    public Code(){}
    public Code(String name, String description, int userId, String filePath){
        this.name = name;
        this.description = description;
        this.userId = userId;
        this.filePath = filePath;
        this.score = 0;
        this.lastCheckDate = new Date();
        this.uploadDate = new Date();
        this.result = "";
    }
}
