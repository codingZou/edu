package com.foxconn.orderservice.service;

import com.foxconn.orderservice.domain.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author zj
 * @since 2020-06-27
 */
public interface OrderService extends IService<Order> {

    String createOrder(String memberId, String courseId);
}
