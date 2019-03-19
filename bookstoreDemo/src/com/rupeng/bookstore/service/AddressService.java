package com.rupeng.bookstore.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.rupeng.bookstore.dao.AddressDao;
import com.rupeng.bookstore.entity.Address;
import com.rupeng.bookstore.utils.JDBCUtils;

public class AddressService {

    private AddressDao addressDao = new AddressDao();

    public List<Address> list(Integer userId) {
        try {
            return addressDao.list(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(Address address) {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);

            // 如果不是默认收货地址
            if (!address.getIsDefault()) {
                // 如果数据库里没有其他收货地址，本次添加的默认就是收货地址
                int count = addressDao.count(conn, address.getUserId());
                if (count == 0) {
                    address.setIsDefault(true);
                }
            }
            // 如果现在是默认收货地址，需要把其他默认的收货地址设置非默认的
            if (address.getIsDefault()) {
                // 先取消现有的默认收货地址
                addressDao.cancelExistingDefault(conn, address.getUserId());
            }

            // 执行添加
            addressDao.add(conn, address);

            // 提交事务
            conn.commit();

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                // 应该记录日志的
            }
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(conn);
        }
    }

    public Address findById(Integer id, Integer userId) {
        try {
            return addressDao.findById(id, userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Address address) {
        // 业务逻辑是这样的
        // 如果此时的isDefault=true，有两种情况，1是此地址原本就为true，2是用户修改成了true。对于第二种情况，就需要先取消其他地址的默认收货地址了

        Connection conn = null;

        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);

            if (address.getIsDefault()) {
                // 可以统一的先把其他收货地址都设置非默认的
                addressDao.cancelExistingDefault(conn, address.getUserId());
            }
            addressDao.update(conn, address);
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
            }
            throw new RuntimeException(e);
        } finally {
            try {
                conn.rollback();
            } catch (Exception e) {
            }
            JDBCUtils.close(conn);
        }

    }

    public void delete(Integer id, Integer userId) {
        Connection conn = null;
        // 业务逻辑是这样的：如果删除的是默认收货地址，如果此时还有其他的地址，则最新添加的收货地址被设置为默认地址
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);

            // 删除之前先查询出来，看看是不是默认收货地址
            Address address = addressDao.findById(id, userId);
            // 执行删除
            addressDao.delete(conn, id, userId);

            // 判断是否是默认收货地址，如果是，则进一步处理
            if (address.getIsDefault()) {
                // 查询出来最新添加的收货地址，然后设置为默认收货地址
                Address latestAddress = addressDao.findLatest(conn, userId);
                if (latestAddress != null) {
                    latestAddress.setIsDefault(true);
                    addressDao.update(conn, latestAddress);
                }
            }
            conn.commit();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
            }
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(conn);
        }

    }

    public void setDefault(Integer id, Integer userId) {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);
            // 取消现有的默认地址
            addressDao.cancelExistingDefault(conn, userId);
            // 设置默认地址
            addressDao.setDefault(conn, id, userId);

            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
            }
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(conn);
        }

    }
}
