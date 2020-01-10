package com.test.testdemo.repository.secondary;

import com.test.testdemo.entity.secondary.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @Desc 第二个数据源
 */
public interface StudentRepository extends CrudRepository<Student, Integer> {

    Integer deleteAllByIdIn(List<Integer> ids);
}
