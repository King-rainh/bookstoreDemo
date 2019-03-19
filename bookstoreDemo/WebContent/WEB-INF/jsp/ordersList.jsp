<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath(); %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单列表</title>
<link type="text/css" rel="stylesheet" href="<%=path%>/css/style.css" />
<script type="text/javascript" src="<%=path%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/tools.js"></script>
</head>
<body>
   <%@include file="/header.jsp"%>

   <div class="main" style="width: 900px;">
        
        <div class="message">
            <p>我的订单</p>
            <span>${message }</span>
        </div>
   
         <table>
            <thead>
                <tr>
                  <td>订单编号</td>
                  <td style="width: 20%">图书名称列表</td>
                  <td>总价格</td>
                  <td style="width: 30%">收货人信息</td>
                  <td>下单时间</td>
                  <td>订单状态</td>
                  <td>操作</td>
               </tr> 
            </thead>
         
            <tbody>
                <c:forEach items="${ordersList }" var="orders">
                       <tr>
                          <td>${orders.id }</td> 
                          <td>
                                <c:forEach items="${orders.ordersItemList }" var="ordersItem">
                                    ${ordersItem.bookName }、
                                </c:forEach>
                          </td> 
                          <td>¥${orders.totalPrice }</td> 
                          <td>${orders.addressInfo }</td> 
                          <td><fmt:formatDate value="${orders.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td> 
                          <td>${orders.status }</td>
                          <td><a href="#" >支付</a><br/><a href="<%=path%>/orders?action=detail&id=${orders.id}" >订单详情</a></td>
                       </tr> 
                </c:forEach>
            </tbody>
            
         </table>
        
   </div>

   <%@include file="/footer.jsp"%>
</body>
</html>