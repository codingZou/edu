package com.foxconn.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(20000, "成功"),
    ERROR(40000, "失败"),
    GET_COURSE_INFO_ERROR(30001, "获取课程信息或简介失败"),
    UPDATE_COURSE_INFO_ERROR(30002, "更新课程信息或简介失败"),
    ;
    private int code;
    private String msg;
}