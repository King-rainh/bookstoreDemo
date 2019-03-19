<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>购物车</title>
<link type="text/css" rel="stylesheet" href="<%=path%>/css/style.css" />
<script type="text/javascript" src="<%=path%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/tools.js"></script>
</head>
<body>
   <%@include file="/header.jsp"%>

   <div class="main" style="width: 900px;">
        
        <div class="message">
            <p>我的购物车</p>
            <span>${message }</span>
        </div>
        
        <form action="<%=path%>/orders" method="post">
            <input type="hidden" name="action" value="create" />
            
             <table>
                <thead>
                    <tr>
                      <td>书名</td>
                      <td>单价</td>
                      <td>数量</td>
                      <td>总价</td>
                      <td>删除</td>
                   </tr>
                </thead>
                <tbody>
                    <c:forEach items="${cart.cartItemList }" var="cartItem">
                        <tr>
                            <td><input type="hidden" name="bookIds" value="${cartItem.bookId }"/>${cartItem.bookName }</td> <td>¥${cartItem.price }</td> <td><input type="text" name="counts"  value="${cartItem.count }" /></td> <td>¥${cartItem.totalPrice }</td> <td><a href="<%=path%>/cart?action=deleteSubmit&bookId=${cartItem.bookId}" >删除</a></td>
                        </tr> 
                    </c:forEach>
                </tbody>
                <tfoot>
                   <tr>
                      <td></td>
                      <td></td>
                      <td>合计：${cart.totalCount }</td>
                      <td>合计：¥${cart.totalPrice }</td>
                      <td></td>
                   </tr>
                </tfoot>
             </table>
             <br/><br/><br/>
             <input type="submit"  value="  结算购物车  " />
        </form>
        
   </div>

   <%@include file="/footer.jsp"%>
</body>
</html>