package com.rupeng.bookstore.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rupeng.bookstore.entity.User;

@WebServlet("/manager")
public class ManagerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        // 如果没有登录则跳转到登录页面
        if (user == null) {
            request.setAttribute("message", "请先登录");
            request.getRequestDispatcher("/WEB-INF/jsp/userLogin.jsp").forward(request, response);
            return;
        }
        // 如果不是管理员则跳转到权限不足页面
        if (!"manager".equals(user.getRole())) {
            request.getRequestDispatcher("/nopermission.jsp").forward(request, response);
            return;
        }

        // 跳转到后台管理首页面
        request.getRequestDispatcher("/WEB-INF/jsp/manager/managerIndex.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
