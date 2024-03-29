package com.wrx.codeplatform.framework.service;

import com.wrx.codeplatform.domain.framework.sql.code.Code;
import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/1 21:57
 */
public interface CodeService {
    /**
     * 查询Code信息
     *
     * @param id   ID
     * @return     Code实体
     */
    Code selectCodeById(int id);

    /**
     * 根据用户ID查询代码列表
     *
     * @param userId   用户ID
     * @return         用户代码集
     */
    List<Code> selectCodesByUserId(int userId);

    /**
     * 查询最近的代码
     *
     * @param size  查询数量
     * @param userId  用户ID
     * @return      代码列表
     */
    List<Code> selectRecentCode(int userId, int size);

    /**
     * 根据下标获取代码列表
     *
     * @param start  开始位置
     * @param add    偏移量
     * @param userId  用户ID
     * @return       代码集
     */
    List<Code> selectCodeByIndex(int start, int add, int userId);

    /**
     * 更新分数
     *
     * @param id       ID
     * @param score    分数
     * @return         影响条数
     */
    int updateScoreById(int id, double score);

    /**
     * 更新文件查重等结果信息
     *
     * @param id           ID
     * @param result       结果-JSON字符串
     * @return             影响条数
     */
    int updateResultById(int id, String result);

    /**
     * 更新代码集信息
     *
     * @param code    代码
     * @return        影响条数
     */
    int updateCode(Code code);

    /**
     * 根据ID删除代码
     *
     * @param id  用户ID
     * @return    影响条数
     */
    int deleteCodeById(int id);

    /**
     * 插入新的代码信息
     *
     * @param userId      用户ID
     * @param filePath    文件路径
     * @param name        名称
     * @param description   描述
     * @return            影响条数
     */
    int insertCode(int userId, String filePath, String name, String description);

    /**
     * 查询所有代码
     *
     * @return 代码结合
     */
    List<Code> selectAllCodes();
}
