package com.foxconn.lecturer.service;

import com.foxconn.lecturer.domain.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.foxconn.lecturer.domain.vo.SubjectOneVo;
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
