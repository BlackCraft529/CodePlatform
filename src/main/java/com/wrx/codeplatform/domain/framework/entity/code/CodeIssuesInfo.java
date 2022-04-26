package com.wrx.codeplatform.domain.framework.entity.code;

import com.wrx.codeplatform.domain.framework.sql.code.CodeIssues;
import com.wrx.codeplatform.utils.common.ServiceFactory;
import lombok.Data;

import java.util.Date;

/**
 * @author 魏荣轩
 * @date 2022/4/20 15:13
 */
@Data
public class CodeIssuesInfo {
    private int id;
    private int fileId;
    private int proposer;
    private String description;
    private boolean open;
    private int solver;
    private Date openDate;
    private Date closeDate;
    private String replay;
    private String proposerName;
    private String solverName;

    public CodeIssuesInfo(CodeIssues codeIssues){
        this.id = codeIssues.getId();
        this.fileId = codeIssues.getFileId();
        this.proposer = codeIssues.getProposer();
        this.description = codeIssues.getDescription();
        this.open = codeIssues.isOpen();
        this.solver = codeIssues.getSolver();
        this.openDate = codeIssues.getOpenDate();
        this.closeDate = codeIssues.getCloseDate();
        this.replay = codeIssues.getReplay();
        this.proposerName = ServiceFactory.userInfoService.selectByUserId(proposer).getNickName();
        if (solver!=-1){
            this.solverName = ServiceFactory.userInfoService.selectByUserId(solver).getNickName();
        }
    }
}
