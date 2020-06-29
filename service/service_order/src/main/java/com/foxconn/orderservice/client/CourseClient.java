package com.foxconn.orderservice.client;

import com.foxconn.util.vo.CourseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * @author zj
 * @create 2020-06-08 22:05
 */
@Component
@FeignClient(name = "service-lecturer")
public interface CourseClient {

    @GetMapping("/eduservice/front/api/course/{courseId}")
    CourseVo getCourseInfoById(@PathVariable("courseId") String courseId);
}

