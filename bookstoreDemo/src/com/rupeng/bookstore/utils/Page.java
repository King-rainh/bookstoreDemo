package com.rupeng.bookstore.utils;

import java.util.List;

//用来实现分页功能
public class Page<T> {

    private int size;// 每页的数量
    private int targetPage;// 需要显示的目标页，从1开始
    private int totalPage;// 总页数

    private List<T> items;// 从数据库中查询出来的要显示在目标页上的数据

    public Page() {
    }

    public Page(int size, int targetPage) {
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

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

}
