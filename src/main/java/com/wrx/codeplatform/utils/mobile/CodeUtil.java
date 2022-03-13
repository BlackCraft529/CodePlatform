package com.wrx.codeplatform.utils.mobile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wrx.codeplatform.domain.enums.ResultCode;
import com.wrx.codeplatform.domain.result.JsonResult;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 魏荣轩
 * @date 2022/3/12 23:11
 */
public class CodeUtil {

    private static final ObjectMapper jsonObjectMapper =  new ObjectMapper();
    private static final String SERVER_URL="https://api.netease.im/sms/sendcode.action";//发送验证码的请求路径URL
    private static final String APP_KEY="becab55d678a6062baf25b858593cfd2";//网易云信分配的账号
    private static final String APP_SECRET="da3b7b7e3f1c";//网易云信分配的密钥
    private static final String MOULD_ID="19472371";//模板ID
    private static final String NONCE="123456";//随机数

    /**
     * 发送验证码请求
     *
     * @param phone       手机号
     * @param account     账户
     * @return            是否发送成功
     */
    public static String sendVerifyCode(String phone, String account){
        try {
            String result = sendMsg(phone);
            if (result.split(":")[0].equals("200")) {
                //发送成功
                return "true:"+result.split(":")[1];
            }
            return "false:"+result.split(":")[0];
        }catch (Exception exception) {
            exception.printStackTrace();
            return "false:-529";
        }
    }

    public static String sendMsg(String phone) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(SERVER_URL);

        String curTime=String.valueOf((new Date().getTime()/1000L));
        String checkSum=CheckSumBuilder.getCheckSum(APP_SECRET,NONCE,curTime);
        //设置请求的header
        post.addHeader("AppKey",APP_KEY);
        post.addHeader("Nonce",NONCE);
        post.addHeader("CurTime",curTime);
        post.addHeader("CheckSum",checkSum);
        post.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");

        //设置请求参数
        List<NameValuePair> nameValuePairs =new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("templateid",MOULD_ID));
        nameValuePairs.add(new BasicNameValuePair("mobile",phone));

        post.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));

        //执行请求
        HttpResponse response=httpclient.execute(post);
        String responseEntity= EntityUtils.toString(response.getEntity(),"utf-8");

        //判断是否发送成功，发送成功返回true
        String code= jsonObjectMapper.readTree(responseEntity).get("code").asText();
        if (code.equals("200")){
            return "success:"+checkSum;
        }
        return "error:"+code;
    }
}
