package com.foxconn.vod.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author zj
 * @create 2020-06-04 21:26
 */
public interface VodService {
    String uploadVideoToAly(MultipartFile file);
}
