package com.wrx.codeplatform.framework.controller.util;

import com.wrx.codeplatform.domain.enums.ResultCode;
import com.wrx.codeplatform.domain.enums.RoleCode;
import com.wrx.codeplatform.domain.framework.sql.user.SysUser;
import com.wrx.codeplatform.domain.framework.sql.user.SysUserRoleRelation;
import com.wrx.codeplatform.domain.framework.sql.user.UserInfo;
import com.wrx.codeplatform.domain.result.JsonResult;
import com.wrx.codeplatform.utils.common.ServiceFactory;

/**
 * @author 魏荣轩
 * @date 2022/5/4 23:49
 */
public class CommonUtils {
    /**
     * 注册用户
     *
     * @param account     账户
     * @param password    密码
     * @param nickName    昵称
     * @param school      学校
     * @param email       邮箱
     * @param location    定位
     * @param phone       手机
     * @return            结果信息
     */
    public static JsonResult registerUser(boolean isTeacher, String account, String password, String nickName, String school, String email, String location, String phone){
        try{
            int successNum = 0;
            SysUser sysUser = new SysUser(account, nickName, password);
            //插入用户基本信息
            successNum+= ServiceFactory.sysUserService.insert(sysUser);
            sysUser = ServiceFactory.sysUserService.selectByAccount(account);
            //插入用户关系表  -- 默认注册用户为老师
            int roleCode = RoleCode.TEACHER.getRoleCode();
            if (!isTeacher){
                roleCode = RoleCode.STUDENT.getRoleCode();
            }
            SysUserRoleRelation sysUserRoleRelation = new SysUserRoleRelation(sysUser.getId(), roleCode);
            successNum += ServiceFactory.sysUserRoleRelationService.insertNewSysUserRoleRelation(sysUserRoleRelation);
            //插入角色基本信息表
            UserInfo userInfo = new UserInfo(sysUser.getId(), "暂未填写", email, phone, location, school, nickName);
            successNum += ServiceFactory.userInfoService.insertUserInfo(userInfo);
            //更新验证信息表
            if (isTeacher) {
                successNum += ServiceFactory.verifyStatusService.updateVerifyStatusComplete(phone, true);
            }
            JsonResult jsonResult;
            if ((successNum == 4 && isTeacher ) || (successNum == 3 && !isTeacher )){
                jsonResult = new JsonResult(true);
                jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
            }else {
                jsonResult = new JsonResult(false);
                jsonResult.setErrorCode(ResultCode.SQL_ERROR.getCode());
                jsonResult.setErrorMsg("数据库错误,请联系管理员");
            }
            return jsonResult;
        }catch (Exception exception){
            exception.printStackTrace();
            JsonResult jsonResult = new JsonResult(false);
            jsonResult.setErrorCode(ResultCode.SQL_ERROR.getCode());
            jsonResult.setErrorMsg("数据库错误,请联系管理员");
            return jsonResult;
        }

    }

}
