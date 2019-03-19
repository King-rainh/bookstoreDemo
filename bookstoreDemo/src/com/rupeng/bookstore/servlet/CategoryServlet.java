package com.rupeng.bookstore.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rupeng.bookstore.entity.Category;
import com.rupeng.bookstore.service.CategoryService;
import com.rupeng.bookstore.utils.AjaxResult;
import com.rupeng.bookstore.utils.PageAjaxResult;
import com.rupeng.bookstore.utils.Utils;

@WebServlet("/category")
public class CategoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("listJson".equals(action)) {
            processListJson(request, response);

        } else if ("managerListJson".equals(action)) {
            processManagerListJson(request, response);

        } else if ("managerList".equals(action)) {
            processManagerList(request, response);

        } else if ("managerUpdate".equals(action)) {
            processManagerUpdate(request, response);

        } else if ("managerUpdateJson".equals(action)) {
            processManagerUpdateJson(request, response);

        } else if ("managerDeleteJson".equals(action)) {
            processManagerDeleteJson(request, response);

        } else if ("managerAdd".equals(action)) {
            processManagerAdd(request, response);

        } else if ("managerAddJson".equals(action)) {
            processManagerAddJson(request, response);
        }
    }

    private void processManagerAddJson(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        if (Utils.isEmpty(name)) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "图书分类名称不能为空"));
            return;
        }
        if (categoryService.isExist(name)) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "此图书分类名称已经存在"));
            return;
        }
        Category category = new Category();
        category.setName(name);

        categoryService.add(category);

        Utils.sendAjaxResponse(response, new AjaxResult("success", "添加成功"));

    }

    private void processManagerAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/manager/categoryAdd.jsp").forward(request, response);

    }

    private void processManagerUpdateJson(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        // 数据有效性检查
        if (Utils.isEmpty(name)) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "图书分类名称不能为空"));
            return;
        }
        // 封装实体类
        Category category = new Category();
        category.setId(id);
        category.setName(name);

        // 执行业务逻辑
        categoryService.update(category);
        // 生成json响应
        Utils.sendAjaxResponse(response, new AjaxResult("success", "修改图书分类成功"));
    }

    private void processManagerDeleteJson(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));

        boolean result = categoryService.deleteById(id);

        if (result) {
            Utils.sendAjaxResponse(response, new AjaxResult("success", "删除成功"));
        } else {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "删除失败，还有图书属于此类别"));
        }

    }

    private void processManagerUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Category category = categoryService.findById(id);
        request.setAttribute("category", category);
        request.getRequestDispatcher("/WEB-INF/jsp/manager/categoryUpdate.jsp").forward(request, response);

    }

    private void processManagerList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/manager/categoryList.jsp").forward(request, response);

    }

    private void processManagerListJson(HttpServletRequest request, HttpServletResponse response) {
        int page = 1;// 相当于targetPage目标页
        int rows = 10;// 每页多少条数据
        String searchText = request.getParameter("searchText");
        // 参数中有则转换，没有则使用默认的
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) {
        }
        try {
            rows = Integer.parseInt(request.getParameter("rows"));
        } catch (Exception e) {
        }

        PageAjaxResult pageAjaxResult = new PageAjaxResult(rows, page);
        if (Utils.isEmpty(searchText)) {
            categoryService.list(pageAjaxResult);
        } else {
            categoryService.list(pageAjaxResult, "%" + searchText + "%");
        }
        // 发送json响应
        Utils.sendAjaxResponse(response, pageAjaxResult);

    }

    private void processListJson(HttpServletRequest request, HttpServletResponse response) {
        List<Category> categoryList = categoryService.list();
        Utils.sendAjaxResponse(response, categoryList);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
