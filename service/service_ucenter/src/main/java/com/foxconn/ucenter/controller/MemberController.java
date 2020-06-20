package com.foxconn.ucenter.controller;


import com.foxconn.servicebase.exception.BaseExceptionHandler;
import com.foxconn.ucenter.domain.Member;
import com.foxconn.ucenter.domain.vo.RegisterVo;
import com.foxconn.ucenter.service.MemberService;
import com.foxconn.util.JwtUtils;
import com.foxconn.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
@CrossOrigin
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/login")
    public Result login(@RequestBody Member member) {
        String token = memberService.login(member);
        return Result.ok().data("eduToken", token);
    }

    @PostMapping("register")
    public Result register(@RequestBody RegisterVo registerVo) {
        boolean flag = memberService.register(registerVo);
        return flag ? Result.ok() : Result.error();
    }


    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("/info")
    public Result getLoginInfo(HttpServletRequest request) {
        try {
            String memberId = JwtUtils.getMemberIdByJwtToken(request);
            Member userInfo = memberService.getById(memberId);
            return Result.ok().data("userInfo", userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseExceptionHandler(80005, "获取用户信息失败");
        }
    }

}

