package com.rupeng.bookstore.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rupeng.bookstore.entity.Address;
import com.rupeng.bookstore.entity.Cart;
import com.rupeng.bookstore.entity.CartItem;
import com.rupeng.bookstore.entity.Orders;
import com.rupeng.bookstore.entity.OrdersItem;
import com.rupeng.bookstore.entity.User;
import com.rupeng.bookstore.service.AddressService;
import com.rupeng.bookstore.service.OrdersService;
import com.rupeng.bookstore.utils.Utils;

@WebServlet("/orders")
public class OrdersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private OrdersService ordersService = new OrdersService();
    private AddressService addressService = new AddressService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            processCreate(request, response);

        } else if ("createSubmit".equals(action)) {
            processCreateSubmit(request, response);

        } else if ("list".equals(action)) {
            processList(request, response);

        } else if ("detail".equals(action)) {
            processDetail(request, response);

        } else if ("pay".equals(action)) {
            processPay(request, response);

        }
    }

    private void processCreate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cart cart = (Cart) request.getSession().getAttribute("cart");

        if (cart == null || Utils.isEmpty(cart.getCartItemList())) {
            request.setAttribute("message", "购物车中空空如也");
            request.getRequestDispatcher("/WEB-INF/jsp/cartList.jsp").forward(request, response);
            return;
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("message", "需要先登录");
            request.getRequestDispatcher("/WEB-INF/jsp/userLogin.jsp").forward(request, response);
            return;
        }

        // 用户可能修改购买数量
        String[] bookIdStrs = request.getParameterValues("bookIds");
        String[] countStrs = request.getParameterValues("counts");

        // 数据有效性检查
        int[] bookIds = new int[bookIdStrs.length];
        int[] counts = new int[bookIdStrs.length];

        for (int i = 0; i < bookIds.length; i++) {
            bookIds[i] = Integer.parseInt(bookIdStrs[i]);

            int count = 0;
            try {
                count = Integer.parseInt(countStrs[i]);
            } catch (Exception e) {
                request.setAttribute("message", "请输入正确的购买数量");
                request.getRequestDispatcher("/WEB-INF/jsp/cartList.jsp").forward(request, response);
                return;
            }

            // 由于用户只会修改数量，所以只对数量进行有效性检查
            if (count < 1) {
                request.setAttribute("message", "购买数量不能小于1");
                request.getRequestDispatcher("/WEB-INF/jsp/cartList.jsp").forward(request, response);
                return;
            }
            counts[i] = count;
        }

        // 重新调整购物车中商品数量
        for (int i = 0; i < bookIds.length; i++) {
            cart.updateItem(bookIds[i], counts[i]);
        }

        // 需要让用户在订单生成页面选择收货地址
        List<Address> addressList = addressService.list(user.getId());
        request.setAttribute("addressList", addressList);

        // 跳转到订单生成（确认）页面
        request.getRequestDispatcher("/WEB-INF/jsp/ordersCreate.jsp").forward(request, response);

    }

    private void processPay(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/ordersPay.jsp").forward(request, response);
    }

    private void processDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        Orders orders = ordersService.findById(id);

        request.setAttribute("orders", orders);

        request.getRequestDispatcher("/WEB-INF/jsp/ordersDetail.jsp").forward(request, response);
    }

    private void processList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        // 只查询订单信息，也需要查询订单项信息
        List<Orders> ordersList = ordersService.list(user.getId());

        request.setAttribute("ordersList", ordersList);

        request.getRequestDispatcher("/WEB-INF/jsp/ordersList.jsp").forward(request, response);
    }

    private void processCreateSubmit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        Cart cart = (Cart) request.getSession().getAttribute("cart");

        // 由于用户在确认生成订单前可以通过再打开一个购物车页面修改购物车，所以最好还是再进行一次非空检查
        if (Utils.isEmpty(cart.getCartItemList())) {
            request.setAttribute("message", "购物车中空空如也");
            request.getRequestDispatcher("/WEB-INF/jsp/cartList.jsp").forward(request, response);
            return;
        }

        // 收货地址
        int addressId = 0;
        try {
            addressId = Integer.parseInt(request.getParameter("addressId"));
        } catch (Exception e) {
            request.setAttribute("message", "您还没有添加收货地址，请先添加收货地址");
            request.getRequestDispatcher("/WEB-INF/jsp/ordersCreate.jsp").forward(request, response);
            return;
        }

        Address address = addressService.findById(addressId, user.getId());

        // 封装实体类
        Orders orders = new Orders();
        orders.setId(Utils.getNewOrdersId());
        orders.setUserId(user.getId());
        orders.setAddressInfo(address.toString());
        orders.setStatus("未支付");
        orders.setTotalPrice(cart.getTotalPrice());
        orders.setCreateTime(new Date());

        List<OrdersItem> ordersItemList = new ArrayList<OrdersItem>();
        List<CartItem> cartItemList = cart.getCartItemList();

        for (CartItem cartItem : cartItemList) {
            OrdersItem ordersItem = new OrdersItem();
            ordersItem.setBookId(cartItem.getBookId());
            ordersItem.setBookName(cartItem.getBookName());
            ordersItem.setCount(cartItem.getCount());
            ordersItem.setOrdersId(orders.getId());
            ordersItem.setPrice(cartItem.getPrice());

            ordersItemList.add(ordersItem);
        }

        orders.setOrdersItemList(ordersItemList);

        // 执行业务逻辑
        ordersService.add(orders);

        // 查询用户的未支付的订单的数量，显示在header区域
        int unpaidOrdersCount = ordersService.findUnpaidOrdersCount(user.getId());
        if (unpaidOrdersCount > 0) {
            request.getSession().setAttribute("unpaidOrdersCount", unpaidOrdersCount);
        }

        // 生成订单成功，清空购物车，跳转到支付页面
        request.getSession().setAttribute("cart", null);
        request.getRequestDispatcher("/WEB-INF/jsp/ordersCreateSuccess.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
