package com.wrx.codeplatform.framework.service.impl;

import com.wrx.codeplatform.domain.framework.sql.code.Code;
import com.wrx.codeplatform.domain.framework.sql.container.Container;
import com.wrx.codeplatform.domain.framework.sql.container.ContainerLink;
import com.wrx.codeplatform.framework.mapper.CodeMapper;
import com.wrx.codeplatform.framework.service.CodeService;
import com.wrx.codeplatform.framework.service.ContainerLinkService;
import com.wrx.codeplatform.framework.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/1 22:01
 */
@Service("codeService")
public class CodeServiceImpl implements CodeService {

    @Autowired
    public CodeMapper codeMapper;
    @Autowired
    public ContainerLinkService containerLinkService;
    @Autowired
    public ContainerService containerService;

    /**
     * 查询Code信息
     *
     * @param id ID
     * @return Code实体
     */
    @Override
    public Code selectCodeById(int id) {
        return codeMapper.selectCodeById(id);
    }

    /**
     * 根据用户ID查询代码列表
     *
     * @param userId 用户ID
     * @return 用户代码集
     */
    @Override
    public List<Code> selectCodesByUserId(int userId) {
        return codeMapper.selectCodesByUserId(userId);
    }

    /**
     * 查询最近的代码
     *
     * @param userId 用户ID
     * @param size   查询数量
     * @return 代码列表
     */
    @Override
    public List<Code> selectRecentCode(int userId, int size) {
        return codeMapper.selectRecentCode(userId, size);
    }

    /**
     * 根据下标获取代码列表
     *
     * @param start 开始位置
     * @param add   偏移量
     * @param userId 用户ID
     * @return 代码集
     */
    @Override
    public List<Code> selectCodeByIndex(int start, int add, int userId) {
        return codeMapper.selectCodesByIndex(start, add, userId);
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
        Code code = codeMapper.selectCodeById(id);
        code.setResult(result);
        return codeMapper.updateCode(code);
    }

    /**
     * 更新代码集信息
     *
     * @param code 代码
     * @return 影响条数
     */
    @Override
    public int updateCode(Code code) {
        return codeMapper.updateCode(code);
    }

    /**
     * 根据ID删除代码
     *
     * @param id 代码ID
     * @return 影响条数
     */
    @Override
    public int deleteCodeById(int id) {
        List<ContainerLink> containerLinkList = containerLinkService.selectContainerLinkByFileId(id);
        //删除代码关联信息
        for (ContainerLink containerLink: containerLinkList){
            containerLinkService.deleteContainerLinkById(containerLink.getId());
        }
        //删除代码
        return codeMapper.deleteCodeById(id);
    }

    /**
     * 插入新的代码信息
     *
     * @param userId   用户ID
     * @param filePath 文件路径.
     * @param name        名称
     * @param description   描述
     * @return 影响条数
     */
    @Override
    public int insertCode(int userId, String filePath, String name, String description) {
        Code code = new Code(name, description, userId, filePath);
        return codeMapper.insertCode(code);
    }

    /**
     * 查询所有代码
     *
     * @return 代码结合
     */
    @Override
    public List<Code> selectAllCodes() {
        return codeMapper.selectAllCodes();
    }

}
