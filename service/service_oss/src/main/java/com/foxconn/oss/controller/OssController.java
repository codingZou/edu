package com.foxconn.oss.controller;

import com.foxconn.oss.service.OssService;
import com.foxconn.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zj
 * @create 2020-05-17 14:29
 */
@RestController
@RequestMapping("/eduoss/file")
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;

    @PostMapping
    public Result uploadFile(MultipartFile file) {
        String avatarPath = ossService.uploadFileAvatar(file);
        return Result.ok().data("url", avatarPath);
    }
}
