package com.foxconn.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.foxconn.eduservice.domain.EduCourse;
import com.foxconn.eduservice.domain.frontvo.CourseFrontQueryVo;
import com.foxconn.eduservice.domain.frontvo.CourseWebVo;
import com.foxconn.eduservice.domain.vo.CourseInfoVo;
import com.foxconn.eduservice.domain.vo.CoursePublishVo;
import com.foxconn.eduservice.domain.vo.CourseQuery;

import java.util.List;
import java.util.Map;

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

    CoursePublishVo getCoursePublishVoInfo(String courseId);

    void pagingCourse(Page<EduCourse> pageParam, CourseQuery courseQuery);

    boolean deleteCourse(String courseId);

    List<EduCourse> listHotCourses();

    Map<String, Object> getFrontCourses(Page<EduCourse> coursePage, CourseFrontQueryVo courseFrontQueryVo);

    CourseWebVo getCourseInfo(String courseId);
}
