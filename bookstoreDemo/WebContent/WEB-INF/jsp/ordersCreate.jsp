<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>生成订单</title>
<link type="text/css" rel="stylesheet" href="<%=path%>/css/style.css" />
<script type="text/javascript" src="<%=path%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/tools.js"></script>
</head>
<body>
   <%@include file="/header.jsp"%>

   <div class="main" style="width: 900px;">
        
        <div class="message">
            <p>生成订单</p>
            <span>${message }</span>
        </div>
   
         <table>
            <thead>
                <tr>
                  <td>书名</td>
                  <td>单价</td>
                  <td>数量</td>
                  <td>总价</td>
               </tr>
            </thead>
         
            <tbody>
                <c:forEach items="${cart.cartItemList }" var="cartItem">
                    <tr>
                        <td>${cartItem.bookName }</td> <td>¥${cartItem.price }</td> <td>${cartItem.count }</td> <td>¥${cartItem.totalPrice }</td> <td></td>
                    </tr> 
                </c:forEach>
            </tbody>
            
            <tfoot>
               <tr>
                  <td></td>
                  <td></td>
                  <td>合计：${cart.totalCount }</td>
                  <td>合计：¥${cart.totalPrice }</td>
               </tr>
            </tfoot>
         </table>
         
         <div style="margin-top:50px">
            <form action="<%=path%>/orders" method="post">
                <input type="hidden" name="action" value="createSubmit" />
                  <label>选择收货地址</label>
                  <select name="addressId" >
                        <c:forEach items="${addressList }" var="address">
                            <option value="${address.id }">${address }</option>
                        </c:forEach>
                  </select>
                   <c:if test="${empty addressList }">
                        <a href="<%=path%>/address?action=add">还没有收货地址，去添加</a>
                   </c:if>
                   <br/> <br/> <br/>
                   <input type="submit"  value="  确认生成订单  "  />
            </form>
            
            <div style="clear: both;"></div>
        </div>
        
   </div>

   <%@include file="/footer.jsp"%>
</body>
</html>