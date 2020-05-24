package com.foxconn.eduservice.controller;

import com.foxconn.util.Result;
import org.springframework.web.bind.annotation.*;

/**
 * @author zj
 * @create 2020-05-11 22:21
 */
@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin
public class EduAdminLoginController {
    @PostMapping("/login")
    public Result login() {
        return Result.ok().data("token", "admin");
    }

    @GetMapping("/info")
    public Result getInfo() {
        return Result.ok().data("name", "admin").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif").data("roles", "[admin]");
    }
}
