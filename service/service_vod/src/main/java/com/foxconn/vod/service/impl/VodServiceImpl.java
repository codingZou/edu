package com.foxconn.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.foxconn.enums.ResultCode;
import com.foxconn.servicebase.exception.BaseExceptionHandler;
import com.foxconn.vod.service.VodService;
import com.foxconn.vod.utils.ConstantPropertiesUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zj
 * @create 2020-06-04 21:27
 */
@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideoToAly(MultipartFile file) {
        try {
            //fileName:文件原始名称
            String filename = file.getOriginalFilename();
            //title:上传之后文件的显示名称 设为去除后缀的文件名
            String title = null;
            if (filename != null) {
                title = filename.substring(0, filename.lastIndexOf("."));
            }
            //文件流
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET, title, filename, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getRequestId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getRequestId();
            }
            return videoId;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BaseExceptionHandler(ResultCode.UPLOAD_VIDEO_ERROR.getCode(),
                    ResultCode.UPLOAD_VIDEO_ERROR.getMsg());
        }
    }
}
