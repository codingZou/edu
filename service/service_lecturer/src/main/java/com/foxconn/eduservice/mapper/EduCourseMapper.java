package com.foxconn.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.foxconn.eduservice.domain.EduCourse;
import com.foxconn.eduservice.domain.frontvo.CourseWebVo;
import com.foxconn.eduservice.domain.vo.CoursePublishVo;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author zj
 * @since 2020-05-24
 */
@Component
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getPublishCourseInfoById(String courseId);

    CourseWebVo getCourseInfoById(String courseId);
}
