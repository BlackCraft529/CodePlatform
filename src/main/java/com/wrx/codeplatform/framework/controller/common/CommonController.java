package com.wrx.codeplatform.framework.controller.common;

import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrx.codeplatform.domain.config.DataPool;
import com.wrx.codeplatform.domain.enums.ResultCode;
import com.wrx.codeplatform.domain.framework.entity.rechecking.RecheckingResult;
import com.wrx.codeplatform.domain.framework.entity.rechecking.RecheckingResultInit;
import com.wrx.codeplatform.domain.framework.sql.code.Code;
import com.wrx.codeplatform.domain.framework.sql.user.SysUser;
import com.wrx.codeplatform.domain.result.JsonResult;
import com.wrx.codeplatform.framework.service.CodeService;
import com.wrx.codeplatform.framework.service.SysUserService;
import com.wrx.codeplatform.utils.code.CodeFactory;
import com.wrx.codeplatform.utils.code.impl.CodeFactoryImpl;
import com.wrx.codeplatform.utils.common.TokenUtil;
import com.wrx.codeplatform.utils.data.SessionStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 魏荣轩
 * @date 2022/3/11 14:42
 */
@RestController
public class CommonController {

    /**
     * json工具对象
     */
    private final ObjectMapper jsonObjectMapper =  new ObjectMapper();
    @Autowired
    private CodeService codeService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取院校信息
     *
     * @return  院校列表
     */
    @RequestMapping(value = "/getSchools",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getSchools(){
        return jsonObjectMapper.valueToTree(new JsonResult(true, ResultCode.SUCCESS, DataPool.schools)).toString();
    }

    /**
     * 文件上传
     *
     * @param files  文件
     * @param request    请求
     * @return       是否成功
     */
    @CrossOrigin(origins = "http://localhost:8080",maxAge = 3600)
    @PostMapping(value = "/uploadFile")
    //@RequestParam的参数值为前端FormData对象的存储内容的key值
    //多文件上传时，用MultipartFile[]数组进行接收，单文件也可以这样接收
    public String uploadFile(@RequestParam("currentFile") MultipartFile[] files, HttpServletRequest request) {
        String token = request.getHeader("token");
        System.out.println(token);
        String description = URLDecoder.decode(request.getHeader("description"));
        System.out.println(description);
        String fileName = URLDecoder.decode(request.getHeader("name"));
        System.out.println(fileName);
        System.out.println("接收到的文件有"+files.length+"个");
        String account = TokenUtil.validToken(token);
        SysUser sysUser = sysUserService.selectByAccount(account);
        if (sysUser == null){
            JsonResult jsonResult = new JsonResult(false);
            jsonResult.setErrorCode(ResultCode.USER_ACCOUNT_DISABLE.getCode());
            jsonResult.setErrorMsg(ResultCode.USER_ACCOUNT_DISABLE.getMessage());
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }
        int sum = 0;
        for(MultipartFile f:files){
            System.out.println("正在存储"+f.getOriginalFilename()+"文件");
            String formatDate = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss").format(new Date());
            String path = DataPool.fileSavePath+File.separator+sysUser.getId();
            //格式化时间+原始文件名
            String name = formatDate+"__"+f.getOriginalFilename();
            String realPath = path+File.separator+name;
            File folder=new File(path);
            if(!folder.isDirectory())
                folder.mkdirs();
            try{
                f.transferTo(new File(folder,name));
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("真实路径: "+realPath);
            //文件存储完毕后插入用户的文件数据信息
            try{
                sum = codeService.insertCode(sysUser.getId(), realPath, fileName, description);
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("操作成功数:"+sum);
        }
        if (sum >= 1){
            JsonResult jsonResult = new JsonResult(true);
            jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }else {
            JsonResult jsonResult = new JsonResult(false);
            jsonResult.setErrorCode(ResultCode.SQL_ERROR.getCode());
            jsonResult.setErrorMsg(ResultCode.SQL_ERROR.getMessage());
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }

    }

    /**
     * 获取所在地列表
     *
     * @return  所在地
     */
    @RequestMapping(value = "/getLocations",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getLocations(){
        return jsonObjectMapper.valueToTree(new JsonResult(true, ResultCode.SUCCESS, DataPool.locations)).toString();
    }

    /**
     * 代码查重
     *
     * @param jsonData json数据
     * @return         是否成功查重
     * @throws JsonProcessingException  json数据
     */
    @PreAuthorize("hasAnyAuthority('view_self')")
    @RequestMapping(value = "/recheckingCode",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String recheckingCode(@RequestBody String jsonData) throws JsonProcessingException {
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
        final String range = node.get("range").asText();
        final String model = node.get("model").asText();
        final int chosenClass = node.get("class").asInt();
        final int chosenTeacher = node.get("teacher").asInt();
        final int codeId = node.get("codeId").asInt();
        //异步查重后写入数据库结果
        new Thread(() -> {
            CodeFactory codeFactory = new CodeFactoryImpl();
            codeFactory.recheckingCode(range, model,chosenClass, chosenTeacher, codeId);
        }).start();
        JsonResult jsonResult = new JsonResult(true);
        jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
        return jsonObjectMapper.valueToTree(jsonResult).toString();
    }

    /**
     * 查询Code的上次查重结果
     *
     * @param jsonData  json数据
     * @return          结果实体
     * @throws JsonProcessingException  json转换
     */
    @PreAuthorize("hasAnyAuthority('view_self')")
    @RequestMapping(value = "/getRecheckingCodeResult",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String getRecheckingCodeResult(@RequestBody String jsonData) {
        try {
            JsonNode node = jsonObjectMapper.readTree(jsonData);
            String token = node.get("token").asText();
            String account = TokenUtil.validToken(token);
            if (SessionStorage.pwdMap.get(account) == null) {
                //用户不存在
                JsonResult jsonResult = new JsonResult(false);
                jsonResult.setErrorCode(ResultCode.USER_ACCOUNT_NOT_EXIST.getCode());
                jsonResult.setErrorMsg(ResultCode.USER_ACCOUNT_NOT_EXIST.getMessage());
                return jsonObjectMapper.valueToTree(jsonResult).toString();
            }
            int codeId = node.get("codeId").asInt();
            Code code = codeService.selectCodeById(codeId);
            //代码信息不存在
            if (code == null) {
                JsonResult jsonResult = new JsonResult(false);
                jsonResult.setErrorCode(ResultCode.CODE_NOT_EXISTS.getCode());
                jsonResult.setErrorMsg(ResultCode.CODE_NOT_EXISTS.getMessage());
                return jsonObjectMapper.valueToTree(jsonResult).toString();
            }
            //代码查重信息为空
            if (code.getResult().equals("{}") || code.getResult().equals("")) {
                JsonResult jsonResult = new JsonResult(false);
                jsonResult.setErrorCode(ResultCode.RECHECKING_RESULT_NOT_EXISTS.getCode());
                jsonResult.setErrorMsg(ResultCode.RECHECKING_RESULT_NOT_EXISTS.getMessage());
                return jsonObjectMapper.valueToTree(jsonResult).toString();
            }
            RecheckingResultInit recheckingResult = jsonObjectMapper.readValue(code.getResult(),RecheckingResultInit.class);
            RecheckingResult result = new RecheckingResult(recheckingResult);
            JsonResult jsonResult = new JsonResult(true);
            jsonResult.setErrorCode(ResultCode.SUCCESS.getCode());
            jsonResult.setData(result);
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }catch (Exception exception){
            exception.printStackTrace();
            JsonResult jsonResult = new JsonResult(false);
            jsonResult.setErrorCode(ResultCode.SERVICE_ERROR.getCode());
            jsonResult.setErrorMsg(ResultCode.SERVICE_ERROR.getMessage());
            return jsonObjectMapper.valueToTree(jsonResult).toString();
        }
    }

}
