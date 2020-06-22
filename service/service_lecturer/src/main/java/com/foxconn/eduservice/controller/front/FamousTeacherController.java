package com.foxconn.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foxconn.eduservice.domain.EduTeacher;
import com.foxconn.eduservice.service.EduTeacherService;
import com.foxconn.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 前台名师controller
 *
 * @author zj
 * @create 2020-06-21 14:50
 */
@RestController
@RequestMapping("/eduservice/front")
@CrossOrigin
public class FamousTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 分页查询名师列表
     *
     * @param current
     * @param limit
     * @return
     */
    @GetMapping("/teachers/{current}/{limit}")
    public Result pagingFindFamousTeacher(@PathVariable long current, @PathVariable long limit) {
        Page<EduTeacher> eduTeacherPage = new Page<>(current, limit);
        Map<String, Object> pagingTeacher = eduTeacherService.pagingTeacher(eduTeacherPage);
        return Result.ok().data("pagingTeacher", pagingTeacher);
    }

    /**
     * 根据讲师id查询讲师详情和课程列表
     *
     * @param teacherId
     * @return
     */
    @GetMapping("teacher/{teacherId}")
    public Result getTeacherAndCoursesById(@PathVariable String teacherId) {
        Map<String, Object> teacherMap = eduTeacherService.listTeacherAndCourses(teacherId);
        return Result.ok().data("teacherMap", teacherMap);
    }

}
