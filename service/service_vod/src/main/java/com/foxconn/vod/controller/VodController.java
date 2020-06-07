package com.foxconn.vod.controller;

import com.foxconn.util.Result;
import com.foxconn.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
}
