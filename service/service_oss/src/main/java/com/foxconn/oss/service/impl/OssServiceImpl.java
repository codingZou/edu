package com.foxconn.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.foxconn.enums.ResultCode;
import com.foxconn.oss.service.OssService;
import com.foxconn.oss.utils.ConstantPropertiesUtil;
import com.foxconn.servicebase.exception.BaseExceptionHandler;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author zj
 * @create 2020-05-17 14:30
 */
@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile multipartFile) {
        //获取阿里云存储相关常量
        String endPoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

            InputStream inputStream = multipartFile.getInputStream();
            //获取上传文件名称
            String filename = multipartFile.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //给文件名加入随机值
            filename = uuid + filename;
            //当前日期当作目录
            String dataPath = new DateTime().toString("yyyy/MM/dd");
            filename = dataPath + "/" + filename;
            // 上传文件。
            ossClient.putObject(bucketName, filename, inputStream);
            //获取url地址
            String uploadUrl = "http://" + bucketName + "." + endPoint + "/" + filename;
            // 关闭OSSClient。
            ossClient.shutdown();
            return uploadUrl;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseExceptionHandler(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg());
        }
    }
}
