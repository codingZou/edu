package com.foxconn.eduservice.controller;


import com.foxconn.eduservice.domain.vo.ChapterVo;
import com.foxconn.eduservice.service.EduChapterService;
import com.foxconn.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zj
 * @since 2020-05-24
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    @GetMapping("/{courseId}")
    public Result getChapterByCourseId(@PathVariable String courseId) {
        List<ChapterVo> chapterVoList = eduChapterService.listChapterByCourseId(courseId);
        return Result.ok().data("chapterList", chapterVoList);
    }
}

