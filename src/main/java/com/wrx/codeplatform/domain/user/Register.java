package com.wrx.codeplatform.domain.user;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.util.Date;

/**
 * @author 魏荣轩
 * @date 2022/2/9 23:05
 */
@Data
public class Register {
    @TableId
    private String account;
    private String code;
    private Date sendDate;
    private boolean successful;

}
