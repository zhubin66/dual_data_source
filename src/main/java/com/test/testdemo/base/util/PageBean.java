package com.test.testdemo.base.util;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Desc 分页工具类
 */
public class PageBean<T> implements Serializable {
    private static final long serialVersionUID = 5229229913348492552L;
    private String select = "";
    private int pageNo = 1;
    private int pageTotal;
    private int rowsCount;
    private int rowsPerPage = 10;
    private List<T> data;
    private String sortOrder = "asc";
    private String sort = "";
    private String entityName = "";
    private StringBuffer conditions = new StringBuffer();
    private String lastSort = "";
    private String query;
    private String countQuery;
    private int rowStart;

    public String getAutoQuery() {
        if (this.isNotNullOrEmpty(this.query)) {
            return this.query;
        } else {
            StringBuilder jpql = new StringBuilder();
            jpql.append(this.select + " from " + this.entityName + " where 1=1 ");
            if (this.isNotNullOrEmpty(this.conditions.toString())) {
                jpql.append(this.conditions.toString());
            }

            if (this.isNotNullOrEmpty(this.sort)) {
                String pattern = "[^+-][a-zA-z_]+[+-]*";
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(this.sort);
                if (m.find()) {
                    jpql.append(" order by " + m.group().replace("+", " asc").replace("-", " desc"));

                    while (m.find()) {
                        jpql.append(", " + m.group().replace("+", " asc").replace("-", " desc"));
                    }
                } else {
                    jpql.append(" order by " + this.sort);
                    if (this.isNotNullOrEmpty(this.sortOrder)) {
                        jpql.append(" " + this.sortOrder);
                    }
                }
            }

            if (this.isNotNullOrEmpty(this.sort)) {
                if (this.isNotNullOrEmpty(this.lastSort) && this.lastSort.trim().length() != 0) {
                    jpql.append("," + this.lastSort);
                }
            } else if (this.isNotNullOrEmpty(this.lastSort) && this.lastSort.trim().length() != 0) {
                jpql.append(" order by " + this.lastSort);
            }

            return jpql.toString();
        }
    }

    public String getAutoCountQuery() {
        if (this.isNotNullOrEmpty(this.countQuery)) {
            return this.countQuery;
        } else {
            String hql = this.getAutoQuery();
            int end = hql.length();
            if (hql.contains("order by")) {
                end = hql.indexOf("order by");
            }

            String queryString;
            int j;
            if (hql.toUpperCase().contains("SELECT")) {
                j = hql.toUpperCase().indexOf("FROM");
                queryString = "select count(1) " + hql.substring(j, end);
            } else {
                queryString = "select count(1) " + hql.substring(0, end);
            }

            j = queryString.toUpperCase().lastIndexOf("ORDER");
            if (j != -1) {
                queryString = queryString.substring(0, j);
            }

            return queryString;
        }
    }

    private boolean isNotNullOrEmpty(String s) {
        return null != s && !s.trim().equals("");
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getEntityName() {
        return this.entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public PageBean() {
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo < 1 ? 1 : pageNo;
    }

    public int getPageTotal() {
        return this.pageTotal = (this.rowsCount - 1) / this.getRowsPerPage() + 1;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getRowsCount() {
        return this.rowsCount;
    }

    public void setRowsCount(int rowsCount) {
        this.rowsCount = rowsCount;
    }

    public int getRowsPerPage() {
        return this.rowsPerPage <= 0 ? 5 : this.rowsPerPage;
    }

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getRowStart() {
        int ret = (this.pageNo - 1) * this.getRowsPerPage();
        return ret < 1 ? 0 : ret;
    }

    private void setRowStart(int rowStart) {
        this.rowStart = rowStart;
    }

    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getCondition() {
        return this.conditions.toString();
    }

    public void setCondition(String condition) {
        this.conditions = new StringBuffer(condition);
    }

    public void addCondition(String condition) {
        this.conditions.append(condition);
    }

    public String getSelect() {
        return this.select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getLastSort() {
        return this.lastSort;
    }

    public void setLastSort(String lastSort) {
        this.lastSort = lastSort;
    }

    public String getQuery() {
        return this.query;
    }

    public String getCountQuery() {
        return this.countQuery;
    }

    public void setCountQuery(String countQuery) {
        this.countQuery = countQuery;
    }
}
