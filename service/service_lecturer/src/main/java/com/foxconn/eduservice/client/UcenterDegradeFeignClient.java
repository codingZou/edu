package com.foxconn.eduservice.client;

import com.foxconn.eduservice.domain.frontvo.UcenterMemberPay;
import org.springframework.stereotype.Component;

@Component
public class UcenterDegradeFeignClient implements UcenterClient {

    @Override
    public UcenterMemberPay getUcenterInfoByuId(String uid) {
        return null;
    }
}