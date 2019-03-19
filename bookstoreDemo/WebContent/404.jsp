<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  isErrorPage="true"%>
<%String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>页面找不到了</title>
<link type="text/css" rel="stylesheet" href="<%=path%>/css/style.css" />
<script type="text/javascript" src="<%=path%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/tools.js"></script>
</head>
<body>
   <%@include file="/header.jsp"%>
 
   <div class="main" style="width: 900px;">
      <div class="message">
            <p>Sorry，您访问的资源不存在</p>
            <span>${message }</span>
        </div>
   </div>

   <%@include file="/footer.jsp"%>
</body>
</html>