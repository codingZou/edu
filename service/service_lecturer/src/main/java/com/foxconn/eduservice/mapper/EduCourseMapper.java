package com.foxconn.eduservice.mapper;

import com.foxconn.eduservice.domain.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.foxconn.eduservice.domain.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author zj
 * @since 2020-05-24
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getPublishCourseInfoById(String courseId);
}
