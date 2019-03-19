package com.rupeng.bookstore.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.rupeng.bookstore.utils.AjaxResult;
import com.rupeng.bookstore.utils.Utils;

//异常过滤器，当服务器代码抛出异常时，此过滤器捕获异常，并根据其他条件生成异常响应发送给浏览器
@WebFilter("/*")
public class ExceptionFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            // 此处应该记录错误日期，学习阶段打印到控制台，免得异常被吃掉
            e.printStackTrace();

            // 如果请求参数中的action值以Json结尾，就说明需要json格式的响应，否则跳转到500.jsp页面
            String action = request.getParameter("action");

            if (action != null && action.endsWith("Json")) {
                Utils.sendAjaxResponse(response, new AjaxResult("error", "服务器出错了"));
            } else {
                request.getRequestDispatcher("/500.jsp").forward(request, response);
            }
        }

    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
    }

}
