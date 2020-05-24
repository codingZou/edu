package com.foxconn.eduservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foxconn.eduservice.domain.EduCourse;
import com.foxconn.eduservice.domain.EduCourseDescription;
import com.foxconn.eduservice.domain.vo.CourseInfoVo;
import com.foxconn.eduservice.mapper.EduCourseMapper;
import com.foxconn.eduservice.service.EduCourseDescriptionService;
import com.foxconn.eduservice.service.EduCourseService;
import com.foxconn.servicebase.exception.BaseExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zj
 * @since 2020-05-24
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Transactional
    public String saveCouresInfo(CourseInfoVo courseInfoVo) {
        //添加课程
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert <= 0) {
            throw new BaseExceptionHandler(20001, "添加课程失败");
        }
        String courseId = eduCourse.getId();
        //添加课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseId);
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        boolean flag = eduCourseDescriptionService.save(eduCourseDescription);
        if (!flag) {
            throw new BaseExceptionHandler(20001, "添加课程简介失败");
        }
        return courseId;
    }
}
