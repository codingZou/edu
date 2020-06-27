package com.foxconn.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foxconn.eduservice.domain.EduCourse;
import com.foxconn.eduservice.domain.frontvo.CourseFrontQueryVo;
import com.foxconn.eduservice.domain.frontvo.CourseWebVo;
import com.foxconn.eduservice.domain.vo.ChapterVo;
import com.foxconn.eduservice.service.EduChapterService;
import com.foxconn.eduservice.service.EduCourseService;
import com.foxconn.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author zj
 * @create 2020-06-22 19:50
 */
@RestController
@RequestMapping("/eduservice/front")
@CrossOrigin
public class CourseFrontController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduChapterService chapterService;

    @PostMapping("/courses/page/{current}/{limit}")
    public Result conditionPageCourse(@RequestBody(required = false) CourseFrontQueryVo courseFrontQueryVo,
                                      @PathVariable long current, @PathVariable long limit) {
        Page<EduCourse> coursePage = new Page<>(current, limit);
        Map<String, Object> courseMap = courseService.getFrontCourses(coursePage, courseFrontQueryVo);
        return Result.ok().data(courseMap);
    }

    /**
     * 根据课程id查询课程信息和视频
     *
     * @param courseId 课程id
     * @return
     */
    @GetMapping("/course/{courseId}")
    public Result getCourseAndVideoByCourseId(@PathVariable String courseId) {
        CourseWebVo courseWebInfo = courseService.getCourseInfo(courseId);
        //查询章节小节
        List<ChapterVo> chapterVoList = chapterService.listChapterByCourseId(courseId);
        return Result.ok().data("courseWeb", courseWebInfo).data("chapterVos", chapterVoList);
    }
}
