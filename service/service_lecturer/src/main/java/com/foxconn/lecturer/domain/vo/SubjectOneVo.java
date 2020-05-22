package com.foxconn.lecturer.domain.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 一级分类
 *
 * @author zj
 * @create 2020-05-22 20:34
 */
@Data
public class SubjectOneVo {
    private String id;
    private String title;
    private List<SubjectTwoVo> children = new ArrayList<>();
}
