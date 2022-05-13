package com.wrx.codeplatform.domain.framework.entity.rechecking;

import lombok.Data;
import java.util.*;

/**
 * @author 魏荣轩
 * @date 2022/3/24 17:53
 */
@Data
public class RecheckingResultInit {
    private String completeDate;
    private int targetCode;
    private int targetClass;
    private String model;
    private String range;
    private List<String> result = new ArrayList<>();

    public RecheckingResultInit(){}

    public RecheckingResultInit(int targetClass, int targetCode, String model, String range){
        this.targetClass = targetClass;
        this.targetCode = targetCode;
        this.model = model;
        this.range = range;
    }
}
