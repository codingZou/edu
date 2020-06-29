package com.foxconn.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * @author zj
 * @create 2020-06-08 22:05
 */
@Component
@FeignClient(name = "service-order")
public interface OrderClient {

    /**
     * 根据课程查询该订单状态(用于判断该课程是否购买)
     *
     * @param courseId 课程id
     * @param memberId 用户id
     * @return
     */
    @GetMapping("/orderservice/order/status/{courseId}/{memberId}")
    boolean isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);
}

