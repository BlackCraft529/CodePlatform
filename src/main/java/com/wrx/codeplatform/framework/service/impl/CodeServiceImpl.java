package com.wrx.codeplatform.framework.service.impl;

import com.wrx.codeplatform.domain.framework.sql.code.Code;
import com.wrx.codeplatform.framework.service.CodeService;
import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/1 22:01
 */
public class CodeServiceImpl implements CodeService {
    /**
     * 查询Code信息
     *
     * @param id ID
     * @return Code实体
     */
    @Override
    public Code selectCodeById(int id) {
        return null;
    }

    /**
     * 根据用户ID查询代码列表
     *
     * @param userId 用户ID
     * @return 用户代码集
     */
    @Override
    public List<Code> selectCodesByUserId(int userId) {
        return null;
    }

    /**
     * 更新分数
     *
     * @param id    ID
     * @param score 分数
     * @return 影响条数
     */
    @Override
    public int updateScoreById(int id, double score) {
        return 0;
    }

    /**
     * 更新文件查重等结果信息
     *
     * @param id     ID
     * @param result 结果-JSON字符串
     * @return 影响条数
     */
    @Override
    public int updateResultById(int id, String result) {
        return 0;
    }

    /**
     * 更新代码集信息
     *
     * @param code 代码
     * @return 影响条数
     */
    @Override
    public int updateCode(Code code) {
        return 0;
    }

    /**
     * 根据ID删除代码
     *
     * @param id 用户ID
     * @return 影响条数
     */
    @Override
    public int deleteCodeById(int id) {
        return 0;
    }

    /**
     * 插入新的代码信息
     *
     * @param userId   用户ID
     * @param filePath 文件路径
     * @return 影响条数
     */
    @Override
    public int insertCode(int userId, String filePath) {
        return 0;
    }
}
