package com.test.testdemo.controller;

import com.test.testdemo.entity.secondary.Student;
import com.test.testdemo.response.ResEntity;
import com.test.testdemo.servcie.StudentServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @Desc 学生管理(双数据源测试)
 */
@Api(value = "学生管理")
@RestController
@RequestMapping("api/student")
public class StudentController {
    @Autowired
    StudentServiceImpl studentService;

    /**
     * 新增学生信息
     *
     * @param student
     * @return
     */
    @PostMapping("")
    @ApiOperation(value = "新增学生信息", notes = "新增学生信息", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object create(@RequestBody Student student) {
        return ResEntity.success(studentService.create(student));
    }

    /**
     * 查找一条学生信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "查找一条学生信息", notes = "查找一条学生信息", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object findOne(@PathVariable Integer id) {
        return ResEntity.success(studentService.findOne(id));
    }
}
