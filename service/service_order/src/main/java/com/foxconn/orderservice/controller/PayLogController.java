package com.foxconn.orderservice.controller;


import com.foxconn.orderservice.service.PayLogService;
import com.foxconn.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author zj
 * @since 2020-06-27
 */
@RestController
@RequestMapping("/orderservice/order/pay")
@CrossOrigin
public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    /**
     * 获取微信支付二维码
     *
     * @param orderNo 订单号
     * @return
     */
    @GetMapping("/{orderNo}")
    public Result createNativeByOrderNO(@PathVariable String orderNo) {
        Map<String, Object> map = payLogService.createNative(orderNo);
        System.out.println(map);
        return Result.ok().data("map", map);
    }

    /**
     * 根据订单号查询订单状态并添加支付记录
     *
     * @param orderNo 订单号
     * @return
     */
    @GetMapping("/status/{orderNo}")
    public Result getOrderStatusByOrderNo(@PathVariable String orderNo) {
        Map<String, String> map = payLogService.getOrderStatus(orderNo);
        if (map == null) {
            return Result.error().message("支付失败");
        }
        if ("SUCCESS".equals(map.get("trade_state"))) { //支付成功
            //添加记录到支付表中并更新订单状态
            payLogService.updateOrderStatus(map);
            return Result.ok().code(66666).message("支付成功");
        }
        return Result.ok().code(25000).message("支付中");
    }

}

