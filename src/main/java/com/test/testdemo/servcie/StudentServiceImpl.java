package com.test.testdemo.servcie;

import com.test.testdemo.entity.secondary.Student;
import com.test.testdemo.repository.secondary.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Desc 学生管理实现层
 */
@Service
public class StudentServiceImpl {
    @Autowired
    private StudentRepository studentRepository;

    /**
     * 新增学生信息
     *
     * @param student
     * @return
     */
    public Student create(Student student) {
        return studentRepository.save(student);
    }

    /**
     * 查找一条
     *
     * @param id
     * @return
     */
    public Student findOne(Integer id) {
        return studentRepository.findOne(id);
    }
}
