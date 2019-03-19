package com.rupeng.bookstore.utils;

//表示Ajax请求的一般json结果，js可通过status是否等于success来判断操作是否成功
//data字段表示额外的提示信息
public class AjaxResult {

    private String status;
    private Object data;

    public AjaxResult() {
    }

    public AjaxResult(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
