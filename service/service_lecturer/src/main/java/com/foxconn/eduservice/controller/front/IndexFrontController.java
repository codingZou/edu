package com.foxconn.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.foxconn.eduservice.domain.EduCourse;
import com.foxconn.eduservice.domain.EduTeacher;
import com.foxconn.eduservice.service.EduCourseService;
import com.foxconn.eduservice.service.EduTeacherService;
import com.foxconn.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zj
 * @create 2020-06-14 14:32
 */
@RestController
@RequestMapping("/eduservice/front")
@CrossOrigin
public class IndexFrontController {
    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;

    @GetMapping("/index")
    public Result findTeachers() {
        //查询热门课程
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("id");
        courseQueryWrapper.last("LIMIT 8");
        List<EduCourse> courses = courseService.list(courseQueryWrapper);

        //查询热门讲师
        QueryWrapper<EduTeacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("id");
        teacherQueryWrapper.last("LIMIT 4");
        List<EduTeacher> teachers = teacherService.list(teacherQueryWrapper);

        return Result.ok().data("teachers", teachers).data("courses", courses);
    }
}
