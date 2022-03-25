package com.wrx.codeplatform.domain.framework.entity.rechecking;

import com.wrx.codeplatform.domain.framework.sql.code.Code;
import com.wrx.codeplatform.domain.framework.sql.user.UserInfo;
import com.wrx.codeplatform.framework.service.UserInfoService;
import com.wrx.codeplatform.utils.common.ServiceFactory;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 魏荣轩
 * @date 2022/3/25 0:18
 */
@Data
public class RecheckingCodeResult {

    private int id;
    private String name;
    private String description;
    private String userName;
    private double score;
    private String uploadDate;
    private double percent;

    public RecheckingCodeResult(Code code){
        this.id = code.getId();
        this.name = code.getName();
        this.description = code.getDescription();
        this.userName = ServiceFactory.userInfoService.selectByUserId(code.getUserId()).getNickName();
        this.score = code.getScore();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.uploadDate = sdf.format(code.getUploadDate());
    }
}
