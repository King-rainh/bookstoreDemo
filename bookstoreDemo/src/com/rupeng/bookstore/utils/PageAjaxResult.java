package com.rupeng.bookstore.utils;

import java.util.List;

//表示分页Ajax请求的json结果
public class PageAjaxResult {

    private int total;// 总数量，并不是总页数
    private List rows;// 本页数据

    private int size;// 每页数量
    private int targetPage;// 目标页

    public PageAjaxResult() {
    }

    public PageAjaxResult(int size, int targetPage) {
        super();
        this.size = size;
        this.targetPage = targetPage;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTargetPage() {
        return targetPage;
    }

    public void setTargetPage(int targetPage) {
        this.targetPage = targetPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

}
