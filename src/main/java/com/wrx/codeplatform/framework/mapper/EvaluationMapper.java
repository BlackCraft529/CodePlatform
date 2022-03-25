package com.wrx.codeplatform.framework.mapper;

import com.wrx.codeplatform.domain.framework.sql.code.Evaluation;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/1 21:29
 */
@Repository
public interface EvaluationMapper {

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
     * 删除一条评价
     *
     * @param id   ID
     * @return     影响条数
     */
    int deleteEvaluation(int id);

    /**
     * 插入评价
     *
     * @param evaluation   评价实体
     * @return             影响条数
     */
    int insertEvaluation(Evaluation evaluation);

    /**
     * 搜索评论
     *
     * @param fileId  文件ID
     * @param start   开始位置
     * @param add     偏移量
     * @return        评论
     */
    List<Evaluation> selectEvaluationByPage(int fileId, int start, int add);

    /**
     * 搜索评论
     *
     * @param fileId  文件ID
     * @return        评论
     */
    List<Evaluation> selectEvaluationByFileId(int fileId);
}
