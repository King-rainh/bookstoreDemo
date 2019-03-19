package com.rupeng.bookstore.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.rupeng.bookstore.entity.User;
import com.rupeng.bookstore.utils.AjaxResult;
import com.rupeng.bookstore.utils.Utils;

// 权限过滤器
@WebFilter("/*")
public class PermissionFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String action = request.getParameter("action");

        // action以manager开头的请求需要检查权限
        if (action != null && action.startsWith("manager")) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            User user = (User) httpRequest.getSession().getAttribute("user");
            // 如果用户的角色为manager，则允许访问
            if (user != null && "manager".equals(user.getRole())) {
                // 有权限，允许通过
                chain.doFilter(request, response);

            } else {
                // 此时需要判断给用户响应json格式的数据还是nopermission.jsp权限不足页面
                if (action.endsWith("Json")) {
                    Utils.sendAjaxResponse(response, new AjaxResult("error", "权限不足，请联系管理员或者尝试重新登录"));
                } else {
                    request.getRequestDispatcher("/nopermission.jsp").forward(request, response);
                }
            }
        } else {
            // 不需要检查权限，直接通过
            chain.doFilter(request, response);
        }

    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
    }

}
