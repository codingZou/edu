package com.foxconn.orderservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foxconn.orderservice.domain.Order;
import com.foxconn.orderservice.domain.PayLog;
import com.foxconn.orderservice.mapper.PayLogMapper;
import com.foxconn.orderservice.service.OrderService;
import com.foxconn.orderservice.service.PayLogService;
import com.foxconn.orderservice.utils.HttpClient;
import com.foxconn.servicebase.exception.BaseExceptionHandler;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author zj
 * @since 2020-06-27
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {
    @Value("${wx.open.app_id}")
    private String appId;

    @Value("${wx.open.app_partner}")
    private String appSecret;

    @Value("${wx.open.app_partner_key}")
    private String appPartnerKey;

    @Value("${wx.open.notify_url}")
    private String notifyUrl;

    @Autowired
    private OrderService orderService;

    @Override
    public Map<String, Object> createNative(String orderNo) {
        try {
            //根据订单id获取订单信息
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no", orderNo);
            Order order = orderService.getOne(wrapper);

            Map<String, String> map = new HashMap<>();
            //1、设置支付参数
            map.put("appid", appId);
            map.put("mch_id", appSecret);
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            map.put("body", order.getCourseTitle());
            map.put("out_trade_no", orderNo);
            map.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");
            map.put("spbill_create_ip", "127.0.0.1");
            map.put("notify_url", notifyUrl);
            map.put("trade_type", "NATIVE");

            //2、HTTPClient来根据URL访问第三方接口并且传递参数
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");

            //client设置参数
            client.setXmlParam(WXPayUtil.generateSignedXml(map, appPartnerKey));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            System.out.println(xml);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //4、封装返回结果集

            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("out_trade_no", orderNo);
            hashMap.put("course_id", order.getCourseId());
            hashMap.put("total_fee", order.getTotalFee());
            hashMap.put("result_code", resultMap.get("result_code"));
            hashMap.put("code_url", resultMap.get("code_url"));
            //微信支付二维码2小时过期，可采取2小时未支付取消订单
            //redisTemplate.opsForValue().set(orderNo, map, 120, TimeUnit.MINUTES);
            return hashMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseExceptionHandler(90006, "获取支付二维码失败");
        }
    }

    /**
     * 查询订单状态
     *
     * @param orderNo 订单号
     * @return
     */
    @Override
    public Map<String, String> getOrderStatus(String orderNo) {
        try {
            //1、封装参数
            Map<String, String> map = new HashMap<>();
            map.put("appid", "wx74862e0dfcf69954");
            map.put("mch_id", "1558950191");
            map.put("out_trade_no", orderNo);
            map.put("nonce_str", WXPayUtil.generateNonceStr());

            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(map, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            //4、转成Map返回
            return WXPayUtil.xmlToMap(xml);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseExceptionHandler(90006, "订单状态异常");
        }
    }

    /**
     * 更新订单状态，插入支付记录
     *
     * @param map
     * @return
     */
    @Override
    @Transactional
    public void updateOrderStatus(Map<String, String> map) {
        try {
            //获取订单id
            String orderNo = map.get("out_trade_no");
            //根据订单id查询订单信息
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no", orderNo);
            Order order = orderService.getOne(wrapper);
            if (order.getStatus() != 1) {
                order.setStatus(1);
                orderService.updateById(order);
                //记录支付日志
                PayLog payLog = new PayLog();
                payLog.setOrderNo(order.getOrderNo());//支付订单号
                payLog.setPayTime(new Date());
                payLog.setPayType(1);//支付类型
                payLog.setTotalFee(order.getTotalFee());//总金额(分)
                payLog.setTradeState(map.get("trade_state"));//支付状态
                payLog.setTransactionId(map.get("transaction_id"));
                payLog.setAttr(JSONObject.toJSONString(map));
                baseMapper.insert(payLog);//插入到支付日志表
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseExceptionHandler(90004, "更新订单状态或新增订单记录失败");
        }
    }
}
