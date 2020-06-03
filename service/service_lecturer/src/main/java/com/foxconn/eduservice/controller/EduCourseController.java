package com.foxconn.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foxconn.eduservice.domain.EduCourse;
import com.foxconn.eduservice.domain.vo.CourseInfoVo;
import com.foxconn.eduservice.domain.vo.CoursePublishVo;
import com.foxconn.eduservice.domain.vo.CourseQuery;
import com.foxconn.eduservice.service.EduCourseService;
import com.foxconn.util.Result;
import io.swagger.annotations.Api;
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
@Api(value = "课程信息Controller", tags = {"课程信息接口"})
@RequestMapping("/eduservice")
@CrossOrigin
public class EduCourseController {
    @Autowired
    EduCourseService eduCourseService;

    /**
     * 条件分页查询课程列表
     *
     * @param courseQuery 查询条件封装类
     * @param current     当前页
     * @param limit       每页数量
     * @return
     */
    @PostMapping("/course/page/{current}/{limit}")
    public Result pageListCourse(@RequestBody(required = false) CourseQuery courseQuery, @PathVariable long current, @PathVariable long limit) {
        Page<EduCourse> pageParam = new Page<>(current, limit);
        eduCourseService.pagingCourse(pageParam, courseQuery);
        List<EduCourse> pagingCourses = pageParam.getRecords();
        long total = pageParam.getTotal();
        return Result.ok().data("total", total).data("pagingCourses", pagingCourses);
    }

    /**
     * 添加课程信息
     *
     * @param courseInfoVo
     * @return
     */
    @PostMapping("/course")
    public Result addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        //添加成功后返回课程id提供给课程大纲使用
        String courseId = eduCourseService.saveCouresInfo(courseInfoVo);
        return Result.ok().data("courseId", courseId);
    }

    /**
     * 根据课程id查询课程大纲
     *
     * @param courseId
     * @return
     */
    @GetMapping("/course/{courseId}")
    public Result getCourseInfoByCourseId(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = eduCourseService.getCourseById(courseId);
        return Result.ok().data("courseInfoVo", courseInfoVo);
    }

    /**
     * 修改课程课程信息
     *
     * @param courseInfoVo
     * @return
     */
    @PutMapping("/course")
    public Result updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateCourseInfo(courseInfoVo);
        return Result.ok();
    }

    /**
     * 根据课程id查询课程最终发布信息
     *
     * @param courseId
     * @return
     */
    @GetMapping("/coursepublishvo/{courseId}")
    public Result getCoursePublishInfoVo(@PathVariable String courseId) {
        CoursePublishVo coursePublishVo = eduCourseService.getCoursePublishVoInfo(courseId);
        return Result.ok().data("coursePublishVo", coursePublishVo);
    }

    /**
     * 发布课程
     *
     * @param courseId 课程id
     * @return
     */
    @PatchMapping("/coursepublishvo/{courseId}")
    public Result updateCoursePublishInfoVo(@PathVariable String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal"); //设置课程发布状态
        eduCourseService.updateById(eduCourse);
        return Result.ok();
    }

    @DeleteMapping("/course/{courseId}")
    public Result deleteCourseById(@PathVariable String courseId) {
        boolean flag = eduCourseService.deleteCourse(courseId);
        return flag ? Result.ok() : Result.error();
    }

}

