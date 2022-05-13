package com.wrx.codeplatform.domain.framework.entity.code;

import com.wrx.codeplatform.domain.framework.sql.code.Evaluation;
import com.wrx.codeplatform.domain.framework.sql.user.UserInfo;
import com.wrx.codeplatform.utils.common.ServiceFactory;
import lombok.Data;

import java.util.Date;

/**
 * @author 魏荣轩
 * @date 2022/3/1 20:44
 */
@Data
public class EvaluationInfo {
    private int id;
    private int fileId;
    private int publisherId;
    private String content;
    private Date publishedDate;
    private Date modifyDate;
    private double score;
    private String headPortrait;

    public EvaluationInfo(){}

    public EvaluationInfo(Evaluation evaluation){
        this.fileId = evaluation.getFileId();
        this.publisherId = evaluation.getPublisherId();
        this.content = evaluation.getContent();
        this.publishedDate = evaluation.getPublishedDate();
        this.modifyDate = evaluation.getModifyDate();
        this.score = evaluation.getScore();
        UserInfo userInfo = ServiceFactory.userInfoService.selectByUserId(publisherId);
        if (userInfo != null) {
            this.headPortrait = userInfo.getHeadPortrait();
        }
    }
}
