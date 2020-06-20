package com.foxconn.msm.controller;

import com.foxconn.msm.service.MsmService;
import com.foxconn.msm.utils.RandomUtil;
import com.foxconn.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zj
 * @create 2020-06-16 20:54
 */
@RestController
@RequestMapping("/msm")
@CrossOrigin
public class MsmController {
    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 发送验证码
     *
     * @param phoneNumber 手机号码
     * @return
     */
    @GetMapping("/msg/{phoneNumber}")
    public Result sendMsg(@PathVariable String phoneNumber) {
        //从redis中获取验证码
        String code = redisTemplate.opsForValue().get(phoneNumber);
        if (!StringUtils.isEmpty(code)) {
            return Result.ok();
        }
        //获取不到则发送短信
        code = RandomUtil.getFourBitRandom(); //生成随机四位验证码
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        boolean flag = msmService.sendMsg(phoneNumber, map);
        if (flag) {
            //发送成功将验证码放入redis缓存中,并设置有效时间为五分钟
            redisTemplate.opsForValue().set(phoneNumber, code, 5, TimeUnit.MINUTES);
            return Result.ok();
        } else {
            return Result.error();
        }
    }
}
