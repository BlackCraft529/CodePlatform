package com.wrx.codeplatform.framework.service.impl;

import com.wrx.codeplatform.domain.framework.sql.code.Evaluation;
import com.wrx.codeplatform.framework.service.EvaluationService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/1 21:49
 */
@Service("evaluationService")
public class EvaluationServiceImpl implements EvaluationService {
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
}
