package com.wrx.codeplatform.domain.framework.entity.code;

import com.wrx.codeplatform.domain.framework.sql.code.Code;
import com.wrx.codeplatform.utils.common.ServiceFactory;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 魏荣轩
 * @date 2022/4/25 16:17
 */
@Data
public class CodeInfo {
    private int id;
    private String name;
    private String description;
    private int userId;
    private String userName;
    private String filePath;
    private double score;
    private String lastCheckDate;
    private String uploadDate;
    private String result;

    public CodeInfo(Code code){
        this.id = code.getId();
        this.name = code.getName();
        this.description = code.getDescription();
        this.userId = code.getUserId();
        this.userName = ServiceFactory.userInfoService.selectByUserId(userId).getNickName();
        this.filePath = code.getFilePath();
        this.score = code.getScore();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.lastCheckDate = sdf.format(code.getLastCheckDate());
        this.uploadDate = sdf.format(code.getUploadDate());
        this.result = code.getResult();
    }
}
