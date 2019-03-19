package com.rupeng.bookstore.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.rupeng.bookstore.entity.OrdersItem;
import com.rupeng.bookstore.utils.JDBCUtils;

public class OrdersItemDao {

    public void add(Connection conn, OrdersItem ordersItem) throws SQLException {
        String sql = "insert into ordersItem(ordersId,bookId,bookName,price,count) values(?,?,?,?,?)";
        JDBCUtils.executeUpdate(conn, sql, ordersItem.getOrdersId(), ordersItem.getBookId(), ordersItem.getBookName(),
                ordersItem.getPrice(), ordersItem.getCount());

    }

    public List<OrdersItem> list(Connection conn, String ordersId) throws SQLException {
        String sql = "select * from ordersItem where ordersId=?";
        ResultSet rs = null;
        try {
            rs = JDBCUtils.executeQuery(conn, sql, ordersId);
            return JDBCUtils.packEntityList(OrdersItem.class, rs);
        } finally {
            JDBCUtils.closeResultSetAndStatement(rs);
        }
    }

}
