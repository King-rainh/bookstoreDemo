package com.rupeng.bookstore.service;

import java.sql.SQLException;
import java.util.List;

import com.rupeng.bookstore.dao.BookDao;
import com.rupeng.bookstore.dao.CategoryDao;
import com.rupeng.bookstore.entity.Category;
import com.rupeng.bookstore.utils.PageAjaxResult;

public class CategoryService {

    private CategoryDao categoryDao = new CategoryDao();
    private BookDao bookDao = new BookDao();

    public List<Category> list() {
        try {
            return categoryDao.list();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void list(PageAjaxResult pageAjaxResult) {
        try {
            categoryDao.list(pageAjaxResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void list(PageAjaxResult pageAjaxResult, String searchText) {
        try {
            categoryDao.list(pageAjaxResult, searchText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Category findById(int id) {
        try {
            return categoryDao.findById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public boolean deleteById(int id) {
        try {
            // 先判断存不存在依赖此类别的图书，如果存在，则禁止删除（即使删除也会抛出异常，因为有外键关联）
            if (bookDao.isNotExistByCategoryId(id)) {
                categoryDao.deleteById(id);
                return true;
            }
            return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void update(Category category) {
        try {
            categoryDao.update(category);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public boolean isExist(String name) {
        try {
            return categoryDao.isExist(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void add(Category category) {
        try {
            categoryDao.add(category);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
