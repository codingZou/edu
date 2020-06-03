package com.foxconn.eduservice.service;

import com.foxconn.eduservice.domain.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.foxconn.eduservice.domain.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zj
 * @since 2020-05-24
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> listChapterByCourseId(String courseId);

    boolean deleteChapterById(String chapterId);

    void deleteChapterByCourseId(String courseId);
}
