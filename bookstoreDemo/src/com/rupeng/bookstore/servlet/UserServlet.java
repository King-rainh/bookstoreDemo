package com.rupeng.bookstore.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rupeng.bookstore.entity.User;
import com.rupeng.bookstore.service.OrdersService;
import com.rupeng.bookstore.service.UserService;
import com.rupeng.bookstore.utils.AjaxResult;
import com.rupeng.bookstore.utils.Utils;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserService userService = new UserService();
    private OrdersService ordersService = new OrdersService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("register".equals(action)) {
            processRegister(request, response);

        } else if ("registerSubmit".equals(action)) {
            processRegisterSubmit(request, response);

        } else if ("login".equals(action)) {
            processLogin(request, response);

        } else if ("loginSubmit".equals(action)) {
            processLoginSubmit(request, response);

        } else if ("logout".equals(action)) {
            processLogout(request, response);

        } else if ("sendCheckCodeJson".equals(action)) {
            processSendCheckCodeJson(request, response);

        } else if ("passwordRetrieve".equals(action)) {
            processPasswordRetrieve(request, response);

        } else if ("passwordRetrieveSubmit".equals(action)) {
            processPasswordRetrieveSubmit(request, response);

        } else if ("passwordUpdate".equals(action)) {
            processPasswordUpdate(request, response);

        } else if ("passwordUpdateSubmit".equals(action)) {
            processPasswordUpdateSubmit(request, response);

        } else if ("managerPasswordUpdate".equals(action)) {
            processManagerPasswordUpdate(request, response);

        } else if ("managerPasswordUpdateJson".equals(action)) {
            processManagerPasswordUpdateJson(request, response);

        }

    }

    private void processManagerPasswordUpdateJson(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        String password = request.getParameter("password");
        String newpassword = request.getParameter("newpassword");
        String renewpassword = request.getParameter("renewpassword");

        if (Utils.isEmptyOrNotLengthEnough(password, 6)) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "原密码至少6位"));
            return;
        }
        if (userService.passwordIsNotRight(user.getEmail(), Utils.md5(password))) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "原密码不正确"));
            return;
        }
        if (Utils.isEmptyOrNotLengthEnough(newpassword, 6)) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "新密码至少6位"));
            return;
        }
        if (!newpassword.equals(renewpassword)) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "两次输入的新密码不一致"));
            return;
        }

        userService.updatePassword(user.getEmail(), Utils.md5(newpassword));

        Utils.sendAjaxResponse(response, new AjaxResult("success", "修改密码成功"));
    }

    private void processManagerPasswordUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/manager/userPasswordUpdate.jsp").forward(request, response);

    }

    private void processPasswordUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/userPasswordUpdate.jsp").forward(request, response);

    }

    private void processPasswordRetrieve(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/userPasswordRetrieve.jsp").forward(request, response);

    }

    private void processRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/userRegister.jsp").forward(request, response);

    }

    private void processLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/userLogin.jsp").forward(request, response);

    }

    private void processPasswordUpdateSubmit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");
        // 原密码
        String password = request.getParameter("password");
        // 新密码
        String newpassword = request.getParameter("newpassword");

        // 原密码长度检查
        if (Utils.isEmptyOrNotLengthEnough(password, 6)) {
            request.setAttribute("message", "原密码长度至少6位");
            request.getRequestDispatcher("/WEB-INF/jsp/userPasswordUpdate.jsp").forward(request, response);
            return;
        }

        // 原密码正确性检查--需要查询数据库
        if (userService.passwordIsNotRight(user.getEmail(), Utils.md5(password))) {
            request.setAttribute("message", "原密码不正确，请重新输入");
            request.getRequestDispatcher("/WEB-INF/jsp/userPasswordUpdate.jsp").forward(request, response);
            return;
        }

        // 新密码非空检查
        if (Utils.isEmptyOrNotLengthEnough(newpassword, 6)) {
            request.setAttribute("message", "新密码长度不能小于6位");
            request.getRequestDispatcher("/WEB-INF/jsp/userPasswordUpdate.jsp").forward(request, response);
            return;
        }

        // 执行修改密码的业务逻辑
        userService.updatePassword(user.getEmail(), Utils.md5(newpassword));

        request.getRequestDispatcher("/WEB-INF/jsp/userPasswordUpdateSuccess.jsp").forward(request, response);

    }

    private void processSendCheckCodeJson(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        // 检查非空、格式
        if (Utils.isEmptyOrNotEmail(email)) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "请输入正确的邮箱地址"));
            return;
        }

        // 检查此邮箱是否已经注册
        if (userService.emailIsNotExist(email)) {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "此邮箱没有被注册"));
            return;
        }

        // 尝试发送验证码邮件
        boolean result = Utils.sendMailCode4PasswordRetrieve(request.getSession(), email);
        // 如果发送成功
        if (result) {
            Utils.sendAjaxResponse(response, new AjaxResult("success", "发送验证码邮件成功"));
        } else {
            Utils.sendAjaxResponse(response, new AjaxResult("error", "发送验证码邮件失败"));
        }

    }

    private void processPasswordRetrieveSubmit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 获取表单数据
        String checkCode = request.getParameter("checkCode");
        String email = request.getParameter("email");
        String newpassword = request.getParameter("newpassword");

        // 检查邮箱非空、格式
        if (Utils.isEmptyOrNotEmail(email)) {
            request.setAttribute("message", "请输入正确的邮箱地址");
            request.getRequestDispatcher("/WEB-INF/jsp/userPasswordRetrieve.jsp").forward(request, response);
            return;
        }

        // 检查此邮箱是否注册了
        if (userService.emailIsNotExist(email)) {
            request.setAttribute("message", "此邮箱没有被注册");
            request.getRequestDispatcher("/WEB-INF/jsp/userPasswordRetrieve.jsp").forward(request, response);
            return;
        }

        // 检查密码长度
        if (Utils.isEmptyOrNotLengthEnough(newpassword, 6)) {
            request.setAttribute("message", "密码密码至少6位");
            request.getRequestDispatcher("/WEB-INF/jsp/userPasswordRetrieve.jsp").forward(request, response);
            return;
        }

        // 检查邮箱验证码是否和session中的一致
        if (Utils.isNotMatchMailCode(checkCode, request.getSession())) {
            request.setAttribute("message", "验证码错误，请重新点击\"获取验证码\"按钮获取新的验证码");
            request.getRequestDispatcher("/WEB-INF/jsp/userPasswordRetrieve.jsp").forward(request, response);
            return;
        }

        // 执行修改密码操作
        userService.updatePassword(email, Utils.md5(newpassword));

        request.getRequestDispatcher("/WEB-INF/jsp/userPasswordRetrieveSuccess.jsp").forward(request, response);

    }

    private void processLogout(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String isManagerLogout = request.getParameter("isManagerLogout");
        // 退出时销毁session
        request.getSession().invalidate();

        if ("yes".equals(isManagerLogout)) {
            Utils.sendAjaxResponse(response, new AjaxResult("success", "退出成功"));
        } else {
            // 退出后跳转到登录页面
            processLogin(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    // 处理登录逻辑
    private void processLoginSubmit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取表单数据
        String imageCode = request.getParameter("imageCode");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");// 如果选中，值为yes

        // 有效性验证
        // 验证验证码
        if (Utils.isNotMatchImageCode(imageCode, request.getSession())) {
            request.setAttribute("message", "验证码错误，请重新填写");
            request.getRequestDispatcher("/WEB-INF/jsp/userLogin.jsp").forward(request, response);
            return;
        }
        if (Utils.isEmptyOrNotEmail(email)) {
            request.setAttribute("message", "请输入正确的邮箱格式");
            request.getRequestDispatcher("/WEB-INF/jsp/userLogin.jsp").forward(request, response);
            return;
        }
        if (Utils.isEmptyOrNotLengthEnough(password, 6)) {
            request.setAttribute("message", "密码至少6位");
            request.getRequestDispatcher("/WEB-INF/jsp/userLogin.jsp").forward(request, response);
            return;
        }

        // 对密码进行md5加密
        password = Utils.md5(password);

        // 执行业务逻辑
        User user = userService.login(email, password);

        // 页面导航

        // 如果登录失败，跳转回登录页面
        if (user == null) {
            request.setAttribute("message", "账号或者密码错误，请重新登录");

            request.getRequestDispatcher("/WEB-INF/jsp/userLogin.jsp").forward(request, response);
            return;
        }

        // 如果登录成功
        request.getSession().setAttribute("user", user);

        // 处理cookie
        int maxAge = 0;// 单位秒，实际效果是删除已经保存在客户端的cookie
        // 如果选中记住我复选框则保存cookie30天，否则删除已经保存在客户端的cookie
        if ("yes".equals(rememberMe)) {
            maxAge = 60 * 60 * 24 * 30;// 30天
        }
        Cookie emailCookie = new Cookie("email", email);
        emailCookie.setMaxAge(maxAge);
        Cookie passwordCookie = new Cookie("password", request.getParameter("password"));// 存入加密前的cookie
        passwordCookie.setMaxAge(maxAge);
        Cookie rememberMeCookie = new Cookie("rememberMe", "yes");// 存入加密前的cookie
        rememberMeCookie.setMaxAge(maxAge);

        response.addCookie(emailCookie);
        response.addCookie(passwordCookie);
        response.addCookie(rememberMeCookie);

        // 如果用户是管理员角色，就跳转到管理员主页面
        if ("manager".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/manager");
            return;
        }

        // 查询用户的未支付的订单的数量，显示在header区域
        int unpaidOrdersCount = ordersService.findUnpaidOrdersCount(user.getId());
        if (unpaidOrdersCount > 0) {
            request.getSession().setAttribute("unpaidOrdersCount", unpaidOrdersCount);
        }

        // 处理登录成功的页面跳转逻辑
        // 由于现在只做了登录功能，所以默认跳转到首页，为了让浏览器地址栏不显示/user，使用重定向
        response.sendRedirect(request.getContextPath());
    }

    // 处理注册请求
    private void processRegisterSubmit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 获取表单数据
        String imageCode = request.getParameter("imageCode");// 验证码
        String email = request.getParameter("email");// 邮箱
        String password = request.getParameter("password");// 密码

        // 有效性验证

        // 验证验证码
        if (Utils.isNotMatchImageCode(imageCode, request.getSession())) {
            request.setAttribute("message", "验证码错误，请重新填写");
            request.getRequestDispatcher("/WEB-INF/jsp/userRegister.jsp").forward(request, response);
            return;
        }

        // 验证邮箱（非空检查、邮箱格式检查、邮箱唯一性检查）
        if (Utils.isEmptyOrNotEmail(email)) {
            request.setAttribute("message", "请输入正确的邮箱格式");
            request.getRequestDispatcher("/WEB-INF/jsp/userRegister.jsp").forward(request, response);
            return;
        } else if (!userService.emailIsNotExist(email)) {
            request.setAttribute("message", "您填写的邮箱已被注册");
            request.getRequestDispatcher("/WEB-INF/jsp/userRegister.jsp").forward(request, response);
            return;
        }

        // 验证密码（非空、长度至少为6）
        if (Utils.isEmptyOrNotLengthEnough(password, 6)) {
            request.setAttribute("message", "密码至少6位");
            request.getRequestDispatcher("/WEB-INF/jsp/userRegister.jsp").forward(request, response);
            return;
        }

        // 有效性验证结束

        // 封装实体类
        User user = new User();
        user.setEmail(email);
        user.setPassword(Utils.md5(password));

        // 调用service对象执行业务逻辑：注册
        userService.register(user);

        request.getRequestDispatcher("/WEB-INF/jsp/userRegisterSuccess.jsp").forward(request, response);
    }
}
