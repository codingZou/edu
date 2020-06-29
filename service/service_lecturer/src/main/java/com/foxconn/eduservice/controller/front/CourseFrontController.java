package com.foxconn.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foxconn.eduservice.client.OrderClient;
import com.foxconn.eduservice.domain.EduCourse;
import com.foxconn.eduservice.domain.frontvo.CourseFrontQueryVo;
import com.foxconn.eduservice.domain.frontvo.CourseWebVo;
import com.foxconn.eduservice.domain.vo.ChapterVo;
import com.foxconn.eduservice.service.EduChapterService;
import com.foxconn.eduservice.service.EduCourseService;
import com.foxconn.util.JwtUtils;
import com.foxconn.util.Result;
import com.foxconn.util.vo.CourseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author zj
 * @create 2020-06-22 19:50
 */
@RestController
@RequestMapping("/eduservice/front")
@CrossOrigin
public class CourseFrontController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private OrderClient orderClient;

    @PostMapping("/courses/page/{current}/{limit}")
    public Result conditionPageCourse(@RequestBody(required = false) CourseFrontQueryVo courseFrontQueryVo,
                                      @PathVariable long current, @PathVariable long limit) {
        Page<EduCourse> coursePage = new Page<>(current, limit);
        Map<String, Object> courseMap = courseService.getFrontCourses(coursePage, courseFrontQueryVo);
        return Result.ok().data(courseMap);
    }

    /**
     * 根据课程id查询课程信息和视频
     *
     * @param courseId 课程id
     * @return
     */
    @GetMapping("/course/{courseId}")
    public Result getCourseAndVideoByCourseId(@PathVariable String courseId, HttpServletRequest request) {
        //查询课程信息
        CourseWebVo courseWebInfo = courseService.getCourseInfo(courseId);
        //查询章节小节
        List<ChapterVo> chapterVoList = chapterService.listChapterByCourseId(courseId);
        //查询课程是否已经购买
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (!StringUtils.isEmpty(memberId)) {
            boolean buyCourse = orderClient.isBuyCourse(courseId, memberId);
            return Result.ok().data("courseWeb", courseWebInfo).data("chapterVos", chapterVoList).data("buyCourse", buyCourse);
        }
        return Result.ok().data("courseWeb", courseWebInfo).data("chapterVos", chapterVoList);
    }

    @GetMapping("/api/course/{courseId}")
    public CourseVo getCourseInfoByCourseId(@PathVariable String courseId) {
        CourseWebVo courseInfo = courseService.getCourseInfo(courseId);
        CourseVo courseVo = new CourseVo();
        BeanUtils.copyProperties(courseInfo, courseVo);
        return courseVo;
    }
}
