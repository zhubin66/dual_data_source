package com.test.testdemo.base.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @Desc
 */
public abstract class BaseCriteria {
    protected Map<String, Object> values = new HashMap();

    public BaseCriteria() {
    }

    public abstract String getCondition();

    public Map<String, Object> getValues() {
        return this.values;
    }

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }
}
