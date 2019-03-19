<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>收货地址管理</title>
<link type="text/css" rel="stylesheet" href="<%=path%>/css/style.css" />
<script type="text/javascript" src="<%=path%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/tools.js"></script>
</head>
<body>
    <%@include file="/header.jsp"%>
    <div class="main" style="width: 777px;">

        <div class="message">
            <p>收货地址</p>
            <span>${message }</span>
        </div>
        
         <div style="margin-bottom: 40px;">
            <a href="<%=path%>/address?action=add" target="_self">添加新收货地址</a>            
        </div>
        <table>
            <thead>
                <tr>
                  <td>收货人</td>
                  <td>联系电话</td>
                  <td>详细位置</td>
                  <td>添加时间</td>
                  <td>操作</td>
               </tr>
            </thead>
         
            <tbody>
                <c:forEach items="${requestScope.addressList }" var="address">
                    <tr 
                        <c:if test="${address.isDefault }">class="address-default"</c:if>
                    >
                        <td>${address.consignee }</td>
                        <td>${address.phone }</td>
                        <td>${address.location }</td> 
                        <td><fmt:formatDate value="${address.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>
                            <a href="<%=path%>/address?action=update&id=${address.id }" >修改</a> &nbsp;&nbsp;
                            <a href="<%=path%>/address?action=deleteSubmit&id=${address.id }"  onclick="return confirm('确定要删除此地址吗？')">删除</a> &nbsp;&nbsp;
                            <c:if test="${address.isDefault }">默认地址</c:if>
                            <c:if test="${not address.isDefault }">
                                <a href="<%=path%>/address?action=setDefaultSubmit&id=${address.id }">设为默认地址</a>
                            </c:if>
                        </td>
                    </tr> 
                </c:forEach>
            </tbody>
         </table>
    </div>

    <%@include file="/footer.jsp"%>
</body>
</html>