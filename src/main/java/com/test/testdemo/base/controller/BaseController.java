package com.test.testdemo.base.controller;

import com.test.testdemo.util.Helpers;
import com.test.testdemo.base.util.PageBean;
import org.springframework.web.bind.ServletRequestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Desc
 */
public class BaseController {
    @Resource
    private HttpServletRequest request;
    private ThreadLocal<Integer> statusCode = new ThreadLocal();
    private ThreadLocal<String> msg = new ThreadLocal();
    private ThreadLocal<String> callback = new ThreadLocal();
    private ThreadLocal<String> locationUrl = new ThreadLocal();

    public BaseController() {
    }

    protected <S> PageBean<S> getPageBean() {
        PageBean<S> pb = new PageBean();
        int page = ServletRequestUtils.getIntParameter(this.request, "page", 1);
        int rows = ServletRequestUtils.getIntParameter(this.request, "rows", 10);
        String sort = ServletRequestUtils.getStringParameter(this.request, "sort", "");
        String order = ServletRequestUtils.getStringParameter(this.request, "order", "");
        pb.setPageNo(page);
        pb.setRowsPerPage(rows);
        sort = Helpers.filterSQLCondition(sort);
        order = Helpers.filterSQLCondition(order);
        if (this.isNotNullAndEmpty(sort)) {
            pb.setSort(sort);
        }

        if (this.isNotNullAndEmpty(order)) {
            pb.setSortOrder(order);
        }

        return pb;
    }

    protected Map<Object, Object> setJsonPaginationMap(PageBean pb, Object... jsonKeyAndValuePair) {
        Map<Object, Object> jsonMap = new HashMap();
        jsonMap.put("page", pb.getPageNo());
        jsonMap.put("rows", pb.getData());
        jsonMap.put("total", pb.getRowsCount());
        if (this.msg.get() == null) {
            this.msg.set("");
        }

        if (this.statusCode.get() == null) {
            this.statusCode.set(200);
        }

        if (this.callback.get() == null) {
            this.callback.set("");
        }

        if (this.locationUrl.get() == null) {
            this.locationUrl.set("");
        }

        jsonMap.put("msg", this.msg.get());
        jsonMap.put("statusCode", this.statusCode.get());
        jsonMap.put("callback", this.callback.get());
        jsonMap.put("locationUrl", this.locationUrl.get());
        if (jsonKeyAndValuePair.length > 0) {
            for(int i = 0; i < jsonKeyAndValuePair.length / 2; ++i) {
                jsonMap.put(jsonKeyAndValuePair[2 * i], jsonKeyAndValuePair[2 * i + 1]);
            }
        }

        this.clearThreadLocalResponse();
        return jsonMap;
    }

    private boolean isNotNullAndEmpty(Object str) {
        return str != null && !str.equals("");
    }

    private void clearThreadLocalResponse() {
        this.statusCode.remove();
        this.msg.remove();
        this.callback.remove();
        this.locationUrl.remove();
    }
}
