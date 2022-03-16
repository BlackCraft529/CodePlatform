package com.wrx.codeplatform.domain.framework.sql.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author 魏荣轩
 * @date 2022/3/1 19:38
 */
@Data
public class UserInfo {
    @TableId(value = "userId",type = IdType.INPUT)
    private int userId;
    private String description;
    private String email;
    private String phone;
    private String location;
    private String school;
    private String nickName;

    public UserInfo(int userId, String description, String email, String phone, String location, String school, String nickName){
        this.userId = userId;
        this.description = description;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.school = school;
        this.nickName = nickName;
    }

}
