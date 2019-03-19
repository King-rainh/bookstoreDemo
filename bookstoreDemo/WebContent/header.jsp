<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="header">
   <div class="operate">
      <ul>
        <c:if test="${not empty sessionScope.user }"><li>您好！${user.email }&nbsp;&nbsp;</li></c:if>
        <li><a href="${pageContext.request.contextPath }/"> 首页 </a></li>
        <li>
            <a target="_blank" href="${pageContext.request.contextPath }/cart?action=list">
               <i class="icon_card"></i>购物车 <span>${cart.cartItemList.size() }</span>
            </a>
         </li>
        <c:if test="${empty sessionScope.user }">
             <li><a target="_blank" href="${pageContext.request.contextPath }/user?action=login"> 登录 </a></li>
             <li><a target="_blank" href="${pageContext.request.contextPath }/user?action=register"> 注册 </a></li>
        </c:if>
        <c:if test="${not empty sessionScope.user }">
            <li>
                <a target="_blank" href="${pageContext.request.contextPath }/orders?action=list">
                   我的订单 <span>${unpaidOrdersCount }</span>
                </a>
            </li>
             <li><a target="_blank" href="${pageContext.request.contextPath }/address?action=list"> 收货地址 </a></li>
             <li><a href="${pageContext.request.contextPath }/user?action=passwordUpdate"> 修改密码 </a></li>
             <li><a href="${pageContext.request.contextPath }/user?action=logout"> 退出 </a></li>
        </c:if>
      </ul>
   </div>
</div>
