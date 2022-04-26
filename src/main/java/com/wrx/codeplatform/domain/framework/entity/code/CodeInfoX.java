package com.wrx.codeplatform.domain.framework.entity.code;

import com.wrx.codeplatform.domain.framework.sql.code.Code;
import com.wrx.codeplatform.utils.common.ServiceFactory;
import lombok.Data;

import java.text.SimpleDateFormat;

/**
 * @author 魏荣轩
 * @date 2022/4/26 0:59
 */
@Data
public class CodeInfoX {
    private int key;
    private String label;
    private boolean disabled = false;

    public CodeInfoX(Code code){
        this.key = code.getId();
        this.label = code.getName();

    }
}
