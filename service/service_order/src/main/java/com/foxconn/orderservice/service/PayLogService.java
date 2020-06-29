package com.foxconn.orderservice.service;

import com.foxconn.orderservice.domain.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author zj
 * @since 2020-06-27
 */
public interface PayLogService extends IService<PayLog> {

    Map<String, Object> createNative(String orderNo);

    Map<String, String> getOrderStatus(String orderNo);

    void updateOrderStatus(Map<String, String> map);
}
