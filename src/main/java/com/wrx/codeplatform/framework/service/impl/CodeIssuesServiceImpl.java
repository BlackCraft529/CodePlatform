package com.wrx.codeplatform.framework.service.impl;

import com.wrx.codeplatform.domain.enums.Common;
import com.wrx.codeplatform.domain.enums.RoleCode;
import com.wrx.codeplatform.domain.framework.sql.code.CodeIssues;
import com.wrx.codeplatform.framework.mapper.CodeIssuesMapper;
import com.wrx.codeplatform.framework.service.CodeIssuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/4/20 13:13
 */
@Service("codeIssuesService")
public class CodeIssuesServiceImpl implements CodeIssuesService {
    @Autowired
    public CodeIssuesMapper codeIssuesMapper;

    /**
     * 查询问题信息
     *
     * @param id 问题ID
     * @return 问题
     */
    @Override
    public CodeIssues selectCodeIssuesById(int id) {
        return codeIssuesMapper.selectCodeIssuesById(id);
    }

    /**
     * 根据文件ID查询问题
     *
     * @param fileId 文件ID
     * @return 问题
     */
    @Override
    public List<CodeIssues> selectCodeIssuesByFileId(int fileId) {
        return codeIssuesMapper.selectCodeIssuesByFileId(fileId);
    }

    /**
     * 更新问题状态
     *
     * @param codeIssues 问题状态
     * @return 影响条数
     */
    @Override
    public int updateCodeIssues(CodeIssues codeIssues) {
        return codeIssuesMapper.updateCodeIssues(codeIssues);
    }

    /**
     * 根据问题ID删除问题
     *
     * @param id id
     * @return 影响条数
     */
    @Override
    public int deleteCodeIssuesById(int id) {
        return codeIssuesMapper.deleteCodeIssuesById(id);
    }

    /**
     * 根据文件删除问题
     *
     * @param fileId 文件ID
     * @return 影响条数
     */
    @Override
    public int deleteCodeIssuesByFileId(int fileId) {
        int sum = 0;
        List<CodeIssues> allIssues = selectCodeIssuesByFileId(fileId);
        for (CodeIssues codeIssues : allIssues){
            sum+=deleteCodeIssuesById(codeIssues.getId());
        }
        return sum;
    }

    /**
     * 插入新的问题
     *
     * @param codeIssues 代码问题
     * @return 影响条数
     */
    @Override
    public int insertNewCodeIssues(CodeIssues codeIssues) {
        return codeIssuesMapper.insertNewCodeIssues(codeIssues);
    }

    /**
     * 搜索问题
     *
     * @param fileId 文件ID
     * @param page   页码
     * @return 问题
     */
    @Override
    public List<CodeIssues> selectCodeIssuesByPage(int fileId, int page) {
        int start = (page-1) * Common.EVERY_PAGE_EVALUATIONS.getPar();
        return codeIssuesMapper.selectCodeIssuesByPage(fileId, start, Common.EVERY_PAGE_EVALUATIONS.getPar());
    }


    /**
     * 查询学生提出的代码问题
     *
     * @param codeId  代码ID
     * @return        条数
     */
    @Override
    public int selectCodeIssuesByFileIdStu(int codeId){
        return codeIssuesMapper.totalCodeIssuesByFileIdAndRoleId(codeId, RoleCode.STUDENT.getRoleCode());
    }

    /**
     * 查询老师提出的代码问题
     *
     * @param codeId  代码ID
     * @return        条数
     */
    public int selectCodeIssuesByFileIdTea(int codeId){
        return codeIssuesMapper.totalCodeIssuesByFileIdAndRoleId(codeId, RoleCode.TEACHER.getRoleCode());
    }

    /**
     * 查询指定角色提出的所有问题信息
     *
     * @param codeId 代码ID
     * @param roleId 角色ID
     * @return 集合
     */
    @Override
    public List<CodeIssues> selectAllCodeIssuesByFileIdAndRoleId(int codeId, int roleId, int page) {
        int start = (page-1) * Common.EVERY_PAGE_EVALUATIONS.getPar();
        return codeIssuesMapper.selectCodeIssuesByFileIdAndRoleId(codeId, roleId, start, Common.EVERY_PAGE_EVALUATIONS.getPar());
    }
}
