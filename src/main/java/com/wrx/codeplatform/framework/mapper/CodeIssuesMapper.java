package com.wrx.codeplatform.framework.mapper;

import com.wrx.codeplatform.domain.framework.sql.code.CodeIssues;
import com.wrx.codeplatform.domain.framework.sql.code.Evaluation;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/4/20 13:08
 */
@Repository
public interface CodeIssuesMapper {

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
     * @param start   开始位置
     * @param add     偏移量
     * @return        问题
     */
    List<CodeIssues> selectCodeIssuesByPage(int fileId, int start, int add);

    /**
     * 根据角色ID查询指定代码的问题列表
     *
     * @param fileId  代码ID
     * @param roleId  角色代码
     * @param start   开始
     * @param add     偏移量
     * @return        集合
     */
    List<CodeIssues> selectCodeIssuesByFileIdAndRoleId(int fileId, int roleId, int start, int add);

    /**
     * 统计条数-根据文件ID和角色ID
     *
     * @param fileId    codeId
     * @param roleId    角色ID
     * @return          条数
     */
    int totalCodeIssuesByFileIdAndRoleId(int fileId, int roleId);

}
