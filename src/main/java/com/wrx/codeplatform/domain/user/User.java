package com.wrx.codeplatform.domain.user;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.util.Date;

/**
 * @author 魏荣轩
 * @date 2022/2/9 21:29
 * @description 用户类
 */
@Data
public class User {
    //主键注解
    @TableId
    private String uuid;
    private String account;
    private String password;
    private String nickName;
    private String studentNum;
    private String teacherNum;
    private String email;
    private Date registerDate;
    private Date lastLoginDate;

}
