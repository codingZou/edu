package com.foxconn.servicebase.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zj
 * @create 2020-05-07 22:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseExceptionHandler extends RuntimeException {
    private Integer code;
    private String msg;

    @Override
    public String toString() {
        return "BaseExceptionHandler{" +
                "message=" + this.getMessage() +
                ", code=" + code +
                '}';
    }
}
