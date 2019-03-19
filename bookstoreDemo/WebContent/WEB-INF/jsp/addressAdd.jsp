<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath(); %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加收货地址</title>
<link type="text/css" rel="stylesheet" href="<%=path%>/css/style.css" />
<script type="text/javascript" src="<%=path%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/tools.js"></script>
<script type="text/javascript">
     $(function(){
         //先是很多失去焦点的事件
         $("#consignee").blur(function(){
             checkNotEmpty("consignee", "consigneeTip");
         });
         $("#phone").blur(function(){
             checkPhone("phone", "phoneTip")
         });
         $("#location").blur(function(){
             checkNotEmpty("location", "locationTip");
         });
         
         //然后表单提交前进行综合验证
         $("#addForm").submit(function(){
             var r1 = checkNotEmpty("consignee", "consigneeTip");
             var r2 = checkPhone("phone", "phoneTip")
             var r3 = checkNotEmpty("location", "locationTip");
             
             return r1 && r2 && r3;
         });
         
     });
</script>
</head>
<body>
    <%@include file="/header.jsp"%>
    <div class="main" style="width: 777px;">

        <div class="message">
            <p>添加收货地址</p>
            <span>${message }</span>
        </div>
        
         <div class="address-add">
            <form id="addForm"  action="<%=path%>/address" method="post">
               <input type="hidden" name="action" value="addSubmit" />
                  <!-- 收货人 -->
                  <div class="text-input">
                     <label for="consignee">收货人 *</label>
                     <input id="consignee" name="consignee"  type="text" value="${param.consignee }" />
                     <span id="consigneeTip" ></span>
                  </div>
            
                  <!-- 手机号 -->
                  <div class="text-input">
                     <label  for="phone">手机号*</label>
                     <input id="phone" name="phone" type="text"  value="${param.phone }" />
                     <span id="phoneTip" ></span>
                  </div>
            
                  <!-- 详细位置 -->
                  <div class="text-input">
                     <label for="location">详细位置 *</label>
                     <input id="location" name="location" type="text"  size="40" value="${param.location }"  />
                     <span id="locationTip"></span>
                  </div>
                  
                  <p>
                     <input  id="isDefault" name="isDefault" type="checkbox"  value="yes"
                           <c:if  test="${param.isDefault == 'yes' }">checked="checked"</c:if>   
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