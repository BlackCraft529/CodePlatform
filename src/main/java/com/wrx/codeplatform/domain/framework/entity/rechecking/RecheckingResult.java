package com.wrx.codeplatform.domain.framework.entity.rechecking;

import com.baomidou.mybatisplus.extension.api.R;
import com.wrx.codeplatform.domain.framework.sql.code.Code;
import com.wrx.codeplatform.utils.common.ServiceFactory;
import lombok.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/25 0:16
 */
@Data
public class RecheckingResult {
    private String completeDate;
    private int targetCode;
    private int targetClass;
    private String model;
    private String range;
    private List<RecheckingCodeResult> result = new ArrayList<>();

    public RecheckingResult(RecheckingResultInit recheckingResultInit){
        this.completeDate = recheckingResultInit.getCompleteDate();
        this.targetCode = recheckingResultInit.getTargetCode();
        this.targetClass = recheckingResultInit.getTargetClass();
        this.model = recheckingResultInit.getModel();
        this.range = recheckingResultInit.getRange();
        System.out.println(recheckingResultInit.getResult());
        for (String res: recheckingResultInit.getResult()){
            int codeId = Integer.parseInt(res.split("::")[0]);
            double percent = Double.parseDouble(res.split("::")[1]);
            Code code = ServiceFactory.codeService.selectCodeById(codeId);
            RecheckingCodeResult recheckingCodeResult = new RecheckingCodeResult(code);
            recheckingCodeResult.setPercent(percent);
            this.result.add(recheckingCodeResult);
        }
    }
}
