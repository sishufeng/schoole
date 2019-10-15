package com.leien.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/9/25 15:31
 * @Description:
 */
public abstract class BaseEntity{
    //分页大小
    private Integer limit;
    //分页开始
    private Integer page;

    private Integer total;

    //排序类型DESC  or  AES
    private String sort;

    private String orderBy;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        //计算当前页
        this.page = (page-1)*limit;
    }

    //这些个参数返回给前端时都不需要给的哦。所以用了@JSONField(serialize=false) 来搞定
    @JSONField(serialize = false)
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
    //这些个参数返回给前端时都不需要给的哦。所以用了@JSONField(serialize=false) 来搞定
    @JSONField(serialize = false)
    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
    //这些个参数返回给前端时都不需要给的哦。所以用了@JSONField(serialize=false) 来搞定
    @JSONField(serialize = false)
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
