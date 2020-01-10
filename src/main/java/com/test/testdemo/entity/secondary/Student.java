package com.test.testdemo.entity.secondary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @Desc 学生实体类
 */
@Table(
        name = "Student"
)
@Entity
@Getter
@Setter
@DynamicInsert//新增时空字段不去插入values
@DynamicUpdate//只跟新变化的字段,结合merge方法使用
@JsonInclude(JsonInclude.Include.NON_NULL)
@EntityListeners({AuditingEntityListener.class})
@JsonIgnoreProperties({"modifier", "modifyDate"})
public class Student {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(columnDefinition = "varchar(20) default ''")
    private String name;
    @Column(columnDefinition = "int default 0")
    private Integer age;
}
