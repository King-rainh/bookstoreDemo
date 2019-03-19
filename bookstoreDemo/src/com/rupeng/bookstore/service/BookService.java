package com.rupeng.bookstore.service;

import java.sql.SQLException;

import com.rupeng.bookstore.dao.BookDao;
import com.rupeng.bookstore.entity.Book;
import com.rupeng.bookstore.utils.Page;
import com.rupeng.bookstore.utils.PageAjaxResult;

public class BookService {

    private BookDao bookDao = new BookDao();

    public void list(Page<Book> page) {
        try {
            bookDao.list(page);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void list(Page<Book> page, Integer categoryId) {

        try {
            bookDao.list(page, categoryId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void list(Page<Book> page, String searchText) {
        try {
            bookDao.list(page, searchText);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Book findById(int id) {
        try {
            return bookDao.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void list(PageAjaxResult pageAjaxResult) {
        try {
            bookDao.list(pageAjaxResult);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void list(PageAjaxResult pageAjaxResult, String searchText) {
        try {
            bookDao.list(pageAjaxResult, searchText);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(Book book) {
        try {
            bookDao.add(book);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteById(int id) {
        try {
            // 伪删除图书信息，因为有很多订单项依赖这些信息，不能真的删除
            bookDao.pseudoDelete(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Book book) {
        try {
            bookDao.update(book);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
