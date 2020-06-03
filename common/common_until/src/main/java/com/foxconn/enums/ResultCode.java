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
    GET_COURSE_PUBLISH_ERROR(30002, "查询课程发布信息失败"),
    UPDATE_COURSE_INFO_ERROR(40001, "更新课程信息或简介失败"),
    SAVE_CHAPTER_ERROR(40002, "保存章节失败"),
    DELETE_CHAPTER_WARMING(60001, "该章节下还存在小节不能删除"),
    DELETE_COURSE_ERROR(50001, "删除课程失败"),
    ;
    private int code;
    private String msg;
}