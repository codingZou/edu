package com.foxconn.eduservice.controller;


import com.foxconn.eduservice.domain.EduChapter;
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
@RequestMapping("/eduservice")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    /**
     * 根据课程id查询课程大纲列表
     *
     * @param courseId
     * @return
     */
    @GetMapping("/chaptervo/{courseId}")
    public Result getChapterVoByCourseId(@PathVariable String courseId) {
        List<ChapterVo> chapterVoList = eduChapterService.listChapterByCourseId(courseId);
        return Result.ok().data("chapterList", chapterVoList);
    }

    /**
     * 保存章节
     *
     * @param eduChapter
     * @return
     */
    @PostMapping("/chapter")
    public Result saveChapter(@RequestBody EduChapter eduChapter) {
        boolean flag = eduChapterService.save(eduChapter);
        if (!flag) {
            return Result.error();
        }
        return Result.ok();
    }

    /**
     * 根据章节id查询章节
     *
     * @param chapterId
     * @return
     */
    @GetMapping("/chapter/{chapterId}")
    public Result getChapterById(@PathVariable String chapterId) {
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return Result.ok().data("eduChapter", eduChapter);
    }

    /**
     * 更新章节
     *
     * @param eduChapter
     * @return
     */
    @PutMapping("/chapter")
    public Result updateChapter(@RequestBody EduChapter eduChapter) {
        boolean flag = eduChapterService.updateById(eduChapter);
        if (!flag) {
            return Result.error();
        }
        return Result.ok();
    }

    @DeleteMapping("/chapter/{chapterId}")
    public Result deleteChapterById(@PathVariable String chapterId) {
        boolean flag = eduChapterService.deleteChapterById(chapterId);
        if (!flag) {
            return Result.error();
        }
        return Result.ok();
    }
}

