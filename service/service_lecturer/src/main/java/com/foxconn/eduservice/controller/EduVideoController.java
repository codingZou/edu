package com.foxconn.eduservice.controller;


import com.foxconn.eduservice.domain.EduVideo;
import com.foxconn.eduservice.service.EduVideoService;
import com.foxconn.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author zj
 * @since 2020-05-24
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;

    @GetMapping("/{videoId}")
    public Result getVideoById(@PathVariable String videoId) {
        EduVideo eduVideo = eduVideoService.getById(videoId);
        return Result.ok().data("eduVideo", eduVideo);
    }

    @PostMapping
    public Result saveVideo(@RequestBody EduVideo eduVideo) {
        boolean flag = eduVideoService.save(eduVideo);
        return flag ? Result.ok() : Result.error();
    }

    @PutMapping
    public Result updateVideo(@RequestBody EduVideo eduVideo) {
        boolean flag = eduVideoService.updateById(eduVideo);
        return flag ? Result.ok() : Result.error();
    }

    //TODO 删除小节时需要把视频删除
    @DeleteMapping("/{videoId}")
    public Result deleteVideo(@PathVariable String videoId) {
        boolean flag = eduVideoService.removeById(videoId);
        return flag ? Result.ok() : Result.error();
    }
}

