package com.wrx.codeplatform.framework.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrx.codeplatform.domain.config.DataPool;
import com.wrx.codeplatform.domain.enums.ResultCode;
import com.wrx.codeplatform.domain.enums.RoleCode;
import com.wrx.codeplatform.domain.framework.sql.user.SysUser;
import com.wrx.codeplatform.domain.framework.sql.user.SysUserRoleRelation;
import com.wrx.codeplatform.domain.framework.sql.user.UserInfo;
import com.wrx.codeplatform.domain.framework.sql.user.VerifyStatus;
import com.wrx.codeplatform.domain.result.JsonResult;
import com.wrx.codeplatform.framework.config.common.PwdEncoder;
import com.wrx.codeplatform.framework.controller.util.CommonUtils;
import com.wrx.codeplatform.framework.service.*;
import com.wrx.codeplatform.utils.common.TimeUtil;
import com.wrx.codeplatform.utils.common.TokenUtil;
import com.wrx.codeplatform.utils.data.SessionStorage;
import com.wrx.codeplatform.utils.mobile.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/2/9 16:53
 *
 * 涉及所有的SysUser 和 VerifyStatus 和 UserInfo
 */
@RestController
public class UserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private VerifyStatusService verifyStatusService;
    @Autowired
    private SysUserRoleRelationService sysUserRoleRelationService;
    @Autowired
    private UserInfoService userInfoService;

    /**
     * json工具对象
     */
    private final ObjectMapper jsonObjectMapper =  new ObjectMapper();

    /**
     * 获取所有的教师信息
     *
     * @param jsonData  json数据
     * @return          教师列表
     * @throws JsonProcessingException  转换错误
     */
    @PreAuthorize("hasAnyAuthority('view_self')")
    @RequestMapping(value = "/getAllTeachers",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getAllTeachers(@RequestBody String jsonData) throws JsonProcessingException {
        JsonNode node = jsonObjectMapper.readTree(jsonData);
        String token = node.get("token").asText();
        String account = TokenUtil.validToken(token);
        if (SessionStorage.pwdMap.get(account) == null ){
            //用户不存在
            JsonResult jsonResult = new JsonResult(false);
            jsonResult.setErrorCode(ResultCode.USER_ACCOUNT_NOT_EXIST.getCode());
            jsonResult.setErrorMsg("无效的登入状态!");
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }
        List<UserInfo> teachers = userInfoService.selectUserInfoByRoleId(RoleCode.TEACHER.getRoleCode());
        JsonResult jsonResult = new JsonResult(true);
        jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
        jsonResult.setData(teachers);
        return jsonObjectMapper.valueToTree(jsonResult).toString();
    }


    /**
     * 用户登出
     *
     * @param jsonData  json数据
     * @return          结果
     * @throws JsonProcessingException   转换错误
     */
    @PreAuthorize("hasAnyAuthority('view_self')")
    @RequestMapping(value = "/logout",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String logout(@RequestBody String jsonData) throws JsonProcessingException {
        JsonNode node = jsonObjectMapper.readTree(jsonData);
        String token = node.get("token").asText();
        String account = TokenUtil.validToken(token);
        if (SessionStorage.pwdMap.get(account) == null ){
            //用户不存在
            JsonResult jsonResult = new JsonResult(false);
            jsonResult.setErrorCode(ResultCode.USER_ACCOUNT_NOT_EXIST.getCode());
            jsonResult.setErrorMsg("无效的登入状态!");
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }
        //删除缓存
        SessionStorage.pwdMap.remove(account);
        JsonResult jsonResult = new JsonResult(true);
        jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
        return jsonObjectMapper.valueToTree(jsonResult).toString();
    }

    /**
     * 前往教务中心
     *
     * @param jsonData  json数据
     * @return          结果
     * @throws JsonProcessingException   转换错误
     */
    @PreAuthorize("hasAnyAuthority('con_create')")
    @RequestMapping(value = "/toManagerCenter",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String toManagerCenter(@RequestBody String jsonData) throws JsonProcessingException {
        JsonNode node = jsonObjectMapper.readTree(jsonData);
        String token = node.get("token").asText();
        String account = TokenUtil.validToken(token);
        if (SessionStorage.pwdMap.get(account) == null ){
            //用户不存在
            JsonResult jsonResult = new JsonResult(false);
            jsonResult.setErrorCode(ResultCode.USER_ACCOUNT_NOT_EXIST.getCode());
            jsonResult.setErrorMsg("无效的登入状态!");
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }
        JsonResult jsonResult = new JsonResult(true);
        jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
        return jsonObjectMapper.valueToTree(jsonResult).toString();
    }

    /**
     * 修改用户信息
     *
     * @param jsonData  json数据
     * @return          结果
     * @throws JsonProcessingException   转换错误
     */
    @PreAuthorize("hasAnyAuthority('view_self')")
    @RequestMapping(value = "/changeUserInfo",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String changeUserInfo(@RequestBody String jsonData) throws JsonProcessingException {
        JsonNode node = jsonObjectMapper.readTree(jsonData);
        String token = node.get("token").asText();
        String account = TokenUtil.validToken(token);
        SysUser sysUser = sysUserService.selectByAccount(account);
        if (sysUser == null) {
            //用户不存在
            JsonResult jsonResult = new JsonResult(false);
            jsonResult.setErrorCode(ResultCode.USER_ACCOUNT_NOT_EXIST.getCode());
            jsonResult.setErrorMsg("账号不存在或登入过期");
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }
        String newNickName = node.get("newNickName").asText();
        String newDescription = node.get("newDescription").asText();
        String newHeadPortrait = node.get("newHeadPortrait").asText();
        String newLocation = node.get("newLocation").asText();
        String newEmail = node.get("newEmail").asText();
        UserInfo userInfo = userInfoService.selectByUserId(sysUser.getId());
        userInfo.setDescription(newDescription);
        userInfo.setHeadPortrait(newHeadPortrait);
        userInfo.setEmail(newEmail);
        userInfo.setLocation(DataPool.locations.get(Integer.parseInt(newLocation)));
        userInfo.setNickName(newNickName);
        if (userInfoService.updateUserInfo(userInfo) == 1){
            JsonResult jsonResult = new JsonResult(true);
            jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }
        JsonResult jsonResult = new JsonResult(false);
        jsonResult.setErrorCode(ResultCode.SQL_ERROR.getCode());
        jsonResult.setErrorMsg(ResultCode.SQL_ERROR.getMessage());
        return jsonObjectMapper.valueToTree(jsonResult).toString();
    }

    /**
     * 修改密码
     *
     * @param jsonData  json数据
     * @return          结果
     * @throws JsonProcessingException   转换错误
     */
    @PreAuthorize("hasAnyAuthority('view_self')")
    @RequestMapping(value = "/changePwd",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String changePwd(@RequestBody String jsonData) throws JsonProcessingException {
        JsonNode node = jsonObjectMapper.readTree(jsonData);
        String token = node.get("token").asText();
        String account = TokenUtil.validToken(token);
        SysUser sysUser = sysUserService.selectByAccount(account);
        if (sysUser == null) {
            //用户不存在
            JsonResult jsonResult = new JsonResult(false);
            jsonResult.setErrorCode(ResultCode.USER_ACCOUNT_NOT_EXIST.getCode());
            jsonResult.setErrorMsg("账号不存在或登入过期");
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }
        String oldPwd = node.get("oldPwd").asText();
        if (!PwdEncoder.getPasswordEncoder().matches(oldPwd,sysUser.getPassword())){
            JsonResult jsonResult = new JsonResult(false);
            jsonResult.setErrorCode(ResultCode.USER_CREDENTIALS_ERROR.getCode());
            jsonResult.setErrorMsg(ResultCode.USER_CREDENTIALS_ERROR.getMessage());
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }
        String newPwd = node.get("newPwd").asText();
        newPwd = PwdEncoder.getPasswordEncoder().encode(newPwd);
        sysUser.setPassword(newPwd);
        if (sysUserService.update(sysUser) == 1){
            JsonResult jsonResult = new JsonResult(true);
            jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }
        JsonResult jsonResult = new JsonResult(false);
        jsonResult.setErrorCode(ResultCode.SQL_ERROR.getCode());
        jsonResult.setErrorMsg(ResultCode.SQL_ERROR.getMessage());
        return jsonObjectMapper.valueToTree(jsonResult).toString();
    }

    /**
     * 获取用户信息
     *
     * @param jsonData   json数据
     * @return           用户详细信息
     * @throws JsonProcessingException   转换错误
     */
    @PreAuthorize("hasAnyAuthority('view_self')")
    @RequestMapping(value = "/getUserInfo",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getUserInfo(@RequestBody String jsonData) throws JsonProcessingException {
        JsonNode node = jsonObjectMapper.readTree(jsonData);
        String token = node.get("token").asText();
        String account = TokenUtil.validToken(token);
        SysUser sysUser = sysUserService.selectByAccount(account);
        if (sysUser == null ){
            //用户不存在
            JsonResult jsonResult = new JsonResult(false);
            jsonResult.setErrorCode(ResultCode.USER_ACCOUNT_NOT_EXIST.getCode());
            jsonResult.setErrorMsg("账号不存在或登入过期");
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }
        UserInfo userInfo = userInfoService.selectByUserAccount(sysUser);
        if (userInfo == null){
            //用户信息不存在
            JsonResult jsonResult = new JsonResult(false);
            jsonResult.setErrorCode(ResultCode.USER_ACCOUNT_NOT_EXIST.getCode());
            jsonResult.setErrorMsg("账号信息不存在");
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }
        JsonResult jsonResult = new JsonResult(true);
        jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
        jsonResult.setData(userInfo);
        return jsonObjectMapper.valueToTree(jsonResult).toString();
    }

    /**
     * 获取用户主页详细数据
     *
     * @param jsonData json data
     * @return 用户详细信息Json
     */
    @RequestMapping(value = "/verifyCodeAndRegister",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String verifyCodeAndRegisterUser(@RequestBody String jsonData) throws JsonProcessingException {
        JsonNode node = jsonObjectMapper.readTree(jsonData);
        String phone = node.get("phone").asText();
        String code = node.get("code").asText();
        String account = node.get("account").asText();
        String password = node.get("password").asText();
        String nickName = node.get("nickname").asText();
        String school = DataPool.schools.get(node.get("school").asInt());
        String email = node.get("email").asText();
        String location = DataPool.locations.get(node.get("location").asInt());
        //对用户手机以及验证码进行校验
        //手机尚未存在验证码信息
        if (verifyStatusService.selectVerifyStatusByPhone(phone) == null){
            //手机号不存在
            JsonResult jsonResult = new JsonResult(false);
            jsonResult.setErrorCode(ResultCode.PHONE_NOT_EXISTS.getCode());
            jsonResult.setErrorMsg("手机号不存在");
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }
        //验证码超时
        if (!verifyStatusService.verifyCodeAndPhone(phone, code)){
            //手机-验证码失效
            JsonResult jsonResult = new JsonResult(false);
            jsonResult.setErrorCode(ResultCode.CODE_OVERDUE.getCode());
            jsonResult.setErrorMsg("验证码失效,请重新获取");
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }
        //注册 + 密码加密
        password = PwdEncoder.getPasswordEncoder().encode(password);
        return jsonObjectMapper.valueToTree(CommonUtils.registerUser(true,account, password, nickName, school, email, location, phone)).toString();
    }

    /**
     * 发送验证码请求
     *
     * @param jsonData  json数据
     * @return          创建完成返回true
     */
    @RequestMapping(value = "/checkOrCreateVerifyStatus",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String createVerifyStatus(@RequestBody String jsonData) {
        try {
            JsonNode node = jsonObjectMapper.readTree(jsonData);
            String account = node.get("account").asText();
            String phone = node.get("phone").asText();
            //发送验证码后返回前端数据
            String res = jsonObjectMapper.valueToTree(verifyAndSendCode(account, phone)).toString();
            System.out.println("结果: " + res);
            return res;
        }catch (Exception exception){
            exception.printStackTrace();
            return "null";
        }
    }


    /**
     * 验证并发送验证码 & 数据校验
     *
     * @param account    账户
     * @param phone      手机
     * @return           结果
     */
    public JsonResult verifyAndSendCode(String account, String phone) throws ParseException {
        //该用户已经注册或者用户名已经被预定
        if ( sysUserService.selectByAccount(account) != null ){
            JsonResult jsonResult = new JsonResult(false);
            jsonResult.setErrorCode(ResultCode.USER_ACCOUNT_ALREADY_EXIST.getCode());
            jsonResult.setErrorMsg("用户名已经被注册!");
            return jsonResult;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //手机号是否已经注册且尚未冷却完成
        VerifyStatus verifyStatus = verifyStatusService.selectVerifyStatusByPhone(phone);
        if ( verifyStatus != null ){
            //冷却中
            if (TimeUtil.minuteBetween(df.format(verifyStatus.getSendDate()), df.format(new Date())) < DataPool.codeCoolDownMin){
                JsonResult jsonResult = new JsonResult(false);
                jsonResult.setErrorCode(ResultCode.CODE_COOL_DOWN.getCode());
                jsonResult.setErrorMsg("验证码发送冷却中");
                return jsonResult;
            }
            //手机号已经完成注册
            if ( verifyStatus.isComplete() ){
                JsonResult jsonResult = new JsonResult(false);
                jsonResult.setErrorCode(ResultCode.PHONE_USED.getCode());
                jsonResult.setErrorMsg("手机号已被注册");
                return jsonResult;
            }
            //验证码已经解除冷却 且 账户尚未完成注册 - 删除已有验证状态信息,重新生成
            verifyStatusService.deleteVerifyStatusByPhone(phone);
        }
        String result = CodeUtil.sendVerifyCode(phone);
        if (result.split(":")[0].equalsIgnoreCase("true")){
            //发送验证码成功 - 返回前端数据
            verifyStatus = new VerifyStatus(account, phone, result.split(":")[1]);
            //插入临时验证数据信息
            verifyStatusService.insertVerifyStatus(verifyStatus);
            JsonResult jsonResult = new JsonResult(true);
            jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
            return jsonResult;
        }else {
            //发送验证码失败 - 网易错误
            JsonResult jsonResult = new JsonResult(false);
            jsonResult.setErrorCode(ResultCode.PHONE_SERVICE_ERROR.getCode());
            jsonResult.setErrorMsg("运营商错误:"+result.split(":")[1]);
            return jsonResult;
        }
    }


}
