package com.foxconn.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author zj
 * @create 2020-06-04 21:26
 */
public interface VodService {
    String uploadVideoToAly(MultipartFile file);

    void deleteVideoBySourceId(String sourceId);

    void batchDelVideoBySourceId(List videoIds);
}
