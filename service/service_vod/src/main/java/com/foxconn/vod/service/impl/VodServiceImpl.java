package com.foxconn.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.foxconn.enums.ResultCode;
import com.foxconn.servicebase.exception.BaseExceptionHandler;
import com.foxconn.vod.service.VodService;
import com.foxconn.vod.utils.ConstantPropertiesUtil;
import com.foxconn.vod.utils.InitVodCilent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BaseExceptionHandler(ResultCode.UPLOAD_VIDEO_ERROR.getCode(),
                    ResultCode.UPLOAD_VIDEO_ERROR.getMsg());
        }
    }

    @Override
    public void deleteVideoBySourceId(String sourceId) {
        try {
            DefaultAcsClient client = InitVodCilent.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            //支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(sourceId);
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new BaseExceptionHandler(ResultCode.DELETE_VOD_ERROR.getCode(),
                    ResultCode.DELETE_VOD_ERROR.getMsg());
        }
    }

    /**
     * 批量删除aly视频
     *
     * @param sourceIds 视频id集合
     */
    public void batchDelVideoBySourceId(List sourceIds) {
        try {
            DefaultAcsClient client = InitVodCilent.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            //支持传入多个视频ID，多个用逗号分隔
            String ids = StringUtils.join(sourceIds.toArray(), ",");
            request.setVideoIds(ids);
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new BaseExceptionHandler(ResultCode.DELETE_VOD_ERROR.getCode(),
                    ResultCode.DELETE_VOD_ERROR.getMsg());
        }
    }
}
