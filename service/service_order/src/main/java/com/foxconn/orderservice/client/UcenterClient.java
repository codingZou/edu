package com.foxconn.orderservice.client;

import com.foxconn.util.vo.MemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * @author zj
 * @create 2020-06-08 22:05
 */
@Component
@FeignClient(name = "service-ucenter")
public interface UcenterClient {

    @GetMapping("/ucenter/info/{uid}")
    MemberVo getUcenterInfoByMemberId(@PathVariable("uid") String memberId);
}

