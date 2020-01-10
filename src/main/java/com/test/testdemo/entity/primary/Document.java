package com.test.testdemo.entity.primary;

import com.test.testdemo.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 描述：文件上传下载
 */
@Table(
        name = "Document"
)
@Entity
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Document implements BaseEntity {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(columnDefinition = "varchar(100) default ''")
    private String name;//文件名
    @Column(columnDefinition = "varchar(100) default ''")
    private String path;//文件路径
    @Column(columnDefinition = "varchar(20) default ''")
    private String fileType;//文件类型
    @Column(columnDefinition = "decimal(10,0) default 0")
    private BigDecimal fileSize;//文件大小
    @Column(columnDefinition = "int default 0")
    private Integer downloadTimes;//下载次数
    @Column(columnDefinition = "varchar(20) default ''")
    private String creatorName;//创建人
}
