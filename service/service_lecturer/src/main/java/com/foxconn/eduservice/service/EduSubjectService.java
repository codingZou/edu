package com.foxconn.eduservice.service;

import com.foxconn.eduservice.domain.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.foxconn.eduservice.domain.vo.SubjectOneVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author zj
 * @since 2020-05-20
 */
public interface EduSubjectService extends IService<EduSubject> {

    void addSubject(MultipartFile file, EduSubjectService eduSubjectService);

    List<SubjectOneVo> getSubjects();
}
