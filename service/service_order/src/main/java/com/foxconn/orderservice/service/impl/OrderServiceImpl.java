package com.foxconn.orderservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foxconn.orderservice.client.CourseClient;
import com.foxconn.orderservice.client.UcenterClient;
import com.foxconn.orderservice.domain.Order;
import com.foxconn.orderservice.mapper.OrderMapper;
import com.foxconn.orderservice.service.OrderService;
import com.foxconn.orderservice.utils.OrderNoUtil;
import com.foxconn.servicebase.exception.BaseExceptionHandler;
import com.foxconn.util.vo.CourseVo;
import com.foxconn.util.vo.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author zj
 * @since 2020-06-27
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private UcenterClient ucenterClient;
    @Autowired
    private CourseClient courseClient;

    /**
     * 生成订单
     *
     * @param memberId 会员id
     * @param courseId 课程id
     * @return
     */
    @Override
    public String createOrder(String memberId, String courseId) {
        try {
            MemberVo memberInfo = ucenterClient.getUcenterInfoByMemberId(memberId);
            CourseVo courseInfo = courseClient.getCourseInfoById(courseId);
            Order order = new Order();
            order.setOrderNo(OrderNoUtil.getOrderNo());
            order.setCourseId(courseId);
            order.setCourseTitle(courseInfo.getTitle());
            order.setCourseCover(courseInfo.getCover());
            order.setTeacherName(courseInfo.getTeacherName());
            order.setTotalFee(courseInfo.getPrice());
            order.setMemberId(memberId);
            order.setMobile(memberInfo.getMobile());
            order.setNickname(memberInfo.getNickname());
            order.setStatus(0);
            order.setPayType(1);
            baseMapper.insert(order);
            return order.getOrderNo();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseExceptionHandler(90004, "订单创建失败");
        }
    }
}
