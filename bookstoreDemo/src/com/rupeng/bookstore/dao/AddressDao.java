package com.rupeng.bookstore.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.rupeng.bookstore.entity.Address;
import com.rupeng.bookstore.utils.JDBCUtils;

public class AddressDao {

    public List<Address> list(Integer userId) throws SQLException {
        String sql = "select * from address where userId=? order by isDefault desc, createTime desc";
		
        ResultSet rs = null;
        try {
            rs = JDBCUtils.executeQuery(sql, userId);

            return JDBCUtils.packEntityList(Address.class, rs);
        } finally {
            JDBCUtils.closeAll(rs);
        }
    }

    public int count(Connection conn, Integer userId) throws SQLException {
        String sql = "select count(*) from address where userId=?";
        ResultSet rs = null;
        try {
            rs = JDBCUtils.executeQuery(conn, sql, userId);
            rs.next();
            return rs.getInt(1);
        } finally {
            JDBCUtils.closeResultSetAndStatement(rs);
        }
    }

    public int cancelExistingDefault(Connection conn, int userId) throws SQLException {
        String sql = "update address set isDefault=false where userid=? and isDefault=true";
        return JDBCUtils.executeUpdate(conn, sql, userId);
    }

    public int add(Connection conn, Address address) throws SQLException {
        String sql = "insert into address(userId,consignee,phone,location,createTime,isDefault) values(?,?,?,?,?,?)";
        return JDBCUtils.executeUpdate(conn, sql, address.getUserId(), address.getConsignee(), address.getPhone(),
                address.getLocation(), address.getCreateTime(), address.getIsDefault());
    }

    public Address findById(int id, Integer userId) throws SQLException {
        String sql = "select * from address where id=? and userId=?";
        ResultSet rs = null;

        try {
            rs = JDBCUtils.executeQuery(sql, id, userId);

            return JDBCUtils.packEntity(Address.class, rs);
        } finally {
            JDBCUtils.closeAll(rs);
        }
    }

    public int update(Connection conn, Address address) throws SQLException {
        String sql = "update address set consignee=? , phone=?, location=? ,isDefault=? where id=? and userId=?";
        return JDBCUtils.executeUpdate(conn, sql, address.getConsignee(), address.getPhone(), address.getLocation(),
                address.getIsDefault(), address.getId(), address.getUserId());
    }

    public int delete(Connection conn, Integer id, Integer userId) throws SQLException {
        String sql = "delete from address where id=? and userId=?";
        return JDBCUtils.executeUpdate(conn, sql, id, userId);

    }

    public Address findLatest(Connection conn, Integer userId) throws SQLException {
        String sql = "select * from address where userId=? order by createTime desc";
        ResultSet rs = null;
        try {
            rs = JDBCUtils.executeQuery(conn, sql, userId);
            return JDBCUtils.packEntity(Address.class, rs);
        } finally {
            JDBCUtils.closeResultSetAndStatement(rs);
        }
    }

    public int setDefault(Connection conn, Integer id, Integer userId) throws SQLException {
        String sql = "update address set isDefault=true where id=? and userId = ?";
        return JDBCUtils.executeUpdate(conn, sql, id, userId);
    }

}
