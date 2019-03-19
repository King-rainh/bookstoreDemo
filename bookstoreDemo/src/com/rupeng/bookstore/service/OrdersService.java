package com.rupeng.bookstore.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.rupeng.bookstore.dao.OrdersDao;
import com.rupeng.bookstore.dao.OrdersItemDao;
import com.rupeng.bookstore.entity.Orders;
import com.rupeng.bookstore.entity.OrdersItem;
import com.rupeng.bookstore.utils.JDBCUtils;

public class OrdersService {

    private OrdersDao ordersDao = new OrdersDao();
    private OrdersItemDao ordersItemDao = new OrdersItemDao();

    public void add(Orders orders) {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);
            // 保存订单信息
            ordersDao.add(conn, orders);
            // 保存订单项信息
            List<OrdersItem> ordersItemList = orders.getOrdersItemList();
            for (OrdersItem ordersItem : ordersItemList) {
                ordersItemDao.add(conn, ordersItem);
            }

            conn.commit();
        } catch (Exception e) {
            JDBCUtils.rollback(conn);
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(conn);
        }

    }

    public List<Orders> list(Integer userId) {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            List<Orders> ordersList = ordersDao.list(conn, userId);
            for (Orders orders : ordersList) {
                List<OrdersItem> ordersItemList = ordersItemDao.list(conn, orders.getId());
                orders.setOrdersItemList(ordersItemList);
            }

            return ordersList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(conn);
        }

    }

    public Orders findById(String id) {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Orders orders = ordersDao.findById(conn, id);
            orders.setOrdersItemList(ordersItemDao.list(conn, id));
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(conn);
        }

    }

    public int findUnpaidOrdersCount(Integer userId) {
        try {
            return ordersDao.findUnpaidOrdersCount(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
