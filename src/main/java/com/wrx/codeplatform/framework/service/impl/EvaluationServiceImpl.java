package com.wrx.codeplatform.framework.service.impl;

import com.wrx.codeplatform.domain.enums.Common;
import com.wrx.codeplatform.domain.framework.sql.code.Evaluation;
import com.wrx.codeplatform.framework.mapper.EvaluationMapper;
import com.wrx.codeplatform.framework.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/1 21:49
 */
@Service("evaluationService")
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    private EvaluationMapper evaluationMapper;

    /**
     * 插入新的评论
     *
     * @param evaluation 实体类
     * @return 影响条数
     */
    @Override
    public int insertNewEvaluation(Evaluation evaluation) {
        return evaluationMapper.insertEvaluation(evaluation);
    }

    /**
     * 查询评价
     *
     * @param id ID
     * @return 评价
     */
    @Override
    public Evaluation selectEvaluationById(int id) {
        return null;
    }

    /**
     * 根据发布者查询评价
     *
     * @param publisherId 发布者ID
     * @return 评价列表
     */
    @Override
    public List<Evaluation> selectEvaluationByPublisherId(int publisherId) {
        return null;
    }

    /**
     * 更新评价
     *
     * @param evaluation 评价实体
     * @return 影响条数
     */
    @Override
    public int updateEvaluation(Evaluation evaluation) {
        return 0;
    }

    /**
     * 更新评价内容
     *
     * @param id      评价ID
     * @param context 评价内容
     * @return 影响条数
     */
    @Override
    public int updateEvaluationContext(int id, String context) {
        return 0;
    }

    /**
     * 删除评价
     *
     * @param id 评价ID
     * @return 影响条数
     */
    @Override
    public int deleteEvaluation(int id) {
        return 0;
    }

    /**
     * 插入新的评价信息
     *
     * @param fileId      文件ID
     * @param publisherId 发布者
     * @param context     内容
     * @return 影响条数
     */
    @Override
    public int insertEvaluation(int fileId, int publisherId, String context) {
        return 0;
    }

    /**
     * 搜索评论
     *
     * @param fileId 文件ID
     * @param page   页码
     * @return 评论
     */
    @Override
    public List<Evaluation> selectEvaluationByPage(int fileId, int page) {
        int start = (page-1) * Common.EVERY_PAGE_EVALUATIONS.getPar();
        return evaluationMapper.selectEvaluationByPage(fileId, start, Common.EVERY_PAGE_EVALUATIONS.getPar());
    }

    /**
     * 搜索评论
     *
     * @param fileId 文件ID
     * @return 评论
     */
    @Override
    public List<Evaluation> selectEvaluationByFileId(int fileId) {
        return evaluationMapper.selectEvaluationByFileId(fileId);
    }
}
