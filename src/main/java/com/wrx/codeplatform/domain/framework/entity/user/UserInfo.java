package com.wrx.codeplatform.domain.framework.entity.user;

import com.wrx.codeplatform.domain.framework.sql.code.Code;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/1 18:59
 *
 * 主页用于展示用户信息的所有数据
 */
@Data
public class UserInfo {
    private String account;
    //前5个代码实体 用于展示
    private List<Code> codes = new ArrayList<>();
    //所有代码的平均评分
    private String avgScore;


}
