package com.foxconn.eduservice.controller;


import com.foxconn.eduservice.domain.EduCourse;
import com.foxconn.eduservice.domain.vo.CourseInfoVo;
import com.foxconn.eduservice.domain.vo.CoursePublishVo;
import com.foxconn.eduservice.service.EduCourseService;
import com.foxconn.util.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zj
 * @since 2020-05-24
 */
@RestController
@Api(value = "课程信息Controller", tags = {"课程信息接口"})
@RequestMapping("/eduservice")
@CrossOrigin
public class EduCourseController {
    @Autowired
    EduCourseService eduCourseService;

    @PostMapping("/course")
    public Result addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        //添加成功后返回课程id提供给课程大纲使用
        String courseId = eduCourseService.saveCouresInfo(courseInfoVo);
        return Result.ok().data("courseId", courseId);
    }

    @GetMapping("/course/{courseId}")
    public Result getCourseInfoByCourseId(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = eduCourseService.getCourseById(courseId);
        return Result.ok().data("courseInfoVo", courseInfoVo);
    }

    @PutMapping("/course")
    public Result updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateCourseInfo(courseInfoVo);
        return Result.ok();
    }

    @GetMapping("/coursepublishvo/{courseId}")
    public Result getCoursePublishInfoVo(@PathVariable String courseId) {
        CoursePublishVo coursePublishVo = eduCourseService.getCoursePublishVoInfo(courseId);
        return Result.ok().data("coursePublishVo", coursePublishVo);
    }

    @PatchMapping("/coursepublishvo/{courseId}")
    public Result updateCoursePublishInfoVo(@PathVariable String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal"); //设置课程发布状态
        eduCourseService.updateById(eduCourse);
        return Result.ok();
    }

}

