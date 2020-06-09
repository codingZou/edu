package com.foxconn.eduservice.controller;


import com.foxconn.eduservice.client.VodClient;
import com.foxconn.eduservice.domain.EduVideo;
import com.foxconn.eduservice.service.EduVideoService;
import com.foxconn.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
    @Autowired
    private VodClient vodClient;

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

    /**
     * 删除小节和视频
     *
     * @param videoId 小节id
     * @return
     */
    @DeleteMapping("/{videoId}")
    public Result deleteVideo(@PathVariable String videoId) {
        EduVideo eduVideo = eduVideoService.getById(videoId);
        String sourceId = eduVideo.getVideoSourceId(); //根据小节id得到视频id
        if (!StringUtils.isEmpty(sourceId)) { //当视频id不为空时删除
            vodClient.deleteVideoBySourceId(sourceId);
        }
        boolean flag = eduVideoService.removeById(videoId);
        return flag ? Result.ok() : Result.error();
    }

}

