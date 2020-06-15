package com.foxconn.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foxconn.eduservice.domain.EduTeacher;
import com.foxconn.eduservice.mapper.EduTeacherMapper;
import com.foxconn.eduservice.service.EduTeacherService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
