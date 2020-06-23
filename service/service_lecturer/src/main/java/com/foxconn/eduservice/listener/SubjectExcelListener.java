//package com.foxconn.eduservice.listener;
//
//import com.alibaba.excel.context.AnalysisContext;
//import com.alibaba.excel.event.AnalysisEventListener;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.foxconn.eduservice.domain.EduSubject;
//import com.foxconn.eduservice.domain.excel.SubjectData;
//import com.foxconn.eduservice.service.EduSubjectService;
//import com.foxconn.servicebase.exception.BaseExceptionHandler;
//
///**
// * @author zj
// * @create 2020-05-20 20:43
// */
//public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
//
//    //因不能交给spring管理(需要自己new)故使用有参构造传入eduSubjectService
//
//    public EduSubjectService eduSubjectService;
//
//    public SubjectExcelListener() {
//    }
//
//
//    public SubjectExcelListener(EduSubjectService eduSubjectService) {
//        this.eduSubjectService = eduSubjectService;
//    }
//
//    /**
//     * 读取excel内容
//     *
//     * @param subjectData
//     * @param analysisContext
//     */
//    @Override
//    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
//        if (subjectData == null) {
//            throw new BaseExceptionHandler(20001, "文件数据为空");
//        }
//        EduSubject existOneSubject = this.existOneSubject(eduSubjectService, subjectData.getOneSubjectName());
//        if (existOneSubject == null) { //判断是否存在一级分类(没有就新增)
//            existOneSubject = new EduSubject();
//            existOneSubject.setParentId("0");
//            existOneSubject.setTitle(subjectData.getOneSubjectName());
//            eduSubjectService.save(existOneSubject);
//        }
//        String pid = existOneSubject.getId();
//        EduSubject existTwoSubject = this.existTwoSubject(eduSubjectService, subjectData.getTwoSubjectName(), pid);
//        if (existTwoSubject == null) { //判断是否存在一级分类(没有就新增)
//            existTwoSubject = new EduSubject();
//            existTwoSubject.setParentId(pid);
//            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
//            eduSubjectService.save(existTwoSubject);
//        }
//    }
//
//    /**
//     * 判断是否存在一级分类
//     *
//     * @param subjectService
//     * @param name
//     * @return
//     */
//    private EduSubject existOneSubject(EduSubjectService subjectService, String name) {
//        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
//        wrapper.eq("title", name);
//        wrapper.eq("parent_id", 0);
//        EduSubject oneSubject = subjectService.getOne(wrapper);
//        return oneSubject;
//    }
//
//    /**
//     * 判断是否存在二级分类
//     *
//     * @param subjectService
//     * @param name
//     * @return
//     */
//    private EduSubject existTwoSubject(EduSubjectService subjectService, String name, String pid) {
//        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
//        wrapper.eq("title", name);
//        wrapper.eq("parent_id", pid);
//        EduSubject twoSubject = subjectService.getOne(wrapper);
//        return twoSubject;
//    }
//
//    @Override
//    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//
//    }
//}
