package com.foxconn.eduservice.controller.front;

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

    /**
     * 首页查询热门课程与热门老师
     *
     * @return
     */
    @GetMapping("/index")
    public Result findHotTeachersAndHotCourses() {
        //查询热门课程
        List<EduCourse> courses = courseService.listHotCourses();
        //查询热门讲师
        List<EduTeacher> teachers = teacherService.listHotTeachers();
        return Result.ok().data("teachers", teachers).data("courses", courses);
    }
}
