package com.rupeng.bookstore.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.rupeng.bookstore.entity.User;
import com.rupeng.bookstore.utils.JDBCUtils;

public class UserDao {

    // 检查邮件是否存在，如果存在，返回true，否则返回false
    public boolean emailIsNotExist(String email) throws SQLException {
        // 只要可以查询出来数据，就说明此email已经在数据库中了
        String sql = "select * from user where email=?";
        ResultSet rs = null;
        try {
            rs = JDBCUtils.executeQuery(sql, email);
            return !rs.next();
        } finally {
            JDBCUtils.closeAll(rs);
        }
    }

    public int register(User user) throws SQLException {
        String sql = "insert into user(email,password) values(?,?)";
        return JDBCUtils.executeUpdate(sql, user.getEmail(), user.getPassword());
    }

    public User login(String email, String password) throws SQLException {
        String sql = "select * from user where email=? and password=? ";
        ResultSet rs = null;
        try {
            rs = JDBCUtils.executeQuery(sql, email, password);
            return JDBCUtils.packEntity(User.class, rs);
        } finally {
            JDBCUtils.closeAll(rs);
        }
    }

    public int updatePassword(String email, String newpassword) throws SQLException {
        String sql = "update user set password=? where email=?";
        // 切记email对应第二个占位符，在后面
        return JDBCUtils.executeUpdate(sql, newpassword, email);
    }

    public boolean passwordIsNotRight(String email, String password) throws SQLException {
        String sql = "select * from user where email=? and password=?";
        ResultSet rs = null;
        try {
            rs = JDBCUtils.executeQuery(sql, email, password);
            return !rs.next();
        } finally {
            JDBCUtils.closeAll(rs);
        }
    }

}
