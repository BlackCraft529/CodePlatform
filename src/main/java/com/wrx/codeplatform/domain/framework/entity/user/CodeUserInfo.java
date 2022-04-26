package com.wrx.codeplatform.domain.framework.entity.user;

import com.wrx.codeplatform.domain.framework.sql.code.Code;
import com.wrx.codeplatform.domain.framework.sql.user.UserInfo;
import lombok.Data;

import java.util.Date;

/**
 * @author 魏荣轩
 * @date 2022/4/26 15:00
 */
@Data
public class CodeUserInfo {
    //代码ID
    private int id;
    //提交者ID
    private int userId;
    //提交者昵称
    private String nickName;
    //代码名称
    private String name;
    private String description;
    private String filePath;
    private double score;
    private Date lastCheckDate;
    private Date uploadDate;
    private String result;

    public CodeUserInfo(UserInfo userInfo, Code code){
        this.id = code.getId();
        this.userId = userInfo.getUserId();
        this.nickName = userInfo.getNickName();
        this.name = code.getName();
        this.description = code.getDescription();
        this.filePath = code.getFilePath();
        this.score = code.getScore();
        this.lastCheckDate = code.getLastCheckDate();
        this.uploadDate = code.getUploadDate();
        this.result = code.getResult();
    }
}
