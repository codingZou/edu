package com.foxconn.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.foxconn.eduservice.domain.EduCourse;
import com.foxconn.eduservice.domain.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zj
 * @since 2020-05-24
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCouresInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseById(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);
}
