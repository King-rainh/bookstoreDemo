package com.rupeng.bookstore.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.rupeng.bookstore.entity.Category;
import com.rupeng.bookstore.utils.JDBCUtils;
import com.rupeng.bookstore.utils.PageAjaxResult;

public class CategoryDao {

    public List<Category> list() throws SQLException {
        String sql = "select * from category";
        ResultSet rs = null;
        try {
            rs = JDBCUtils.executeQuery(sql);
            return JDBCUtils.packEntityList(Category.class, rs);
        } finally {
            JDBCUtils.closeAll(rs);
        }

    }

    public void list(PageAjaxResult pageAjaxResult) throws SQLException {
        String sql = "select * from category limit ?,?";
        String countSql = "select count(*) from category";

        Connection conn = null;
        ResultSet rs = null;
        ResultSet countRs = null;

        try {
            conn = JDBCUtils.getConnection();
            // 查询目标页数据
            rs = JDBCUtils.executeQuery(conn, sql, (pageAjaxResult.getTargetPage() - 1) * pageAjaxResult.getSize(),
                    pageAjaxResult.getSize());
            pageAjaxResult.setRows(JDBCUtils.packEntityList(Category.class, rs));

            // 查询总数量，注意不是总页数
            countRs = JDBCUtils.executeQuery(conn, countSql);
            countRs.next();
            int count = countRs.getInt(1);
            pageAjaxResult.setTotal(count);

        } finally {
            JDBCUtils.closeResultSetAndStatement(rs);
            JDBCUtils.closeAll(countRs);
        }

    }

    public void list(PageAjaxResult pageAjaxResult, String searchText) throws SQLException {
        String sql = "select * from category where  name like ?   limit ?,?";
        String countSql = "select count(*) from category  where  name like ?";

        Connection conn = null;
        ResultSet rs = null;
        ResultSet countRs = null;

        try {
            conn = JDBCUtils.getConnection();
            // 查询目标页数据
            rs = JDBCUtils.executeQuery(conn, sql, searchText,
                    (pageAjaxResult.getTargetPage() - 1) * pageAjaxResult.getSize(), pageAjaxResult.getSize());
            pageAjaxResult.setRows(JDBCUtils.packEntityList(Category.class, rs));

            // 查询总数量，注意不是总页数
            countRs = JDBCUtils.executeQuery(conn, countSql, searchText);
            countRs.next();
            int count = countRs.getInt(1);
            pageAjaxResult.setTotal(count);

        } finally {
            JDBCUtils.closeResultSetAndStatement(rs);
            JDBCUtils.closeAll(countRs);
        }
    }

    public Category findById(int id) throws SQLException {
        String sql = "select * from category where id=?";
        ResultSet rs = null;
        try {
            rs = JDBCUtils.executeQuery(sql, id);
            return JDBCUtils.packEntity(Category.class, rs);

        } finally {
            JDBCUtils.closeAll(rs);
        }
    }

    public void deleteById(int id) throws SQLException {
        String sql = "delete from category where id=?";
        JDBCUtils.executeUpdate(sql, id);
    }

    public void update(Category category) throws SQLException {
        String sql = "update category set name=? where id=?";
        JDBCUtils.executeUpdate(sql, category.getName(), category.getId());
    }

    public boolean isExist(String name) throws SQLException {
        String sql = "select count(*) from category where name=?";
        ResultSet rs = null;
        try {
            rs = JDBCUtils.executeQuery(sql, name);
            rs.next();
            return rs.getInt(1) > 0;
        } finally {
            JDBCUtils.closeAll(rs);
        }
    }

    public void add(Category category) throws SQLException {
        String sql = "insert into category (name) values(?)";
        JDBCUtils.executeUpdate(sql, category.getName());

    }

}
