package com.foxconn.msm.service;

import java.util.Map;

/**
 * @author foxconn
 * @create 2020-06-16 20:55
 */
public interface MsmService {
    boolean sendMsg(String phoneNumber, Map<String, Object> map);
}
