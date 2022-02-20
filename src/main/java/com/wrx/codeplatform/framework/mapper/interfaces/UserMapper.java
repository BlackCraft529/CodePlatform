package com.wrx.codeplatform.framework.mapper.interfaces;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wrx.codeplatform.domain.user.User;
import org.springframework.stereotype.Repository;

/**
 * @author 魏荣轩
 * @date 2022/2/9 21:38
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

}
