package com.foxconn.ucenter.controller;


import com.foxconn.ucenter.domain.Member;
import com.foxconn.ucenter.domain.vo.RegisterVo;
import com.foxconn.ucenter.service.MemberService;
import com.foxconn.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author zj
 * @since 2020-06-18
 */
@RestController
@RequestMapping("/ucenter")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/login")
    public Result login(@RequestBody Member member) {
        String token = memberService.login(member);
        return Result.ok().data("token", token);
    }

    @PostMapping("register")
    public Result register(@RequestBody RegisterVo registerVo) {
        boolean flag = memberService.regist(registerVo);
        return flag ? Result.ok() : Result.error();
    }

}

