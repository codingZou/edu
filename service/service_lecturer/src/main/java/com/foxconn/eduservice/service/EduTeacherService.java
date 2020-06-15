package com.foxconn.eduservice.service;

import com.foxconn.eduservice.domain.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
}
