package com.foxconn.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foxconn.eduservice.domain.EduCourse;
import com.foxconn.eduservice.domain.EduTeacher;
import com.foxconn.eduservice.mapper.EduTeacherMapper;
import com.foxconn.eduservice.service.EduCourseService;
import com.foxconn.eduservice.service.EduTeacherService;
import com.foxconn.servicebase.exception.BaseExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author zj
 * @since 2020-05-03
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {
    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 查询热门老师
     *
     * @return
     */
    @Override
    @Cacheable(value = "teachers", key = "'teachers'")
    public List<EduTeacher> listHotTeachers() {
        QueryWrapper<EduTeacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("id");
        teacherQueryWrapper.last("LIMIT 4");
        return baseMapper.selectList(teacherQueryWrapper);
    }

    @Override
    public Map<String, Object> pagingTeacher(Page<EduTeacher> eduTeacherPage) {
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("sort");
        baseMapper.selectPage(eduTeacherPage, queryWrapper);
        List<EduTeacher> records = eduTeacherPage.getRecords();
        long current = eduTeacherPage.getCurrent();
        long pages = eduTeacherPage.getPages();
        long size = eduTeacherPage.getSize();
        long total = eduTeacherPage.getTotal();
        boolean hasNext = eduTeacherPage.hasNext();
        boolean hasPrevious = eduTeacherPage.hasPrevious();
        Map<String, Object> teacherMap = new HashMap<>();
        teacherMap.put("items", records);
        teacherMap.put("current", current);
        teacherMap.put("pages", pages);
        teacherMap.put("size", size);
        teacherMap.put("total", total);
        teacherMap.put("hasNext", hasNext);
        teacherMap.put("hasPrevious", hasPrevious);
        return teacherMap;
    }

    @Override
    @Transactional
    public Map<String, Object> listTeacherAndCourses(String teacherId) {
        try {
            EduTeacher teacherInfo = baseMapper.selectById(teacherId);
            QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("teacher_id", teacherId);
            List<EduCourse> eduCourses = eduCourseService.list(queryWrapper);
            Map<String, Object> map = new HashMap<>();
            map.put("teacherInfo", teacherInfo);
            map.put("eduCourses", eduCourses);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseExceptionHandler(80004, "获取讲师信息或课程失败");
        }
    }
}
