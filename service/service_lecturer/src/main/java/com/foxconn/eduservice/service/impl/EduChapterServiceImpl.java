package com.foxconn.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foxconn.eduservice.domain.EduChapter;
import com.foxconn.eduservice.domain.EduVideo;
import com.foxconn.eduservice.domain.vo.ChapterVo;
import com.foxconn.eduservice.domain.vo.VideoVo;
import com.foxconn.eduservice.mapper.EduChapterMapper;
import com.foxconn.eduservice.service.EduChapterService;
import com.foxconn.eduservice.service.EduVideoService;
import com.foxconn.enums.ResultCode;
import com.foxconn.servicebase.exception.BaseExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zj
 * @since 2020-05-24
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> listChapterByCourseId(String courseId) {
        //根据课程id查询章节
        QueryWrapper<EduChapter> eduChapterWrapper = new QueryWrapper<>();
        eduChapterWrapper.eq("course_id", courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(eduChapterWrapper);
        //根据课程id查询小节
        QueryWrapper<EduVideo> eduVideoWrapper = new QueryWrapper<>();
        eduVideoWrapper.eq("course_id", courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(eduVideoWrapper);
        //封装ChapterVo
        List<ChapterVo> chapterVoList = new ArrayList<>();
        for (EduChapter eduChapter : eduChapterList) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            chapterVoList.add(chapterVo);
            List<VideoVo> videoVoList = new ArrayList<>();
            for (EduVideo eduVideo : eduVideoList) {
                if (eduVideo.getChapterId().equals(chapterVo.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);
        }

        return chapterVoList;
    }

    @Transactional
    public boolean deleteChapterById(String chapterId) {
        QueryWrapper<EduVideo> eduVideoWrapper = new QueryWrapper<>();
        eduVideoWrapper.eq("chapter_id", chapterId);
        int count = eduVideoService.count(eduVideoWrapper);
        if (count > 0) { //判断该章节存在小节不允许删除
            throw new BaseExceptionHandler(ResultCode.DELETE_CHAPTER_WARMING.getCode(), ResultCode.DELETE_CHAPTER_WARMING.getMsg());
        }
        int result = baseMapper.deleteById(chapterId);
        return result > 0;
    }
}
