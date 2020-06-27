package com.foxconn.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foxconn.eduservice.client.UcenterClient;
import com.foxconn.eduservice.domain.EduComment;
import com.foxconn.eduservice.domain.frontvo.UcenterMemberPay;
import com.foxconn.eduservice.mapper.EduCommentMapper;
import com.foxconn.eduservice.service.EduCommentService;
import com.foxconn.servicebase.exception.BaseExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author zj
 * @since 2020-06-26
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {
    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public Map<String, Object> pagingConmmentsBycourseId(Page<EduComment> commentPage, String courseId) {
        try {
            QueryWrapper<EduComment> commentQueryWrapper = new QueryWrapper<>();
            commentQueryWrapper.eq("course_id", courseId);
            commentQueryWrapper.orderByDesc("gmt_create");
            baseMapper.selectPage(commentPage, commentQueryWrapper);
            Map<String, Object> map = new HashMap<>();
            map.put("total", commentPage.getTotal());
            map.put("current", commentPage.getCurrent());
            map.put("pages", commentPage.getPages());
            map.put("records", commentPage.getRecords());
            map.put("size", commentPage.getSize());
            map.put("hasNext", commentPage.hasNext());
            map.put("hasPrevious", commentPage.hasPrevious());
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseExceptionHandler(70006, "查询课程评论失败");
        }
    }

    @Override
    public boolean addComment(EduComment eduComment) {
        try {
            UcenterMemberPay userInfo = ucenterClient.getUcenterInfoByuId(eduComment.getMemberId());
            eduComment.setNickname(userInfo.getNickname());
            eduComment.setAvatar(userInfo.getAvatar());
            int count = baseMapper.insert(eduComment);
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseExceptionHandler(80006, "添加评论失败");
        }

    }
}
