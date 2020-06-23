package com.foxconn.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foxconn.eduservice.domain.EduCourse;
import com.foxconn.eduservice.domain.EduCourseDescription;
import com.foxconn.eduservice.domain.frontvo.CourseFrontQueryVo;
import com.foxconn.eduservice.domain.frontvo.CourseWebVo;
import com.foxconn.eduservice.domain.vo.CourseInfoVo;
import com.foxconn.eduservice.domain.vo.CoursePublishVo;
import com.foxconn.eduservice.domain.vo.CourseQuery;
import com.foxconn.eduservice.mapper.EduCourseMapper;
import com.foxconn.eduservice.service.EduChapterService;
import com.foxconn.eduservice.service.EduCourseDescriptionService;
import com.foxconn.eduservice.service.EduCourseService;
import com.foxconn.eduservice.service.EduVideoService;
import com.foxconn.enums.ResultCode;
import com.foxconn.servicebase.exception.BaseExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private EduCourseMapper eduCourseMapper;

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

    /**
     * 根据课程id查询课程相关信息
     *
     * @return 课程信息
     */
    @Transactional
    public CourseInfoVo getCourseById(String courseId) {
        try {
            EduCourse eduCourse = baseMapper.selectById(courseId);
            EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);
            CourseInfoVo courseInfoVo = new CourseInfoVo();
            BeanUtils.copyProperties(eduCourse, courseInfoVo);
            courseInfoVo.setDescription(eduCourseDescription.getDescription());
            return courseInfoVo;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseExceptionHandler(ResultCode.GET_COURSE_INFO_ERROR.getCode(), ResultCode.GET_COURSE_INFO_ERROR.getMsg());
        }
    }

    /**
     * 更新课程信息
     *
     * @param courseInfoVo 课程信息VO类
     * @return
     */
    @Transactional
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, eduCourseDescription);
        try {
            baseMapper.updateById(eduCourse);
            eduCourseDescriptionService.updateById(eduCourseDescription);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseExceptionHandler(ResultCode.UPDATE_COURSE_INFO_ERROR.getCode(), ResultCode.UPDATE_COURSE_INFO_ERROR.getMsg());
        }

    }

    @Override
    public CoursePublishVo getCoursePublishVoInfo(String courseId) {
        try {
            return eduCourseMapper.getPublishCourseInfoById(courseId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseExceptionHandler(ResultCode.GET_COURSE_PUBLISH_ERROR.getCode(),
                    ResultCode.GET_COURSE_PUBLISH_ERROR.getMsg());
        }
    }

    public void pagingCourse(Page<EduCourse> pageParam, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();
        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(teacherId)) {
            queryWrapper.eq("teacher_id", teacherId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.eq("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.eq("subject_id", subjectId);
        }
        queryWrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Transactional
    public boolean deleteCourse(String courseId) {
        try {
            //小节
            eduVideoService.deleteVideoByCourseId(courseId);
            //章节
            eduChapterService.deleteChapterByCourseId(courseId);
            //描述
            eduCourseDescriptionService.removeById(courseId);
            //课程
            baseMapper.deleteById(courseId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseExceptionHandler(ResultCode.DELETE_COURSE_ERROR.getCode(),
                    ResultCode.DELETE_COURSE_ERROR.getMsg());
        }
    }

    /**
     * 查询热门课程
     *
     * @return
     */
    @Override
    @Cacheable(value = "courses", key = "'courses'")
    public List<EduCourse> listHotCourses() {
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("id");
        courseQueryWrapper.last("LIMIT 8");
        return baseMapper.selectList(courseQueryWrapper);
    }

    /**
     * 前台接口条件分页查询课程列表
     *
     * @param coursePage
     * @param courseFrontQueryVo
     * @return
     */
    @Override
    @Transactional
    public Map<String, Object> getFrontCourses(Page<EduCourse> coursePage, CourseFrontQueryVo courseFrontQueryVo) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseFrontQueryVo.getSubjectParentId())) {// 一级分类
            queryWrapper.eq("subject_parent_id", courseFrontQueryVo.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(courseFrontQueryVo.getSubjectId())) {// 二级分类
            queryWrapper.eq("subject_id", courseFrontQueryVo.getSubjectId());
        }

        if (!StringUtils.isEmpty(courseFrontQueryVo.getBuyCountSort())) {// 购买数量
            queryWrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(courseFrontQueryVo.getGmtCreateSort())) {// 创建时间
            queryWrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontQueryVo.getPriceSort())) {// 价格
            queryWrapper.orderByDesc("price");
        }
        baseMapper.selectPage(coursePage, queryWrapper);

        List<EduCourse> records = coursePage.getRecords();
        long current = coursePage.getCurrent();
        long pages = coursePage.getPages();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();
        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    /**
     * 根据课程id查询课程信息和视频
     *
     * @param courseId
     * @return
     */
    @Override
    public CourseWebVo getCourseInfo(String courseId) {
        return eduCourseMapper.getCourseInfoById(courseId);
    }
}
