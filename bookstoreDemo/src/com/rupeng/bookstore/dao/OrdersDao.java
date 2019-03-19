package com.rupeng.bookstore.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.rupeng.bookstore.entity.Orders;
import com.rupeng.bookstore.utils.JDBCUtils;

public class OrdersDao {
    public void add(Connection conn, Orders orders) throws SQLException {
        String sql = "insert into orders(id,userId,addressInfo,totalPrice,status,createTime) values(?,?,?,?,?,?)";
        JDBCUtils.executeUpdate(conn, sql, orders.getId(), orders.getUserId(), orders.getAddressInfo(),
                orders.getTotalPrice(), orders.getStatus(), orders.getCreateTime());
    }

    // 按照下单时间降序排列
    public List<Orders> list(Connection conn, Integer userId) throws SQLException {
        String sql = "select * from orders where userid=? order by createTime desc";
        ResultSet rs = null;
        try {
            rs = JDBCUtils.executeQuery(conn, sql, userId);
            return JDBCUtils.packEntityList(Orders.class, rs);
        } finally {
            JDBCUtils.closeResultSetAndStatement(rs);
        }
    }

    public Orders findById(Connection conn, String id) throws SQLException {
        String sql = "select * from orders where id=?";
        ResultSet rs = null;
        try {
            rs = JDBCUtils.executeQuery(conn, sql, id);
            return JDBCUtils.packEntity(Orders.class, rs);
        } finally {
            JDBCUtils.closeResultSetAndStatement(rs);
        }

    }

    public int findUnpaidOrdersCount(Integer userId) throws SQLException {
        String sql = "select count(*) from orders where status='未支付' and userId=?";
        ResultSet rs = null;
        try {
            rs = JDBCUtils.executeQuery(sql, userId);
            rs.next();
            return rs.getInt(1);
        } finally {
            JDBCUtils.closeAll(rs);
        }
    }

}
