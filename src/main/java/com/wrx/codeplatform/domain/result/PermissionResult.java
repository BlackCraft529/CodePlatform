package com.wrx.codeplatform.domain.result;

import lombok.Data;
import java.util.Date;

/**
 * @author 魏荣轩
 * @date 2022/2/22 21:57
 */
@Data
public class PermissionResult {
    private String reason;
    private Date date;

    public PermissionResult( String reason, Date date){
        this.reason = reason;
        this.date = date;
    }
}
