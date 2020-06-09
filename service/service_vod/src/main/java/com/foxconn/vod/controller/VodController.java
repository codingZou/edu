package com.foxconn.vod.controller;

import com.foxconn.util.Result;
import com.foxconn.vod.service.VodService;
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
}
