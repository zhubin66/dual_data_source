package com.test.testdemo.criteria;

import com.test.testdemo.base.util.BaseCriteria;
import com.test.testdemo.util.Helpers;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class DocumentCriteria extends BaseCriteria implements Serializable {
    private String name;//文件名
    private String fileType;//文件类型
    private List<Integer> ids;//文件主键

    @Override
    public String getCondition() {
        values.clear(); //清除条件数据
        StringBuilder condition = new StringBuilder();

        if (this.ids != null && this.ids.size() > 0) {
            condition.append(" and id in :ids");
            values.put("ids", this.ids);
        }
        if (Helpers.isNotNullAndEmpty(this.name)) {
            condition.append(" and name like :name");
            values.put("name", "%" + this.name + "%");
        }
        if (Helpers.isNotNullAndEmpty(this.fileType)) {
            condition.append(" and fileType = :fileType");
            values.put("fileType", this.fileType);
        }

        return condition.toString();
    }
}
