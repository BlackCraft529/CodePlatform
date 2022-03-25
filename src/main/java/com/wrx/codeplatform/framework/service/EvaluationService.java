package com.wrx.codeplatform.framework.service;

import com.wrx.codeplatform.domain.framework.sql.code.Evaluation;
import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/1 21:37
 */
public interface EvaluationService {

    /**
     * 插入新的评论
     *
     * @param evaluation  实体类
     * @return            影响条数
     */
    int insertNewEvaluation(Evaluation evaluation);

    /**
     * 查询评价
     *
     * @param id   ID
     * @return     评价
     */
    Evaluation selectEvaluationById(int id);

    /**
     * 根据发布者查询评价
     *
     * @param publisherId   发布者ID
     * @return              评价列表
     */
    List<Evaluation> selectEvaluationByPublisherId(int publisherId);

    /**
     * 更新评价
     *
     * @param evaluation  评价实体
     * @return            影响条数
     */
    int updateEvaluation(Evaluation evaluation);

    /**
     * 更新评价内容
     *
     * @param id          评价ID
     * @param context     评价内容
     * @return            影响条数
     */
    int updateEvaluationContext(int id, String context);

    /**
     * 删除评价
     *
     * @param id    评价ID
     * @return      影响条数
     */
    int deleteEvaluation(int id);

    /**
     * 插入新的评价信息
     *
     * @param fileId         文件ID
     * @param publisherId    发布者
     * @param context        内容
     * @return               影响条数
     */
    int insertEvaluation(int fileId, int publisherId, String context);

    /**
     * 搜索评论
     *
     * @param fileId  文件ID
     * @param page    页码
     * @return        评论
     */
    List<Evaluation> selectEvaluationByPage(int fileId, int page);

    /**
     * 搜索评论
     *
     * @param fileId  文件ID
     * @return        评论
     */
    List<Evaluation> selectEvaluationByFileId(int fileId);
}
