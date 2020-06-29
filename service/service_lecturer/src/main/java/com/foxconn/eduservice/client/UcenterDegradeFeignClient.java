package com.foxconn.eduservice.client;

import com.foxconn.util.vo.MemberVo;
import org.springframework.stereotype.Component;

@Component
public class UcenterDegradeFeignClient implements UcenterClient {

    @Override
    public MemberVo getUcenterInfoByuId(String uid) {
        return null;
    }
}