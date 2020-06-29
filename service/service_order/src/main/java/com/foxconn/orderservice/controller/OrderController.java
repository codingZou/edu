package com.foxconn.orderservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.foxconn.orderservice.domain.Order;
import com.foxconn.orderservice.service.OrderService;
import com.foxconn.util.JwtUtils;
import com.foxconn.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author zj
 * @since 2020-06-27
 */
@RestController
@RequestMapping("/orderservice/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 生成订单
     *
     * @param courseId 课程id
     * @param request
     * @return
     */
    @PostMapping("/{courseId}")
    public Result addOrder(@PathVariable String courseId, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)) {
            return Result.error().code(64000).message("请先登录");
        }
        String orderNo = orderService.createOrder(memberId, courseId);
        return Result.ok().data("orderNo", orderNo);
    }

    /**
     * 根据订单号查询订单信息
     *
     * @param orderNo 订单号
     * @return
     */
    @GetMapping("/{orderNo}")
    public Result getOrderInfoByOrderNo(@PathVariable String orderNo) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(queryWrapper);
        return Result.ok().data("order", order);
    }

    /**
     * 根据课程查询该订单状态(用于判断该课程是否购买)
     *
     * @param courseId 课程id
     * @param memberId 用户id
     * @return
     */
    @GetMapping("/status/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId, @PathVariable String memberId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.eq("member_id", memberId);
        queryWrapper.eq("status", 1); //支付状态1：已支付 0：未支付
        int count = orderService.count(queryWrapper);
        return count > 0;
    }

}

