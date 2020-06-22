package com.foxconn.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foxconn.eduservice.domain.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author zj
 * @since 2020-05-03
 */
public interface EduTeacherService extends IService<EduTeacher> {

    List<EduTeacher> listHotTeachers();

    Map<String, Object> pagingTeacher(Page<EduTeacher> eduTeacherPage);

    Map<String, Object> listTeacherAndCourses(String teacherId);
}
