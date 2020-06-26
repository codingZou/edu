package com.foxconn.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.foxconn.servicebase.exception.BaseExceptionHandler;
import com.foxconn.util.Result;
import com.foxconn.vod.service.VodService;
import com.foxconn.vod.utils.ConstantPropertiesUtil;
import com.foxconn.vod.utils.InitVodCilent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author zj
 * @create 2020-06-04 21:26
 */
@RestController
@RequestMapping("/vod")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;

    /**
     * 上传文件到阿里云
     *
     * @param file 文件流
     * @return
     */
    @PostMapping("/video")
    public Result uploadVideo(MultipartFile file) {
        String videoId = vodService.uploadVideoToAly(file);
        return Result.ok().data("videoId", videoId);
    }

    @DeleteMapping("/video/{sourceId}")
    public Result deleteVideoBySourceId(@PathVariable String sourceId) {
        vodService.deleteVideoBySourceId(sourceId);
        return Result.ok();
    }

    @DeleteMapping("/video")
    public Result batchDelVideoBySourceId(@RequestParam("sourceId") List<String> sourceIds) {
        vodService.batchDelVideoBySourceId(sourceIds);
        return Result.ok();
    }

    /**
     * 根据视频id获取视频凭证
     *
     * @param videoId
     * @return
     * @throws Exception
     */
    @GetMapping("/video/auth/{videoId}")
    public Result getVideoPlayAuth(@PathVariable("videoId") String videoId) throws Exception {
        try {
            //初始化
            DefaultAcsClient client =
                    InitVodCilent.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            //请求
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            request.setVideoId(videoId);

            //响应
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);

            //得到播放凭证
            String playAuth = response.getPlayAuth();

            //返回结果
            return Result.ok().data("playAuth", playAuth);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseExceptionHandler(80003, "获取视频凭证失败");
        }
    }
}
