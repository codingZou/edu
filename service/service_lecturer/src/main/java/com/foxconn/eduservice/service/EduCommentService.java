package com.foxconn.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foxconn.eduservice.domain.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author zj
 * @since 2020-06-26
 */
public interface EduCommentService extends IService<EduComment> {

    Map<String, Object> pagingConmmentsBycourseId(Page<EduComment> commentPage, String courseId);

    boolean addComment(EduComment eduComment);
}
