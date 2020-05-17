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
    ;
    private int code;
    private String msg;
}