package com.wrx.codeplatform.framework.service;

import com.wrx.codeplatform.domain.framework.sql.code.CodeIssues;
import com.wrx.codeplatform.domain.framework.sql.code.Evaluation;

import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/4/20 13:12
 */
public interface CodeIssuesService {

    /**
     * 查询问题信息
     *
     * @param id   问题ID
     * @return     问题
     */
    CodeIssues selectCodeIssuesById(int id);

    /**
     * 根据文件ID查询问题
     *
     * @param fileId  文件ID
     * @return        问题
     */
    List<CodeIssues> selectCodeIssuesByFileId(int fileId);

    /**
     * 更新问题状态
     *
     * @param codeIssues  问题状态
     * @return            影响条数
     */
    int updateCodeIssues(CodeIssues codeIssues);

    /**
     * 根据问题ID删除问题
     *
     * @param id id
     * @return   影响条数
     */
    int deleteCodeIssuesById(int id);

    /**
     * 根据文件删除问题
     *
     * @param fileId   文件ID
     * @return         影响条数
     */
    int deleteCodeIssuesByFileId(int fileId);

    /**
     * 插入新的问题
     *
     * @param codeIssues   代码问题
     * @return             影响条数
     */
    int insertNewCodeIssues(CodeIssues codeIssues);

    /**
     * 搜索问题
     *
     * @param fileId  文件ID
     * @param page    页码
     * @return        问题
     */
    List<CodeIssues> selectCodeIssuesByPage(int fileId, int page);

    /**
     * 查询学生提出的代码问题
     *
     * @param codeId  代码ID
     * @return        条数
     */
    int selectCodeIssuesByFileIdStu(int codeId);

    /**
     * 查询老师提出的代码问题
     *
     * @param codeId  代码ID
     * @return        条数
     */
    int selectCodeIssuesByFileIdTea(int codeId);

    /**
     * 查询指定角色提出的所有问题信息
     *
     * @param codeId    代码ID
     * @param roleId    角色ID
     * @param page      页数
     * @return          集合
     */
    List<CodeIssues> selectAllCodeIssuesByFileIdAndRoleId(int codeId, int roleId, int page);
}
