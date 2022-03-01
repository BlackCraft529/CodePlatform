package com.wrx.codeplatform.framework.mapper;

import com.wrx.codeplatform.domain.framework.sql.code.Code;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/1 21:51
 */
@Repository
public interface CodeMapper {
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
     * @param code   代码信息
     * @return       影响条数
     */
    int insertCode(Code code);
}
