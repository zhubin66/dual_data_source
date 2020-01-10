package com.test.testdemo.repository.primary;

import com.test.testdemo.entity.primary.Document;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * 描述：文件管理
 */
public interface DocumentRepository extends CrudRepository<Document, Integer> {

    Integer deleteAllByIdIn(List<Integer> ids);
}
