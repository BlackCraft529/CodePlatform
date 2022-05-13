package com.wrx.codeplatform.domain.framework.sql.code;

import com.wrx.codeplatform.domain.framework.sql.user.UserInfo;
import com.wrx.codeplatform.utils.common.ServiceFactory;
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
    private String content;
    private Date publishedDate;
    private Date modifyDate;
    private double score;

    public Evaluation(){}

    public Evaluation(int fileId, int publisherId, String content, double score){
        this.fileId = fileId;
        this.publisherId = publisherId;
        this.content = content;
        this.publishedDate = new Date();
        this.modifyDate = new Date();
        this.score = score;
    }
}
