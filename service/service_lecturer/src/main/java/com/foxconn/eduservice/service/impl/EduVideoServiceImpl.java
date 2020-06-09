package com.foxconn.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foxconn.eduservice.client.VodClient;
import com.foxconn.eduservice.domain.EduVideo;
import com.foxconn.eduservice.mapper.EduVideoMapper;
import com.foxconn.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author zj
 * @since 2020-05-24
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    private VodClient vodClient;

    /**
     * 根据课程ids删除小节并删除里面的视频
     *
     * @param courseId 课程id
     */
    @Transactional
    public void deleteVideoByCourseId(String courseId) {
        //删除视频
        QueryWrapper<EduVideo> eduVideoQuery = new QueryWrapper<>();
        eduVideoQuery.eq("course_id", courseId);
        eduVideoQuery.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(eduVideoQuery);
        List<String> ids = new ArrayList<>();
        for (EduVideo eduVideo : eduVideos) {
            String sourceId = eduVideo.getVideoSourceId();
            if (!StringUtils.isEmpty(sourceId)) {
                ids.add(sourceId);
            }
        }
        if (ids.size() > 0) {
            vodClient.batchDelVideoBySourceId(ids);
        }
        //删除小节
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        baseMapper.delete(queryWrapper);
    }
}
