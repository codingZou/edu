package com.foxconn.eduservice.domain.frontvo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zj
 * @create 2020-06-27 11:21
 */
@Data
public class UcenterMemberPay implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nickname;
    private String avatar;
}
