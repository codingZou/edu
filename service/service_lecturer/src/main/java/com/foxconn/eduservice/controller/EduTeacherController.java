package com.foxconn.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foxconn.eduservice.domain.EduTeacher;
import com.foxconn.eduservice.domain.vo.TeacherQuery;
import com.foxconn.eduservice.service.EduTeacherService;
import com.foxconn.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zj
 * @since 2020-05-03
 */
@RestController
@RequestMapping("/eduservice")
@CrossOrigin
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @GetMapping("/teacher")
    public Result findTeachers() {
        List<EduTeacher> eduTeachers = eduTeacherService.list(null);
        return Result.ok().data("eduTeachers", eduTeachers);
    }

    /**
     * 根据id逻辑删除讲师
     *
     * @param id
     * @return
     */
    @DeleteMapping("/teacher/{id}")
    public Result removeTeacherById(@PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        if (flag) {
            return Result.ok();
        }
        return Result.error();
    }

    @GetMapping("/teachers/{current}/{limit}")
    public Result listTeachers(@PathVariable long current, @PathVariable long limit) {
        Page<EduTeacher> eduTeacherPage = new Page<>(current, limit);
        eduTeacherService.page(eduTeacherPage, null);
        List<EduTeacher> records = eduTeacherPage.getRecords();
        long total = eduTeacherPage.getTotal();
        return Result.ok().data("total", total).data("records", records);

    }

    /**
     * 条件分页查询讲师列表
     *
     * @param teacherQuery 条件对象
     * @param current      当前页
     * @param limit        每页条数
     * @return
     */
    @PostMapping("/conditionteacher/{current}/{limit}")
    public Result conditiontListTeachers(@RequestBody(required = false) TeacherQuery teacherQuery, @PathVariable long current, @PathVariable long limit) {
        Page<EduTeacher> eduTeacherPage = new Page<>(current, limit);
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            queryWrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create", end);
        }
        queryWrapper.orderByDesc("gmt_create");
        eduTeacherService.page(eduTeacherPage, queryWrapper);
        List<EduTeacher> records = eduTeacherPage.getRecords();
        long total = eduTeacherPage.getTotal();
        return Result.ok().data("total", total).data("records", records);
    }

    /**
     * 添加讲师
     *
     * @param eduTeacher 讲师对象
     * @return
     */
    @PostMapping("/teacher")
    public Result saveTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.save(eduTeacher);
        if (flag) {
            return Result.ok();
        }
        return Result.error();
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("/teacher/{id}")
    public Result getTeacherById(@PathVariable String id) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return Result.ok().data("eduTeacher", eduTeacher);
    }

    @PutMapping("/teacher")
    public Result updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag) {
            return Result.ok();
        }
        return Result.error();
    }

}

