package com.rupeng.bookstore.entity;

import java.util.Date;
import java.util.List;

//订单类
public class Orders {
    private String id;// 订单编号，格式为：时间戳（13位）+序列号（3位），具体生成规则见工具类
    private int userId;// 下单的用户的用户编号
    private String addressInfo;// 收货地址信息，格式：收货人，联系电话，详细地址
    private double totalPrice;// 订单总价格
    private String status;// 订单状态，简单起见，只有未支付和已支付两种状态
    private Date createTime;// 下单时间

    private List<OrdersItem> ordersItemList;// 此字段和数据库无关，只是为了方便的把一个订单和它的多个订单项结合起来，方便使用

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrdersItem> getOrdersItemList() {
        return ordersItemList;
    }

    public void setOrdersItemList(List<OrdersItem> ordersItemList) {
        this.ordersItemList = ordersItemList;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Orders other = (Orders) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
