<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改收货地址</title>
<link type="text/css" rel="stylesheet" href="<%=path%>/css/style.css" />
<script type="text/javascript" src="<%=path%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/tools.js"></script>
</head>
<body>
    <%@include file="/header.jsp"%>

    <div class="main" style="width: 777px;">

        <div class="message">
            <p></p>
            <span>${message }</span>
        </div>
        
        <div class="address-add">
            <h5>修改收货地址：</h5>
            <form action="<%=path%>/address" method="post">
               <input type="hidden" name="action" value="updateSubmit" />
               <input type="hidden" name="id" value="${address.id }" />
                  <!-- 收货人 -->
                  <div class="text-input">
                     <label for="consignee">收货人 *</label>
                     <input id="consignee" name="consignee"  type="text"  value="${address.consignee }" />
                     <span id="consigneeTip" ></span>
                  </div>
            
                  <!-- 手机号 -->
                  <div class="text-input">
                     <label  for="phone">手机号*</label>
                     <input id="phone" name="phone" type="text"   value="${address.phone }" />
                     <span id="phoneTip" ></span>
                  </div>
            
                  <!-- 详细位置 -->
                  <div class="text-input">
                     <label for="location">详细位置 *</label>
                     <input id="location" name="location" type="text"  size="40"  value="${address.location }"  />
                     <span id="locationTip"></span>
                  </div>
                  
                  <p>
                     <!-- 如果是默认收货地址，则不可取消，要不然就没有默认收货地址了，如果本来不是默认收货地址，则可选中 -->
                     <input  id="isDefault" name="isDefault" type="checkbox"  value="yes"
                            <c:if test="${address.isDefault}">
                                checked="checked"  disabled="disabled"
                            </c:if>                     
                     />
                     <label for="isDefault">设为默认地址</label>
                  </p>
                  
                  <br/><br/>
                  <input type="submit"  value="   保   存   " />
          </form>
        </div>
    </div>

    <%@include file="/footer.jsp"%>
</body>
</html>