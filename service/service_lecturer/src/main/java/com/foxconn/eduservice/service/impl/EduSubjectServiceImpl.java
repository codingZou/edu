//package com.foxconn.eduservice.service.impl;
//
//import com.alibaba.excel.EasyExcel;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.foxconn.eduservice.domain.EduSubject;
//import com.foxconn.eduservice.domain.excel.SubjectData;
//import com.foxconn.eduservice.domain.vo.SubjectOneVo;
//import com.foxconn.eduservice.domain.vo.SubjectTwoVo;
//import com.foxconn.eduservice.listener.SubjectExcelListener;
//import com.foxconn.eduservice.mapper.EduSubjectMapper;
//import com.foxconn.eduservice.service.EduSubjectService;
//import org.springframework.beans.BeanUtils;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * <p>
// * 课程科目 服务实现类
// * </p>
// *
// * @author zj
// * @since 2020-05-20
// */
//@Service
//public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
//
//    @Override
//    public void addSubject(MultipartFile file, EduSubjectService eduSubjectService) {
//        try {
//            InputStream inputStream = file.getInputStream();
//            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public List<SubjectOneVo> getSubjects() {
//        //查出一级分类
//        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
//        wrapper.eq("parent_id", 0);
//        List<EduSubject> oneSubjects = baseMapper.selectList(wrapper);
//        //查出二级分类
//        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
//        queryWrapper.ne("parent_id", 0);
//        List<EduSubject> twoSbjects = baseMapper.selectList(queryWrapper);
//        List<SubjectOneVo> list = new ArrayList<>();
//        for (EduSubject oneSubject : oneSubjects) {
//            SubjectOneVo subjectOneVo = new SubjectOneVo();
//            BeanUtils.copyProperties(oneSubject, subjectOneVo);
//            List<SubjectTwoVo> subjectTwoVos = new ArrayList<>();
//            for (EduSubject twoSbject : twoSbjects) {
//                SubjectTwoVo subjectTwoVo = new SubjectTwoVo();
//                BeanUtils.copyProperties(twoSbject, subjectTwoVo);
//                if (twoSbject.getParentId().equals(subjectOneVo.getId())) { //判断一级parentId是否等于二级分类id
//                    BeanUtils.copyProperties(twoSbject, subjectTwoVo);
//                    subjectTwoVos.add(subjectTwoVo);
//                }
//            }
//            subjectOneVo.setChildren(subjectTwoVos);
//            list.add(subjectOneVo);
//        }
//        return list;
//    }
//}
