package com.foxconn.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author zj
 * @create 2020-05-17 14:30
 */
public interface OssService {
    String uploadFileAvatar(MultipartFile multipartFile);
}
