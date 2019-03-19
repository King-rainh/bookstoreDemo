package com.rupeng.bookstore.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rupeng.bookstore.entity.Address;
import com.rupeng.bookstore.entity.User;
import com.rupeng.bookstore.service.AddressService;
import com.rupeng.bookstore.utils.Utils;

@WebServlet("/address")
public class AddressServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private AddressService addressService = new AddressService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            processAdd(request, response);

        } else if ("addSubmit".equals(action)) {
            processAddSubmit(request, response);

        } else if ("list".equals(action)) {
            processList(request, response);

        } else if ("update".equals(action)) {
            processUpdate(request, response);

        } else if ("updateSubmit".equals(action)) {
            processUpdateSubmit(request, response);

        } else if ("deleteSubmit".equals(action)) {
            processDeleteSubmit(request, response);

        } else if ("setDefaultSubmit".equals(action)) {
            processSetDefaultSubmit(request, response);

        }
    }

    private void processAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/addressAdd.jsp").forward(request, response);
    }

    private void processSetDefaultSubmit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        int id = Integer.parseInt(request.getParameter("id"));
        addressService.setDefault(id, user.getId());
        request.setAttribute("message", "设置默认收货地址成功");

        // 跳转回addressList.jsp页面前需要先查出来list数据
        processList(request, response);
    }

    private void processDeleteSubmit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        int id = Integer.parseInt(request.getParameter("id"));

        addressService.delete(id, user.getId());

        request.setAttribute("message", "删除成功");

        // 跳转回addressList.jsp页面前需要先查出来list数据
        processList(request, response);

    }

    private void processUpdateSubmit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        int id = Integer.parseInt(request.getParameter("id"));
        String consignee = request.getParameter("consignee");
        String phone = request.getParameter("phone");
        String location = request.getParameter("location");
        String isDefaultStr = request.getParameter("isDefault");

        // 有效性检查

        // 收货人非空检查
        if (Utils.isEmpty(consignee)) {
            request.setAttribute("message", "收货人不能为空");
            // 当数据无效时应该跳转回修改页面，由于是修改页面，页面既需要显示要修改的原数据，又要回显用户修改的数据，这样会造成冲突，所以干脆不在回显用户的操作，而只显示原数据
            processUpdate(request, response);
            return;
        }

        // 手机号非空检查、格式检查
        if (Utils.isEmptyOrNotPhone(phone)) {
            request.setAttribute("message", "请填写正确的手机号");
            processUpdate(request, response);
            return;
        }

        // 收货地址非空检查
        if (Utils.isEmpty(location)) {
            request.setAttribute("message", "收货地址不能为空");
            processUpdate(request, response);
            return;
        }

        boolean isDefault = false;
        if ("yes".equals(isDefaultStr)) {
            isDefault = true;
        }

        // 由于有不需要用户修改的字段，所以最好先从数据库中把数据查询出来，只修改哪些需要修改的字段
        Address address = addressService.findById(id, user.getId());

        address.setConsignee(consignee);
        address.setLocation(location);
        address.setPhone(phone);
        // 如果本来不是默认收货地址，才允许修改
        if (!address.getIsDefault()) {
            address.setIsDefault(isDefault);
        }

        // 执行修改的业务逻辑
        addressService.update(address);

        request.getRequestDispatcher("/WEB-INF/jsp/addressUpdateSuccess.jsp").forward(request, response);

    }

    private void processUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 用户只能修改自己的地址
        User user = (User) request.getSession().getAttribute("user");
        // 要修改的地址的id
        int id = Integer.parseInt(request.getParameter("id"));

        // 查询到要修改的address
        Address address = addressService.findById(id, user.getId());

        request.setAttribute("address", address);
        request.getRequestDispatcher("/WEB-INF/jsp/addressUpdate.jsp").forward(request, response);

    }

    private void processAddSubmit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 检查用户是否登录
        User user = (User) request.getSession().getAttribute("user");

        String consignee = request.getParameter("consignee");
        String phone = request.getParameter("phone");
        String location = request.getParameter("location");
        String isDefault = request.getParameter("isDefault");

        // 有效性检查

        // 收件人非空检查
        if (Utils.isEmpty(consignee)) {
            request.setAttribute("message", "收件人不能为空");
            request.getRequestDispatcher("/WEB-INF/jsp/addressAdd.jsp").forward(request, response);
            return;
        }

        // 手机号非空检查、格式检查
        if (Utils.isEmptyOrNotPhone(phone)) {
            request.setAttribute("message", "请输入正确的手机号");
            request.getRequestDispatcher("/WEB-INF/jsp/addressAdd.jsp").forward(request, response);
            return;
        }

        // 检查详细位置非空
        if (Utils.isEmpty(location)) {
            request.setAttribute("message", "详细位置不能为空");
            request.getRequestDispatcher("/WEB-INF/jsp/addressAdd.jsp").forward(request, response);
            return;
        }

        // 封装实体类对象
        Address address = new Address();
        address.setUserId(user.getId());
        address.setConsignee(consignee);
        address.setPhone(phone);
        address.setLocation(location);
        address.setCreateTime(new Date());
        if ("yes".equals(isDefault)) {
            address.setIsDefault(true);
        }

        // 执行添加
        addressService.add(address);

        request.getRequestDispatcher("/WEB-INF/jsp/addressAddSuccess.jsp").forward(request, response);
    }

    private void processList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取当前登录的用户
        User user = (User) request.getSession().getAttribute("user");

        List<Address> addressList = addressService.list(user.getId());

        request.setAttribute("addressList", addressList);

        request.getRequestDispatcher("/WEB-INF/jsp/addressList.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
