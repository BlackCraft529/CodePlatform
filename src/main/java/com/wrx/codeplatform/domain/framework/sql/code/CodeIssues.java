package com.wrx.codeplatform.domain.framework.sql.code;

import lombok.Data;

import java.util.Date;

/**
 * @author 魏荣轩
 * @date 2022/4/20 13:05
 */
@Data
public class CodeIssues {
    private int id;
    private int fileId;
    private int proposer;
    private String description;
    private boolean open;
    private int solver;
    private Date openDate;
    private Date closeDate;
    private String replay;
    public CodeIssues(){}

    public CodeIssues(String context, int codeId, int proposerId){
        this.description = context;
        this.fileId = codeId;
        this.proposer = proposerId;
        this.open = true;
        this.openDate = new Date();
        this.closeDate = new Date();
        this.replay = "暂未回复";
        this.solver = -1;
    }
}
