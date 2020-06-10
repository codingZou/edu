package com.foxconn.eduservice.client;

import com.foxconn.util.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public Result deleteVideoBySourceId(String sourceId) {
        return Result.error().message("删除视频time out");
    }

    @Override
    public Result batchDelVideoBySourceId(List<String> sourceIds) {
        return Result.error().message("批量删除视频time out");
    }
}