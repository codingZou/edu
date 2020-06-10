package com.foxconn.eduservice.client;

import com.foxconn.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * @author zj
 * @create 2020-06-08 22:05
 */
@Component
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
public interface VodClient {

    @DeleteMapping("/vod/video/{sourceId}")
    Result deleteVideoBySourceId(@PathVariable("sourceId") String sourceId);

    @DeleteMapping("/vod/video")
    Result batchDelVideoBySourceId(@RequestParam("sourceId") List<String> sourceIds);
}

