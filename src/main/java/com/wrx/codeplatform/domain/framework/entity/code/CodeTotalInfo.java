package com.wrx.codeplatform.domain.framework.entity.code;

import lombok.Data;

/**
 * @author 魏荣轩
 * @date 2022/3/21 2:03
 */
@Data
public class CodeTotalInfo {
    private int codeCount;

    public CodeTotalInfo(int codeCount){
        this.codeCount = codeCount;
    }
}
