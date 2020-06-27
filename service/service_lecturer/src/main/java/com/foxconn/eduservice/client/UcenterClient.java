package com.foxconn.eduservice.client;

import com.foxconn.eduservice.domain.frontvo.UcenterMemberPay;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * @author zj
 * @create 2020-06-08 22:05
 */
@Component
@FeignClient(name = "service-ucenter", fallback = UcenterDegradeFeignClient.class)
public interface UcenterClient {

    @GetMapping("/ucenter/info/{uid}")
    UcenterMemberPay getUcenterInfoByuId(@PathVariable("uid") String uid);
}

