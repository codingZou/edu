package com.foxconn.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.foxconn.enums.ResultCode;
import com.foxconn.msm.service.MsmService;
import com.foxconn.servicebase.exception.BaseExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author zj
 * @create 2020-06-16 20:55
 */
@Service
public class MsmServiceImpl implements MsmService {
    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;

    @Override
    public boolean sendMsg(String phoneNumber, Map<String, Object> map) {
        if (StringUtils.isEmpty(phoneNumber)) return false;
        DefaultProfile profile =
                DefaultProfile.getProfile("default", keyId, keySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        //设置固定参数
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25"); //版本号
        request.setAction("SendSms");

        //设置发送相关参数
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "富学宝典在线教育平台");
        request.putQueryParameter("TemplateCode", "SMS_193237885");
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map));

        try {
            CommonResponse response = client.getCommonResponse(request);
            return response.getHttpResponse().isSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseExceptionHandler(ResultCode.SEND_MSG_FAIL.getCode(),
                    ResultCode.SEND_MSG_FAIL.getMsg());
        }

    }
}
