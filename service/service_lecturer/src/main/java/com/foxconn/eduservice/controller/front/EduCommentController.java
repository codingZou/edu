package com.foxconn.eduservice.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foxconn.eduservice.domain.EduComment;
import com.foxconn.eduservice.service.EduCommentService;
import com.foxconn.util.JwtUtils;
import com.foxconn.util.Result;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 课程评论 前端控制器
 * </p>
 *
 * @author zj
 * @since 2020-06-26
 */
@RestController
@RequestMapping("/eduservice/comment")
@CrossOrigin
public class EduCommentController {
    @Autowired
    private EduCommentService commentService;

    /**
     * 分页查询课程评论列表
     *
     * @param current
     * @param limit
     * @param courseId
     * @return
     */
    @GetMapping("/{current}/{limit}")
    public Result pagingFindComments(@ApiParam(name = "current", value = "当前页码", required = true)
                                     @PathVariable Long current,
                                     @ApiParam(name = "limit", value = "每页记录数", required = true)
                                     @PathVariable Long limit,
                                     @ApiParam(name = "courseId", value = "课程id", required = true)
                                     @RequestParam String courseId) {
        System.out.println(courseId);
        Page<EduComment> commentPage = new Page<>(current, limit);
        Map<String, Object> commentMap = commentService.pagingConmmentsBycourseId(commentPage, courseId);
        return Result.ok().data("pageComment", commentMap);
    }

    /**
     * 新增评论
     *
     * @param eduComment
     * @return
     */
    @PostMapping
    public Result saveComment(@RequestBody EduComment eduComment, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)) {
            return Result.error().code(64000).message("请登录");
        }
        eduComment.setMemberId(memberId);
        boolean flag = commentService.addComment(eduComment);
        return flag ? Result.ok() : Result.error();
    }

}

