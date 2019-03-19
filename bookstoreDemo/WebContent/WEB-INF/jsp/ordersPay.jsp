<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>支付页面</title>
<link type="text/css" rel="stylesheet" href="<%=path%>/css/style.css" />
</head>
<body>
   <%@include file="/header.jsp"%>

   <div class="main" style="width: 900px;">
        <div class="message">
            <p>支付页面（现阶段不做）</p>
            <span>${message }</span>
        </div>
   </div>

   <%@include file="/footer.jsp"%>
</body>
</html>